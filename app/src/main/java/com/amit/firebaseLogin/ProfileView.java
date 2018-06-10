package com.amit.firebaseLogin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileView extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    TextView textViewEmail;
    Button editProfile;
    TextView textViewName;
    TextView textViewPhone;
    TextView textViewDOB;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_view);

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() == null){
            return;
        }
        final FirebaseUser user = firebaseAuth.getCurrentUser();

        databaseReference =  FirebaseDatabase.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        textViewEmail = (TextView)findViewById(R.id.textViewEmailProfileView);
        editProfile = (Button)findViewById(R.id.editProfile);
        textViewName = (TextView) findViewById(R.id.textProfileViewName);
        textViewPhone = (TextView)findViewById(R.id.textProfileViewPhone);
        textViewDOB = (TextView) findViewById(R.id.textProfileViewDOB);

        textViewEmail.setText("Email id : "+user.getEmail());

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
            }
        });

        ChildEventListener childEventListener = new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                UserInformation userInformation = dataSnapshot.getValue(UserInformation.class);
                textViewName.setText("Name : "+userInformation.name);
                textViewPhone.setText("Phone : "+userInformation.phone);
                textViewDOB.setText("Birth Date : "+userInformation.DOB);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                UserInformation userInformation = dataSnapshot.getValue(UserInformation.class);
                textViewName.setText("Name : "+userInformation.name);
                textViewPhone.setText("Phone : "+userInformation.phone);
                textViewDOB.setText("Birth Date : "+userInformation.DOB);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        };
        databaseReference.addChildEventListener(childEventListener);


    }
}
