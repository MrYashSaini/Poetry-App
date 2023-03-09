package com.example.poetryapp.adapters;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.poetryapp.R;
import com.example.poetryapp.Response.DeleteResponse;
import com.example.poetryapp.api.ApiInterface;
import com.example.poetryapp.api.apiclient;
import com.example.poetryapp.model.PoetryModel;
import com.example.poetryapp.updateActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PoetryAdapter extends RecyclerView.Adapter<PoetryAdapter.ViewHolder> {
    Context context;
    List<PoetryModel> poetryModels;
    ApiInterface apiInterface;

    public PoetryAdapter(Context context, List<PoetryModel> poetryModels) {
        this.context = context;
        this.poetryModels = poetryModels;
        Retrofit retrofit = apiclient.getclient();
        apiInterface = retrofit.create(ApiInterface.class);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.poerty_design_view,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    holder.poetname.setText(poetryModels.get(position).getPoetname());
    holder.poetry.setText(poetryModels.get(position).getPoetry());
    holder.datetime.setText(poetryModels.get(position).getDate_time());
    holder.delete.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            deletepoetry(poetryModels.get(position).getId()+"",position);
        }
    });
    holder.update.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, updateActivity.class);
            intent.putExtra("p_id",poetryModels.get(position).getId());
            intent.putExtra("p_data",poetryModels.get(position).getPoetry());
            context.startActivity(intent);

        }
    });
    }

    @Override
    public int getItemCount() {
        return poetryModels.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView poetname,poetry,datetime;
        Button update,delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            poetname = itemView.findViewById(R.id.poetnameview);
            poetry = itemView.findViewById(R.id.poetryshowview);
            datetime = itemView.findViewById(R.id.dateandtimeview);
            update = itemView.findViewById(R.id.upatebutton);
            delete = itemView.findViewById(R.id.deletebutton);
        }
    }
    private void deletepoetry(String id,int pos){
        apiInterface.deletepoetry(id).enqueue(new Callback<DeleteResponse>() {
            @Override
            public void onResponse(Call<DeleteResponse> call, Response<DeleteResponse> response) {
                try {
                    if(response!=null){
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();
                        if(response.body().getStatus().equals("1")){
                            poetryModels.remove(pos);
                            notifyDataSetChanged();

                        }
                    }

                }catch (Exception e) {
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
