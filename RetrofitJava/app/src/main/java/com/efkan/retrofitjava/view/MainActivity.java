package com.efkan.retrofitjava.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.efkan.retrofitjava.R;
import com.efkan.retrofitjava.adapter.RecyclerViewAdapter;
import com.efkan.retrofitjava.model.CryptoModel;
import com.efkan.retrofitjava.service.CryptoAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    ArrayList<CryptoModel> cryptoModels;
    private String BASE_URL="https://raw.githubusercontent.com/";
    Retrofit retrofit;
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    CompositeDisposable compositeDisposable; //tek kullanımlık(temizlemeye işe yarar)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recyclerView);
        //Retrofit & JSON
        Gson gson=new GsonBuilder().setLenient().create();   //setlenient( JSON formatının standartlara tamamen uymadığı durumlarda toleranslı olmasını sağlar. )
        retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())   //
                // burayı sonradan ekledim . sonradan rxjava kütüphanesini kullandığım için burada retrofiti başlatırken belirtmem lazımdı .
                .build();

        loadData();
    }
    private void loadData(){
        final CryptoAPI cryptoAPI=retrofit.create(CryptoAPI.class);

        compositeDisposable=new CompositeDisposable();
        compositeDisposable.add(cryptoAPI.getData()
                .subscribeOn(Schedulers.io())   //arka plandaki uzun süren işlemleri yönetmek için kullanılır.
                .observeOn(AndroidSchedulers.mainThread()) //burada main threadde çalışacağını bildiriyoruz .
                .subscribe(this::handlerResponse));   //ortaya çıkan şeyide burda ele alıcaz


        /*Call<List<CryptoModel>> call=cryptoAPI.getData();
        call.enqueue(new Callback<List<CryptoModel>>() {
            @Override
            public void onResponse(Call<List<CryptoModel>> call, Response<List<CryptoModel>> response) {
                List<CryptoModel> responseList=response.body();
                cryptoModels=new ArrayList<>(responseList);

                //RecyclerView
                recyclerView.setLayoutManager( new LinearLayoutManager(MainActivity.this));
                recyclerViewAdapter=new RecyclerViewAdapter(cryptoModels);
                recyclerView.setAdapter(recyclerViewAdapter);




                for(CryptoModel cryptoModel:cryptoModels){
                    System.out.println(cryptoModel.currency);
                    System.out.println(cryptoModel.price);
                }

            }

            @Override
            public void onFailure(Call<List<CryptoModel>> call, Throwable t) {
            t.printStackTrace();
            }
        });
*/ //burası rx kullanmadan
    }
    private void handlerResponse(List<CryptoModel> cryptoModelList){
        cryptoModels=new ArrayList<>(cryptoModelList);

        //RecyclerView
        recyclerView.setLayoutManager( new LinearLayoutManager(MainActivity.this));
        recyclerViewAdapter=new RecyclerViewAdapter(cryptoModels);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    //https://raw.githubusercontent.com/atilsamancioglu/K21-JSONDataSet/master/crypto.json
}