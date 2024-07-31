package com.example.firebase_eva;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextCpf;
    private Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (isCpfSaved()){
            startMainActivity();
            return;
        }

        editTextCpf = findViewById(R.id.editTextCpf);
        buttonLogin = findViewById(R.id.buttonLogin);

        buttonLogin.setOnClickListener(view ->{
            String cpf = editTextCpf.getText().toString().trim();
            if (isValidCPF(cpf)) {
                saveCptToPreferences(cpf);
                startMainActivity();
                savePendingTokenIfAvailable(cpf);
            } else {
                Toast.makeText(this, "CPF inválido", Toast.LENGTH_LONG).show();
            }
        });
    }

    private boolean isCpfSaved(){
        SharedPreferences preferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        String cpf = preferences.getString("user_cpf", null);
        return cpf != null;
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

    private void savePendingTokenIfAvailable(String cpf){
        SharedPreferences preferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        String pendingToken = preferences.getString("pending_token", null);
        if (pendingToken != null){
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            Map<String, Object> tokenMap = new HashMap<>();
            tokenMap.put("token", pendingToken);

            db.collection("tokens")
                    .document(cpf)
                    .set(tokenMap)
                    .addOnSuccessListener(aVoid -> {
                        Log.d("FirebaseService", "Token salvo com sucesso");
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.remove("pending_token");
                        editor.apply();
                    })
                    .addOnFailureListener(e -> {
                        Log.e("FirebaseService", "Erro ao salvar o token", e);
                    });
        }
    }
}