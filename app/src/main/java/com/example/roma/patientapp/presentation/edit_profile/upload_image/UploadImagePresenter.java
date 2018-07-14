package com.example.roma.patientapp.presentation.edit_profile.upload_image;

import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.example.roma.patientapp.PatientApplication;
import com.example.roma.patientapp.data.model.uploadimage.UploadImageResponse;
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

    @Inject
    public UploadImagePresenter(UploadImageUseCase uploadImageUseCase) {
        this.uploadImageUseCase = uploadImageUseCase;
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
}
