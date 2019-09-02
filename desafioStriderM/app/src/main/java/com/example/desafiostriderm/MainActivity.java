package com.example.desafiostriderm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity{

    private ListView listView;
    private List<Tarefa> tarefasArrayList;
    private Button btnFazer;
    ListAdapter arrayAdapter;
    public static final int TIRAR_FOTO = 8765239;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);
        preencherDados();
        verificarPermissoes();
//        new Timer().scheduleAtFixedRate(new TimerTask(){
//            @Override
//            public void run(){
//                preencherDados();
//            }
//        },0,5000);

        Button btnAtualizar = (Button) findViewById(R.id.btnAtualizar);
        btnAtualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preencherDados();
            }
        });

    }

    private void verificarPermissoes() {
        if(ActivityCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.CAMERA
            }, 1);

        }
        if(ActivityCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE
            }, 1);

        }
        if(ActivityCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, 1);
        }
    }

    private void preencherDados() {

        final List<Tarefa>[] arraytarefas = new List[1];

        String url = "http://localhost:3333/tarefaPendente";

        RequestQueue requestQueue;

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, new JSONArray(),
                new Response.Listener<JSONArray>() {
                    private Button btnFazer;
                    @Override
                    public void onResponse(JSONArray response) {
                        Type listType = new TypeToken<List<Tarefa>>(){}.getType();
                        arraytarefas[0] = new Gson().fromJson(response.toString(), listType);
                        Log.i("response",response.toString());
                                arrayAdapter = new ListAdapter(MainActivity.this, getApplicationContext(), arraytarefas[0]);
                                listView.setAdapter(arrayAdapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("ErroWEB",error.getMessage());
                    }
                });
        requestQueue.add(jsonArrayRequest);
    }


    public void btnFazerClick(View view) {
        Log.i("Clicke","Ola mundo");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("asd","---");
        if(requestCode == TIRAR_FOTO ){
            if(resultCode == Activity.RESULT_OK){
                if(arrayAdapter != null){
                    Log.i("asdasdfa", arrayAdapter.idTarefa+"");
                }
                Bundle bundle = data.getExtras();
                Bitmap imagemTirada = (Bitmap) bundle.get("data");
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                imagemTirada.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                byte[] imgInBytes = outputStream.toByteArray();
                String b64Encoded = Base64.encodeToString(imgInBytes, Base64.DEFAULT);
                // Enviar a string da imagem para o servidor

                String url = "http://localhost:3333/t/"+arrayAdapter.idTarefa;

                JSONObject jsonObject = new JSONObject();

                try {
                    jsonObject.put("img64",b64Encoded );
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                RequestQueue requestQueue;

                requestQueue = Volley.newRequestQueue(getApplicationContext());

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                        new Response.Listener<JSONObject>() {
                            private Button btnFazer;
                            @Override
                            public void onResponse(JSONObject response) {
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.i("ErroWEB",error.getMessage());
                            }
                        });
                requestQueue.add(jsonObjectRequest);


            }
        }

        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setTitle("Enviando dados");
        progressDialog.setMessage("Aguarde...");
        progressDialog.show();
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(10000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                progressDialog.dismiss();
            }
        }).start();

    }

    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
}
