package com.example.roma.patientapp.presentation.home;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.roma.patientapp.PatientApplication;
import com.example.roma.patientapp.R;
import com.example.roma.patientapp.data.model.search_doctor.Doctor;
import com.example.roma.patientapp.data.model.specialities.Speciality;
import com.example.roma.patientapp.presentation.base.BaseActivity;
import com.example.roma.patientapp.presentation.base.BaseFragment;
import com.example.roma.patientapp.presentation.home.search_doctor.SearchDoctorAdapter;
import com.example.roma.patientapp.presentation.home.search_doctor.SearchDoctorFragment;
import com.example.roma.patientapp.presentation.login.SignInActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeActivity extends BaseActivity implements HomeContract.View {

    private static final String ACTIVITY_NAVIGATED = "asd";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.nav_drawer)
    DrawerLayout drawerLayout;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.search_doc_rv)
    RecyclerView specialitiesRv;
    @BindView(R.id.search_et)
    EditText searchEditText;
    @BindView(R.id.search_bar)
    LinearLayout searchBar;

    private boolean showen = true;
    @Inject
    HomePresenter homePresenter;

    private SpecialitiesAdapter specialitiesAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        super.initView();
        setSupportActionBar(toolbar);
        hideSearchBar();
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
        BaseFragment fragment = (BaseFragment) getSupportFragmentManager().findFragmentById(R.id.home_container);
        if (fragment instanceof SearchDoctorFragment) {
            ((SearchDoctorFragment)fragment).searchDoctor("");
        }

    }


    private void handleNavigation() {

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawerLayout.closeDrawers();
                switch (item.getItemId()) {
                    case R.id.home_item:
                        homePresenter.navigateToHomeFragment();
                        hideSearchBar();
                        break;
                    case R.id.profile_item:
                        homePresenter.navigateToEditProfileFragment();
                        hideSearchBar();
                        break;
                    case R.id.appoint_item:
                        homePresenter.navigateToAppointmentsFragment();
                        hideSearchBar();
                        break;
                    case R.id.setting_item:
                        homePresenter.navigateToSettingsFragment();
                        hideSearchBar();
                        break;
                    case R.id.logout_item:
                        homePresenter.handleUserLogout();
                        break;
                    case R.id.about_item:
                        homePresenter.navigateToAboutFragment();
                        hideSearchBar();
                        break;
                }
                return false;
            }
        });

    }

    private void loadRegionsSpecialitiesDoctors() {
        homePresenter.getAllSpecialities();
        homePresenter.getALlRegions();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().findFragmentById(R.id.home_container) instanceof SearchDoctorFragment) {
            hideSearchBar();
        }
        super.onBackPressed();
    }

    public void showSearchBar() {
        searchBar.setVisibility(View.VISIBLE);
    }

    public void hideSearchBar() {
        searchBar.setVisibility(View.GONE);
    }

    public EditText getSearchEditText() {
        return searchEditText;
    }

    @Override
    public void loadData(List<Speciality> specialities) {
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        specialitiesRv.setLayoutManager(mLayoutManager);
        specialitiesRv.setItemAnimator(new DefaultItemAnimator());
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(this, R.dimen.item_offset);
        specialitiesRv.addItemDecoration(itemDecoration);
        specialitiesAdapter = new SpecialitiesAdapter();
        specialitiesAdapter.setSpecialities(specialities);
        specialitiesAdapter.setOnSpecialityClick(new SpecialitiesAdapter.OnSpecialityClick() {
            @Override
            public void onSpecialityClick(Speciality speciality) {
                homePresenter.navigateToSearchFragment(speciality);
            }
        });
        specialitiesRv.setAdapter(specialitiesAdapter);
    }

    @Override
    public void navigateToAuth() {
        startActivity(new Intent(this, SignInActivity.class));
        finish();
    }

}
