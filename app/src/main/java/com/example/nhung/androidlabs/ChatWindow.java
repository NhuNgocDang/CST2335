package com.example.nhung.androidlabs;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatWindow extends Activity {
   // public ChatAdapter messageAdapter;
    private ListView listView;
    private EditText editText;
    private Button sendButton;
    static ArrayList<String> chatList; // chua string
    LayoutInflater inflater;
    TextView message;
    protected static final String ACTIVITY_NAME = "ChatWindow";
    // public ChatAdapter chatAdapter;
    public SQLiteDatabase db;
    public static ChatAdapter messageAdapter; // chua id
    View frameLayout;
    Cursor results;
    TextView messageID;
    public boolean iAmTablet;
    public int position;
    public long idPassed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        listView = findViewById(R.id.chatView);
        editText = findViewById(R.id.editText);

        // does the framelayout exist?
        frameLayout = findViewById(R.id.fragment_location);

        // was a frameLayout found?
         iAmTablet = (frameLayout != null);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle infoPass = new Bundle(); // pass the message

                infoPass.putString("Message", chatList.get(position));
                infoPass.putLong("ID", messageAdapter.getItemId(position));

                if(iAmTablet){// on a tablet
                    MessageFragment newFragment = new MessageFragment();
                    newFragment.iAmTablet=true;

                    // give information to bundle
                    newFragment.setArguments(infoPass);

                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ftrans = fm.beginTransaction();
                    ftrans.replace(R.id.fragment_location, newFragment); // load a fragment into the framelayout
                    ftrans.addToBackStack("name doesn't matter"); // changes the back button behaviour
                    ftrans.commit(); // actually load it
                }
                else //on the phone
                {
                    // go to the new window
                    Intent nextPage = new Intent(ChatWindow.this, MessageDetails.class);
                    nextPage.putExtras(infoPass); // send info
                    startActivityForResult(nextPage, 67);

                }

            }
        });

        chatList = new ArrayList<>();
        ChatDatabaseHelper dbOpener = new ChatDatabaseHelper(this); // helper object

        db = dbOpener.getWritableDatabase();
        results = db.query(ChatDatabaseHelper.TABLE_NAME, new String[]{ChatDatabaseHelper.KEY_ID, ChatDatabaseHelper.KEY_MESSAGE}, null, null, null, null, null, null);


                if (results != null) {
            Log.i(ACTIVITY_NAME, "Cursor's column count =" + results.getColumnCount());

            while (results.moveToNext()) {
                String message = results.getString(results.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE));
                Log.i(ACTIVITY_NAME, "SQL MESSAGE:" + message);

                chatList.add(message);
            }

            for (int i = 0; i < results.getColumnCount(); i++) {
                Log.i(ACTIVITY_NAME, "Column Name: " + results.getColumnName(i));
            }
        }

        messageAdapter = new ChatAdapter(this);
        listView.setAdapter(messageAdapter);

        sendButton = findViewById(R.id.sendButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // string message
                String message = editText.getText().toString();
                ContentValues newRow = new ContentValues();
                newRow.put(ChatDatabaseHelper.KEY_MESSAGE, message); //all columns have a value
                // ready to insert into database
                db.insert(ChatDatabaseHelper.TABLE_NAME, null, newRow);
                results = db.query(ChatDatabaseHelper.TABLE_NAME, new String[]{ChatDatabaseHelper.KEY_ID, ChatDatabaseHelper.KEY_MESSAGE}, null, null, null, null, null, null);


                chatList.add(message);
                messageAdapter.notifyDataSetChanged(); // data has changed

                editText.setText("");


            }
        });


    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        db.close();
    }

    private class ChatAdapter extends ArrayAdapter<String> {

        public ChatAdapter(Context ctx) {
            super(ctx, 0);
        }


        public int getCount() {

            return chatList.size();
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            inflater = ChatWindow.this.getLayoutInflater();

            View result = null;
            if (position % 2 == 0)
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            else
                result = inflater.inflate(R.layout.chat_row_outgoing, null);
            message = (TextView) result.findViewById(R.id.message_text);
            message.setText(getItem(position));

            return result;
        }


        public String getItem(int position) {
            return chatList.get(position);
        }

        public long getItemId(int position) {
            results.moveToPosition(position);
            long messageID =  results.getLong(results.getColumnIndex(ChatDatabaseHelper.KEY_ID));
            return messageID;
        }

    }

    public void onActivityResult(int request, int result, Intent data){
        if (result == Activity.RESULT_OK){
            Intent deleteInt = new Intent();
            deleteMessage(idPassed);
        }


    }
    public void deleteMessage(long id){
        Intent deleteIntent = new Intent(this, ChatWindow.class);
        this.startActivity(deleteIntent);
        db.delete(ChatDatabaseHelper.TABLE_NAME, ChatDatabaseHelper.KEY_ID +" = ?", new String[]{"id"});
        results = db.query(ChatDatabaseHelper.TABLE_NAME, new String[]{ChatDatabaseHelper.KEY_ID, ChatDatabaseHelper.KEY_MESSAGE}, null, null, null, null, null, null);
        chatList.remove(position);
        messageAdapter.notifyDataSetChanged(); // data has changed
    }

}

