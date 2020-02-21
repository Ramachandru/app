package com.example.myapplication;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import com.example.myapplication.Listeners.OnDataPassedListener;



public class ContinentAdapter extends RecyclerView.Adapter<ContinentAdapter.ViewHolder> {
    private List<String> myList;
    public OnDataPassedListener onDataPassedListener;
    public ContinentAdapter(Context context) {
        onDataPassedListener = (OnDataPassedListener)context;
    }
    public void setMyList(List<String> myList){
        this.myList = myList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.textView.setText(myList.get(position));
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDataPassedListener.OnDataPassed(myList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return myList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView textView;

        ViewHolder(View itemView) {
            super(itemView);
            textView = (AppCompatTextView) itemView.findViewById(R.id.text1);
        }
    }
}
