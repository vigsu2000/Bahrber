package com.example.vignesh.imageupload;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Result extends AppCompatActivity {
    private static final String TAG = "Bahrber:Result";

    Button retrieveImg1, retrieveImg2;
    ImageView imgView1, imgView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        retrieveImg1 = (Button)findViewById(R.id.getResult);
        retrieveImg2 = (Button) findViewById(R.id.getResult2);

        imgView1 = (ImageView)findViewById(R.id.imgView3);
        imgView2 = (ImageView) findViewById(R.id.imgView4);

        //creating reference to firebase storage
        FirebaseStorage storage = FirebaseStorage.getInstance();
        final StorageReference storageRef = FirebaseStorage.getInstance().getReference();

        retrieveImg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Result 1 button clicked");
                String url1 = "https://firebasestorage.googleapis.com/v0/b/imageupload-ebbed.appspot.com/o/Long%20Hair%2F2A%2Fmens-long-messy-hair-683x1024.jpg?alt=media&token=321e2a58-161f-4660-88e0-e8bf94137d21";
                Glide.with(getApplicationContext()).load(url1).into(imgView1);
            }
        });

        retrieveImg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Result 2 button clicked");
                String url2 = "https://firebasestorage.googleapis.com/v0/b/imageupload-ebbed.appspot.com/o/Long%20Hair%2F1B%2F40d36d5ba3673e20a9d2951bfbc10301.jpg?alt=media&token=5101588a-192b-413b-b6c2-417b4d631805";
                Glide.with(getApplicationContext()).load(url2).into(imgView2);
            }
        });
    }
}
