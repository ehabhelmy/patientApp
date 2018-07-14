package com.example.roma.patientapp.presentation.home.book_appointment.adapter;

import com.example.roma.patientapp.presentation.doctor_details.adapter.DoctorDetailsModel;

import java.io.Serializable;

/**
 * Created by Romisaa on 6/29/2018.
 */
public class AppointmentRequestDetailModel {

    private String requestId;
    private String time;
    private String name;
    private String speciality;

    public AppointmentRequestDetailModel(String requestId, String time, String name, String speciality) {
        this.requestId = requestId;
        this.time = time;
        this.name = name;
        this.speciality = speciality;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }
}
