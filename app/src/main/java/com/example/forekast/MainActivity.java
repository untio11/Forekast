package com.example.forekast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;

import com.example.forekast.external_data.AppDatabase;
import com.example.forekast.external_data.Repository;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Repository.setDB(Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "clothing").fallbackToDestructiveMigration().build());
    }
}
