package com.ekorydes.bscs6thb060720;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInPage extends AppCompatActivity {

    private EditText emailET,passwordET;
    private ProgressBar objectWaitPB;

    private Button signInUserBtn;
    private FirebaseAuth objectFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_page);

        objectFirebaseAuth=FirebaseAuth.getInstance();
        connectXML();
    }

    private void connectXML()
    {
        try
        {
            emailET=findViewById(R.id.userEmailET);
            passwordET=findViewById(R.id.userPasswordET);

            signInUserBtn =findViewById(R.id.signInUserBtn);
            objectWaitPB=findViewById(R.id.waitProgressBar);

            signInUserBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    signInUser();
                }
            });
        }
        catch (Exception e)
        {
            Toast.makeText(this, "connectXML:"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void signInUser()
    {
        try
        {
            if(!emailET.getText().toString().isEmpty() && !passwordET.getText().toString().isEmpty() && objectFirebaseAuth!=null)
            {
                if(objectFirebaseAuth.getCurrentUser()!=null)
                {
                    objectFirebaseAuth.signOut();
                    Toast.makeText(this, "Already Logged In user sign out successfully", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    objectWaitPB.setVisibility(View.VISIBLE);
                    signInUserBtn.setEnabled(false);
                    objectFirebaseAuth.signInWithEmailAndPassword(emailET.getText().toString(),passwordET.getText().toString())
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    Toast.makeText(SignInPage.this, "User Sign In Successfully", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(SignInPage.this,MainContentPage.class));

                                    objectWaitPB.setVisibility(View.INVISIBLE);
                                    signInUserBtn.setEnabled(true);
                                    finish();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                    objectWaitPB.setVisibility(View.INVISIBLE);
                                    signInUserBtn.setEnabled(true);
                                    Toast.makeText(SignInPage.this, "Fails to sign in user:"
                                            +e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
            else if(emailET.getText().toString().isEmpty())
            {
                Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
                emailET.requestFocus();
            }
            else if(passwordET.getText().toString().isEmpty())
            {
                Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
                passwordET.requestFocus();
            }
            else if(objectFirebaseAuth==null)
            {
                Toast.makeText(this, "Firebase Auth is null", Toast.LENGTH_SHORT).show();
                emailET.requestFocus();
            }
        }
        catch (Exception e)
        {
            objectWaitPB.setVisibility(View.INVISIBLE);
            signInUserBtn.setEnabled(true);
            Toast.makeText(this, "signInUser:"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }



}
