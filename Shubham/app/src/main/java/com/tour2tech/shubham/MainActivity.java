package com.tour2tech.shubham;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView uname,uemail,uphno;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    RecyclerView mRecyclerView;
    List<Imagedata> myImageList;
    ProgressDialog progressDialog;

    private DatabaseReference databaseReference;
    private ValueEventListener eventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        uname=(TextView)findViewById(R.id.tvname);
        uemail=(TextView)findViewById(R.id.tvemail);
        uphno=(TextView)findViewById(R.id.tvmobile);

        firebaseAuth= FirebaseAuth.getInstance();

        firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference myRef=firebaseDatabase.getReference("User_Records").child(firebaseAuth.getCurrentUser().getUid());
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserData data = dataSnapshot.getValue(UserData.class);
                uname.setText(data.getName());
                uemail.setText(data.getEmail());
                uphno.setText(data.getPhoneno());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        // layoutManager.setReverseLayout(true);
        //  layoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(layoutManager);

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Loading Projects ..");
        progressDialog.setCanceledOnTouchOutside(false);

        myImageList=new ArrayList<>();

        final ImageAdapter myAdapterImage = new ImageAdapter(getApplicationContext(),myImageList);
        mRecyclerView.setAdapter(myAdapterImage);

        databaseReference= FirebaseDatabase.getInstance().getReference("User_Images");

        progressDialog.show();

        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                myImageList.clear();

                for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {

                    Imagedata imgData = itemSnapshot.getValue(Imagedata.class);
                    myImageList.add(imgData);

                }

                myAdapterImage.notifyDataSetChanged();
                progressDialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                progressDialog.dismiss();

            }
        });

    }

    public void logout(View view) {

        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(MainActivity.this, Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        Toast.makeText(MainActivity.this, "User LogOut...", Toast.LENGTH_SHORT).show();
        startActivity(intent);
        finish();

    }

    public void imageUpload(View view) {

        startActivity(new Intent(MainActivity.this,Image.class));

    }
}
