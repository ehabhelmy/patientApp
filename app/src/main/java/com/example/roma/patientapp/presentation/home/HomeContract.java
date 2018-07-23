package com.example.roma.patientapp.presentation.home;

import com.example.roma.patientapp.data.model.search_doctor.Doctor;
import com.example.roma.patientapp.data.model.specialities.SpecialitiesResponse;
import com.example.roma.patientapp.data.model.specialities.Speciality;
import com.example.roma.patientapp.presentation.base.BaseView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Romisaa on 6/15/2018.
 */

public interface HomeContract {

    interface View extends BaseView{
        void loadData(List<Speciality> specialities);
        void navigateToAuth();
    }

    interface Presenter {
        void getAllSpecialities();

        void getALlRegions();

        void navigateToSearchFragment(Speciality speciality);

        void navigateToEditProfileFragment();

        void navigateToAppointmentsFragment();

        void navigateToSettingsFragment();

        void navigateToLogOutFragment();

        void navigateToAboutFragment();
        }
}
