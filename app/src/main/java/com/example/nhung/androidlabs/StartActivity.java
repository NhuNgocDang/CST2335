package com.example.nhung.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.w3c.dom.Text;

public class StartActivity extends Activity {
    protected static final String ACTIVITY_NAME = "StartActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Log.i(ACTIVITY_NAME, "In onCreate()");

        Button bt = findViewById(R.id.button6);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextScreen = new Intent(StartActivity.this, ListItemsActivity.class);
                startActivityForResult(nextScreen, 50);
                Log.i(ACTIVITY_NAME,"In onClick()");
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int responseCode, Intent data){
        if (requestCode == 50 && responseCode == Activity.RESULT_OK){
            Log.i(ACTIVITY_NAME,"Returned to Start.onActivityResult");
            String messagePassed = data.getStringExtra("Response");

            String text1 = "ListItemsActivity passed: My information to share";
            int duration1 = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(StartActivity.this, text1, duration1);
            toast.show();

        }

    }


    @Override
    public void onResume(){
        super.onResume();
        Log.i(ACTIVITY_NAME,"In onResume()");
    }

    @Override
    public void onStart(){
        super.onStart();
        Log.i(ACTIVITY_NAME,"In onStart()");
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.i(ACTIVITY_NAME,"In onPause()");
    }

    @Override
    public void onStop(){
        super.onStop();
        Log.i(ACTIVITY_NAME, "In onStop()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(ACTIVITY_NAME, "In onDestroy()");

    }
}
