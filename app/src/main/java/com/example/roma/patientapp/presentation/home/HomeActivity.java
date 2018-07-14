package com.example.roma.patientapp.presentation.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.roma.patientapp.PatientApplication;
import com.example.roma.patientapp.R;
import com.example.roma.patientapp.data.model.search_doctor.Doctor;
import com.example.roma.patientapp.presentation.base.BaseActivity;
import com.example.roma.patientapp.presentation.home.search_doctor.SearchDoctorAdapter;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeActivity extends BaseActivity implements HomeContract.View, SearchDoctorAdapter.OnItemClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.nav_drawer)
    DrawerLayout drawerLayout;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.search_et)
    EditText searchEditText;
    @BindView(R.id.search_doc_rv)
    RecyclerView doctorRecyclarView;

    @Inject
    HomePresenter homePresenter;

    private SearchDoctorAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    @Override
    protected void initView() {
        super.initView();
        setSupportActionBar(toolbar);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        doctorRecyclarView.setLayoutManager(mLayoutManager);
        doctorRecyclarView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void initializeDagger() {
        PatientApplication.getComponenet().inject(this);
    }

    @Override
    protected void initializePresenter() {
        presenter = homePresenter;
        homePresenter.setView(this);
        handleNavigation();
        loadRegionsSpecialitiesDoctors();
    }

    @OnClick(R.id.menu_iv)
    void openNavigationDrawer() {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    @OnClick(R.id.clear_iv)
    void search() {
        searchEditText.setText("");
        homePresenter.searchDoctor("");

    }


    private void handleNavigation() {

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawerLayout.closeDrawers();
                switch (item.getItemId()) {
                    case R.id.profile_item:
                        homePresenter.navigateToEditProfileFragment();
                        break;
                    case R.id.appoint_item:
                        homePresenter.navigateToAppointmentsFragment();
                        break;
                    case R.id.setting_item:
                        homePresenter.navigateToSettingsFragment();
                        break;
                    case R.id.logout_item:
                        homePresenter.navigateToLogOutFragment();
                        break;
                    case R.id.about_item:
                        homePresenter.navigateToAboutFragment();
                        break;
                }
                return false;
            }
        });

    }

    private void loadRegionsSpecialitiesDoctors() {
        homePresenter.getAllSpecialities();
        homePresenter.getALlRegions();
        homePresenter.searchDoctor("");
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
    public void onItemClick(Doctor doctor) {
        navigationManager.navigateDoctorDetailsActivity(doctor);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
