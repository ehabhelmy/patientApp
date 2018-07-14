package com.example.roma.patientapp.presentation.home;

import com.example.roma.patientapp.data.model.regions.RegionResponse;
import com.example.roma.patientapp.data.model.search_doctor.Doctor;
import com.example.roma.patientapp.data.model.search_doctor.SearchDoctorResponse;
import com.example.roma.patientapp.data.model.specialities.SpecialitiesResponse;
import com.example.roma.patientapp.domain.usecase.AllRegionsUseCase;
import com.example.roma.patientapp.domain.usecase.AllSpecialitiesUseCase;
import com.example.roma.patientapp.domain.usecase.SearchDoctorUseCase;
import com.example.roma.patientapp.domain.usecase.base.DefaultObserver;
import com.example.roma.patientapp.presentation.base.BasePresenter;
import com.example.roma.patientapp.utils.constants.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

/**
 * Created by Romisaa on 6/15/2018.
 */

public class HomePresenter extends BasePresenter<HomeActivity> implements HomeContract.Presenter {

    private SearchDoctorUseCase searchDoctorUseCase;
    private AllSpecialitiesUseCase allSpecialitiesUseCase;
    private AllRegionsUseCase allRegionsUseCase;

    @Inject
    public HomePresenter(SearchDoctorUseCase searchDoctorUseCase, AllSpecialitiesUseCase allSpecialitiesUseCase, AllRegionsUseCase allRegionsUseCase) {
        this.searchDoctorUseCase = searchDoctorUseCase;
        this.allSpecialitiesUseCase = allSpecialitiesUseCase;
        this.allRegionsUseCase = allRegionsUseCase;
    }

    @Override
    public void navigateToEditProfileFragment() {
        navigationManager.navigateToEditProfileFragment();
    }

    @Override
    public void navigateToAppointmentsFragment() {
        navigationManager.navigateToAppointmentsFragment();
    }

    @Override
    public void navigateToSettingsFragment() {
        navigationManager.navigateToSettingsFragment();
    }

    @Override
    public void navigateToLogOutFragment() {
        navigationManager.navigateToLogOutFragment();
    }

    @Override
    public void navigateToAboutFragment() {
        navigationManager.navigateToAboutFragment();
    }

    @Override
    public void searchDoctor(String value) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(Constants.DOCTOR_NAME, value);

        searchDoctorUseCase.execute(parameters, new DefaultObserver<SearchDoctorResponse>() {
            @Override
            public void onNext(SearchDoctorResponse searchDoctorResponse) {
                super.onNext(searchDoctorResponse);
                getView().loadData((ArrayList<Doctor>) searchDoctorResponse.getDoctors());
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        });
    }

    @Override
    public void getAllSpecialities() {
        allSpecialitiesUseCase.execute(null, new DefaultObserver<SpecialitiesResponse>() {
            @Override
            public void onNext(SpecialitiesResponse specialitiesResponse) {
                super.onNext(specialitiesResponse);
                if (specialitiesResponse.getId() == 0) {
                    allSpecialitiesUseCase.saveSpecialitiesResponse(specialitiesResponse);
                }
            }
        });
    }

    @Override
    public void getALlRegions() {
        allRegionsUseCase.execute(null, new DefaultObserver<RegionResponse>() {
            @Override
            public void onNext(RegionResponse regionResponse) {
                super.onNext(regionResponse);
                if (regionResponse.getId() == Constants.REQUEST_SUCCESS) {
                    allRegionsUseCase.saveRegionResponse(regionResponse);
                }
            }
        });
    }
}
