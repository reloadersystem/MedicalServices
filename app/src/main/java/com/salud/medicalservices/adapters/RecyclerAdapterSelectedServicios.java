package com.salud.medicalservices.adapters;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.salud.medicalservices.R;
import com.salud.medicalservices.entidades.EntitySelectedServicios;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerAdapterSelectedServicios extends RecyclerView.Adapter<RecyclerAdapterSelectedServicios.MyViewHolder> {

    private Context mContext;
    private List<EntitySelectedServicios> mServiciosSelectedList;

    public RecyclerAdapterSelectedServicios(Context mContext, List<EntitySelectedServicios> mServiciosSelectedList) {
        this.mContext = mContext;
        this.mServiciosSelectedList = mServiciosSelectedList;
    }

    @NonNull
    @Override
    public RecyclerAdapterSelectedServicios.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.item_servicios_seleccionados, parent, false);
        return new RecyclerAdapterSelectedServicios.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapterSelectedServicios.MyViewHolder holder, int position) {


        holder.img_producto.setImageResource(mServiciosSelectedList.get(position).getImagen_logo());
        holder.txt_comercial.setText(mServiciosSelectedList.get(position).getNombre_comercial());
        holder.txt_generico.setText(mServiciosSelectedList.get(position).getNombre_generico());
        holder.txt_presentacion.setText(mServiciosSelectedList.get(position).getNombre_presentacion());
        holder.txt_laboratorio.setText(mServiciosSelectedList.get(position).getNombre_laboratorio());
        holder.txt_laboratorio.setText(mServiciosSelectedList.get(position).getNombre_laboratorio());
        holder.txt_precio.setText(mServiciosSelectedList.get(position).getPrecio());
        holder.txt_subTotal.setText(mServiciosSelectedList.get(position).getSubtotal());
        holder.txt_unidades.setText(mServiciosSelectedList.get(position).getUnidades());
    }

    @Override
    public int getItemCount() {
        return mServiciosSelectedList.size();
    }

    public void UpdateClear() {
        mServiciosSelectedList.clear();
        notifyDataSetChanged();
    }

    public void RemoveItem( int position)
    {
        mServiciosSelectedList.remove(position);
        notifyItemRemoved(position);
    }

    public  void restoreItem(EntitySelectedServicios item , int  position)
    {
        mServiciosSelectedList.add(position, item);
        notifyItemInserted(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView img_producto;
        private TextView txt_comercial;
        private TextView txt_generico;
        private TextView txt_presentacion;
        private TextView txt_laboratorio;
        private TextView txt_subTotal;
        private TextView txt_precio;
        private TextView txt_unidades;

        public RelativeLayout viewBackground;
        public LinearLayout viewForeground;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img_producto = itemView.findViewById(R.id.img_producto);
            txt_comercial = itemView.findViewById(R.id.txt_comercial);
            txt_generico = itemView.findViewById(R.id.txt_generico);
            txt_presentacion = itemView.findViewById(R.id.txt_presentacion);
            txt_laboratorio = itemView.findViewById(R.id.txt_laboratorio);
            txt_precio = itemView.findViewById(R.id.txt_precioxunidad);
            txt_subTotal = itemView.findViewById(R.id.txt_subTotal);
            txt_unidades = itemView.findViewById(R.id.txt_unidades);

            viewBackground= itemView.findViewById(R.id.view_background);
            viewForeground= itemView.findViewById(R.id.view_foreground);

//            ln_producto.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    int posicion = getAdapterPosition();
//                    onProductosDetalleListener.onImagenClicked(posicion);
//
//                }
//            });
        }
    }
}
