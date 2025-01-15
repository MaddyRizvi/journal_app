package com.example.journalapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

//3
public class JournalListActivity extends AppCompatActivity {


    // Firebase Auth
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser user;

    // Firebase FireStore
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = db.collection("Journal");

    // Firebase Storage
    private StorageReference storageReference;

    // List of Journals
    private List<Journal> journalList;

    // Recycler View
    private RecyclerView recyclerView;
    private MyAdapter myAdapter;

    // Widgets
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_journal_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        // Recycler view
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Posts ArrayList
        journalList = new ArrayList<>();

        fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(JournalListActivity.this, AddJournalActivity.class);
               startActivity(intent);
            }
        });

    }
        // 2 - Adding a Menu
         @Override
         public boolean onCreateOptionsMenu(Menu menu){
            getMenuInflater().inflate(R.menu.my_menu, menu);
            return super.onCreateOptionsMenu(menu);
         }

         @Override
         public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                 case R.id.action_add:
                if(user!=null && firebaseAuth != null){
                    Intent intent = new Intent(JournalListActivity.this,
                            AddJournalActivity.class);
                    startActivity(intent);
                }
                    break;
                case R.id.action_signout:
                if(user!= null && firebaseAuth != null){
                    firebaseAuth.signOut();
                    Intent intent = new Intent(JournalListActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                    break;
        }

        return super.onOptionsItemSelected(item);
    }

    // Getting the Data from the Firebase Firestore
    @Override
    protected void onStart() {
        super.onStart();

        collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                // queryDocumentSnapshot is an object that represent the each document
                // in the Firestore
                for(QueryDocumentSnapshot journals: queryDocumentSnapshots)
                {
                    // Convert the document in the custom Object (Journal)
                    Journal journal = journals.toObject(Journal.class);
                    journalList.add(journal);
                }
                // Adapter
                myAdapter = new MyAdapter(JournalListActivity.this, journalList);
                recyclerView.setAdapter(myAdapter);
                myAdapter.notifyDataSetChanged();
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(JournalListActivity.this,
                        "Oops! Something went wrong",
                        Toast.LENGTH_SHORT).show();
                Log.e("FirestoreError", "Error fetching data", e);
            }
        });
    }

}