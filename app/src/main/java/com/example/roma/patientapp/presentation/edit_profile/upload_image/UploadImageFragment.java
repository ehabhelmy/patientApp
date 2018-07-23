package com.example.roma.patientapp.presentation.edit_profile.upload_image;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import com.example.roma.patientapp.PatientApplication;
import com.example.roma.patientapp.R;
import com.example.roma.patientapp.data.model.login.SignInResponse;
import com.example.roma.patientapp.data.model.regions.RegionResponse;
import com.example.roma.patientapp.presentation.base.BaseFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class UploadImageFragment extends BaseFragment implements UploadImageContract.View {

    @BindView(R.id.photo_iv)
    CircleImageView photo_iv;

    @BindView(R.id.upload_photo_tv)
    TextView upload_photo_tv;

    @Inject
    UploadImagePresenter uploadImagePresenter;

    private static final int CAMERA_CAPTURE_REQUEST_CODE = 1;
    private static final int REQUEST_WRITE_PERMISSION = 2;
    private Uri imageUri;

    @Override
    protected void initializeDagger() {
        PatientApplication.getComponenet().inject(this);
    }

    @Override
    protected void initializePresenter() {
        super.presenter = uploadImagePresenter;
        uploadImagePresenter.setView(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_upload_image;
    }


    @OnClick(R.id.photo_iv)
    public void pickImage() {
        requestPermission();
    }

    @OnClick(R.id.skip_tv)
    public void skip(){
        navigationManager.navigateToHomeActivity();
    }

    @OnClick(R.id.upload_photo_tv)
    public void uploadImage() {
        uploadImagePresenter.uploadImage(imageUri);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_CAPTURE_REQUEST_CODE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageUri = data.getData();
            photo_iv.setImageURI(imageUri);
        }
    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
        } else {
            openGallery();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_WRITE_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        }
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, CAMERA_CAPTURE_REQUEST_CODE);
    }

    @Override
    public void loadData(SignInResponse response) {

    }

    @Override
    public void loadSpinner(RegionResponse response) {

    }
}
