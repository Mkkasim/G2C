package com.mk.g2c;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.mk.g2c.databinding.ActivityBookingDetailsBinding;

public class BookingDetailsActivity extends AppCompatActivity {

    ActivityBookingDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBookingDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent in = new Intent(BookingDetailsActivity.this,MainActivity.class);
        startActivity(in);
        finish();
        Animatoo.animateSlideRight(BookingDetailsActivity.this);
    }
}