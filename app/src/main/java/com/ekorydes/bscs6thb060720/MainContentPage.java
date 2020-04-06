package com.ekorydes.bscs6thb060720;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MainContentPage extends AppCompatActivity {

    FirebaseAuth objectFirebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_content_page);

        objectFirebaseAuth=FirebaseAuth.getInstance();
        if(objectFirebaseAuth.getCurrentUser()!=null)
        {
            Toast.makeText(this, ""+objectFirebaseAuth.getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();
        }
    }
}
