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

public class LoginActivity extends AppCompatActivity {
    private Button signin;
    private EditText email;
    private EditText passw;
    private TextView textViewSignin;
    ProgressDialog pd;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()!=null){
            finish();
            startActivity(new Intent(getApplicationContext(),Dashboard.class));
            //prpfile activity.
        }
        signin = (Button) findViewById(R.id.buttonLogin);
        email = (EditText)findViewById(R.id.editTextEmail);
        passw = (EditText)findViewById(R.id.editTextPassword);
        textViewSignin = (TextView)findViewById(R.id.textViewRegisterHere);

        pd = new ProgressDialog(this);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v == signin)
                {
                    userLogin();
                }
            }
        });
        textViewSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
            }
        });
    }
    private void userLogin(){
        String e_mai = email.getText().toString().trim();
        String P_assw = passw.getText().toString().trim();

        if(TextUtils.isEmpty(e_mai))
        {
            Toast.makeText(this,"Enter Email",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(P_assw))
        {
            Toast.makeText(this,"Enter pass",Toast.LENGTH_SHORT).show();
            return;
        }
        pd.setMessage("wait");
        pd.show();
        firebaseAuth.signInWithEmailAndPassword(e_mai,P_assw)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        pd.dismiss();
                        if(task.isSuccessful()){
                            //Start profile Activity
                            finish();
                            startActivity(new Intent(getApplicationContext(),Dashboard.class));
                        }
                    }
                });
    }
}
