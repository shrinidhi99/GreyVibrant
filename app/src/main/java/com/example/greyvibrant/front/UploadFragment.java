package com.example.greyvibrant.front;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.greyvibrant.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UploadFragment extends Fragment {
    String TAG = "Permission";
    TextView location;
    FloatingActionButton pickFile;
    SharedPreferences sharedPreferences;
    Intent FileIntent;
    Button uploadFile;
    private StorageReference mStorageRef;
    ProgressBar mProgressBar;
    private DatabaseReference mDatabaseRef;
    Uri path;
    private StorageTask<UploadTask.TaskSnapshot> mUploadTask;
    EditText songName, album, genre, language;
    String songUrl, artistAIDPut;
    static String URL_REGIST = "https://sabios-97.000webhostapp.com/song.php";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.artist_fragment_upload, container, false);
        location = view.findViewById(R.id.songUri);
        pickFile = view.findViewById(R.id.add_file);
        uploadFile = view.findViewById(R.id.upload_song);
        mProgressBar = view.findViewById(R.id.progress_bar);
        songName = view.findViewById(R.id.song_name);
        album = view.findViewById(R.id.song_album);
        genre = view.findViewById(R.id.genre);
        language = view.findViewById(R.id.language);
        mStorageRef = FirebaseStorage.getInstance().getReference("Music");
        sharedPreferences = getContext().getSharedPreferences("com.example.greyvibrant.front", Context.MODE_PRIVATE);
        artistAIDPut = sharedPreferences.getString("AID", null);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Music");
        pickFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileIntent = new Intent(Intent.ACTION_GET_CONTENT);
                FileIntent.setType("audio/mp3");
                startActivityForResult(FileIntent, 10);
            }
        });
        uploadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(getContext(), "Upload in progress", Toast.LENGTH_SHORT).show();
                } else {
                    uploadMusic();
                }
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 10: {
                path = Uri.parse("");
                if (resultCode == Activity.RESULT_OK) {
                    path = data.getData();
                    location.setText(path.toString());
                }
                break;
            }
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadMusic() {
        if (isStoragePermissionGranted()) {
            if (path != null) {

                StorageMetadata metadata = new StorageMetadata.Builder()
                        .setContentType("audio/mp3")
                        .build();
                final StorageReference fileReference = mStorageRef.child(System.currentTimeMillis() + "." + getFileExtension(path));


                mUploadTask = fileReference.putFile(path, metadata).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mProgressBar.setProgress(0);
                            }
                        }, 500);
                        Toast.makeText(getContext(), "Upload Successful", Toast.LENGTH_SHORT).show();
                        Upload upload = new Upload(songName.getText().toString().trim(), taskSnapshot.getMetadata().getReference().getDownloadUrl().toString());
                        String uploadId = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId).setValue(upload);
                        fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                songUrl = uri.toString();
                                // add MySQL code here
                                AddSongData(songUrl);


                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                        mProgressBar.setProgress((int) progress);
                    }
                });
            } else {
                Toast.makeText(getContext(), "No file selected", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void AddSongData(final String songurl) {


        final String songname = this.songName.getText().toString().trim();
        final String album = this.album.getText().toString().trim();
        final String genre = this.genre.getText().toString().trim();
        final String language = this.language.getText().toString().trim();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("RESPONSE FROM PHP", response);
                        try {
                            if (response == null || response.equals(""))
                                Log.i("RESPONSE", "IS NULL");

                            JSONObject jsonObject = new JSONObject(response);


                            String success = jsonObject.getString("success");
                            if (success.equals("1")) {
                                Toast.makeText(getContext(), "Data Added", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "DB Error", Toast.LENGTH_SHORT).show();

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "DB Error 2", Toast.LENGTH_SHORT).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                Log.i("SONG URL", songurl);
                params.put("songname", songname);
                params.put("songurl", songurl);
                params.put("genre", genre);
                params.put("language", language);
                params.put("album", album);
                params.put("AID", artistAIDPut);


                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }

    private boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            if (getActivity().checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG, "Permission is granted");
                return true;
            } else {

                Log.v(TAG, "Permission is revoked");
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else {
            //permission is automatically granted on sdk < 23 upon installation
            Log.v(TAG, "Permission is granted");
            return true;
        }
    }
}
