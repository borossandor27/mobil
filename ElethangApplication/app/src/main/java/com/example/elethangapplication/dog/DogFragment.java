package com.example.elethangapplication.dog;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elethangapplication.R;
import com.example.elethangapplication.RequestHandler;
import com.example.elethangapplication.Response;
import com.example.elethangapplication.cat.Cat;
import com.example.elethangapplication.cat.CatAdapter;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DogFragment extends Fragment {
    private class RequestTask extends AsyncTask<Void,Void, Response>{

        @Override
        protected Response doInBackground(Void... voids) {
            Response response = null;
            try {
                response = RequestHandler.get(url);
            }catch (IOException e){
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(Response response) {
            super.onPostExecute(response);
            if (response != null) {
                Gson converter = new Gson();
                Dog[] dogs = converter.fromJson(response.getContent(), Dog[].class);
                dogList.clear();
                dogList.addAll(Arrays.asList(dogs));
                dogAdapter.setDogList(dogList);
            }
        }
    }


    private RecyclerView recyclerView;
    private DogAdapter dogAdapter;
    private List<Dog> dogList;

    //eduroam
    //private String url = "http://10.148.149.41:8000/api/dog";
    //otthon laptop
    //private String url = "http://192.168.0.210:8000/api/dog";
    //otthon gép
    private String url = "http://192.168.0.48:8000/api/dog";
    //emulator
    //private String url = "http://10.0.2.2:8000/api/dog";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dog, container, false);
        init(view);
        recyclerView.setAdapter(dogAdapter);
        return view;
    }
    public void init(View view){
        recyclerView = view.findViewById(R.id.recyclerViewDogs);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        dogList=new ArrayList<>();
        dogAdapter = new DogAdapter(getContext(), dogList);
        RequestTask task = new RequestTask();
        task.execute();
    }

}
