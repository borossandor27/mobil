package com.example.elethangapplication.cat;

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
import com.example.elethangapplication.login.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class CatAdapter extends RecyclerView.Adapter<CatAdapter.CatHolder> {
    Context context;
    List<Cat>catList;
    Cat cat;
    Boolean pozitiv;

    //private String url = "http://10.0.2.2:8000/api/catAdoptionLoggedin/";
    private String url = "http://192.168.0.48:8000/api/catAdoptionLoggedin/";

    public CatAdapter(Context context, List<Cat> catList) {
        this.context = context;
        this.catList = catList;
    }

    public void setCatList(List<Cat> catList) {
        this.catList = catList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CatAdapter.CatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_cats, parent, false);
        return new CatHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CatAdapter.CatHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.catName.setText(catList.get(position).getCatName());
        holder.description.setText(catList.get(position).getDescription());
        cat = catList.get(position);

        holder.btnadoption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences= context.getSharedPreferences("token", Context.MODE_PRIVATE);
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setPositiveButton("Virtuális örökbefogadás", (dialogInterface, i) -> {
                    cat = catList.get(position);
                    pozitiv = true;
                    RequestTask rt =new RequestTask();
                    rt.execute();
                });

                alert.setNegativeButton("Ált.örökbefogadás", (dialogInterface, i) -> {
                    cat = catList.get(position);
                    pozitiv = false;
                    RequestTask rt =new RequestTask();
                    rt.execute();
                });
                alert.create().show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return catList.size();
    }


    private class RequestTask extends AsyncTask<Void, Void, Response>{

        @Override
        protected Response doInBackground(Void... voids) {

            Response response = null;

            SharedPreferences sharedPreferences = context.getSharedPreferences("token", Context.MODE_PRIVATE);

            try {
                if (pozitiv){
                    response = RequestHandler.postAuth(url+cat.getId(),"{\"adoption_type_id\":2}", sharedPreferences.getString("token",""));
                }else {
                    response = RequestHandler.postAuth(url+cat.getId(),"{\"adoption_type_id\":1}", sharedPreferences.getString("token",""));
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


    public static class CatHolder extends RecyclerView.ViewHolder{
        TextView catName;
        TextView description;
        ImageView pictureCat;
        Button btnadoption;

        public CatHolder(@NonNull View itemView) {
            super(itemView);
            catName = itemView.findViewById(R.id.catName);
            description = itemView.findViewById(R.id.description);
            pictureCat = itemView.findViewById(R.id.pictureCat);
            btnadoption = itemView.findViewById(R.id.btnadoption);
        }
    }
}
