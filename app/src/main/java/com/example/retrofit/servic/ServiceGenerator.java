package com.example.retrofit.servic;

import com.example.retrofit.constant.JsonConstants;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
    // No need to instantiate this class.
    private ServiceGenerator() {}

    private static  String url = JsonConstants.BASE_URL;
    private static Retrofit retrofit ;

    private static Retrofit.Builder builder = new Retrofit.Builder().
            baseUrl(url).
            addConverterFactory(GsonConverterFactory.create());

    static private HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    static private OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();

    public  static  void changeApiBaseUrl(String newUrl){
        url = newUrl;
        builder= new Retrofit.Builder().
                addConverterFactory(GsonConverterFactory.create())
                .baseUrl(url);
    }


    public static <S> S creatServise(Class<S> serviceClass) {
        if (! okHttpClientBuilder.interceptors().contains(loggingInterceptor)){
            okHttpClientBuilder.addInterceptor(loggingInterceptor);
          builder=  builder.client(okHttpClientBuilder.build());
          retrofit=builder.build();
        }
        return retrofit.create(serviceClass);
    }


}
