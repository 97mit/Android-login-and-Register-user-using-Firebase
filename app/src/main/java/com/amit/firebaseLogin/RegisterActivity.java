package com.amit.firebaseLogin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    private Button register;
    private EditText name;
    private EditText phone;
    private EditText email;
    private EditText pass;
    private EditText confermpass;
    private TextView tv;
    private FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    private ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        firebaseAuth = FirebaseAuth.getInstance();


        if(firebaseAuth.getCurrentUser()!=null){
            finish();
            startActivity(new Intent(getApplication(),Dashboard.class));
        }


        databaseReference =  FirebaseDatabase.getInstance().getReference();

        register = (Button)findViewById(R.id.buttonRegister);
        email = (EditText) findViewById(R.id.editTextRegEmail);
        pass = (EditText)findViewById(R.id.editTextRegPassword);
        confermpass = (EditText) findViewById(R.id.editTextRegConfermPass);
        tv = (TextView) findViewById(R.id.textViewRegLogin);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(getApplication(),LoginActivity.class));
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();

            }
        });
        pd = new ProgressDialog(this);
    }
    private void registerUser(){

        String email_tv = email.getText().toString().trim();
        String password = pass.getText().toString().trim();
        String confermpassword = confermpass.getText().toString().trim();

        if(TextUtils.isEmpty(email_tv))
        {
            Toast.makeText(this,"Enter Email",Toast.LENGTH_SHORT).show();
            //finish();
            //startActivity(new Intent(getApplication(),Dashboard.class));
            return;
        }
        if(TextUtils.isEmpty(password))
        {
            Toast.makeText(this,"Enter password",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(confermpassword))
        {
            Toast.makeText(this,"Enter Conform password",Toast.LENGTH_SHORT).show();
            return;
        }
        if(!password.equals(confermpassword))
        {
            Toast.makeText(this,"Password Not Match",Toast.LENGTH_SHORT).show();
            //finish();
            //startActivity(new Intent(getApplication(),Dashboard.class));
            return;
        }
        pd.setMessage("wait");
        pd.show();
        firebaseAuth.createUserWithEmailAndPassword(email_tv,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        pd.dismiss();
                        if(task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this,"Reg Success",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                        }else {
                            Toast.makeText(RegisterActivity.this,"not reg pass",Toast.LENGTH_SHORT).show();
                        }
                    }

                });


    }
}
