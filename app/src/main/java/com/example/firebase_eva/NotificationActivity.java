package com.example.firebase_eva;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class NotificationActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        imageView = findViewById(R.id.imageview_notification);
        textView = findViewById(R.id.textview_notification);

        String url = getIntent().getStringExtra("url");
        String message = getIntent().getStringExtra("mensagem");

        exibirNotificacao(url,message);

    }

    private void exibirNotificacao(String url, String message){

        Picasso.get().load(url).into(imageView);
        textView.setText(message);
    }
}