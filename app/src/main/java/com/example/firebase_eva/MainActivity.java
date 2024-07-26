package com.example.firebase_eva;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Switch switchEs;
    private Switch switchMt;
    private Button buttonLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        switchEs = findViewById(R.id.switch_es);
        switchMt = findViewById(R.id.switch_mt);
        buttonLogout = findViewById(R.id.button_logout);

        switchEs.setOnClickListener(view -> {
            if (switchEs.isChecked()) {
                Toast.makeText(getBaseContext(), "Switch Es está ligado", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getBaseContext(), "Switch Es está desligado", Toast.LENGTH_LONG).show();
            }
        });

        switchMt.setOnClickListener(view -> {
            if (switchMt.isChecked()) {
                Toast.makeText(getBaseContext(), "Switch MT está ligado", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getBaseContext(), "Switch MT está desligado", Toast.LENGTH_LONG).show();
            }
        });

        buttonLogout.setOnClickListener(view -> Toast.makeText(getBaseContext(), "Botão Proibido", Toast.LENGTH_LONG).show());
    }
}
