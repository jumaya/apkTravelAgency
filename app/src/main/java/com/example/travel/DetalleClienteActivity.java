package com.example.travel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class DetalleClienteActivity extends AppCompatActivity {

    TextView nombre, tel, dir, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_cliente);

        nombre = (TextView) findViewById(R.id.txtNombre);
        tel = (TextView) findViewById(R.id.txtPhone);
        dir = (TextView) findViewById(R.id.txtDir);
        email = (TextView) findViewById(R.id.txtEmail);

        String name =  getIntent().getStringExtra("nombre");
        String phone =  getIntent().getStringExtra("tel");
        String address =  getIntent().getStringExtra("address");
        String mail =  getIntent().getStringExtra("email");

        nombre.setText(name);
        tel.setText(phone);
        dir.setText(address);
        email.setText(mail);
    }
}
