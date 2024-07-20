package com.efkan.retrofitjava.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.efkan.retrofitjava.R;
import com.efkan.retrofitjava.model.CryptoModel;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RowHolder> {
    private String[] colors={"#9b3dea","#ff7f50","#cbdab9","#daa520","#eeeeee","#d2956b","#d9dee6","#FFCBDB"};
    private ArrayList<CryptoModel> cryptoList;
    public RecyclerViewAdapter(ArrayList<CryptoModel> cryptoList) {
        this.cryptoList = cryptoList;
    }
    //------------------------------------> bu constructorı sonradan ekledim
    @NonNull
    @Override
    public RowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.row_layout,parent,false);
        return new RowHolder(view);
    }

    @Override
    public int getItemCount() {
        return cryptoList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull RowHolder holder, int position) {
    holder.bind(cryptoList.get(position),colors,position);
    }

    public class RowHolder extends RecyclerView.ViewHolder{
        TextView textName,textPrice;
        public RowHolder(@NonNull View itemView) {
            super(itemView);

        }
        public void bind(CryptoModel cryptoModel ,String[] colors,Integer position){
            itemView.setBackgroundColor(Color.parseColor(colors[position % 8]));  //position sayımız 8 i geçebileceğinden  burda mod taktiğini kullanıyorum
            textName=itemView.findViewById(R.id.text_name);
            textPrice=itemView.findViewById(R.id.text_price);
            textName.setText(cryptoModel.currency);
            textPrice.setText(cryptoModel.price);
        }
    }
}
