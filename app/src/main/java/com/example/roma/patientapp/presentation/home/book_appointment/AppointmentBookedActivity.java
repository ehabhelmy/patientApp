package com.example.roma.patientapp.presentation.home.book_appointment;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.example.roma.patientapp.PatientApplication;
import com.example.roma.patientapp.R;
import com.example.roma.patientapp.data.model.doctor_details.DoctorAppointment;
import com.example.roma.patientapp.presentation.base.BaseActivity;
import com.example.roma.patientapp.presentation.doctor_details.adapter.DoctorDetailsModel;
import com.example.roma.patientapp.presentation.home.book_appointment.adapter.AppointmentsAdapter;
import com.example.roma.patientapp.utils.constants.Constants;

import javax.inject.Inject;

import butterknife.BindView;

import static com.example.roma.patientapp.PatientApplication.getContext;

public class AppointmentBookedActivity extends BaseActivity implements AppointmentBookedContract.View{

    @BindView(R.id.all_appointments_rv)
    RecyclerView appointmentsRV;

    @Inject
    AppointmentBookedPresenter appointmentBookedPresenter;

    private DoctorDetailsModel doctorDetailsModel;
    private AppointmentsAdapter adapter;


    @Override
    protected void initView() {
        super.initView();
        doctorDetailsModel = (DoctorDetailsModel) getIntent().getParcelableExtra(Constants.DOCTOR);
        setupToolbar();
        setupAppointmentsRV();
    }

    private void setupToolbar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_close);
        actionBar.setTitle(Constants.Appointment);
    }

    private void setupAppointmentsRV() {
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext());
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL);
        adapter = new AppointmentsAdapter(doctorDetailsModel.getDoctorAppointments(), new OnItemClickListener() {
            @Override
            public void onItemClick(DoctorAppointment appointment) {
                showBookingDialog(appointment.getId());
            }
        });
        appointmentsRV.setLayoutManager(manager);
        appointmentsRV.addItemDecoration(itemDecoration);
        appointmentsRV.setAdapter(adapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_appointment_booked;
    }

    @Override
    protected void initializeDagger() {
        PatientApplication.getComponenet().inject(this);
    }

    @Override
    protected void initializePresenter() {
        super.presenter = appointmentBookedPresenter;
        appointmentBookedPresenter.setView(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

    @Override
    public void showSuccess() {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.success))
                .setMessage("Booked Successfully")
                .show();
    }

    public interface OnItemClickListener {
        void onItemClick(DoctorAppointment appointment);
    }

    private void showBookingDialog(final String appointmentId) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Book Appointment");
        alertDialogBuilder
                .setMessage(" Are you sure you want to book the appointment ? ")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        appointmentBookedPresenter.bookAppointment(doctorDetailsModel, appointmentId);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog dialog = alertDialogBuilder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
    }
}


