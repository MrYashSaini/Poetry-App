package com.example.poetryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.poetryapp.Response.DeleteResponse;
import com.example.poetryapp.api.ApiInterface;
import com.example.poetryapp.api.apiclient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Add_Activity extends AppCompatActivity {
    Toolbar toolbar;
    EditText poetry_box,poet_box;
    AppCompatButton submit;
    ApiInterface apiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        intiallization();
        setuptoolbar();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String poetrydatastring = poetry_box.getText().toString();
                String poetnamestring  = poet_box.getText().toString();
                if (poetrydatastring.equals("")){
                    poetry_box.setError("Field is empty");
                }
                else{
                    if(poet_box.equals("")){
                        poet_box.setError("Field is empty");
                    }
                    else{
                        Toast.makeText(Add_Activity.this, "cakk", Toast.LENGTH_SHORT).show();
                        callapi(poetrydatastring,poetnamestring);
                    }
                }
            }
        });
    }
    private void intiallization(){
        toolbar = findViewById(R.id.toolbar_add);
        poet_box = findViewById(R.id.poet_entrybox);
        poetry_box = findViewById(R.id.poetry_entrybox);
        submit = findViewById(R.id.sumbit_poetry);
        Retrofit retrofit = apiclient.getclient();
        apiInterface = retrofit.create(ApiInterface.class);
    }
    private void setuptoolbar(){
//        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void callapi(String poetryData,String poetname){
        apiInterface.addpoetry(poetryData,poetname).enqueue(new Callback<DeleteResponse>() {
            @Override
            public void onResponse(Call<DeleteResponse> call, Response<DeleteResponse> response) {
                try{
                    if(response.body().getStatus().equals("1")){
                        Toast.makeText(Add_Activity.this, "Add Successful", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(Add_Activity.this, "Not Add Successful", Toast.LENGTH_SHORT).show();

                    }
                }
                catch (Exception e){
                    Log.e("exception",e.getLocalizedMessage());

                }

            }

            @Override
            public void onFailure(Call<DeleteResponse> call, Throwable t) {
                Log.e("failer",t.getLocalizedMessage());
            }
        });
    }
}