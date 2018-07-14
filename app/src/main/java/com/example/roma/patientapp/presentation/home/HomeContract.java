package com.example.roma.patientapp.presentation.home;

import com.example.roma.patientapp.data.model.search_doctor.Doctor;

import java.util.ArrayList;

/**
 * Created by Romisaa on 6/15/2018.
 */

public interface HomeContract {

    interface View {
        void loadData(ArrayList<Doctor> doctors);
    }

    interface Presenter {
        void getAllSpecialities();

        void getALlRegions();

        void navigateToEditProfileFragment();

        void navigateToAppointmentsFragment();

        void navigateToSettingsFragment();

        void navigateToLogOutFragment();

        void navigateToAboutFragment();

        void searchDoctor(String value);
    }
}
