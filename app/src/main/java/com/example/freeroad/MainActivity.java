package com.example.freeroad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button mapPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapPage = findViewById(R.id.mapButton);
        mapPage.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                openMapsActivity();
            }
        });
    }

    public void openMapsActivity(){
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
}