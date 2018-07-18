package com.example.roma.patientapp.presentation.home.book_appointment;

import com.example.roma.patientapp.data.model.appointment_booked.AppointmentBookedResponse;
import com.example.roma.patientapp.domain.usecase.AppointmentBookedUseCase;
import com.example.roma.patientapp.domain.usecase.base.DefaultObserver;
import com.example.roma.patientapp.presentation.base.BasePresenter;
import com.example.roma.patientapp.presentation.doctor_details.adapter.DoctorDetailsModel;
import com.example.roma.patientapp.presentation.home.book_appointment.adapter.AppointmentRequestDetailModel;
import com.example.roma.patientapp.utils.constants.Constants;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import static com.example.roma.patientapp.utils.constants.Constants.REQUEST_SUCCESS;

/**
 * Created by Romisaa on 6/27/2018.
 */
public class AppointmentBookedPresenter extends BasePresenter<AppointmentBookedActivity> implements AppointmentBookedContract.Presenter {

    private AppointmentBookedUseCase appointmentBookedUseCase;

    @Inject
    public AppointmentBookedPresenter(AppointmentBookedUseCase appointmentBookedUseCase) {
        this.appointmentBookedUseCase = appointmentBookedUseCase;
    }

    @Override
    public void bookAppointment(final DoctorDetailsModel model, final String appointmentId) {
        Map<String, Object> params = new HashMap<>();
        params.put(Constants.DOCTOR_CODE, model.getMedicalCode());
        params.put(Constants.SERVICE_ID, model.getServices().get(0).getId());
        params.put(Constants.APPOINTMENT_ID, appointmentId);

        appointmentBookedUseCase.execute(params, new DefaultObserver<AppointmentBookedResponse>() {
            @Override
            public void onNext(AppointmentBookedResponse appointmentBookedResponse) {
                super.onNext(appointmentBookedResponse);
                if (isViewAttached()) {
                    if (appointmentBookedResponse.getId() == REQUEST_SUCCESS) {
                        getView().showSuccess();
                        appointmentBookedUseCase.saveAppointmentRequestDetail(new AppointmentRequestDetailModel(appointmentBookedResponse.getRequestId(),
                                model.getTime(), model.getLastName(), model.getSpeciality()));
                    } else {
                        getView().showError(appointmentBookedResponse.getDescription());
                    }
                }
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


}
