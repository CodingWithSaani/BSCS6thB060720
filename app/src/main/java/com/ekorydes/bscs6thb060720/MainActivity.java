package com.ekorydes.bscs6thb060720;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void moveToSignUpPage(View v)
    {
        try
        {
            startActivity(new Intent(this,SignUpPage.class));
        }
        catch (Exception e)
        {
            Toast.makeText(this, "moveToSignUpPage:"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void moveToSignInPage(View v)
    {
        try
        {
            startActivity(new Intent(this,SignInPage.class));
        }
        catch (Exception e)
        {
            Toast.makeText(this, "moveToSignInPage:"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
