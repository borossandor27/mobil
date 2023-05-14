package com.example.elethangapplication.dog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elethangapplication.R;
import com.example.elethangapplication.RequestHandler;
import com.example.elethangapplication.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class DogAdapter extends RecyclerView.Adapter<DogAdapter.DogHolder> {

    Context context;
    List<Dog> dogList;
    Dog dog;
    Boolean pozitiv;

    //private String url = "http://10.0.2.2:8000/api/dogAdoptionLoggedin/";
    private String url = "http://192.168.0.48:8000/api/dogAdoptionLoggedin/";

    public DogAdapter(Context context, List<Dog> dogList) {
        this.context = context;
        this.dogList = dogList;
    }
    public void setDogList(List<Dog> dogList){
        this.dogList = dogList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DogAdapter.DogHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_dogs, parent, false);
        return new DogHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DogHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.dogName.setText(dogList.get(position).getDogName());
        holder.dogDescription.setText(dogList.get(position).getDogDescription());
        dog = dogList.get(position);

        holder.btnDogAdoption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = context.getSharedPreferences("token", Context.MODE_PRIVATE);
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setPositiveButton("Virtuális örökbefogadás", (dialogInterface, i) -> {
                    dog = dogList.get(position);
                    pozitiv = true;
                    RequestTask task = new RequestTask();
                    task.execute();
                });
                alert.setNegativeButton("Általános Örökbefogadás", (dialogInterface, i) -> {
                    dog = dogList.get(position);
                    pozitiv = false;
                    RequestTask task = new RequestTask();
                    task.execute();
                });
                alert.create().show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return dogList.size();
    }

    private class RequestTask extends AsyncTask<Void, Void, Response>{

        @Override
        protected Response doInBackground(Void... voids) {

            Response response = null;

            SharedPreferences sharedPreferences = context.getSharedPreferences("token", Context.MODE_PRIVATE);

            try {
                if (pozitiv){
                    response = RequestHandler.postAuth(url+dog.getId(),"{\"adoption_type_id\":2}", sharedPreferences.getString("token",""));
                }else {
                    response = RequestHandler.postAuth(url+dog.getId(),"{\"adoption_type_id\":1}", sharedPreferences.getString("token",""));
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(Response response) {
            super.onPostExecute(response);
            JSONObject jsonObject;
            String message;
            try {
                jsonObject = new JSONObject(response.getContent());
                message = jsonObject.getString("message");
                Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_SHORT).show();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    public static class DogHolder extends RecyclerView.ViewHolder{

        TextView dogName;
        TextView dogDescription;
        ImageView dogimage;
        Button btnDogAdoption;
        public DogHolder(@NonNull View itemView) {
            super(itemView);
            dogName = itemView.findViewById(R.id.dogName);
            dogDescription = itemView.findViewById(R.id.dogDescription);
            dogimage = itemView.findViewById(R.id.pictureDog);
            btnDogAdoption = itemView.findViewById(R.id.btnDogadoption);
        }
    }
}
