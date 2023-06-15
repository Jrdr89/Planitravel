package com.example.planitravelv2.Adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.planitravelv2.Entidades.Actividad;
import com.example.planitravelv2.R;

import java.util.ArrayList;
import java.util.List;

public class ActividadAdapter extends RecyclerView.Adapter<ActividadAdapter.ActividadViewHolder> {

    private List<Actividad> actividadList;

    public ActividadAdapter(List<Actividad> actividadList) {
        this.actividadList = actividadList;
    }

    public void setActividadList(List<Actividad> actividadList) {
        this.actividadList = actividadList;
    }

    @NonNull
    @Override
    public ActividadViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_plan, parent, false);
        return new ActividadViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActividadViewHolder holder, int position) {
        Actividad actividad = actividadList.get(position);
        holder.bind(actividad);
    }

    @Override
    public int getItemCount() {
        return actividadList.size();
    }



    public class ActividadViewHolder extends RecyclerView.ViewHolder {

        private TextView momentoDiaTextView;
        private EditText descripcionEditText;
        private EditText notasEditText;
        private Button mapsButton;

        public ActividadViewHolder(@NonNull View itemView) {
            super(itemView);
            momentoDiaTextView = itemView.findViewById(R.id.momento_dia_textView);
            descripcionEditText = itemView.findViewById(R.id.descripcion_editText);
            notasEditText = itemView.findViewById(R.id.notas_editText);
            mapsButton = itemView.findViewById(R.id.maps_button);
        }

        public void bind(Actividad actividad) {
            momentoDiaTextView.setText(actividad.getMomentoDia());
            descripcionEditText.setText(actividad.getDescripcion());
            notasEditText.setText(actividad.getNotas());

            // Aquí puedes agregar la lógica para el botón de "Ver en Google Maps"
            // Por ejemplo, puedes configurar un clic en el botón para abrir la ubicación en Google Maps.
            mapsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Lógica para abrir la ubicación en Google Maps
                }
            });
        }
    }
}
