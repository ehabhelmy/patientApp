package com.example.roma.patientapp.presentation.home.search_doctor;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;

import com.example.roma.patientapp.MainActivity;
import com.example.roma.patientapp.PatientApplication;
import com.example.roma.patientapp.R;
import com.example.roma.patientapp.data.model.search_doctor.Doctor;
import com.example.roma.patientapp.presentation.base.BaseFragment;
import com.example.roma.patientapp.presentation.home.HomeActivity;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;

public class SearchDoctorFragment extends BaseFragment implements SearchDoctorContract.View,SearchDoctorAdapter.OnItemClickListener{

    @BindView(R.id.search_doc_rv)
    RecyclerView doctorRecyclarView;

    private SearchDoctorAdapter adapter;
    private String speciality;
    @Inject
    SearchDoctorPresenter presenter;

    public static final String SPECIALITY_ID = "specialityId";

    @Override
    protected void initializeDagger() {
        PatientApplication.getComponenet().inject(this);
    }

    @Override
    protected void initializePresenter() {
        super.presenter = presenter;
        presenter.setView(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search;
    }

    @Override
    public void loadData(ArrayList<Doctor> doctors) {
        if (adapter == null) {
            adapter = new SearchDoctorAdapter(doctors, this);
            doctorRecyclarView.setAdapter(adapter);
        } else {
            adapter.updateData(doctors);
        }
    }

    @Override
    protected void initView() {
        super.initView();
        if (getArguments() != null){
            speciality = getArguments().getString(SPECIALITY_ID);
        }
        ((HomeActivity)getActivity()).showSearchBar();
        presenter.searchDoctor("",speciality);
        ((HomeActivity)getActivity()).getSearchEditText().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    presenter.searchDoctor(((HomeActivity)getActivity()).getSearchEditText().getText().toString().trim(),speciality);
                    return true;
                }
                return false;
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        doctorRecyclarView.setLayoutManager(mLayoutManager);
        doctorRecyclarView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onItemClick(Doctor doctor) {
        navigationManager.navigateDoctorDetailsActivity(doctor);
    }

    public void searchDoctor(String keyword){
        presenter.searchDoctor(keyword,speciality);
    }
}
