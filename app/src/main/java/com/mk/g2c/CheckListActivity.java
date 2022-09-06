package com.mk.g2c;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mk.g2c.databinding.ActivityCheckListBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

public class CheckListActivity extends AppCompatActivity {

    ActivityCheckListBinding binding;

    DatabaseReference checksRef,checkUpRef;
    ArrayList<ChecksModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCheckListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getChecksLists();

        binding.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDialog(CheckListActivity.this);

            }
        });
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });



    }


    public void showDialog(Activity activity){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.checks_add_popup);

        EditText textEdt = (EditText) dialog.findViewById(R.id.checksEdt);

        ImageView closeBtn = (ImageView) dialog.findViewById(R.id.cancel);
        Button addBtn = (Button) dialog.findViewById(R.id.addBtn);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (textEdt.getText().toString().isEmpty()){
                    Toast.makeText(activity, "Please enter text", Toast.LENGTH_SHORT).show();
                }else {
                    String text = textEdt.getText().toString();
                    uploadToFirebase(text);
                    dialog.dismiss();
                }
            }
        });

        dialog.show();

    }

    private void uploadToFirebase(String checksName) {

        checkUpRef = FirebaseDatabase.getInstance().getReference("ChecksLists");
        Date currentTime = Calendar.getInstance().getTime();
        //String key = String.valueOf(currentTime);
        String key = checkUpRef.push().getKey();
        ChecksModel checksModel = new ChecksModel(checksName,key);
        //String shortName = StringUtil..(checksName, 0, 2) ;

        checkUpRef.child(key).setValue(checksModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Snackbar.make(binding.getRoot(),"Checks Added Successfuly",Snackbar.LENGTH_SHORT).show();

                }else {
                    Snackbar.make(binding.getRoot(),"Something went wrong!",Snackbar.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("TAG2", "onFailure: ----"+ e.getMessage());
            }
        });

    }

    private void getChecksLists() {

        list = new ArrayList<>();
        checksRef = FirebaseDatabase.getInstance().getReference("ChecksLists");
        binding.recycChecks.setHasFixedSize(true);
        LinearLayoutManager linearLayout = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        binding.recycChecks.setLayoutManager(linearLayout);

        RecycChecksAdapter recycChecksAdapter = new RecycChecksAdapter(this, list, new RecycChecksAdapter.OnItemClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onItemClick(ChecksModel model) {

                final Dialog dialog = new Dialog(CheckListActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.delete_popup);

                Button deleteBtn = (Button) dialog.findViewById(R.id.deleteBtn);

                deleteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FirebaseDatabase.getInstance().getReference("ChecksLists").child(model.getKey()).removeValue();
                        dialog.dismiss();
                    }
                });

                dialog.show();





            }
        });



        binding.recycChecks.setAdapter(recycChecksAdapter);

        checksRef.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                list.clear();
                if (snapshot.exists()){
                    try {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                            ChecksModel ChecksModel = dataSnapshot.getValue(ChecksModel.class);
                            list.add(ChecksModel);


                        }

                        Collections.reverse(list);
                        recycChecksAdapter.notifyDataSetChanged();



                    }catch (Exception e){
                        Log.d("TAG2", "onDataChange: ===="+e.getMessage());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(CheckListActivity.this,MainActivity.class);
        startActivity(i);
        finish();
        Animatoo.animateSlideRight(CheckListActivity.this);
    }
}