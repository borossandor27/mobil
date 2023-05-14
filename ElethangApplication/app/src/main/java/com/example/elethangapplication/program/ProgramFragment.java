package com.example.elethangapplication.program;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.elethangapplication.R;
import com.example.elethangapplication.RequestHandler;
import com.example.elethangapplication.Response;
import com.example.elethangapplication.cat.Cat;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ProgramFragment extends Fragment {

    private class RequestTask extends AsyncTask<Void, Void, Response> {

        @Override
        protected Response doInBackground(Void... voids) {
            Response response = null;
            try {
                response = RequestHandler.get(url);
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
                Program[] programs = converter.fromJson(response.getContent(), Program[].class);
                programList.clear();
                programList.addAll(Arrays.asList(programs));
                programAdapter.setProgramList(programList);
            }
        }
    }

    private RecyclerView recyclerView;
    private ProgramAdapter programAdapter;
    private List<Program>programList;

    //private String url = "http://10.0.2.2:8000/api/programinfo";
    private String url = "http://192.168.0.48:8000/api/programinfo";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_program, container, false);
        init(view);
        recyclerView.setAdapter(programAdapter);
        return view;
    }
    public void init(View view){
        recyclerView = view.findViewById(R.id.recyclerViewPrograms);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        programList = new ArrayList<>();
        programAdapter = new ProgramAdapter(getContext(), programList);
        RequestTask task = new RequestTask();
        task.execute();

    }
}