package com.example.desafiostriderm;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.util.List;

import static androidx.core.app.ActivityCompat.startActivityForResult;
import static androidx.core.content.ContextCompat.startActivities;

public class ListAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<Tarefa> itens;
    private TextView tvDescricao;
    private Button btnFazer;
    private Context context;
    private Activity activity;
    public String idTarefa = "";
    public ListAdapter(Activity activity, Context context, List<Tarefa> itens) {
        this.itens = itens;
        this.context = context;
        this.activity = activity;
        //Objeto responsável por pegar o Layout do item.
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return itens.size();
    }

    @Override
    public Object getItem(int i) {
        return itens.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if(view == null){
            view = mInflater.inflate(R.layout.item_layout, null);
        }
        tvDescricao = view.findViewById(R.id.tvDescricao);
//        tvDescricao.setText(itens.get(i).getDescricao());
        tvDescricao.setText(itens.get(i).getDescricao());
        btnFazer = (Button) view.findViewById(R.id.buttonFazer);

        btnFazer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                File file = new File(Environment.getExternalStorageDirectory() + "/foto"+i+".jpg");
//                Uri outputUri = Uri.fromFile(file);
                idTarefa = itens.get(i).getId();
                itens.remove(i);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                startActivityForResult(activity, intent, MainActivity.TIRAR_FOTO, null);
            }
        });

        return view;
    }


}
