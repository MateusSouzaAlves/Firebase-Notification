package com.example.firebase_eva;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextCpf;
    private Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextCpf = findViewById(R.id.editTextCpf);
        buttonLogin = findViewById(R.id.buttonLogin);

        buttonLogin.setOnClickListener(view ->{
            String cpf = editTextCpf.getText().toString().trim();
            if (isValidCPF(cpf)) {
                saveCptToPreferences(cpf);
                startMainActivity();
            } else {
                Toast.makeText(this, "CPF inválido", Toast.LENGTH_LONG).show();
            }
        });
    }

    private boolean isValidCPF(String cpf){
        CPFValidator validator = new CPFValidator();
        try {
            validator.assertValid(cpf);
            return true;
        } catch (InvalidStateException e){
            return false;
        }
    }

    private void saveCptToPreferences(String cpf){
        SharedPreferences preferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();;
        editor.putString("user_cpf", cpf);
        editor.apply();
    }

    private void startMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}