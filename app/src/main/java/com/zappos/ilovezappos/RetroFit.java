package com.zappos.ilovezappos;

import android.app.Activity;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.*;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by yash on 02/02/17.
 */

public class RetroFit {
    public static final String BASE_URL = "https://api.zappos.com/";
    public static Retrofit getInstance() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit;
    }

    public static void SendRequest(final MainActivity activity,final Context context, APIEndPointInterface apiService, String term){
        Call<Products> call = apiService.getProducts(term);

        call.enqueue(new Callback<Products>() {
            @Override
            public void onResponse(Call<Products> call, Response<Products> response) {
                int statusCode = response.code();
                Products products = response.body();
                final List<Result> product = products.getResults();
                activity.onBackgroundTaskDataObtained(context,product);
            }

            @Override
            public void onFailure(Call<Products> call, Throwable t) {
                // Log error here since request failed
            }
        });

    }
}

