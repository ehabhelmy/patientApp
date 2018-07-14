package com.example.roma.patientapp.presentation.edit_profile.update_info;


import android.support.v7.widget.AppCompatEditText;

import com.example.roma.patientapp.PatientApplication;
import com.example.roma.patientapp.R;
import com.example.roma.patientapp.data.model.login.SignInResponse;
import com.example.roma.patientapp.data.model.regions.RegionResponse;
import com.example.roma.patientapp.presentation.base.BaseFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class EditProfileFragment extends BaseFragment implements EditProfileContract.View {

    @BindView(R.id.id_et)
    AppCompatEditText idEditText;

    @BindView(R.id.name_et)
    AppCompatEditText nameEditText;

    @Inject
    EditProfilePresenter editProfilePresenter;

    @Override
    protected void initializeDagger() {
        PatientApplication.getComponenet().inject(this);
    }

    @Override
    protected void initializePresenter() {
        super.presenter = editProfilePresenter;
        editProfilePresenter.setView(this);
        editProfilePresenter.getSignInResponse();
        editProfilePresenter.getAllRegionResponse();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_edit_profile;
    }

    @OnClick(R.id.next_tv)
    public void navigateToUploadImageFragment() {
        navigationManager.navigateToUploadImageFragment();
    }

    @OnClick(R.id.change_pw_tv)
    public void navigateToChangePwFragment() {
        navigationManager.navigateToChangePwFragment();
    }

    @Override
    public void loadData(SignInResponse response) {
        idEditText.setText(String.valueOf(response.getId()));
        nameEditText.setText(String.format("%s %s", response.getUserInfo().getFirstName(), response.getUserInfo().getLastName()));
    }

    @Override
    public void loadSpinner(RegionResponse response) {

    }
}
