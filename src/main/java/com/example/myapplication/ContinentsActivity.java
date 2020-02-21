package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Listeners.OnDataPassedListener;
import com.example.myapplication.models.CountryModel;
import com.example.myapplication.viewmodel.ContinentViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ContinentsActivity extends AppCompatActivity implements OnDataPassedListener {
    private Set<String> mStringList=new HashSet<>();
    private List<String> continentListData;
    private int mLoadedItems = 0;
    private ContinentAdapter mSampleAdapter;
    ProgressBar itemProgressBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ContinentViewModel model = ViewModelProviders.of(this).get(ContinentViewModel.class);

        model.getContinentsMap().observe(this, new Observer<HashMap<String, List<CountryModel>>>() {
            @Override
            public void onChanged(@Nullable HashMap<String,List<CountryModel>> continentList) {
                if(continentList != null) {
                    CountryTon.getInstance().setmCountryTon(continentList);
                    mStringList=continentList.keySet();
                    continentListData = new ArrayList<>(mStringList);
                    mSampleAdapter.setMyList(continentListData);
                    mSampleAdapter.notifyDataSetChanged();
                }
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ContinentsActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        RecyclerView recyclerView=findViewById(R.id.recycler_view);
        itemProgressBar=findViewById(R.id.item_progress_bar);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(ContinentsActivity.this, 0));
        continentListData = new ArrayList<>(mStringList);
        mSampleAdapter = new ContinentAdapter(ContinentsActivity.this);
        mSampleAdapter.setMyList(continentListData);
        recyclerView.setAdapter(mSampleAdapter);
    }

    @Override
    public void OnDataPassed(String paramString) {
        Intent lIntent=new Intent(ContinentsActivity.this,CountriesActivity.class);
        lIntent.putExtra("params",paramString);
        startActivity(lIntent);
    }
}
