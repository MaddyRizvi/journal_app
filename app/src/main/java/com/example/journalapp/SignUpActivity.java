package com.example.journalapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

//3
public class SignUpActivity extends AppCompatActivity {

    EditText password_create, username_create, email_create;
    Button signUpBtn;

    // Firebase authentication
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser currentUser;

    // Firebase Connection
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = db.collection("Users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main1), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        email_create = findViewById(R.id.email_create);
        password_create = findViewById(R.id.password_create);
        username_create = findViewById(R.id.username_create_ET);

        signUpBtn = findViewById(R.id.acc_signup_btn);

        // Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance();

        // listening for the changes in the Auth state
        // i.e user Signed in / Signed out
        // and responding accordingly
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
               currentUser = firebaseAuth.getCurrentUser();

               // Check if the user is logged in or not
                if(currentUser != null){
                    // User already logged in
                }else{
                    // The user is signed out
                }
            }
        };

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!TextUtils.isEmpty(email_create.getText().toString())
                && !TextUtils.isEmpty(username_create.getText().toString())
                && !TextUtils.isEmpty(password_create.getText().toString()))
                {
                    String email = email_create.getText().toString().trim();
                    String pass = password_create.getText().toString().trim();
                    String username = username_create.getText().toString().trim();
                    createUserAccount(email, pass, username);
                }else{
                    Toast.makeText(SignUpActivity.this,
                            "Please fill all the fields",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private void createUserAccount(String email, String pass, String username){

            firebaseAuth.createUserWithEmailAndPassword(
                    email, pass
            ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                   if(task.isSuccessful()){
                       // The user is created successfully!
                       Toast.makeText(SignUpActivity.this,
                               "Account is created successfully!",
                               Toast.LENGTH_SHORT).show();
                   }
                }
            });

    }
}