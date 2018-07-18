package com.example.roma.patientapp.presentation.home.appointments;

import com.example.roma.patientapp.data.model.appointments.RequestStatusResponse;
import com.example.roma.patientapp.domain.usecase.RequestStatusUseCase;
import com.example.roma.patientapp.domain.usecase.base.DefaultObserver;
import com.example.roma.patientapp.presentation.base.BasePresenter;
import com.example.roma.patientapp.presentation.home.book_appointment.adapter.AppointmentRequestDetailModel;
import com.example.roma.patientapp.utils.constants.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * Created by Romisaa on 6/29/2018.
 */
public class AppointmentsPresenter extends BasePresenter<AppointmentsFragment> implements AppointmentsContract.Presenter {

    private RequestStatusUseCase requestStatusUseCase;
    private ArrayList<AppointmentRequestDetailModel> allRequests;
    private ArrayList<AppointmentRequestDetailModel> pendingRequests;
    private ArrayList<AppointmentRequestDetailModel> approvedRequests;
    private ArrayList<AppointmentRequestDetailModel> cancelledRequests;
    private int count = 0;

    @Inject
    public AppointmentsPresenter(RequestStatusUseCase requestStatusUseCase) {
        this.requestStatusUseCase = requestStatusUseCase;
        allRequests = new ArrayList<>();
        pendingRequests = new ArrayList<>();
        approvedRequests = new ArrayList<>();
        cancelledRequests = new ArrayList<>();
    }

    @Override
    public void getAppointmentsRequests() {
        List<AppointmentRequestDetailModel> requests = requestStatusUseCase.getAppointmentsRequestsDetails();
        if (requests != null) {
            allRequests = (ArrayList<AppointmentRequestDetailModel>) requests;
            for (AppointmentRequestDetailModel request : allRequests) {
                getRequestStatus(request);
            }
        }
    }

    private void getRequestStatus(final AppointmentRequestDetailModel request) {
        Map<String, Object> params = new HashMap<>();
        params.put(Constants.REQUEST_ID, request.getRequestId());
        requestStatusUseCase.execute(params, new DefaultObserver<RequestStatusResponse>() {
            @Override
            public void onNext(RequestStatusResponse requestStatusResponse) {
                super.onNext(requestStatusResponse);
                if (requestStatusResponse.getId() == Constants.REQUEST_SUCCESS) {
                    count++;
                    switch (requestStatusResponse.getRequestStatus()) {
                        case Constants.REQUEST_STATUS_PENDING:
                            pendingRequests.add(request);
                            break;
                        case Constants.REQUEST_STATUS_APPROVED:
                            approvedRequests.add(request);
                            break;
                        case Constants.REQUEST_STATUS_CANCELLED:
                            cancelledRequests.add(request);
                            break;
                        default:
                            break;
                    }
                }else {
                    if (isViewAttached()){
                        getView().showError(requestStatusResponse.getDescription());
                    }
                }
                isServiceFinished();
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

    private void isServiceFinished() {
        if (count == allRequests.size()) {
            getView().loadDate(allRequests, pendingRequests, approvedRequests, cancelledRequests);
        }
    }
}
