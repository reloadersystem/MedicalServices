package com.salud.medicalservices.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.salud.medicalservices.R;
import com.salud.medicalservices.entidades.ItemProductos;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerServicioAdapter extends RecyclerView.Adapter<RecyclerServicioAdapter.MyViewHolder>{

    Context context;
    ArrayList<ItemProductos> list_pedido;

    public RecyclerServicioAdapter(Context context, ArrayList<ItemProductos> list_pedido) {

        this.context = context;
        this.list_pedido = list_pedido;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(context).inflate(R.layout.item_servicio, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        ItemProductos pedido = list_pedido.get(position);

        holder.tv_nombre_servicio.setText(pedido.getNombre_comercial());
        holder.tv_precio_servicio.setText(pedido.getPrecio());
    }

    @Override
    public int getItemCount() {
        return list_pedido.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_nombre_servicio;
        private TextView tv_precio_servicio;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_nombre_servicio = itemView.findViewById(R.id.tv_nombre_servicio);
            tv_precio_servicio = itemView.findViewById(R.id.tv_precio_servicio);

        }
    }
}
