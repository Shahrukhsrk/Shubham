package com.tour2tech.shubham;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    EditText email,pass;
    private FirebaseAuth mAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        email=(EditText) findViewById(R.id.email);
        pass=(EditText) findViewById(R.id.password);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("LOGGING IN ...");

    }

    public void signIn(View view) {

        progressDialog.show();

        if(TextUtils.isEmpty(email.getText().toString().trim())){

            Toast.makeText(Login.this, "Email Should not be Empty", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
            return;
        }

        if(TextUtils.isEmpty(pass.getText().toString().trim())){

            Toast.makeText(Login.this, "Password Should not be Empty", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
            return;
        }

        if(pass.length()<6){

            Toast.makeText(Login.this, "Short Password,length must be more than 6", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
            return;
        }


        mAuth.signInWithEmailAndPassword(email.getText().toString().trim(), pass.getText().toString().trim())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            startActivity(new Intent(Login.this,MainActivity.class));
                            Toast.makeText(Login.this, "Logged in", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Login.this, "Login Failed !", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }

                        // ...
                    }
                });


    }

    public void createAcc(View view) {

        startActivity(new Intent(Login.this,Register.class));

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {

        if (currentUser != null) {
            // User is signed in
            Intent i = new Intent(Login.this, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        } else {
            // User is signed out

        }

    }
}
