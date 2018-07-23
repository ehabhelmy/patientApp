package com.example.roma.patientapp.presentation.home;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.roma.patientapp.R;
import com.example.roma.patientapp.data.model.specialities.Speciality;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SpecialitiesAdapter extends RecyclerView.Adapter<SpecialitiesAdapter.ViewHolder> {

    private List<Speciality> specialities;
    private OnSpecialityClick onSpecialityClick;

    public void setOnSpecialityClick(OnSpecialityClick onSpecialityClick) {
        this.onSpecialityClick = onSpecialityClick;
    }

    public interface OnSpecialityClick {
        void onSpecialityClick(Speciality speciality);
    }

    public void setSpecialities(List<Speciality> specialities) {
        this.specialities = specialities;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_speciality, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Speciality speciality = specialities.get(position);
        holder.specialityName.setText(speciality.getName());
        holder.specialityContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSpecialityClick.onSpecialityClick(speciality);
            }
        });
    }

    @Override
    public int getItemCount() {
        return specialities != null ? specialities.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.speciality_name)
        TextView specialityName;

        @BindView(R.id.speciality_container)
        CardView specialityContainer;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
