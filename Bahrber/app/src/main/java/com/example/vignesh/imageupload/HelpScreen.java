package com.example.vignesh.imageupload;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HelpScreen extends AppCompatActivity {
    private static final String TAG = "Bahrber:HelpScreen";

    Button back;
    TextView help;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_screen);

        back = (Button)findViewById(R.id.helpBack);
        help = (TextView)findViewById(R.id.hairDescription);

        Spanned helpText = Html.fromHtml( getString(R.string.hair_description));
        help.setText(helpText);

        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Log.d(TAG, "Help button clicked");
                Intent intent = new Intent(HelpScreen.this, FormScreen.class);
                startActivity(intent);
            }
        });

    }
}
