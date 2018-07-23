package com.example.roma.patientapp.presentation.home.search_doctor;

import com.example.roma.patientapp.data.model.search_doctor.Doctor;
import com.example.roma.patientapp.data.model.search_doctor.SearchDoctorResponse;
import com.example.roma.patientapp.domain.usecase.SearchDoctorUseCase;
import com.example.roma.patientapp.domain.usecase.base.DefaultObserver;
import com.example.roma.patientapp.presentation.base.BasePresenter;
import com.example.roma.patientapp.utils.constants.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

public class SearchDoctorPresenter extends BasePresenter<SearchDoctorContract.View> implements SearchDoctorContract.Presenter{

    private SearchDoctorUseCase searchDoctorUseCase;

    @Inject
    public SearchDoctorPresenter(SearchDoctorUseCase searchDoctorUseCase) {
        this.searchDoctorUseCase = searchDoctorUseCase;
    }

    @Override
    public void searchDoctor(String value,String speciality) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(Constants.DOCTOR_NAME, value);
        parameters.put(Constants.DOCTOR_SPEC,speciality);
        searchDoctorUseCase.execute(parameters, new DefaultObserver<SearchDoctorResponse>() {
            @Override
            public void onNext(SearchDoctorResponse searchDoctorResponse) {
                super.onNext(searchDoctorResponse);
                if (searchDoctorResponse.getId() == 0) {
                    if (isViewAttached()) {
                        getView().loadData((ArrayList<Doctor>) searchDoctorResponse.getDoctors());
                    }
                }else {
                    if (isViewAttached()){
                        getView().showError(searchDoctorResponse.getDescription());
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
}
