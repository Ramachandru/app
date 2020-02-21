package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Listeners.OnDataPassedListener;
import com.example.myapplication.models.CountryModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CountriesActivity extends AppCompatActivity implements OnDataPassedListener {
    private List<CountryModel> mStringList=new ArrayList<>();
    List<CountryModel> wholecountriesList;
    private int mLoadedItems = 0;
    private CountryAdapter mSampleAdapter;
    ProgressBar itemProgressBar;
    RecyclerView recyclerView;
    private boolean mIsDesceningBtnClicked=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        String key=getIntent().getExtras().getString("params");
        wholecountriesList = CountryTon.getInstance().getmCountryTon().get(key);
        CountryTon.getInstance().setmCountryList(wholecountriesList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CountriesActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView=findViewById(R.id.recycler_view);
        itemProgressBar=findViewById(R.id.item_progress_bar);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(CountriesActivity.this, 0));
        mSampleAdapter = new CountryAdapter(CountriesActivity.this);
        mSampleAdapter.setMyList(mStringList);
        recyclerView.setAdapter(mSampleAdapter);
        addDataToList();
        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                addDataToList();
            }
        });
    }

    private void addDataToList() {
        itemProgressBar.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                int lReminder=wholecountriesList.size();
                if(mLoadedItems != 0) {
                    lReminder = wholecountriesList.size() % mLoadedItems;
                }
                int lSize = lReminder;;
                if(lReminder >= 10){
                    lSize=10;
                }

                for (int i = 0; i <= lSize; i++) {
                    if(wholecountriesList.size() == mLoadedItems)
                        break;
                    mStringList.add(wholecountriesList.get(mLoadedItems));
                    mLoadedItems++;
                }
                mSampleAdapter.notifyDataSetChanged();
                itemProgressBar.setVisibility(View.GONE);
            }
        }, 1500);

    }
    private void refresh(){
        mStringList.clear();
        mLoadedItems=0;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    private MenuItem menuItem;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_load:
                if(mIsDesceningBtnClicked){
                    Toast.makeText(CountriesActivity.this,"Sorting Mode",Toast.LENGTH_LONG).show();
                    return true;
                }
                mIsDesceningBtnClicked=true;
                menuItem = item;
                menuItem.expandActionView();
                refresh();
                Collections.sort(wholecountriesList,new CountryModel());
                for(CountryModel country : wholecountriesList){
                    Log.d("<<< COUNTRY NAME ", " "+ country.getName()+" , "+ country.getBorders().size());
                }
                addDataToList();
                recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
                    @Override
                    public void onLoadMore() {
                        addDataToList();
                    }
                });
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void OnDataPassed(String paramString) {
        Intent lIntent=new Intent(CountriesActivity.this,CountryDetails.class);
        lIntent.putExtra("params_nxt",paramString);
        startActivity(lIntent);
    }
}
