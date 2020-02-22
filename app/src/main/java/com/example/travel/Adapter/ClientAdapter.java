package com.example.travel.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travel.DetalleClienteActivity;
import com.example.travel.Models.Client;
import com.example.travel.R;

import java.util.List;

public class ClientAdapter  extends RecyclerView.Adapter<ClientAdapter.ClientHolder>{

    private List<Client> clients;
    private Activity activity;

    public ClientAdapter(List<Client> clients, Activity activity) {
        this.clients = clients;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ClientHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview_client, viewGroup, false);
        return new ClientHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientHolder holder, int i) {

        final Client orden = clients.get(i);
        holder.nombreCliente.setText(orden.getName()+" "+orden.getLastName());

        holder.cvClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, DetalleClienteActivity.class);
                intent.putExtra("nombre",orden.getName()+" "+orden.getLastName());
                intent.putExtra("email",orden.getEmail());
                intent.putExtra("tel",orden.getPhone());
                intent.putExtra("address",orden.getAddress());
                activity.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return clients.size();
    }

    class ClientHolder extends RecyclerView.ViewHolder {

        public TextView nombreCliente;
        CardView cvClient;

        public ClientHolder  (@NonNull View itemView) {
            super(itemView);

            nombreCliente = (TextView) itemView.findViewById(R.id.txtNombre);
            cvClient = itemView.findViewById(R.id.cvClient);



        }
    }
}
