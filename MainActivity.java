package com.example.android.loginwindow;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private EditText email, password;
    private Button signUp, toLogin;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private String emailHolder, passwordHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.signUp_email);
        password =  findViewById(R.id.signUp_password);
        toLogin = findViewById(R.id.to_sign_in_button);
        signUp =  findViewById(R.id.sign_up_button);
        progressDialog = new ProgressDialog(MainActivity.this);
        firebaseAuth = FirebaseAuth.getInstance();

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextEmptyOrNot();
                userRegistration();
                if(editTextEmptyOrNot()){
                    Toast.makeText(getApplicationContext(),"One or More Fields are Empty\n Please Fill to Proceed",Toast.LENGTH_LONG).show();
                } else {
                    userRegistration();
                }
            }
        });
        toLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }
    public boolean editTextEmptyOrNot(){
        boolean editTextIsEmpty;
        emailHolder = email.toString().trim();
        passwordHolder = password.toString().trim();

        editTextIsEmpty = TextUtils.isEmpty(emailHolder) || TextUtils.isEmpty(passwordHolder);
        return editTextIsEmpty;
    }

    public void userRegistration(){
        progressDialog.setMessage("Be Patient\nRegistration in Progress");
        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(emailHolder,passwordHolder).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"Successfully Registered\nProceed to Login",Toast.LENGTH_LONG).show();
                    firebaseAuth.signOut();
                }
                else {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"Something went Wrong\nTry Signing Up Again",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
