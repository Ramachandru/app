package com.example.myapplication.viewmodel;


import android.text.TextUtils;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.example.myapplication.api.ContinentApi;
import com.example.myapplication.models.CountryModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ContinentViewModel extends ViewModel {

    //this is the data that we will fetch asynchronously
    private MutableLiveData<List<CountryModel>> continentList;
    private MutableLiveData<HashMap<String, List<CountryModel>>> continentMaps;

    public LiveData<HashMap<String, List<CountryModel>>> getContinentsMap() {
        //if the list is null
        if (continentList == null) {
            continentList = new MutableLiveData<List<CountryModel>>();
            continentMaps = new MutableLiveData<>();
            //we will load it asynchronously from server in this method
            loadHeroes();
        }

        //finally we will return the list
        return continentMaps;
    }


    //This method is using Retrofit to get the JSON data from URL
    private void loadHeroes() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ContinentApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ContinentApi api = retrofit.create(ContinentApi.class);
        Call<List<CountryModel>> call = api.getContinents();


        call.enqueue(new Callback<List<CountryModel>>() {
            @Override
            public void onResponse(Call<List<CountryModel>> call, Response<List<CountryModel>> response) {
                //finally we are setting the list to our MutableLiveData
                continentList.setValue(response.body());
                continentMaps.setValue(setContinentHashmaps(response.body()));
            }

            @Override
            public void onFailure(Call<List<CountryModel>> call, Throwable t) {

                Log.d("Onfailure_called "," "+ t.getMessage());

            }
        });
    }

    private HashMap<String, List<CountryModel>> setContinentHashmaps(List<CountryModel> list) {
        if (list != null && list.size() > 0) {
            HashMap<String, List<CountryModel>> maps = new HashMap<>();
            for (CountryModel res : list) {
                setValue(maps, res);
            }

            return maps;
        }

        return null;
    }

    private void setValue(HashMap<String, List<CountryModel>> map, CountryModel country) {
        if (map != null && country != null && !TextUtils.isEmpty(country.getRegion())) {
            if (map.containsKey(country.getRegion())) {
                List<CountryModel> list = map.get(country.getRegion());
                list.add(country);
                map.put(country.getRegion(), list);
            } else {
                List<CountryModel> list = new ArrayList<>();
                list.add(country);
                map.put(country.getRegion(), list);
            }
        }
    }
}

