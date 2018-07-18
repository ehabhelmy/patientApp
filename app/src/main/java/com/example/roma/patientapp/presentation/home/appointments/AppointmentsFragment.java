package com.example.roma.patientapp.presentation.home.appointments;


import android.content.res.ColorStateList;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.roma.patientapp.PatientApplication;
import com.example.roma.patientapp.R;
import com.example.roma.patientapp.presentation.base.BaseFragment;
import com.example.roma.patientapp.presentation.home.appointments.adapter.AppointmentsAdapter;
import com.example.roma.patientapp.presentation.home.book_appointment.adapter.AppointmentRequestDetailModel;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class AppointmentsFragment extends BaseFragment implements AppointmentsContract.View {

    @BindView(R.id.appointments_rv)
    RecyclerView recyclerView;
    @BindView(R.id.all_requests_tv)
    TextView allRequestsTv;
    @BindView(R.id.pending_requests_tv)
    TextView pendingRequestsTv;
    @BindView(R.id.approved_requests_tv)
    TextView approvedRequestsTv;
    @BindView(R.id.cancelled_requests_tv)
    TextView cancelledRequestsTv;

    private ColorStateList oldColors;


    @Inject
    AppointmentsPresenter appointmentsPresenter;

    private ArrayList<AppointmentRequestDetailModel> allRequests;
    private ArrayList<AppointmentRequestDetailModel> pendingRequests;
    private ArrayList<AppointmentRequestDetailModel> approvedRequests;
    private ArrayList<AppointmentRequestDetailModel> cancelledRequests;
    private AppointmentsAdapter adapter;

    @Override
    protected void initializeDagger() {
        PatientApplication.getComponenet().inject(this);
    }

    @Override
    protected void initializePresenter() {
        super.presenter = appointmentsPresenter;
        appointmentsPresenter.setView(this);
        appointmentsPresenter.getAppointmentsRequests();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_appointments;
    }

    @Override
    protected void initView() {
        super.initView();
        oldColors =  approvedRequestsTv.getTextColors(); //save original colors
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new AppointmentsAdapter();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void loadDate(ArrayList<AppointmentRequestDetailModel> allRequests,
                         ArrayList<AppointmentRequestDetailModel> pendingRequests,
                         ArrayList<AppointmentRequestDetailModel> approvedRequests,
                         ArrayList<AppointmentRequestDetailModel> cancelledRequests) {

        this.allRequests = allRequests;
        this.pendingRequests = pendingRequests;
        this.approvedRequests = approvedRequests;
        this.cancelledRequests = cancelledRequests;
        renderRecyclarViewData(this.allRequests);
    }

    void renderRecyclarViewData(ArrayList<AppointmentRequestDetailModel> data) {
        adapter.updateData(data);
    }

    @OnClick(R.id.all_requests_tv)
    void getAllRequests() {
        allRequestsTv.setTextColor(getResources().getColor(R.color.colorPrimary));
        pendingRequestsTv.setTextColor(oldColors);
        approvedRequestsTv.setTextColor(oldColors);
        cancelledRequestsTv.setTextColor(oldColors);
        adapter.updateData(allRequests);
    }

    @OnClick(R.id.pending_requests_tv)
    void getPendingRequests() {
        allRequestsTv.setTextColor(oldColors);
        pendingRequestsTv.setTextColor(getResources().getColor(R.color.colorPrimary));
        approvedRequestsTv.setTextColor(oldColors);
        cancelledRequestsTv.setTextColor(oldColors);
        adapter.updateData(pendingRequests);
    }

    @OnClick(R.id.approved_requests_tv)
    void getApprovedRequests() {
        allRequestsTv.setTextColor(oldColors);
        pendingRequestsTv.setTextColor(oldColors);
        approvedRequestsTv.setTextColor(getResources().getColor(R.color.colorPrimary));
        cancelledRequestsTv.setTextColor(oldColors);
        adapter.updateData(approvedRequests);
    }

    @OnClick(R.id.cancelled_requests_tv)
    void getCancelledRequests() {
        allRequestsTv.setTextColor(oldColors);
        pendingRequestsTv.setTextColor(oldColors);
        approvedRequestsTv.setTextColor(oldColors);
        cancelledRequestsTv.setTextColor(oldColors);
        adapter.updateData(cancelledRequests);
    }
}
