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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;

public class SignUpPage extends AppCompatActivity {

    private EditText emailET,passwordET;
    private ProgressBar objectWaitPB;

    private Button signUpUserBtn;
    private FirebaseAuth objectFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);

        objectFirebaseAuth=FirebaseAuth.getInstance();
        connectXML();
    }

    private void connectXML()
    {
        try
        {
            emailET=findViewById(R.id.userEmailET);
            passwordET=findViewById(R.id.userPasswordET);

            signUpUserBtn=findViewById(R.id.signUpUserBtn);
            objectWaitPB=findViewById(R.id.waitProgressBar);

            signUpUserBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkIfUserExists();
                }
            });
        }
        catch (Exception e)
        {
            Toast.makeText(this, "connectXML:"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void checkIfUserExists()
    {
        try
        {
            if(!emailET.getText().toString().isEmpty() && !passwordET.getText().toString().isEmpty())
            {
                objectWaitPB.setVisibility(View.VISIBLE);
                signUpUserBtn.setEnabled(false);
                if(objectFirebaseAuth!=null)
                {
                    objectFirebaseAuth.fetchSignInMethodsForEmail(emailET.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                                @Override
                                public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                                     boolean check=task.getResult().getSignInMethods().isEmpty();
                                     if(check)
                                     {
                                         signUpUser();
                                     }
                                     else if(!check)
                                     {
                                         objectWaitPB.setVisibility(View.INVISIBLE);
                                         signUpUserBtn.setEnabled(true);
                                         Toast.makeText(SignUpPage.this, "User already exists in firebase", Toast.LENGTH_SHORT).show();
                                     }
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                    objectWaitPB.setVisibility(View.INVISIBLE);
                                    signUpUserBtn.setEnabled(true);
                                    Toast.makeText(SignUpPage.this, "Fails to get user list:"+
                                            e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                else
                {
                    objectWaitPB.setVisibility(View.INVISIBLE);
                    signUpUserBtn.setEnabled(true);
                    Toast.makeText(this, "Firebase Auth is null", Toast.LENGTH_SHORT).show();
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
        }
        catch (Exception e)
        {
            objectWaitPB.setVisibility(View.INVISIBLE);
            signUpUserBtn.setEnabled(true);
            Toast.makeText(this, "checkIfUserExists:"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    private void signUpUser()
    {
        try
        {
            if(!emailET.getText().toString().isEmpty() && !passwordET.getText().toString().isEmpty())
            {
                if(objectFirebaseAuth!=null)
                {
                    objectFirebaseAuth.createUserWithEmailAndPassword(
                            emailET.getText().toString(),
                            passwordET.getText().toString()
                    )
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            objectWaitPB.setVisibility(View.INVISIBLE);
                            signUpUserBtn.setEnabled(true);
                            Toast.makeText(SignUpPage.this, "User sign up successfully", Toast.LENGTH_SHORT).show();
                            if(authResult.getUser()!=null)
                            {

                                objectFirebaseAuth.signOut();
                                startActivity(new Intent(SignUpPage.this,
                                        MainActivity.class));

                                finish();
                            }

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            signUpUserBtn.setEnabled(true);
                            objectWaitPB.setVisibility(View.INVISIBLE);
                            Toast.makeText(SignUpPage.this, "Failed to sign up:"+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    ;
                }
                else
                {
                    signUpUserBtn.setEnabled(true);
                    objectWaitPB.setVisibility(View.INVISIBLE);
                    Toast.makeText(this, "Firebase Auth is null", Toast.LENGTH_SHORT).show();
                }
            }
            else if(emailET.getText().toString().isEmpty())
            {

                signUpUserBtn.setEnabled(true);
                objectWaitPB.setVisibility(View.INVISIBLE);
                Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
                emailET.requestFocus();
            }
            else if(passwordET.getText().toString().isEmpty())
            {
                signUpUserBtn.setEnabled(true);
                objectWaitPB.setVisibility(View.INVISIBLE);
                Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
                passwordET.requestFocus();
            }
        }
        catch (Exception e)
        {
            signUpUserBtn.setEnabled(true);
            objectWaitPB.setVisibility(View.INVISIBLE);
            Toast.makeText(this, "signUpUser:"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
