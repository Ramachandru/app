package com.example.myapplication;

import com.example.myapplication.models.CountryModel;

import java.util.HashMap;
import java.util.List;

public class CountryTon {
    private HashMap<String, List<CountryModel>> mCountryList;
    private List<CountryModel> mCountryData;
    private static CountryTon mCountryTon;
    private CountryTon(){

    }
    public static CountryTon getInstance(){
        if(mCountryTon == null) {
            synchronized (CountryTon.class) {
                if (mCountryTon == null) {
                    mCountryTon = new CountryTon();
                }
            }
        }
        return mCountryTon;
    }
    public void setmCountryTon(HashMap<String, List<CountryModel>> countryList){
        mCountryList = countryList;
    }

    public HashMap<String, List<CountryModel>>  getmCountryTon() {
        return mCountryList;
    }
    public void setmCountryList(List<CountryModel> countryList){
        mCountryData = countryList;
    }
    public List<CountryModel> getmCountryData(){
        return mCountryData;
    }
}
