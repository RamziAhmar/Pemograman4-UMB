package com.example.utspemograman4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Login extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btLogin;
    private TextView tvRegister;

    private DatabaseHelperLogin db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btLogin = findViewById(R.id.btLogin);
        tvRegister = findViewById(R.id.tvRegister);

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register.newInstance().show(getSupportFragmentManager(), Register.TAG);
            }
        });

        db = new DatabaseHelperLogin(this);

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getUsername = etUsername.getText().toString();
                String getPassword = etPassword.getText().toString();

                if (getPassword.isEmpty() && getPassword.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Username atau Password salah!", Toast.LENGTH_LONG).show();
                } else {
                    Boolean masuk = db.checkLogin(getUsername, getUsername);
                    if (masuk == true) {
                        boolean updateSession = db.upgradeSession("ada", 1);
                        if (updateSession == true) {
                            Toast.makeText(getApplicationContext(), "Berhasil Masuk", Toast.LENGTH_LONG).show();

                            Intent dashboard = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(dashboard);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Gagal Masuk", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });
    }
}