package com.example.journalapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private EditText password, email;
    Button loginBtn, createAccountBtn;

    // Firebase Auth
    FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        password = findViewById(R.id.password);
        email = findViewById(R.id.email);

        loginBtn = findViewById(R.id.login_BTN);
        createAccountBtn = findViewById(R.id.create_accountBTN);

        createAccountBtn.setOnClickListener(view -> {

            Intent i = new Intent(MainActivity.this, SignUpActivity.class);
            startActivity(i);
        });

        // Firebase Authentication

        firebaseAuth = FirebaseAuth.getInstance();

        loginBtn.setOnClickListener(view -> {
            loginEmailPassUser(email.getText().toString().trim(),
                    password.getText().toString().trim());
        });

    }

    private void loginEmailPassUser(String email, String pass){

        if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass)){
            firebaseAuth.signInWithEmailAndPassword(
                    email, pass
            ).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    currentUser = firebaseAuth.getCurrentUser();
                    Intent intent = new Intent(MainActivity.this, JournalListActivity.class);
                    startActivity(intent);
                }
            });
        }
    }
}