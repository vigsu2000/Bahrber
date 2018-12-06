package com.example.vignesh.imageupload;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class HelpScreen extends AppCompatActivity {
    private static final String TAG = "Bahrber:HelpScreen";

    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_screen);

        back = (Button)findViewById(R.id.helpBack);

        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Log.d(TAG, "Help button clicked");
                Intent intent = new Intent(HelpScreen.this, FormScreen.class);
                startActivity(intent);
            }
        });

    }
}
