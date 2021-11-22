package com.example.authsem16;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {
    private EditText emailET, passwordET;
    private Button loginBtn;
    private TextView toSignupTV;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();

        emailET = findViewById(R.id.emailET);
        passwordET = findViewById(R.id.passwordET);
        loginBtn = findViewById(R.id.loginBtn);
        toSignupTV = findViewById(R.id.toSignupTV);

        loginBtn.setOnClickListener((view) -> {
            auth.signInWithEmailAndPassword(emailET.getText().toString(), passwordET.getText().toString())
                    .addOnCompleteListener((authTask) -> {
                        if (authTask.isSuccessful()){
                            Intent profileIntent = new Intent(this, ProfileActivity.class);
                            startActivity(profileIntent);
                            finish();
                        }else{
                            Toast.makeText(this, authTask.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        });

        toSignupTV.setOnClickListener((view) -> {
            Intent signupIntent = new Intent(this, SignupActivity.class);
            startActivity(signupIntent);
            finish();
        });
    }
}