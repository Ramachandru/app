package com.example.myapplication.api;

import com.example.myapplication.models.CountryModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ContinentApi {

    String BASE_URL = "https://restcountries.eu/rest/v2/";

    @GET("all")
    Call<List<CountryModel>> getContinents();
}
