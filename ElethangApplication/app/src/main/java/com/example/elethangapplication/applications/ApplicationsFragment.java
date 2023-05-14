package com.example.elethangapplication.applications;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.elethangapplication.R;
import com.example.elethangapplication.RequestHandler;
import com.example.elethangapplication.Response;
import com.example.elethangapplication.cat.Cat;
import com.example.elethangapplication.cat.CatAdapter;
import com.example.elethangapplication.cat.CatFragment;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ApplicationsFragment extends Fragment {

    private class RequestTask extends AsyncTask<Void, Void, Response>{

        @Override
        protected Response doInBackground(Void... voids) {
            Response response = null;
            try {

                SharedPreferences sharedPreferences= getActivity().getSharedPreferences("token", Context.MODE_PRIVATE);
                response = RequestHandler.getAuth(url, sharedPreferences.getString("token",""));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(Response response) {
            super.onPostExecute(response);
            if (response != null) {
                Gson converter = new Gson();
                Applications[] applications = converter.fromJson(response.getContent(), Applications[].class);
                applicationsList.clear();
                applicationsList.addAll(Arrays.asList(applications));
                applicationAdapter.setApplicationsList(applicationsList);
                Log.d("Kiir", response.getContent());
            }
        }
    }

    private RecyclerView recyclerViewApplications;
    private ApplicationAdapter applicationAdapter;
    private List<Applications> applicationsList;
    //private String url = "http://10.0.2.2:8000/api/showApplication";
    private String url = "http://192.168.0.48:8000/api/showApplication";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_applications, container, false);
        init(view);
        recyclerViewApplications.setAdapter(applicationAdapter);
        return view;
    }

    private void init(View view) {
        recyclerViewApplications = view.findViewById(R.id.recyclerViewApplications);
        recyclerViewApplications.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerViewApplications.setLayoutManager(layoutManager);
        applicationsList=new ArrayList<>();
        applicationAdapter= new ApplicationAdapter(getContext(),applicationsList);
        RequestTask task = new RequestTask();
        task.execute();
    }
}