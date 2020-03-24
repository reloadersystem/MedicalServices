package com.salud.medicalservices.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.salud.medicalservices.R;
import com.salud.medicalservices.entidades.ItemProductos;
import com.salud.medicalservices.entidades.ItemProductos;
import com.salud.medicalservices.interfaces.OnProductosDetalleListener;
import com.salud.medicalservices.interfaces.OnProductosListener;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerDetalleProductosAdapter  extends RecyclerView.Adapter<RecyclerDetalleProductosAdapter.MyViewHolder> {

    private Context mContext;
    private List<ItemProductos> mlistCursos;
    OnProductosDetalleListener onProductosDetalleListener;

    public void setOnProductosDetalleListener(OnProductosDetalleListener onProductosDetalleListener) {
        this.onProductosDetalleListener = onProductosDetalleListener;
    }
    public RecyclerDetalleProductosAdapter(Context mContext, List<ItemProductos> mlistProductos) {
        this.mContext = mContext;
        this.mlistCursos = mlistProductos;
    }

    @NonNull
    @Override
    public RecyclerDetalleProductosAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.item_detalle_producto, viewGroup, false);
        return new RecyclerDetalleProductosAdapter.MyViewHolder(view, onProductosDetalleListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerDetalleProductosAdapter.MyViewHolder holder, int position) {

        holder.img_producto.setImageResource(mlistCursos.get(position).getImagen_logo());
        holder.txt_comercial.setText(mlistCursos.get(position).getNombre_comercial());
        holder.txt_generico.setText(mlistCursos.get(position).getNombre_generico());
        holder.txt_presentacion.setText(mlistCursos.get(position).getNombre_presentacion());
        holder.txt_laboratorio.setText(mlistCursos.get(position).getNombre_laboratorio());
        holder.txt_precio.setText(mlistCursos.get(position).getPrecio());
    }

    @Override
    public int getItemCount() {
        return mlistCursos.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView img_producto;
        private TextView txt_comercial;
        private TextView txt_generico;
        private TextView txt_presentacion;
        private TextView txt_laboratorio;
        private LinearLayout ln_producto;
        private TextView txt_precio;

        public MyViewHolder(@NonNull View itemView, final OnProductosDetalleListener onProductosDetalleListener) {
            super(itemView);

            img_producto = itemView.findViewById(R.id.img_producto);
            ln_producto = itemView.findViewById(R.id.ln_producto);
            txt_comercial = itemView.findViewById(R.id.txt_comercial);
            txt_generico = itemView.findViewById(R.id.txt_generico);
            txt_presentacion = itemView.findViewById(R.id.txt_presentacion);
            txt_laboratorio = itemView.findViewById(R.id.txt_laboratorio);
            txt_precio = itemView.findViewById(R.id.txt_precio);

            img_producto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int posicion = getAdapterPosition();
                    onProductosDetalleListener.onImagenClicked(posicion);

                }
            });
        }
    }
}
