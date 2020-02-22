package com.example.travel.Asyntask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.view.Gravity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travel.Adapter.ClientAdapter;
import com.example.travel.Constants.Constants;
import com.example.travel.Models.Client;
import com.example.travel.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ClientAsyntask extends AsyncTask<String, Void, String> {

    private Activity activity;
    String serverUrl = Constants.urlCliente;
    ProgressDialog progressDialog;

    public ClientAsyntask(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Cargando...");
        progressDialog.setIndeterminate(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setGravity(Gravity.CENTER);
        progressDialog.show();
    }


    @Override
    protected String doInBackground(String... strings) {
        return cliente();
    }

    @Override
    protected void onPostExecute(String s) {
        if (s!=null){
            try {
                RecyclerView rvServices = activity.findViewById(R.id.rvCliente);
                LinearLayoutManager manager = new LinearLayoutManager(activity);
                manager.setOrientation(LinearLayoutManager.VERTICAL);
                ClientAdapter adapter =new ClientAdapter((List<Client>) buildList(s),activity);
                rvServices.setLayoutManager(manager);
                rvServices.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        progressDialog.dismiss();
    }

    private String cliente() {
        // Estos dos deben ser declarados fuera de la try / catch
        // Fin de que puedan ser cerradas en el bloque finally.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Contendra las respuesta del JSON en un Araylist
        String forecastJsonStr = null;

        try{

            URL url = new URL(serverUrl);

            //Crear el request para las materias del docente, abre una conexión
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setConnectTimeout(8000);
            urlConnection.connect();

            // lee Respons de entrada en una cadena
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }

            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Ya que es JSON , la adición de una nueva línea no es necesario ( no afectará el análisis sintáctico )
                // De modo hace que la depuración sea mucho más fácil
                // Búfer para la depuración.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            forecastJsonStr = buffer.toString();
            return forecastJsonStr;
        }catch (SocketTimeoutException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    // Log.e(LOG_TAG, "Error ", e);
                }
            }
        }

        return null;
    }

    private List<?> buildList(String arrayListordenes) throws JSONException {


        List<Client> ordenList = new ArrayList<>();
        JSONArray ordenArray = new JSONArray(arrayListordenes);

        for (int i = 0;i<ordenArray.length();i++){
            JSONObject orden = ordenArray.getJSONObject(i);

            String idclien = "";
            String nombreCliente = "";
            String lastClient = "";
            String email = "";
            String address = "";
            String phone = "";

            if (!orden.isNull("client_id")) {
                idclien = orden.getString("client_id");
            }
            if (!orden.isNull("first_name")) {
                nombreCliente = orden.getString("first_name");
            }
            if (!orden.isNull("last_name")) {
                lastClient = orden.getString("last_name");
            }
            if (!orden.isNull("email")) {
                email = orden.getString("email");
            }
            if (!orden.isNull("address")) {
                address = orden.getString("address");
            }
            if (!orden.isNull("phone")) {
                phone = orden.getString("phone");
            }

            Client ordenInterna = new Client();

            ordenInterna.setIdClient(idclien);
            ordenInterna.setName(nombreCliente);
            ordenInterna.setLastName(lastClient);
            ordenInterna.setEmail(email);
            ordenInterna.setAddress(address);
            ordenInterna.setPhone(phone);
            ordenList.add(ordenInterna);

        }

        return ordenList;
    }
}
