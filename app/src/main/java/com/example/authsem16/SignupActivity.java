package com.example.authsem16;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseAuth;
import android.util.Log;

public class SignupActivity extends AppCompatActivity {

    private EditText usernameET, edadET, emailET, passwordET;
    private Button signupBtn;
    private TextView toLoginTV;

    private FirebaseDatabase db;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        db = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        usernameET = findViewById(R.id.usernameET);
        edadET = findViewById(R.id.edadET);
        emailET = findViewById(R.id.emailET);
        passwordET = findViewById(R.id.passwordET);
        signupBtn = findViewById(R.id.loginBtn);
        toLoginTV = findViewById(R.id.toLoginTV);

        signupBtn.setOnClickListener((view) -> {

            auth.createUserWithEmailAndPassword(emailET.getText().toString(), passwordET.getText().toString())
                    .addOnCompleteListener((authTask) -> {
                        if(authTask.isSuccessful()){
                            // Guardar en la bd
                            String id = authTask.getResult().getUser().getUid();
                            String id2 = auth.getCurrentUser().getUid();
                            Log.d("id1", id);
                            Log.d("id2", id2);
                            User newUser = new User(
                                    id,
                                    usernameET.getText().toString(),
                                    edadET.getText().toString(),
                                    emailET.getText().toString(),
                                    passwordET.getText().toString());
                            DatabaseReference dbRef = db.getReference("sem15/users/"+id);
                            dbRef.setValue(newUser).addOnCompleteListener((dbTask) -> {
                                if(dbTask.isSuccessful()){
                                    Intent profileIntent = new Intent(this, ProfileActivity.class);
                                    startActivity(profileIntent);
                                    finish();
                                }
                            });

                        }else{
                            Toast.makeText(this, authTask.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        });
        toLoginTV.setOnClickListener((view) -> {
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivity(loginIntent);
            finish();
        });
    }
}