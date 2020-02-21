package com.example.myapplication;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.myapplication.Listeners.OnDataPassedListener;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.models.CountryModel;

import java.util.List;


public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.ViewHolder> {
    private List<CountryModel> myList;
    Context mContext;
    public OnDataPassedListener onDataPassedListener;
    public CountryAdapter(Context context) {
        mContext= context;
        onDataPassedListener = (OnDataPassedListener)context;
    }
    public void setMyList(List<CountryModel> myList){
        this.myList = myList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.country_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.countryName.setText(myList.get(position).getName());
        holder.countryCapital.setText(myList.get(position).getCapital());
        Glide.with(mContext)
                .load(myList.get(position).getFlag())
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDataPassedListener.OnDataPassed(""+position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return myList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView countryName,
                 countryCapital;
        ImageView image;
        ViewHolder(View itemView) {
            super(itemView);
            countryName = (TextView) itemView.findViewById(R.id.country_name);
            countryCapital = (TextView) itemView.findViewById(R.id.country_capital);
            image = (ImageView)itemView.findViewById(R.id.image);
        }
    }
}
