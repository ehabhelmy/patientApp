package com.example.roma.patientapp.data.remote;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Romisaa on 4/21/2018.
 */

@Singleton
public class ServiceGenerator {

    private final int TIMEOUT_CONNECT = 30;   //In seconds
    private final int TIMEOUT_READ = 30;   //In seconds
    private final String CONTENT_TYPE = "Content-Type";
    private final String API_KEY = "apikey";
    private String CONTENT_TYPE_VALUE = "application/json";
    private OkHttpClient.Builder okHttpBuilder;
    private HttpLoggingInterceptor interceptor;

    @Inject
    public ServiceGenerator() {
        okHttpBuilder = new OkHttpClient.Builder();
        interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        okHttpBuilder.addInterceptor(interceptor);
    }

    public <s> s createService(Class<s> serviceClass, String url) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(okHttpBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory((RxJava2CallAdapterFactory.create()))
                .build();
        return retrofit.create(serviceClass);
    }

    private Interceptor headerInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request original = chain.request();
            Request request = original.newBuilder()
                    .header(CONTENT_TYPE, CONTENT_TYPE_VALUE)
                    .method(original.method(), original.body())
                    .build();

            return chain.proceed(request);
        }
    };

    public void setCONTENT_TYPE_VALUE(String CONTENT_TYPE_VALUE) {
        this.CONTENT_TYPE_VALUE = CONTENT_TYPE_VALUE;
    }
}
