package com.tour2tech.shubham;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.util.Calendar;

public class Image extends AppCompatActivity {

    ImageView Image;
    Uri uri;
    EditText text_name;
    String imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        Image = (ImageView)findViewById(R.id.iv_Image);
        text_name=(EditText)findViewById(R.id.txt_name);


    }

    public void btnSelectImage(View view) {

        Intent photoPicker = new Intent(Intent.ACTION_PICK);
        photoPicker.setType("image/*");
        startActivityForResult(photoPicker,1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK){

            uri=data.getData();
            Image.setImageURI(uri);

        }
        else Toast.makeText(this, "You have not picked a image", Toast.LENGTH_SHORT).show();

    }

    public void uploadImage()
    {

        StorageReference storageRefrence = FirebaseStorage.getInstance().getReference().child("ProjectImages").child(uri.getLastPathSegment());
        storageRefrence.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Task<Uri> uriTask= taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete());
                Uri uriImage = uriTask.getResult();
                imageUrl= uriImage.toString();
                uploadImageData();


            }
        });
    }

    public void UploadProjectInfo(View view) {

        uploadImage();

    }

    public void uploadImageData(){

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Project Info .Uploading...");
        progressDialog.show();


       Imagedata Data = new Imagedata(text_name.getText().toString(),imageUrl);

        String myCurrentDateTime = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

        FirebaseDatabase.getInstance().getReference("User_Images").child(myCurrentDateTime).setValue(Data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {


                if (task.isSuccessful()){

                    Toast.makeText(Image.this, "Project Info. Uploaded", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    finish();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Image.this,"Failed To Upload..", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        });



    }

}

