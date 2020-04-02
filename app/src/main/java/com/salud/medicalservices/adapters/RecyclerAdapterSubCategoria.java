package com.salud.medicalservices.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.salud.medicalservices.R;
import com.salud.medicalservices.entidades.SubCategorias;
import com.salud.medicalservices.entidades.SubCategorias;
import com.salud.medicalservices.interfaces.OnSubCategoriasListener;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerAdapterSubCategoria extends RecyclerView.Adapter<RecyclerAdapterSubCategoria.MyViewHolder> {

    private Context mContext;
    private List<SubCategorias> mlistCategorias;
    OnSubCategoriasListener onSubCategoriasListener;

    public void setOnSubCategoriasListener(OnSubCategoriasListener onSubCategoriasListener) {
        this.onSubCategoriasListener = onSubCategoriasListener;
    }

    public RecyclerAdapterSubCategoria(Context mContext, List<SubCategorias> mItemCategorias) {
        this.mContext = mContext;
        this.mlistCategorias = mItemCategorias;
    }

    @NonNull
    @Override
    public RecyclerAdapterSubCategoria.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.item_servicios, viewGroup, false);
        return new RecyclerAdapterSubCategoria.MyViewHolder(view, onSubCategoriasListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapterSubCategoria.MyViewHolder holder, int position) {
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

        public MyViewHolder(@NonNull View itemView, final OnSubCategoriasListener onSubCategoriasListener) {
            super(itemView);

            img_recursoproducto = itemView.findViewById(R.id.img_recursoproducto);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            ln_categoria = itemView.findViewById(R.id.ln_categoria);

            ln_categoria.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int posicion = getAdapterPosition();
                    onSubCategoriasListener.onImagenClicked(posicion);

                }
            });
        }
    }
}
