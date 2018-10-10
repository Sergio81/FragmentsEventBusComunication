package com.androidtrainin.fragmentseventbuscomunication;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import static android.support.v4.content.ContextCompat.getSystemService;


/**
 * A simple {@link Fragment} subclass.
 */
public class MessageFragment extends Fragment
    implements View.OnClickListener {

    TextView editText;
    TextView txtMessage;
    TextView txtTitle;

    public class MessageEvent {
        private final String message;
        private final int idSender;

        public MessageEvent(String message, int idSender) {
            this.message = message;
            this.idSender = idSender;
        }

        public String getMessage() {
            return message;
        }

        public int getIdSender(){
            return idSender;
        }
    }

    public MessageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        EventBus.getDefault().register(this);
        View view = inflater.inflate(R.layout.fragment_message, container, false);

        Button btnSubmit = view.findViewById(R.id.button);
        txtTitle = view.findViewById(R.id.txtTitle);
        txtMessage = view.findViewById(R.id.txtMessage);
        editText = view.findViewById(R.id.editText);

        btnSubmit.setOnClickListener(this);

        return view;
    }

    @Subscribe
    public void onEvent(MessageEvent event){
        if(this.getId() != event.idSender)
            txtMessage.setText(event.getMessage());
    }

    @Override
    public void onClick(View v) {
        EventBus.getDefault().post(new MessageEvent(editText.getText().toString(), this.getId()));
        editText.setText("");
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

}
