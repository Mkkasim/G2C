package com.mk.g2c;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.mk.g2c.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.img1.setOnClickListener(view -> {
            Intent in = new Intent(MainActivity.this,CheckListActivity.class);
            startActivity(in);
            finish();
            Animatoo.animateSlideLeft(MainActivity.this);
        });

        binding.img2ui.setOnClickListener(view -> {
            Intent in = new Intent(MainActivity.this,BookingDetailsActivity.class);
            startActivity(in);
            finish();
            Animatoo.animateSlideLeft(MainActivity.this);
        });

    }
}