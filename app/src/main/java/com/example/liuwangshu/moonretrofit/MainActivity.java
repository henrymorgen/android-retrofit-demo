package com.example.liuwangshu.moonretrofit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.liuwangshu.moonretrofit.model.IpModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public class MainActivity extends AppCompatActivity {
    private Button bt_request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt_request = (Button) findViewById(R.id.bt_request);
        bt_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getIpInformation("59.108.54.37");
            }
        });
    }

    private void getIpInformation(String ip) {
        String url = "http://ip.taobao.com/service/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                //增加返回值为String的支持
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IpService ipService = retrofit.create(IpService.class);
        Call<IpModel>call=ipService.getIpMsg(ip);
        call.enqueue(new Callback<IpModel>() {
            @Override
            public void onResponse(Call<IpModel> call, Response<IpModel> response) {
               String country= response.body().getData().getCountry();
                Log.i("wangshu","country"+country);
                Toast.makeText(getApplicationContext(),country,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<IpModel> call, Throwable t) {

            }
        });
    }
}
