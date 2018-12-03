package com.example.vignesh.imageupload;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

import java.text.Normalizer;

public class Home extends AppCompatActivity {
    private static final String TAG = "Bahrber:Home";

    Button nextPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        nextPage = (Button) findViewById(R.id.toMain);

        nextPage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Log.d(TAG, "Next button clicked");
                Intent intent = new Intent(Home.this, FormScreen.class);
                startActivity(intent);
            }
        });
    }
}