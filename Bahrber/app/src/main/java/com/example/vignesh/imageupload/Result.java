package com.example.vignesh.imageupload;

import java.io.*;
import android.app.*;
import android.content.*;
import android.media.Image;
import android.os.*;
import android.util.Log;
import android.graphics.*;
import com.microsoft.projectoxford.face.*;
import com.microsoft.projectoxford.face.contract.*;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.*;


public class Result extends Activity {

    private final String apiEndpoint = "https://westus.api.cognitive.microsoft.com/face/v1.0";
    private final String subscriptionKey = "fd5fc95df0344e5c8b7f65f0ff5a483b";

    private final FaceServiceClient faceServiceClient =
            new FaceServiceRestClient(apiEndpoint, subscriptionKey);

    private final int PICK_IMAGE = 1;
    private ProgressDialog detectionProgressDialog;

    private ArrayList<Bitmap> bmapList, bmapListCopy;
    List<UUID> allUUID = new ArrayList<>();
    SimilarFace[] allSimilarFaces;

    public Bitmap targetBitmap;
    private UUID targetID;

    public static final String TAG = "Log";


    Uri filePath;
    ProgressDialog pd;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseRef = database.getReference();
    private FirebaseFirestore mFirestore;
    private Query mQuery;

    public ImageView imageView1;

    /* Hashmap to represent each Haircut. Represented by url as the first parameter and the hairtype as the secondParameter.
     */
    private HashMap<Object, Object> longHairPictures;
    private HashMap<Object, Object> mediumHairPictures;
    private HashMap<Object, Object> shortHairPictures;
    private HashMap<UUID, Integer> facemap = new HashMap<>();

    private int index = 0;

    private String hairLength;
    private String hairType;

    private Button button1, addImages, findSimilarFace, printFace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Intent activityThatCalled = getIntent();
        hairLength = activityThatCalled.getExtras().getString("length");
        hairType = activityThatCalled.getExtras().getString("type");
        longHairPictures = new HashMap<Object, Object>();
        shortHairPictures = new HashMap<Object, Object>();
        mediumHairPictures = new HashMap<Object, Object>();

        pd = new ProgressDialog(this);
        pd.setMessage("Uploading....");
        mFirestore = FirebaseFirestore.getInstance();
        //creating reference to firebase storage
        FirebaseStorage storage = FirebaseStorage.getInstance();
        final StorageReference storageRef = FirebaseStorage.getInstance().getReference();

        bmapList = new ArrayList<>();
        bmapListCopy = new ArrayList<>();

        mFirestore = FirebaseFirestore.getInstance();
        mQuery = mFirestore.collection("LongHair");
        mQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Map<String, Object> x = document.getData();
                        Object type = x.get("type");
                        Object url = x.get("url");
                        longHairPictures.put(url, type);
                    }
                } else {
                    System.out.println("Pull Failed");
                }
            }
        });
        mQuery = mFirestore.collection("MediumHair");
        mQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Map<String, Object> x = document.getData();
                        Object type = x.get("type");
                        Object url = x.get("url");
                        mediumHairPictures.put(url, type);
                    }
                } else {
                    System.out.println("Pull Failed");
                }
            }
        });
        mQuery = mFirestore.collection("ShortHair");
        mQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Map<String, Object> x = document.getData();
                        Object type = x.get("type");
                        Object url = x.get("url");
                        shortHairPictures.put(url, type);
                    }
                } else {
                    System.out.println("Pull Failed");
                }
            }
        });

        button1 = findViewById(R.id.addPhoto);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hairLength.equalsIgnoreCase("Short")) {
                    Object[] urlsShortHair = shortHairPictures.keySet().toArray();
                    for (Object url : urlsShortHair) {
                        if (shortHairPictures.get(url).toString().contains(hairType)) {
                            new UrlToBitmap().execute(url.toString());
                        }
                    }
                } else if (hairLength.equalsIgnoreCase("Medium")) {
                    Object[] urlsMediumHair = mediumHairPictures.keySet().toArray();
                    for (Object url : urlsMediumHair) {
                        if (mediumHairPictures.get(url).toString().contains(hairType)) {
                            new UrlToBitmap().execute(url.toString());
                        }
                    }
                } else if (hairLength.equalsIgnoreCase("Long")) {
                    Object[] urlsLongHair = longHairPictures.keySet().toArray();
                    for (Object url : urlsLongHair) {
                        if (longHairPictures.get(url).toString().contains(hairType)) {
                            new UrlToBitmap().execute(url.toString());
                        }
                    }
                }
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(
                        intent, "Select Picture"), PICK_IMAGE);
                addImages.setEnabled(true);
                findSimilarFace.setEnabled(false);
                printFace.setEnabled(false);
            }
        });


        addImages = findViewById(R.id.uploadImages);
        addImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Bitmap map : bmapList) {
                    detectAndFrame(map, 1);
                }
                button1.setEnabled(false);
                findSimilarFace.setEnabled(true);
                printFace.setEnabled(false);
            }
        });
        addImages.setEnabled(false);

        findSimilarFace = findViewById(R.id.findSimilar);
        findSimilarFace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findSimilarFaces();
                button1.setEnabled(false);
                addImages.setEnabled(false);
                printFace.setEnabled(true);
            }
        });
        findSimilarFace.setEnabled(false);

        printFace = findViewById(R.id.printButton);
        printFace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView imageView = findViewById(R.id.imageView1);
                for (SimilarFace sface : allSimilarFaces) {
                    if (sface != null) {
                        Log.e(TAG, sface.faceId + "");
                        Log.e(TAG, sface.confidence + "");
                        Log.e(TAG, facemap.get(sface.faceId) + "");
                        if (facemap.get(sface.faceId) != null)
                            imageView.setImageBitmap(bmapListCopy.get(facemap.get(sface.faceId)));
                    }
                }
                button1.setEnabled(false);
                addImages.setEnabled(false);
                findSimilarFace.setEnabled(false);
            }
        });
        printFace.setEnabled(false);

        detectionProgressDialog = new ProgressDialog(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK &&
                data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(
                        getContentResolver(), uri);
                targetBitmap = bitmap;

                ImageView imageView = findViewById(R.id.imageView1);
                imageView.setImageBitmap(bitmap);

                // Comment out for tutorial
                detectAndFrame(bitmap, 0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Detect faces by uploading a face image.
    // Frame faces after detection.
    private void detectAndFrame(final Bitmap imageBitmap, final int mode) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        ByteArrayInputStream inputStream =
                new ByteArrayInputStream(outputStream.toByteArray());

        AsyncTask<InputStream, String, Face[]> detectTask =
                new AsyncTask<InputStream, String, Face[]>() {
                    String exceptionMessage = "";

                    @Override
                    protected Face[] doInBackground(InputStream... params) {
                        try {
                            publishProgress("Detecting...");
                            Face[] result = faceServiceClient.detect(
                                    params[0],
                                    true,         // returnFaceId
                                    false,        // returnFaceLandmarks
                                    null          // returnFaceAttributes:
                                    /* new FaceServiceClient.FaceAttributeType[] {
                                        FaceServiceClient.FaceAttributeType.Age,
                                        FaceServiceClient.FaceAttributeType.Gender }
                                    */
                            );

                            if (result == null) {
                                publishProgress(
                                        "Detection Finished. Nothing detected");
                                return null;
                            }
                            publishProgress(String.format(
                                    "Detection Finished. %d face(s) detected",
                                    result.length));
                            return result;
                        } catch (Exception e) {
                            exceptionMessage = String.format(
                                    "Detection failed: %s", e.getMessage());
                            return null;
                        }
                    }

                    @Override
                    protected void onPreExecute() {
                        //TODO: show progress dialog
                        detectionProgressDialog.show();
                    }

                    @Override
                    protected void onProgressUpdate(String... progress) {
                        //TODO: update progress
                        detectionProgressDialog.setMessage(progress[0]);
                    }

                    @Override
                    protected void onPostExecute(Face[] result) {
                        //TODO: update face frames
                        detectionProgressDialog.dismiss();

                        if (!exceptionMessage.equals("")) {
                            showError(exceptionMessage);
                        }
                        if (result == null) return;

                        ImageView imageView = findViewById(R.id.imageView1);

                        if (mode == 0) {
                            targetID = result[0].faceId;
                            facemap.put(result[0].faceId, -1);
                        } else {
                            for (int i = 0; i < result.length; i++) {
                                allUUID.add(result[i].faceId);
                                facemap.put(result[i].faceId, index);
                                index++;
                            }
                        }

                        imageView.setImageBitmap(
                                drawFaceRectanglesOnBitmap(imageBitmap, result));
                        imageBitmap.recycle();
                    }
                };

        detectTask.execute(inputStream);
    }

    private void findSimilarFaces() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ByteArrayInputStream inputStream =
                new ByteArrayInputStream(outputStream.toByteArray());

        AsyncTask<InputStream, String, SimilarFace[]> detectTask =
                new AsyncTask<InputStream, String, SimilarFace[]>() {
                    String exceptionMessage = "";

                    @Override
                    protected SimilarFace[] doInBackground(InputStream... params) {
                        try {
                            publishProgress("Detecting...");
                            for (UUID id : allUUID) {
                                if (id != null) {
                                    Log.e("IDS", id.toString());
                                }
                            }
                            SimilarFace[] result = faceServiceClient.findSimilar(targetID, Arrays.copyOfRange(allUUID.toArray(), 0, allUUID.size(), UUID[].class), 1, FaceServiceClient.FindSimilarMatchMode.matchFace);
                            allSimilarFaces = result;

                            if (result == null) {
                                publishProgress(
                                        "Detection Finished. Nothing detected");
                                Log.e("BAD", "Nothing DETECTED");
                                return null;
                            }
                            publishProgress(String.format(
                                    "Detection Finished. %d similar face(s) detected",
                                    result.length));
                            return result;
                        } catch (Exception e) {
                            exceptionMessage = String.format(
                                    "Detection failed: %s", e.getMessage());
                            return null;
                        }
                    }

                    @Override
                    protected void onPreExecute() {
                        //TODO: show progress dialog
                        detectionProgressDialog.show();
                    }

                    @Override
                    protected void onProgressUpdate(String... progress) {
                        //TODO: update progress
                        detectionProgressDialog.setMessage(progress[0]);
                    }

                    @Override
                    protected void onPostExecute(SimilarFace[] result) {
                        //TODO: update face frames
                        detectionProgressDialog.dismiss();

                        if (!exceptionMessage.equals("")) {
                            showError(exceptionMessage);
                        }
                        if (result == null) return;

                        ImageView imageView = findViewById(R.id.imageView1);

                        for (int i = 0; i < result.length; i++) {
                            allSimilarFaces[i] = result[i];
                        }
                    }
                };

        detectTask.execute(inputStream);
    }

    private void showError(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                })
                .create().show();
    }

    private static Bitmap drawFaceRectanglesOnBitmap(
            Bitmap originalBitmap, Face[] faces) {
        Bitmap bitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(10);
        if (faces != null) {
            for (Face face : faces) {
                FaceRectangle faceRectangle = face.faceRectangle;
                canvas.drawRect(
                        faceRectangle.left,
                        faceRectangle.top,
                        faceRectangle.left + faceRectangle.width,
                        faceRectangle.top + faceRectangle.height,
                        paint);
            }
        }
        return bitmap;
    }

    public class UrlToBitmap extends AsyncTask<String, Void, List<Bitmap>> {
        protected List<Bitmap> doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                bmapList.add(myBitmap);
                bmapListCopy.add(myBitmap.copy(myBitmap.getConfig(), myBitmap.isMutable()));
                return bmapList;
            } catch (IOException e) {
                // Log exception
                System.out.println("exception");
                return null;
            }
        }
    }
}

