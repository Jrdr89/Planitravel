package com.example.planitravelv2.Adaptadores;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.planitravelv2.Entidades.Actividad;
import com.example.planitravelv2.Entidades.Holder;
import com.example.planitravelv2.R;
import com.example.planitravelv2.SQlite.DatabaseHelper;
import com.example.planitravelv2.Vistas.PlanActivity;

import java.util.ArrayList;
import java.util.List;

public class ActividadAdapter extends RecyclerView.Adapter<ActividadAdapter.ActividadViewHolder> {

    private List<Actividad> actividadList;
    private Context context;

    public ActividadAdapter(List<Actividad> actividadList, Context context) {
        this.actividadList = actividadList;
        this.context = context;
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

        holder.icon_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.eliminarRegistro(actividad);
            }
        });

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
        private ImageView icon_check;

        public ActividadViewHolder(@NonNull View itemView) {
            super(itemView);
            momentoDiaTextView = itemView.findViewById(R.id.momento_dia_textView);
            descripcionEditText = itemView.findViewById(R.id.descripcion_editText);
            notasEditText = itemView.findViewById(R.id.notas_editText);
            mapsButton = itemView.findViewById(R.id.maps_button);
            icon_check = itemView.findViewById(R.id.check_imageView);
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
        private void eliminarRegistro(Actividad actividad) {
            // Obtener el objeto Actividad correspondiente utilizando la posición o identificador único
            Holder.setActividad(null);
            Holder.setActividad(actividad);
            // Iniciar la actividad PlanActivity con datos extras
            Intent intent = new Intent(context, PlanActivity.class);
            intent.putExtra("actualizacionRecyclerView", true); // Agregar dato extra con clave "actualizacionRecyclerView"
            context.startActivity(intent);
        }


    }


}
