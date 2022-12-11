package com.app.oceanapp;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.app.oceanapp.constantes.Utils;
import com.app.oceanapp.entity.Foto;
import com.app.oceanapp.entity.RegisterResponse;
import com.app.oceanapp.repositories.local.usuario.SessionManagement;
import com.app.oceanapp.repositories.remote.ServiceFactory;
import com.app.oceanapp.repositories.remote.request.FotoService;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegistrarFotoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegistrarFotoFragment extends Fragment implements AdapterView.OnItemSelectedListener  {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RegistrarFotoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegistrarFotoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegistrarFotoFragment newInstance(String param1, String param2) {
        RegistrarFotoFragment fragment = new RegistrarFotoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private ListView listViewGaleriaFoto;
    String rutaImagen = "";
    SessionManagement sessionManagement;
    ProgressDialog progressDialog;
    AdapterListViewGaleriaFoto adapter;
    List<Foto> listFoto;
    String imageUploadMethod = "";
    String path = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_registrar_foto, container, false);

        sessionManagement = new SessionManagement(getContext());

        listViewGaleriaFoto = v.findViewById(R.id.listGaleriaFotos);

        Button btnNuevaFoto = v.findViewById(R.id.btnProcesarFoto);
        Button btnAdjuntarFoto = v.findViewById(R.id.btnAdjuntarFoto);


        listFoto = new ArrayList<Foto>();
        adapter = new AdapterListViewGaleriaFoto(getContext(),listFoto);


        FotoService jsonPlaceHolderApi = ServiceFactory.retrofit.create(FotoService.class);
        Call<List<Foto>> call = jsonPlaceHolderApi.getGaleriaFotos(sessionManagement.getLanceIdSession());
        call.enqueue(new Callback<List<Foto>>() {
            public void onResponse(Call<List<Foto>> call, Response<List<Foto>> response) {

                if(!response.isSuccessful()){
                    Toast.makeText(getActivity(),"OCURRIO UN ERROR EN EL SERVIDOR",Toast.LENGTH_LONG).show();
                }
                else{
                    for(Foto foto: response.body()){
                        listFoto.add(new Foto(foto.getId(), foto.getUrl()));
                    }
                }

                listViewGaleriaFoto.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Foto>> call, Throwable t) {
                Toast.makeText(getActivity(),"ERROR: "+t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

        btnNuevaFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageUploadMethod = Utils.upload_camara;
                openCamara();
            }
        });

        btnAdjuntarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ContextCompat.checkSelfPermission(getContext().getApplicationContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                    imageUploadMethod = Utils.upload_attach;
                    Intent iGallery = new Intent();
                    iGallery.setType("image/*");
                    iGallery.setAction(Intent.ACTION_GET_CONTENT);

                    startActivityForResult(iGallery, 1);
                } else {
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                }
            }
        });

        return v;
    }

    private void openCamara(){
        Intent intent  = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //if(intent.resolveActivity(getPackageManager()) != null){
        File imagenArchivo = null;
        try{
            imagenArchivo = crearImagen();
        }catch (IOException ex){
            Log.e("Error",ex.toString());
        }

        if(imagenArchivo != null){
            Uri fotoUri = FileProvider.getUriForFile(getContext(),"com.app.oceanapp.fileprovider",imagenArchivo);
            intent.putExtra(MediaStore.EXTRA_OUTPUT,fotoUri);
            startActivityForResult(intent,1);
        }
        //}
    }

    public void onActivityResult(int requestCode,int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == -1) {
            showProgressDialog();
            File file = null;

            if(imageUploadMethod.equals(Utils.upload_camara)) {
                file = new File(rutaImagen);
            } else {
                Uri selectedImageUri = data.getData();

                Bitmap thumbnail = null;
                try {
                    thumbnail
                            = MediaStore.Images.Media.getBitmap(
                            getActivity().getContentResolver(),
                            selectedImageUri);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }

                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
                File destination = new File(Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES),"test.jpg");
                FileOutputStream fo;
                try {
                    fo = new FileOutputStream(destination);
                    fo.write(bytes.toByteArray());
                    fo.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                file = new File(destination.getAbsolutePath());
            }

            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part image = MultipartBody.Part.createFormData("image", file.getName(), requestFile);
            RequestBody lance_id = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(sessionManagement.getLanceIdSession()));
            RequestBody user_id = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(sessionManagement.getSession()));


            FotoService jsonPlaceHolderApi = ServiceFactory.retrofit.create(FotoService.class);
            Call<RegisterResponse> call = jsonPlaceHolderApi.cargarFoto(image,lance_id,user_id);

            call.enqueue(new Callback<RegisterResponse>() {
                @Override
                public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                    if(!response.isSuccessful()){
                        Toast.makeText(getActivity(),"NO SE PUDO CARGAR LA IMAGEN",Toast.LENGTH_LONG).show();
                    }else{
                        RegisterResponse res = response.body();

                        if(res.getCode() == 1){
                            listFoto.add(res.getData());
                            adapter.notifyDataSetChanged();
                            Toast.makeText(getActivity(),"SE CARGO LA IMAGEN CORRECTAMENTE",Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(getActivity(),"NO SE PUDO CARGAR LA IMAGEN",Toast.LENGTH_LONG).show();
                        }
                    }
                    hideProgressDialog();
                }

                @Override
                public void onFailure(Call<RegisterResponse> call_2, Throwable t) {
                    hideProgressDialog();
                    //Toast.makeText(getContext(),"ERROR: "+t.getMessage(),Toast.LENGTH_LONG).show();
                    Toast.makeText(getActivity(),"NO SE PUDO CARGAR LA IMAGEN",Toast.LENGTH_LONG).show();
                }
            });
        }else{
            rutaImagen = "";
        }
    }

    public static File bitmapToFile(Context context, Bitmap bitmap, String fileNameToSave) { // File name like "image.png"
        //create a file to write bitmap data
        File file = null;
        try {
            file = new File(Environment.getExternalStorageDirectory() + File.separator + fileNameToSave);
            file.createNewFile();

//Convert bitmap to byte array
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0 , bos); // YOU can also save it in JPEG
            byte[] bitmapdata = bos.toByteArray();

//write the bytes in file
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
            return file;
        }catch (Exception e){
            e.printStackTrace();
            return file; // it will return null
        }
    }

    private File crearImagen() throws IOException {
        String nombreImagen = "foto_";
        File directorio = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imagen = File.createTempFile(nombreImagen,".jpg",directorio);

        rutaImagen = imagen.getAbsolutePath();
        return imagen;
    }


    private void showProgressDialog(){
        progressDialog = new ProgressDialog(getContext());

        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(
                R.color.transparent
        );
        progressDialog.setCanceledOnTouchOutside(false);
    }

    private void hideProgressDialog(){
        progressDialog.dismiss();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}