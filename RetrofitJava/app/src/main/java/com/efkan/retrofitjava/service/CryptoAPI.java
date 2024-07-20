package com.efkan.retrofitjava.service;

import com.efkan.retrofitjava.model.CryptoModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;

public interface CryptoAPI {
    ////https://raw.githubusercontent.com/atilsamancioglu/K21-JSONDataSet/master/crypto.json
    @GET("atilsamancioglu/K21-JSONDataSet/master/crypto.json")
    Observable<List<CryptoModel>> getData();  //bir değişiklik oldğunda bu değişikliği bildirir diyebiliriz
    //Call<List<CryptoModel>> getData();      //burası rx kullanmadan

}
