package com.example.firebase_eva;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.firebase_eva.util.NetworkUtil;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    private Switch switchEs;
    private Switch switchMt;
    private Button buttonLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!isCpfSaved()) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        setContentView(R.layout.activity_main);

        switchEs = findViewById(R.id.switch_es);
        switchMt = findViewById(R.id.switch_mt);
        buttonLogout = findViewById(R.id.button_logout);

        FirebaseMessaging.getInstance().subscribeToTopic("all")
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d("FirebaseService", "Inscrito no tópico all");
                    } else {
                        Log.d("FirebaseService", "Falha na inscrição no tópico all");
                    }
                });
        switchEs.setOnClickListener(view -> handleSwitchClick(switchEs, "ES"));
        switchMt.setOnClickListener(view -> handleSwitchClick(switchMt, "MT"));

        buttonLogout.setOnClickListener(view ->
                Toast.makeText(getBaseContext(), "Botão Proibido", Toast.LENGTH_SHORT).show());
    }

    private boolean isCpfSaved(){
        SharedPreferences preferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        String cpf = preferences.getString("user_cpf", null);
        return cpf != null;
    }

    private void handleSwitchClick(Switch switchView, String topic) {
        if (!NetworkUtil.isNetworkAvailable(this)) {
            showToast("Sem conexão de rede");
            switchView.setChecked(false);
            return;
        }

        if (switchView.isChecked()) {
            subscribeToTopic(topic, switchView);
        } else {
            unsubscribeFromTopic(topic, switchView);
        }
    }

    private void subscribeToTopic(String topic, Switch switchView) {
        FirebaseMessaging.getInstance().subscribeToTopic(topic)
                .addOnCompleteListener(task -> handleTaskCompletion(task, "Inscrito no tópico " + topic, "Falha na inscrição no tópico " + topic, switchView));
    }

    private void unsubscribeFromTopic(String topic, Switch switchView) {
        FirebaseMessaging.getInstance().unsubscribeFromTopic(topic)
                .addOnCompleteListener(task -> handleTaskCompletion(task, "Desinscrito do tópico " + topic, "Falha na desinscrição do tópico " + topic, switchView));
    }

    private void handleTaskCompletion(Task<Void> task, String successMessage, String failureMessage, Switch switchView) {
        if (task.isSuccessful()) {
            showToast(successMessage);
        } else {
            switchView.setChecked(false);
            showToast(failureMessage);
        }
    }

    private void showToast(String message) {
        Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT).show();
    }


}
