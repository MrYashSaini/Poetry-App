package com.example.poetryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

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

public class updateActivity extends AppCompatActivity {
    EditText poetryData;
    AppCompatButton submitBtn;
    int poetryId;
    String poetryDataString;
    ApiInterface apiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        initialization();
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String p_data = poetryData.getText().toString();
                if(p_data.equals("")){
                    poetryData.setError("Field is Empty");
                }
                else{
                    callapi(p_data,poetryId+"");
                }
            }
        });
    }
    private void initialization(){
        poetryData = findViewById(R.id.updateentrybox);
        submitBtn = findViewById(R.id.update_poetry);
        poetryId = getIntent().getIntExtra("p_id",0);
        poetryDataString = getIntent().getStringExtra("p_data");
        poetryData.setText(poetryDataString);
        Retrofit retrofit = apiclient.getclient();
        apiInterface = retrofit.create(ApiInterface.class);
    }
    private void callapi(String pData,String pid){
        apiInterface.updatepoetry(pData,pid).enqueue(new Callback<DeleteResponse>() {
            @Override
            public void onResponse(Call<DeleteResponse> call, Response<DeleteResponse> response) {
                try{
                    if(response.body().getStatus().equals("1")){
                        Toast.makeText(updateActivity.this, "Data  Updated", Toast.LENGTH_SHORT).show();

                    }
                    else{
                        Toast.makeText(updateActivity.this, "Data Not Updated", Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception e) {
                    Log.e("catch",e.getLocalizedMessage());
                }
            }

            @Override
            public void onFailure(Call<DeleteResponse> call, Throwable t) {
                Log.e("failer",t.getLocalizedMessage());

            }
        });
    }
}