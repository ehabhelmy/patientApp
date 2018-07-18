package com.example.roma.patientapp.presentation.edit_profile.update_info;


import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.roma.patientapp.PatientApplication;
import com.example.roma.patientapp.R;
import com.example.roma.patientapp.data.model.login.SignInResponse;
import com.example.roma.patientapp.data.model.regions.Government;
import com.example.roma.patientapp.data.model.regions.Region;
import com.example.roma.patientapp.data.model.regions.RegionResponse;
import com.example.roma.patientapp.presentation.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class EditProfileFragment extends BaseFragment implements EditProfileContract.View {

    @BindView(R.id.id_et)
    AppCompatEditText idEditText;

    @BindView(R.id.first_name_et)
    AppCompatEditText nameEditText;

    @BindView(R.id.last_name_et)
    AppCompatEditText lastNameEditText;

    @BindView(R.id.phone_number_et)
    AppCompatEditText mobileEditText;

    @BindView(R.id.email_et)
    AppCompatEditText emailEditText;

    @BindView(R.id.city_et)
    AppCompatSpinner citySpinner;

    @Inject
    EditProfilePresenter editProfilePresenter;

    private ArrayAdapter<String> spinnerArrayAdapter;
    private ArrayList<Region> regions;
    private String region;

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
        editProfilePresenter.updateInfo(nameEditText.getText().toString().trim(),lastNameEditText.getText().toString().trim(),mobileEditText.getText().toString().trim(),emailEditText.getText().toString().trim(),region);
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
    protected void initView() {
        super.initView();
        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                region = regions.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void loadSpinner(RegionResponse response) {
        ArrayList<Government> governments = (ArrayList<Government>) response.getGovernments();
        regions = new ArrayList<>();
        List<String> strings = new ArrayList<>();
        if (governments != null) {
            for (Government government : governments) {
                regions.addAll(government.getRegions());
            }
            for (Region region : regions) {
                strings.add(region.getName());
            }
            spinnerArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, strings); //selected item will look like a spinner set from XML
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        }
      }
}
