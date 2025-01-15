package com.example.journalapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.DataCollectionDefaultChange;
import com.google.firebase.Firebase;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Date;

//5
public class AddJournalActivity extends AppCompatActivity {

    private Button saveButton;
    private EditText titleEditText;
    private EditText thoughtsEditText;
    private ImageView addPhotoBtn;
    private ImageView imageView;
    private ProgressBar progressBar;


    private String currentUserId;
    private String currentUserName;

    // Firebase Auth
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser user;


    //Firebase (FireStore)
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = db.collection("Journal");

    // Firebase (Storage)
    private StorageReference storageReference;

    Uri imageUri;

    // Activity Result Launcher
    ActivityResultLauncher<String> mTakePhoto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_journal);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        saveButton = findViewById(R.id.post_save_journal_button);
        titleEditText = findViewById(R.id.post_title_et);
        thoughtsEditText = findViewById(R.id.post_description_et);
        imageView = findViewById(R.id.post_imageView);
        // addPhotoBtn is imageView not a button
        addPhotoBtn = findViewById(R.id.postCameraButton);
        progressBar = findViewById(R.id.post_progressBar);

        progressBar.setVisibility(View.INVISIBLE);

        // Firebase (Storage)
        // getting the reference to the root storage
        storageReference = FirebaseStorage.getInstance().getReference();

        // Auth
        firebaseAuth = FirebaseAuth.getInstance();

        // Getting the Current User
        if (user != null){
            currentUserId = user.getUid();
            currentUserName = user.getDisplayName();
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveJournal();
            }
        });

        // picking a content from a user device
        mTakePhoto = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {
                        // Displaying the image
                        imageView.setImageURI(result);

                        // Get Image Uri to be used to upload the image in the storage
                        imageUri = result;
                    }
                }
        );


        addPhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Getting the image from the Gallery
                mTakePhoto.launch("image/*"); 
            }
        });

    }

    private void SaveJournal() {

        String title = titleEditText.getText().toString().trim();
        String thoughts = thoughtsEditText.getText().toString().trim();

        progressBar.setVisibility(View.VISIBLE);

        if(!TextUtils.isEmpty(title) && !TextUtils.isEmpty(thoughts)
            && imageUri !=null){

            // the saving path in the firebase Storage
            //..... /journal_images/my_image_202410241424
            final StorageReference filePath = storageReference
                    .child("journal_images")
                    .child("my_image_"+ Timestamp.now().getSeconds());

            // uploading the image
            filePath.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                           filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                               @Override
                               public void onSuccess(Uri uri) {
                                   String imageUrl = uri.toString();

                                   // Creating a Journal Object
                                   Journal journal = new Journal();
                                   journal.setTitle(title);
                                   journal.setThoughts(thoughts);
                                   journal.setImageURL(imageUrl);

                                   journal.setTimeAdded(new Timestamp(new Date()));
                                   journal.setUserName(currentUserName);
                                   journal.setUserId(currentUserId);

                                   collectionReference.add(journal)
                                           .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                               @Override
                                               public void onSuccess(DocumentReference documentReference) {
                                                   progressBar.setVisibility(View.INVISIBLE);

                                                   Intent intent = new Intent(AddJournalActivity.this,
                                                           JournalListActivity.class);
                                                   startActivity(intent);
                                                   finish();
                                               }
                                           }).addOnFailureListener(new OnFailureListener() {
                                               @Override
                                               public void onFailure(@NonNull Exception e) {
                                                   progressBar.setVisibility(View.INVISIBLE);
                                                   Toast.makeText(AddJournalActivity.this,
                                                           "Failed to save journal. Please try again.",
                                                           Toast.LENGTH_SHORT).show();
                                                   Log.e("FirestoreError", "Error adding journal to Firestore", e);
                                               }
                                           });
                               }
                           }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    Toast.makeText(AddJournalActivity.this,
                                            "Failed to get image URL. Please try again.",
                                            Toast.LENGTH_SHORT).show();
                                    Log.e("FirestoreError", "Error getting download URL", e);
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(AddJournalActivity.this
                                    ,
                                    "Failed to upload image. Please try again.",
                                    Toast.LENGTH_SHORT).show();
                            Log.e("FirestoreError", "Error uploading Image", e);
                        }
                    });
        }else{
            progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(AddJournalActivity.this,
                    "Please fill out all fields and select an image.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    // When dealing with firebase always use this onStart method
    // to getCurrentuser reference
    @Override
    protected void onStart() {
        super.onStart();
        user = firebaseAuth.getCurrentUser();
    }
}