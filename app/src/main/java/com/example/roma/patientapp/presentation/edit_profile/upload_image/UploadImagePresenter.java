package com.example.roma.patientapp.presentation.edit_profile.upload_image;

import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.example.roma.patientapp.PatientApplication;
import com.example.roma.patientapp.data.model.updateimage.UpdateImageResponse;
import com.example.roma.patientapp.data.model.uploadimage.UploadImageResponse;
import com.example.roma.patientapp.domain.usecase.UpdateImageUseCase;
import com.example.roma.patientapp.domain.usecase.UploadImageUseCase;
import com.example.roma.patientapp.domain.usecase.base.DefaultObserver;
import com.example.roma.patientapp.presentation.base.BasePresenter;
import com.example.roma.patientapp.utils.constants.Constants;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

public class UploadImagePresenter extends BasePresenter<UploadImageFragment> implements UploadImageContract.Presenter {

    private UploadImageUseCase uploadImageUseCase;
    private UpdateImageUseCase updateImageUseCase;

    @Inject
    public UploadImagePresenter(UploadImageUseCase uploadImageUseCase, UpdateImageUseCase updateImageUseCase) {
        this.uploadImageUseCase = uploadImageUseCase;
        this.updateImageUseCase = updateImageUseCase;
    }

    @Override
    public void getSignInResponse() {

    }

    @Override
    public void getAllRegionResponse() {

    }

    @Override
    public void uploadImage(Uri image) {
        File imageFile = null;
        if (image != null) {
            imageFile = new File(getRealPathFromURI(image));
        }

        Map<String, Object> parameters = new HashMap<>();
        parameters.put(Constants.FILE, imageFile);

        uploadImageUseCase.execute(parameters, new DefaultObserver<UploadImageResponse>() {
            @Override
            public void onNext(UploadImageResponse uploadImageResponse) {
                super.onNext(uploadImageResponse);
                if (uploadImageResponse.getResponseCode() == Constants.REQUEST_SUCCESS) {
                    updateImage(uploadImageResponse.getLocation());
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        });

    }

    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(PatientApplication.getContext(), contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }

    private void updateImage(String location) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(Constants.LOCATION, location);

        updateImageUseCase.execute(parameters, new DefaultObserver<UpdateImageResponse>() {
            @Override
            public void onNext(UpdateImageResponse updateImageResponse) {
                super.onNext(updateImageResponse);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        });
    }
}
