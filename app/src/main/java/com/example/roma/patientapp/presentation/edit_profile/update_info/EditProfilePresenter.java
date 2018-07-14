package com.example.roma.patientapp.presentation.edit_profile.update_info;

import com.example.roma.patientapp.data.model.login.SignInResponse;
import com.example.roma.patientapp.data.model.regions.RegionResponse;
import com.example.roma.patientapp.domain.usecase.EditProfileUseCase;
import com.example.roma.patientapp.presentation.base.BasePresenter;

import javax.inject.Inject;

/**
 * Created by Romisaa on 6/15/2018.
 */

public class EditProfilePresenter extends BasePresenter<EditProfileFragment> implements EditProfileContract.Presenter {

    private EditProfileUseCase editProfileUseCase;

    @Inject
    public EditProfilePresenter(EditProfileUseCase editProfileUseCase) {
        this.editProfileUseCase = editProfileUseCase;
    }

    @Override
    public void getSignInResponse() {
        SignInResponse response = editProfileUseCase.getSignInResponse();
        if (response != null) {
            getView().loadData(response);
        }
    }

    @Override
    public void getAllRegionResponse() {
        RegionResponse response = editProfileUseCase.getRegionResponse();
        if (response != null) {
            getView().loadSpinner(response);
        }
    }
}
