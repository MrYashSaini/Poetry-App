package com.example.poetryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.poetryapp.Response.GetPoetryResponse;
import com.example.poetryapp.adapters.PoetryAdapter;
import com.example.poetryapp.api.ApiInterface;
import com.example.poetryapp.api.apiclient;
import com.example.poetryapp.model.PoetryModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    PoetryAdapter poetryAdapter;
    ApiInterface apiInterface;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.main_toolbar);

        intialization();
//        setSupportActionBar(toolbar);
        getdata();
    }
    private void intialization(){
        recyclerView = findViewById(R.id.poetryRecyleView);
        Retrofit retrofit = apiclient.getclient();
        apiInterface = retrofit.create(ApiInterface.class);


    }
    private void setadapter(List<PoetryModel> poetryModels){
        poetryAdapter = new PoetryAdapter(this,poetryModels);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(poetryAdapter);
    }
    private void getdata(){
        apiInterface.getpoetry().enqueue(new Callback<GetPoetryResponse>() {
            @Override
            public void onResponse(Call<GetPoetryResponse> call, Response<GetPoetryResponse> response){
                try{
                    if (response.body().getStatus().equals("1")){
                        setadapter(response.body().getData());
                    }
                    else{
                        Toast.makeText(MainActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception e){
                    Log.e("catch",e.getLocalizedMessage());

                }
            }

            @Override
            public void onFailure(Call<GetPoetryResponse> call, Throwable t) {
                Log.e("failer",t.getLocalizedMessage());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_property:
                Intent intent = new Intent(MainActivity.this,Add_Activity.class);
                startActivity(intent);
                return true;
            default:

                return super.onOptionsItemSelected(item);
        }

    }
}