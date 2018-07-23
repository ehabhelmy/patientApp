package com.example.roma.patientapp.presentation.home.search_doctor;

import com.example.roma.patientapp.data.model.search_doctor.Doctor;
import com.example.roma.patientapp.presentation.base.BaseView;

import java.util.ArrayList;

public interface SearchDoctorContract {

    interface View extends BaseView{
        void loadData(ArrayList<Doctor> doctors);
    }

    interface Presenter {
        void searchDoctor(String value,String speciality);
    }
}
