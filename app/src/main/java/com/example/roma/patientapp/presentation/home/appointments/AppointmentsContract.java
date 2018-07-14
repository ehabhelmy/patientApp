package com.example.roma.patientapp.presentation.home.appointments;

import com.example.roma.patientapp.presentation.base.BaseView;
import com.example.roma.patientapp.presentation.home.book_appointment.adapter.AppointmentRequestDetailModel;

import java.util.ArrayList;

/**
 * Created by Romisaa on 6/29/2018.
 */
public interface AppointmentsContract {
    interface View extends BaseView {

        void loadDate(ArrayList<AppointmentRequestDetailModel> allRequests, ArrayList<AppointmentRequestDetailModel> pendingRequests, ArrayList<AppointmentRequestDetailModel> approvedRequests, ArrayList<AppointmentRequestDetailModel> cancelledRequests);
    }

    interface Presenter {
        void getAppointmentsRequests();
    }
}
