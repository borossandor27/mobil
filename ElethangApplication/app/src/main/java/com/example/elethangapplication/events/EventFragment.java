package com.example.elethangapplication.events;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.elethangapplication.R;
import com.example.elethangapplication.RequestHandler;
import com.example.elethangapplication.Response;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class EventFragment extends Fragment {
    private class RequestTask extends AsyncTask<Void,Void, Response> {

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
                Event[] events = converter.fromJson(response.getContent(), Event[].class);
                eventList.clear();
                eventList.addAll(Arrays.asList(events));
                eventAdapter.setEventList(eventList);
            }
        }
    }

    private RecyclerView recyclerView;
    private EventAdapter eventAdapter;
    private List<Event> eventList;

    //eduroam
    //private String url = "http://10.148.149.41:8000/api/event";
    //otthon laptop
    //private String url = "http://192.168.0.210:8000/api/event";
    //otthon gép
    private String url = "http://192.168.0.48:8000/api/event";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event, container, false);
        init(view);
        recyclerView.setAdapter(eventAdapter);
        return view;
    }

    private void init(View view) {
        recyclerView = view.findViewById(R.id.recyclerViewEvent);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        eventList = new ArrayList<>();
        eventAdapter = new EventAdapter(getContext(), eventList);
        RequestTask task = new RequestTask();
        task.execute();

    }
}