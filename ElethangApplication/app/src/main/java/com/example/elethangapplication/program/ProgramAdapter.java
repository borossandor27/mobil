package com.example.elethangapplication.program;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elethangapplication.R;
import com.example.elethangapplication.RequestHandler;
import com.example.elethangapplication.Response;
import com.example.elethangapplication.cat.CatAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class ProgramAdapter extends RecyclerView.Adapter<ProgramAdapter.ProgramHolder> {

    Context context;
    List<Program>programList;
    Program program;

    //private String url = "http://10.0.2.2:8000/api/storeProgramApplication";
    private String url = "http://192.168.0.48:8000/api/storeProgramApplication";

    public ProgramAdapter(Context context, List<Program> programList) {
        this.context = context;
        this.programList = programList;
    }

    public void setProgramList(List<Program> programList) {
        this.programList = programList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProgramAdapter.ProgramHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_programs, parent, false);
        return new ProgramHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProgramAdapter.ProgramHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.programType.setText(programList.get(position).getType());
        holder.date.setText(programList.get(position).getDate());
        holder.time.setText(programList.get(position).getTime());
        

        
        holder.btnjelentkezik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setMessage("Biztos szeretne jelentkezni?");
                alert.setPositiveButton("Igen", (dialogInterface, i) -> {
                    program = programList.get(position);
                    RequestTask rt =new RequestTask();
                    rt.execute();
                });
                alert.setNegativeButton("Nem", (dialogInterface, i) -> {
                    dialogInterface.cancel();
                });
                AlertDialog alertDialog = alert.create();
                alertDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return programList.size();
    }

    private class RequestTask extends AsyncTask<Void, Void, Response>{

        @Override
        protected Response doInBackground(Void... voids) {

            Response response = null;

            SharedPreferences sharedPreferences = context.getSharedPreferences("token", Context.MODE_PRIVATE);

            try {
                    String program_info_id = "{\"program_info_id\":"+program.getId()+"}";
                    response = RequestHandler.postAuth(url,program_info_id, sharedPreferences.getString("token",""));


            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(Response response) {
            super.onPostExecute(response);
            if (response.getResponseCode()<400){
                Toast.makeText(context.getApplicationContext(), "Sikeres jelentkezés", Toast.LENGTH_SHORT).show();
            }

        }
    }

    public static class ProgramHolder extends RecyclerView.ViewHolder{
        TextView programType;
        TextView date;
        TextView time;
        Button btnjelentkezik;

        public ProgramHolder(@NonNull View itemView) {
            super(itemView);
            programType = itemView.findViewById(R.id.programType);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
            btnjelentkezik = itemView.findViewById(R.id.btnjelentkezik);
        }
    }
}
