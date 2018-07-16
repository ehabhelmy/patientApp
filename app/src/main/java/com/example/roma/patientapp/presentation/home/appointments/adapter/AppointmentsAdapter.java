package com.example.roma.patientapp.presentation.home.appointments.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.roma.patientapp.R;
import com.example.roma.patientapp.presentation.home.book_appointment.adapter.AppointmentRequestDetailModel;

import java.util.ArrayList;

/**
 * Created by Romisaa on 6/29/2018.
 */
public class AppointmentsAdapter extends RecyclerView.Adapter<AppointmentsAdapter.AppointmentViewHolder> {

    private ArrayList<AppointmentRequestDetailModel> list;

    public AppointmentsAdapter() {
        this.list = new ArrayList<>();
    }

    @NonNull
    @Override
    public AppointmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.appointment_request_details_row, parent, false);

        return new AppointmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public void updateData(ArrayList<AppointmentRequestDetailModel> data) {
        list.clear();
        if (data != null) {
            list.addAll(data);
        }
        notifyDataSetChanged();
    }

    class AppointmentViewHolder extends RecyclerView.ViewHolder {

        private ImageView photo;
        private TextView time;
        private TextView name;
        private TextView speciality;

        public AppointmentViewHolder(View view) {
            super(view);
            photo = (ImageView) view.findViewById(R.id.photo_iv);
            time = (TextView) view.findViewById(R.id.time_tv);
            name = (TextView) view.findViewById(R.id.name_tv);
            speciality = (TextView) view.findViewById(R.id.speciality_tv);
        }

        public void bind(AppointmentRequestDetailModel model) {
//            time.setText(model.getTime());
            name.setText(model.getName());
            speciality.setText(model.getSpeciality());
        }
    }
}
