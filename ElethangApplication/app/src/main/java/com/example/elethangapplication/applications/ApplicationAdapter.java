package com.example.elethangapplication.applications;

import android.annotation.SuppressLint;
import android.content.Context;

import android.content.DialogInterface;
import android.os.AsyncTask;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elethangapplication.MainActivity;
import com.example.elethangapplication.R;
import com.example.elethangapplication.RequestHandler;
import com.example.elethangapplication.Response;

import java.io.IOException;
import java.util.List;

public class ApplicationAdapter extends RecyclerView.Adapter<ApplicationAdapter.ApplicationHolder> {
    Context context;
    List<Applications>applicationsList;
    Applications applications;

    //private  String url ="http://10.0.2.2:8000/api/deleteProgramApplication/";
    private  String url ="http://192.168.0.48:8000/api/deleteProgramApplication/";

    public ApplicationAdapter(Context context, List<Applications> applicationsList) {
        this.context = context;
        this.applicationsList = applicationsList;
    }

    public void setApplicationsList(List<Applications> applicationsList) {
        this.applicationsList = applicationsList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ApplicationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_application, parent, false);
        return new ApplicationHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ApplicationHolder holder, @SuppressLint("RecyclerView") int position) {

        switch (applicationsList.get(position).getProgram_info_id()) {
            case 1:
                holder.application.setText("Kutyaterápia gyerekeknek\n\t 2022-06-25 10:00");
                break;
            case 2:
                holder.application.setText("Kutyaterápia gyerekeknek\n\t 2022-06-25 12:00");
                break;

            case 3:
                holder.application.setText("Kutyaterápia gyerekeknek\n\t 2022-06-25 14:00");
                break;

            case 4:
                holder.application.setText("Kutyaterápia gyerekeknek\n\t 2022-06-25 16:00");
                break;

            case 5:
                holder.application.setText("Kutya sétáltatás\n\t 2022-06-28 10:00");
                break;

            case 6:
                holder.application.setText("Kutya sétáltatás\n\t 2022-06-28 12:00");
                break;

            case 7:
                holder.application.setText("Kutya sétáltatás\n\t 2022-06-28 14:00");
                break;

            case 8:
                holder.application.setText("Kutya sétáltatás\n\t 2022-06-28 16:00");
                break;

            default:
                holder.application.setText("Nincs");
                break;

        }
        holder.deleteApplication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AlertDialog.Builder builder = new  AlertDialog.Builder(context);
                builder.setMessage("Biztos szeretné törölni?");
                builder.setNegativeButton("Igen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        applications = applicationsList.get(position);
                        RequestTask task = new RequestTask();
                        task.execute();
                    }
                });
                builder.setPositiveButton("Nem", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return applicationsList.size();
    }

    private class RequestTask extends AsyncTask<Void, Void, Response>{

        @Override
        protected Response doInBackground(Void... voids) {
            Response response = null;


            try {

                response = RequestHandler.delete(url+applications.getId());


            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(Response response) {
            super.onPostExecute(response);
            if (response.getResponseCode()<400){

                Toast.makeText(context.getApplicationContext(), "Sikeres Törlés", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static class ApplicationHolder extends RecyclerView.ViewHolder{

        TextView application;
        ImageView deleteApplication;

        public ApplicationHolder(@NonNull View itemView) {
            super(itemView);
            application = itemView.findViewById(R.id.application);
            deleteApplication = itemView.findViewById(R.id.deleteApplication);
        }

    }
}
