package com.example.roma.patientapp.domain.usecase;

import com.example.roma.patientapp.data.DataRepository;
import com.example.roma.patientapp.data.model.edit_profile.UpdateInfoModel;
import com.example.roma.patientapp.data.model.login.SignInResponse;
import com.example.roma.patientapp.data.model.regions.RegionResponse;
import com.example.roma.patientapp.domain.usecase.base.UseCase;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Romisaa on 6/29/2018.
 */
public class EditProfileUseCase extends UseCase<UpdateInfoModel> {

    @Inject
    public EditProfileUseCase(CompositeDisposable compositeDisposable, DataRepository dataRepository) {
        super(compositeDisposable, dataRepository);
    }

    @Override
    protected Observable<UpdateInfoModel> buildUseCaseObservable(Map<String, Object> parameters) {
        return null;
    }

    public SignInResponse getSignInResponse() {
        return dataRepository.getSignInResponse();
    }

    public RegionResponse getRegionResponse() {
        return dataRepository.getRegionResponse();
    }
}
