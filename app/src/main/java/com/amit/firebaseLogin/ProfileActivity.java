package com.amit.firebaseLogin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    TextView textViewEmail;
    Button saveProfile;
    EditText editText_name;
    EditText editText_phone;
    EditText editText_DOB;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() == null){
            return;
        }
        FirebaseUser user = firebaseAuth.getCurrentUser();

        databaseReference = FirebaseDatabase.getInstance().getReference();
        textViewEmail = (TextView)findViewById(R.id.textViewEmailProfile);
        saveProfile = (Button)findViewById(R.id.buttonSaveProfile);
        editText_name = (EditText)findViewById(R.id.editTextProfileName);
        editText_phone = (EditText)findViewById(R.id.editTextProfilePhone);
        editText_DOB = (EditText)findViewById(R.id.editTextProfileDOB);

        textViewEmail.setText("Email id : "+user.getEmail());
        saveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserInfo();

            }
        });
    }
    private void saveUserInfo(){
        String name = editText_name.getText().toString().trim();
        String address = editText_phone.getText().toString().trim();
        String DOB = editText_DOB.getText().toString();

        UserInformation userInformation = new UserInformation(name,address,DOB);
        FirebaseUser user = firebaseAuth.getCurrentUser();
        databaseReference.child(user.getUid()).setValue(userInformation);
        Toast.makeText(this, "INFO SAVED...",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(),Dashboard.class));
    }
}
