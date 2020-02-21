package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.myapplication.databinding.ActivityCountryDetailsBinding;
import com.example.myapplication.models.CountryModel;
import com.example.myapplication.models.Currency;
import com.example.myapplication.models.Language;

import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.os.Bundle;

import java.util.List;

public class CountryDetails extends AppCompatActivity {
    private ActivityCountryDetailsBinding activityCountryDetailsBinding;
    RequestBuilder<PictureDrawable> requestBuilder = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityCountryDetailsBinding= DataBindingUtil.setContentView(this,R.layout.activity_country_details);
        String key=getIntent().getStringExtra("params_nxt");
        CountryModel countryDetail=CountryTon.getInstance().getmCountryData().get(Integer.parseInt(key));
        activityCountryDetailsBinding.countryNameField.setText(countryDetail.getName());
        activityCountryDetailsBinding.countryCapitalValue.setText(countryDetail.getCapital());
        String currencyValue="";
        for(Currency currency : countryDetail.getCurrencies()){
            currencyValue=currencyValue+currency.getName()+" , ";
        }
        activityCountryDetailsBinding.currencyValue.setText(currencyValue);
        String languages="";
        for(Language language : countryDetail.getLanguages()){
            languages=languages+language.getName()+" , ";
        }
        activityCountryDetailsBinding.languageValue.setText(languages);
        activityCountryDetailsBinding.countryCapitalValue.setText(countryDetail.getCapital());
        activityCountryDetailsBinding.populationValue.setText(""+countryDetail.getPopulation());
        Uri uri = Uri.parse(countryDetail.getFlag());
        Glide.with(this)
                .load(countryDetail.getFlag())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(activityCountryDetailsBinding.countryFlag);
    }
}
