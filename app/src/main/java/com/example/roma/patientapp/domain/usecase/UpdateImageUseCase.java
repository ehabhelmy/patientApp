package com.example.roma.patientapp.domain.usecase;

import com.example.roma.patientapp.data.DataRepository;
import com.example.roma.patientapp.data.model.updateimage.UpdateImageResponse;
import com.example.roma.patientapp.domain.usecase.base.UseCase;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Romisaa on 7/14/2018.
 */
public class UpdateImageUseCase extends UseCase<UpdateImageResponse> {

    @Inject
    public UpdateImageUseCase(CompositeDisposable compositeDisposable, DataRepository dataRepository) {
        super(compositeDisposable, dataRepository);
    }

    @Override
    protected Observable<UpdateImageResponse> buildUseCaseObservable(Map<String, Object> parameters) {
        return dataRepository.updateImage(parameters);
    }


}
