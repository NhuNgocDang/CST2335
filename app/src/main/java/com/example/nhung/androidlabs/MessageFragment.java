package com.example.nhung.androidlabs;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;



public class MessageFragment extends Fragment {

    public boolean iAmTablet;
    ChatWindow parent;
    int position;
    String passedMessage;
    long idPassed;
    Intent response;

    public MessageFragment(){}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle infoToPass = getArguments();
       passedMessage = infoToPass.getString("Message");
       idPassed = infoToPass.getLong("ID");

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){


        View screen = inflater.inflate(R.layout.activity_message_fragment, container, false);
        TextView messageDetails = screen.findViewById(R.id.fragment_message_details);
        TextView idDetails = screen.findViewById(R.id.fragment_id_details);

        messageDetails.setText("Message is:"+ passedMessage);
        idDetails.setText("ID=" + idPassed);
        // tv.setText("Message is:"+ passedMessage + "and ID=" + idPassed);


        Button deleteButton = screen.findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(iAmTablet) {
                    parent= (ChatWindow) getActivity();
                    parent.deleteMessage(position);
                    /*MessageFragment newFragment = new MessageFragment();
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ftrans = fm.beginTransaction();
                    ftrans.replace(R.id.chatView, newFragment); // load a fragment into the framelayout
                    ftrans.addToBackStack("name doesn't matter"); // changes the back button behaviour
                    //ftrans.remove(newFragment);
                    ftrans.commit();*/
                    parent.getFragmentManager().beginTransaction().remove(MessageFragment.this).commit();
                }
                else
                    response= new Intent(getActivity(),ChatWindow.class);
                    response.putExtra("DeleteMessage", idPassed);

                    getActivity().setResult(Activity.RESULT_OK,  response);
                    getActivity().finish(); // go to previous activity
            }
        });
        return screen;
    }


}
