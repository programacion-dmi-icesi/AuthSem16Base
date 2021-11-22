package com.example.authsem16;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    TextView welcomeTV;
    Button signoutBtn;

    private FirebaseAuth auth;
    private FirebaseDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        welcomeTV = findViewById(R.id.welcomeTV);
        signoutBtn = findViewById(R.id.signoutBtn);

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();

        loadUser();

        signoutBtn.setOnClickListener((view) -> {
            auth.signOut();
           goToLogin();
        });
    }

    public void loadUser(){
        if (auth.getCurrentUser() != null){
            // Loguegado
            String id = auth.getCurrentUser().getUid();
            DatabaseReference dbRef = db.getReference("sem15/users/"+id);
            dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User currentUser = snapshot.getValue(User.class);
                    welcomeTV.setText("Welcome "+currentUser.getUsername());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }else{
            goToLogin();
        }
    }

    public void goToLogin(){
        Intent loginIntent = new Intent(this, LoginActivity.class);
        startActivity(loginIntent);
        finish();
    }
}