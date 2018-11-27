package com.example.nhung.androidlabs;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TestToolBar extends AppCompatActivity {

    Toast toastAbout;
    Button bottomButton;
    Snackbar  snackbar;
    String currentMessage = "You selected item 1";
    EditText editTextDialog3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_tool_bar);

        //add code
        Toolbar lab8_toolbar = findViewById(R.id.lab8_toolbar);
        setSupportActionBar(lab8_toolbar);
        bottomButton = findViewById(R.id.toolbar_bottom_button);
        bottomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.make(findViewById(R.id.action_one), "Message to show", Snackbar.LENGTH_LONG)
                        .show();
            }
        });
    }

    /**
     * @param m
     * the function is to create the toolbar by inflating it from xml file
     * */

       public boolean onCreateOptionsMenu(Menu m){
            getMenuInflater().inflate(R.menu.toolbar_menu, m);

            return true;
    }
    /**
     * @param mi : the object was selected by user
     * */
    public boolean onOptionsItemSelected(MenuItem mi){
        int id = mi.getItemId();
        switch (id){
            case R.id.action_one:
                snackbar.make(findViewById(R.id.lab8_toolbar), currentMessage, snackbar.LENGTH_SHORT).show();
                Log.d("Toolbar", "Option 1 is selected");
                break;
            case R.id.action_two:
                AlertDialog.Builder builder = new AlertDialog.Builder(TestToolBar.this);
                builder.setTitle(R.string.dialog_tittle);
                        // Add the buttons
                builder.setPositiveButton(R.string.positive_button, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                        Intent intent = new Intent(TestToolBar.this, StartActivity.class);
                        TestToolBar.this.finish();
                    }
                });
                builder.setNegativeButton(R.string.negative_button, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
                        // Create the AlertDialog
                AlertDialog dialog = builder.create();
                dialog.show();


                Log.d("Toolbar", "Option 2 is selected");
                break;
            case R.id.action_three:
                AlertDialog.Builder builderItem3 = new AlertDialog.Builder(TestToolBar.this);
                // get the layout inflater
                LayoutInflater inflater = TestToolBar.this.getLayoutInflater();
                View dialog3View;
                dialog3View =inflater.inflate(R.layout.dialog_item3, null);
                builderItem3.setView(dialog3View);

                editTextDialog3 = dialog3View.findViewById(R.id.dialog_edit_text);


                builderItem3.setPositiveButton(R.string.positive_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        currentMessage = editTextDialog3.getText().toString();

                    }
                });

                builderItem3.setNegativeButton(R.string.negative_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog dialog3 = builderItem3.create();
                dialog3.show();

                Log.d("Toolbar", "Option 3 is selected");

                break;
            case R.id.action_about:
                Log.d("Tooldbar", "About is selected");
                toastAbout.makeText(TestToolBar.this, "Version 1.0, by Nhu Ngoc Dang", Toast.LENGTH_LONG).show();
                break;

        }
        return true;
    }
}
