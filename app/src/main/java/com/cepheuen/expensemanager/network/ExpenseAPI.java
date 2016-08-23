package com.cepheuen.expensemanager.network;

import android.app.Application;

import com.cepheuen.expensemanager.model.Expenses;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CookieJar;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Ashik Vetrivelu on 22/08/16.
 */
public class ExpenseAPI {
    private static final String endpointURL = "https://expensemanager-test.herokuapp.com";
    private static ExpenseInternalAPI applicationService;

    public static ExpenseInternalAPI getApplicationService()
    {
        if(applicationService == null)
        {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.connectTimeout(10, TimeUnit.MINUTES);
            httpClient.readTimeout(10,TimeUnit.MINUTES);
            httpClient.writeTimeout(10,TimeUnit.MINUTES);
            httpClient.addInterceptor(logging);
            httpClient.retryOnConnectionFailure(true);
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(endpointURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
            applicationService = retrofit.create(ExpenseInternalAPI.class);
        }
        return applicationService;
    }

    public interface ExpenseInternalAPI
    {
        @POST("/addExpense")
        Call<Expenses> addExpense(@Body Expenses expense);

        @GET("/")
        Call<List<Expenses>> getAllExpenses();

        @Headers("Content-Type:application/json")
        @POST("/updateExpense")
        Call<List<Expenses>> updateExpense(@Body Expenses expense);

        @GET("/deleteExpense/{timestamp}")
        Call<List<Expenses>> deleteExpense(@Path("timestamp") long timeStamp);

    }
}

