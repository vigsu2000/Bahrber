package com.example.vignesh.imageupload;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.UploadTask;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Bahrber:Main";

    Button chooseImg, uploadImg, retrieveImg1, retrieveImg2, nextPage;
    ImageView imgView1, imgView2;
    int PICK_IMAGE_REQUEST = 111;
    Uri filePath;
    ProgressDialog pd;
    DatabaseReference databaseReference;
    boolean gaveImage = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chooseImg = (Button)findViewById(R.id.chooseImg);
        uploadImg = (Button)findViewById(R.id.uploadImg);
        retrieveImg1 = (Button)findViewById(R.id.retrieveImg1);
        retrieveImg2 = (Button) findViewById(R.id.retrieveImg2);
        imgView1 = (ImageView)findViewById(R.id.imgView1);
        imgView2 = (ImageView) findViewById(R.id.imgView2);
        nextPage = (Button) findViewById(R.id.next);

        nextPage.getBackground().setAlpha(128);
        nextPage.setTextColor(Color.parseColor("#80000000"));

        pd = new ProgressDialog(this);
        pd.setMessage("Uploading....");

        //creating reference to firebase storage
        FirebaseStorage storage = FirebaseStorage.getInstance();
        final StorageReference storageRef = FirebaseStorage.getInstance().getReference();

        chooseImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
                nextPage.getBackground().setAlpha(255);
                nextPage.setTextColor(Color.parseColor("#FF000000"));
                gaveImage = true;
            }
        });

        uploadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(filePath != null) {
                    pd.show();

                    StorageReference childRef = storageRef.child("image.jpg");

                    //uploading the image
                    UploadTask uploadTask = childRef.putFile(filePath);

                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            pd.dismiss();
                            Toast.makeText(MainActivity.this, "Upload successful", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            pd.dismiss();
                            Toast.makeText(MainActivity.this, "Upload Failed -> " + e, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else {
                    Toast.makeText(MainActivity.this, "Select an image", Toast.LENGTH_SHORT).show();
                }
            }
        });

        retrieveImg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url1 = "https://firebasestorage.googleapis.com/v0/b/imageupload-ebbed.appspot.com/o/Long%20Hair%2F2A%2Fmens-long-messy-hair-683x1024.jpg?alt=media&token=321e2a58-161f-4660-88e0-e8bf94137d21";
                Glide.with(getApplicationContext()).load(url1).into(imgView1);
            }
        });

        retrieveImg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url2 = "https://firebasestorage.googleapis.com/v0/b/imageupload-ebbed.appspot.com/o/Long%20Hair%2F1B%2F40d36d5ba3673e20a9d2951bfbc10301.jpg?alt=media&token=5101588a-192b-413b-b6c2-417b4d631805";
                Glide.with(getApplicationContext()).load(url2).into(imgView2);
            }
        });

        nextPage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Log.d(TAG, "Next button clicked");
                if (gaveImage) {
                    Intent intent = new Intent(MainActivity.this, Result.class);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();

            try {
                //getting image from gallery
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);

                //Setting image to ImageView
                imgView1.setImageBitmap(bitmap);
                imgView2.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
