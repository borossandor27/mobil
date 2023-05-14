package com.example.elethangapplication.events;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elethangapplication.R;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventHolder> {

    Context context;
    List<Event> eventList;

    public EventAdapter(Context context, List<Event> eventList) {
        this.context = context;
        this.eventList = eventList;
    }
    public void setEventList(List<Event> eventList){
        this.eventList = eventList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public EventAdapter.EventHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_event, parent, false);
        return new EventHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventAdapter.EventHolder holder, int position) {
        holder.eventName.setText(eventList.get(position).getEvent_name());
        holder.eventDescription.setText(eventList.get(position).getDescription());
        holder.eventDate.setText(eventList.get(position).getEventDate());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Kattintás "+holder.getAdapterPosition(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public static class EventHolder extends RecyclerView.ViewHolder {

        TextView eventName;
        TextView eventDescription;
        TextView eventDate;

        public EventHolder(@NonNull View itemView) {
            super(itemView);
            eventName = itemView.findViewById(R.id.eventName);
            eventDescription = itemView.findViewById(R.id.eventDescription);
            eventDate = itemView.findViewById(R.id.eventDate);
        }
    }
}
