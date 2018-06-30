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

public class LoginActivity extends AppCompatActivity {

    private EditText email, password;
    private String emailHolder, passwordHolder;
    private Button signIn, tosignUp;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.login_email);
        password = findViewById(R.id.login_password);
        signIn = findViewById(R.id.sign_in_button);
        tosignUp = findViewById(R.id.to_sign_up_button);
        progressDialog = new ProgressDialog(LoginActivity.this);
        firebaseAuth = FirebaseAuth.getInstance();

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextEmptyorNot();

                if (!editTextEmptyorNot()) {
                    LoginFunction();
                } else {
                    Toast.makeText(getApplicationContext(), "One or more fields are empty", Toast.LENGTH_SHORT).show();
                }
            }
        });
        tosignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    public boolean editTextEmptyorNot() {
        boolean editTextIsEmpty;
        emailHolder = email.toString().trim();
        passwordHolder = password.toString().trim();

        editTextIsEmpty = TextUtils.isEmpty(emailHolder) || TextUtils.isEmpty(passwordHolder);
        return editTextIsEmpty;
    }

    public void LoginFunction() {
        progressDialog.setMessage("Logging In\n Please Wait");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(emailHolder, passwordHolder).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progressDialog.dismiss();
                    finish();
                    Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_LONG).show();
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Invalid Login Credentials\nPlease Check Again or Sign Up", Toast.LENGTH_LONG).show();
                }
            }
        });


    }
}
