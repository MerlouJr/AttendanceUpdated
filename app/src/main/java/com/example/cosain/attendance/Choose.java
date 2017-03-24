package com.example.cosain.attendance;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Choose extends AppCompatActivity {
    private Button button1,button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);

        button1 = (Button) findViewById(R.id.choiceMessage);
        button2 = (Button) findViewById(R.id.choiceAttendance);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isNetworkAvailable() == true){
                    Toast.makeText(Choose.this,"Internet Connection is Available",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(Choose.this,Users.class));
                }else {
                    Toast.makeText(Choose.this, "No Internet Connection", Toast.LENGTH_LONG).show();
                    finish();
                    startActivity(new Intent(Choose.this, Choose.class));
                }
            }
        });


        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isNetworkAvailable() == true) {
                    Toast.makeText(Choose.this, "Internet Connection is Available", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(Choose.this, Attendance.class));
                }else{
                    Toast.makeText(Choose.this,"No Internet Connection",Toast.LENGTH_LONG).show();
                    finish();
                    startActivity(new Intent(Choose.this, Choose.class));

                }
            }
        });


    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
