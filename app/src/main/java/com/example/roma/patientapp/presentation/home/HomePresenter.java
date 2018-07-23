package com.example.roma.patientapp.presentation.home;

import android.content.Intent;

import com.example.roma.patientapp.data.model.regions.RegionResponse;
import com.example.roma.patientapp.data.model.search_doctor.Doctor;
import com.example.roma.patientapp.data.model.search_doctor.SearchDoctorResponse;
import com.example.roma.patientapp.data.model.specialities.SpecialitiesResponse;
import com.example.roma.patientapp.data.model.specialities.Speciality;
import com.example.roma.patientapp.domain.usecase.AllRegionsUseCase;
import com.example.roma.patientapp.domain.usecase.AllSpecialitiesUseCase;
import com.example.roma.patientapp.domain.usecase.SearchDoctorUseCase;
import com.example.roma.patientapp.domain.usecase.TokenUseCase;
import com.example.roma.patientapp.domain.usecase.base.DefaultObserver;
import com.example.roma.patientapp.presentation.base.BasePresenter;
import com.example.roma.patientapp.presentation.login.SignInActivity;
import com.example.roma.patientapp.utils.constants.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

/**
 * Created by Romisaa on 6/15/2018.
 */

public class HomePresenter extends BasePresenter<HomeContract.View> implements HomeContract.Presenter {

    private AllSpecialitiesUseCase allSpecialitiesUseCase;
    private AllRegionsUseCase allRegionsUseCase;
    private TokenUseCase tokenUseCase;

    @Inject
    public HomePresenter(AllSpecialitiesUseCase allSpecialitiesUseCase, AllRegionsUseCase allRegionsUseCase, TokenUseCase tokenUseCase) {
        this.allSpecialitiesUseCase = allSpecialitiesUseCase;
        this.allRegionsUseCase = allRegionsUseCase;
        this.tokenUseCase = tokenUseCase;
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

    public void navigateToHomeFragment() {
        navigationManager.navigateToHomeActivity();
    }

    @Override
    public void getAllSpecialities() {
        allSpecialitiesUseCase.execute(null, new DefaultObserver<SpecialitiesResponse>() {
            @Override
            public void onNext(SpecialitiesResponse specialitiesResponse) {
                super.onNext(specialitiesResponse);
                if (specialitiesResponse.getId() == 0) {
                    allSpecialitiesUseCase.saveSpecialitiesResponse(specialitiesResponse);
                    if (isViewAttached()){
                        getView().loadData(specialitiesResponse.getSpecialities());
                    }
                }else {
                    if (isViewAttached()){
                        getView().showError(specialitiesResponse.getDescription());
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if (isViewAttached()){
                    getView().showError("Server Error");
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

    @Override
    public void navigateToSearchFragment(Speciality speciality) {
        navigationManager.navigateToSearchFragment(speciality);
    }

    public void handleUserLogout() {
        tokenUseCase.clearToken();
        if (isViewAttached()) {
            getView().navigateToAuth();
        }
    }
}
