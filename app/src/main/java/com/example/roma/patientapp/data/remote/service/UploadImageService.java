package com.example.roma.patientapp.data.remote.service;

import com.example.roma.patientapp.data.model.uploadimage.UploadImageResponse;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UploadImageService {

    @Multipart
    @POST("uploadImage")
    Observable<UploadImageResponse> uploadImage(@Part("token") String token,
                                                @Part MultipartBody.Part profile_image);

}
