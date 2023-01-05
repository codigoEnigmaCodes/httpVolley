package com.codigoEnigma.consumohttpvolley;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class adaptador extends RecyclerView.Adapter<adaptador.ViewHolder> implements View.OnClickListener{

    public List<ModeloAuto> listaData;
    private View.OnClickListener listener;

public void setOnClickListener(View.OnClickListener listenerr){
    this.listener = listenerr;
}

    @Override
    public void onClick(View view) {
    if (listener!=null){
        listener.onClick(view);
    }

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvMatricula, tvMarca, tvModelo, tvColor, tvAnio, tvCombustible, tvRenta;


        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            tvMatricula = (TextView) view.findViewById(R.id.tv_matricula);
            tvMarca = (TextView) view.findViewById(R.id.tv_marca);
            tvModelo = (TextView) view.findViewById(R.id.tv_mdoelo);
            tvColor = (TextView) view.findViewById(R.id.tv_color);
            tvAnio = (TextView) view.findViewById(R.id.tv_anio);
            tvCombustible = (TextView) view.findViewById(R.id.tv_combustible);
            tvRenta = (TextView) view.findViewById(R.id.tv_renta);

        }

    }

    public adaptador(List<ModeloAuto> modeloP) {
        this.listaData = modeloP;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adaptador_lista, parent, false);
        vista.setOnClickListener(this);
        return new ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull adaptador.ViewHolder holder, int position) {
        holder.tvMatricula.setText(listaData.get(position).getMatricula());
        holder.tvMarca.setText(listaData.get(position).getMarca());
        holder.tvModelo.setText(listaData.get(position).getModelo());
        holder.tvColor.setText(listaData.get(position).getColor());
        holder.tvAnio.setText(listaData.get(position).getAnio());
        holder.tvCombustible.setText(listaData.get(position).getCombustible());
        holder.tvRenta.setText(listaData.get(position).getRenta());
    }

    @Override
    public int getItemCount() {
        return listaData.size();
    }
}
