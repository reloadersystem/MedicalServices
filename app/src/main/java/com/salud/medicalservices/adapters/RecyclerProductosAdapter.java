package com.salud.medicalservices.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.salud.medicalservices.R;
import com.salud.medicalservices.entidades.Categorias;
import com.salud.medicalservices.interfaces.OnProductosListener;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerProductosAdapter extends RecyclerView.Adapter<RecyclerProductosAdapter.MyViewHolder> {

    private Context mContext;
    private List<Categorias> mlistCategorias;
    OnProductosListener onProductosListener;

    public void setOnProductosListener(OnProductosListener onProductosListener) {
        this.onProductosListener = onProductosListener;
    }

    public RecyclerProductosAdapter(Context mContext, List<Categorias> mItemCategorias) {
        this.mContext = mContext;
        this.mlistCategorias = mItemCategorias;
    }

    @NonNull
    @Override
    public RecyclerProductosAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.item_servicios, viewGroup, false);
        return new RecyclerProductosAdapter.MyViewHolder(view, onProductosListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerProductosAdapter.MyViewHolder holder, int position) {
        holder.tvDescription.setText(mlistCategorias.get(position).getName());
       // holder.img_recursoproducto.setImageResource(mlistCategorias.get(position).getIcon());
    }

    @Override
    public int getItemCount() {
        return mlistCategorias.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView img_recursoproducto;
        private TextView tvDescription;
        private LinearLayout ln_categoria;


        public MyViewHolder(@NonNull View itemView, final OnProductosListener onProductosListener) {
            super(itemView);

            img_recursoproducto = itemView.findViewById(R.id.img_recursoproducto);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            ln_categoria = itemView.findViewById(R.id.ln_categoria);

            ln_categoria.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int posicion = getAdapterPosition();
                    onProductosListener.onImagenClicked(posicion);

                }
            });
        }
    }
}
