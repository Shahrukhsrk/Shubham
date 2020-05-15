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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    EditText email,mobile,name,pass,repass;
    FirebaseAuth mAuth;
    UserData userData;
    ProgressDialog progressDialog;
    FirebaseDatabase database;
    DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        email=(EditText) findViewById(R.id.email);
        pass=(EditText) findViewById(R.id.sppassword);
        repass=(EditText) findViewById(R.id.repassword);
        mobile=(EditText) findViewById(R.id.mobile);
        name=(EditText) findViewById(R.id.name);
        userData = new UserData();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Creating Account ...");
        database = FirebaseDatabase.getInstance();
        myRef= database.getReference("User_Records");

    }

    public void createAcc(View view) {

        if(TextUtils.isEmpty(email.getText().toString())){

            Toast.makeText(Register.this, "Email Should not be Empty", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
            return;
        }

        if(TextUtils.isEmpty(pass.getText().toString().trim())){

            Toast.makeText(Register.this, "Password Should not be Empty", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
            return;
        }

        if(pass.length()<6){

            Toast.makeText(Register.this, "Short Password,length must be more than 6", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
            return;
        }

        if(TextUtils.isEmpty(mobile.getText().toString().trim())){

            Toast.makeText(Register.this, "Mobile No. Should not be Empty", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
            return;
        }

        if(TextUtils.isEmpty(name.getText().toString().trim())){

            Toast.makeText(Register.this, "Name Should not be Empty", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
            return;
        }


        if (pass.getText().toString().trim().equals(repass.getText().toString().trim()))
        {

            progressDialog.show();
            mAuth.createUserWithEmailAndPassword(email.getText().toString().trim(), pass.getText().toString().trim())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                userData.setEmail(email.getText().toString().trim());
                                userData.setName(name.getText().toString().trim());
                                userData.setPhoneno(mobile.getText().toString().trim());
                                myRef.child(mAuth.getCurrentUser().getUid()).setValue(userData);
                                progressDialog.dismiss();
                                startActivity(new Intent(Register.this, MainActivity.class));
                                Toast.makeText(Register.this, "Account Created Successfully", Toast.LENGTH_SHORT).show();

                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(Register.this, "Sign in Failed !", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }

                            // ...
                        }
                    });
        }

    }


}
