package com.example.planitravelv2.Adaptadores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.planitravelv2.Entidades.DiaViaje;
import com.example.planitravelv2.PruebaActivity;
import com.example.planitravelv2.R;
import com.example.planitravelv2.Vistas.PlanActivity;

import java.util.List;

public class DiaViajeAdapter extends RecyclerView.Adapter<DiaViajeAdapter.DiaViajeViewHolder> {
    private List<DiaViaje> diaViajeList;
    private Context context;

    public DiaViajeAdapter(List<DiaViaje> diaViajeList, Context context) {
        this.diaViajeList = diaViajeList;
        this.context= context;
    }

    @NonNull
    @Override
    public DiaViajeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_dias, parent, false);
        return new DiaViajeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DiaViajeViewHolder holder, int position) {
        DiaViaje diaViaje = diaViajeList.get(position);
        holder.dayTextView.setText(diaViaje.getDia().toString());
        holder.descriptionEditText.setText(diaViaje.getDescripcion());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PlanActivity.class);
                intent.putExtra("diaViaje",diaViaje);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return diaViajeList.size();
    }

    public class DiaViajeViewHolder extends RecyclerView.ViewHolder {
        public TextView dayTextView;
        public EditText descriptionEditText;

        public DiaViajeViewHolder(View itemView) {
            super(itemView);
            dayTextView = itemView.findViewById(R.id.day_textView);
            descriptionEditText = itemView.findViewById(R.id.description_editText);
        }
    }
}
