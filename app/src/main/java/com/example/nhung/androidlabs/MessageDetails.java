package com.example.nhung.androidlabs;


import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

public class MessageDetails extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_details);

        //get bundle back
        Bundle infoPass = getIntent().getExtras();

        // repeat from tablet sectiom

        MessageFragment newFragment = new MessageFragment();
        newFragment.iAmTablet = false;

        // give information to bundle
        newFragment.setArguments(infoPass);

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ftrans = fm.beginTransaction();
        ftrans.replace(R.id.fragment_location, newFragment);
        ftrans.addToBackStack("name doesn't matter");
        ftrans.commit();
    }
}
