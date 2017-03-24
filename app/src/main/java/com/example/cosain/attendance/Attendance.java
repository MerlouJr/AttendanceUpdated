package com.example.cosain.attendance;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.firebase.client.Firebase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import static com.example.cosain.attendance.UserDetails.username;


public class Attendance extends AppCompatActivity {
    ListView usersAttendance;
    Button button1;
    TextView noUsersText;

    ArrayList<String> al = new ArrayList<>();
    int totalUsers = 0;
    ProgressDialog pd;
    ListView list;
    final Context context = this;
    Firebase ref1, ref2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        usersAttendance = (ListView) findViewById(R.id.usersAtt);
        noUsersText = (TextView) findViewById(R.id.noUsersAtt);

        //final TextView tv = (TextView) findViewById(R.id.tv);
        //final LinearLayout rl = (LinearLayout) findViewById(R.id.rl);
        button1 = (Button) findViewById(R.id.button3);

        pd = new ProgressDialog(Attendance.this);
        pd.setMessage("Loading...");
        pd.show();

        String url = "https://attendanceapp-d5b2d.firebaseio.com/user.json";


        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                doOnSuccess(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println("" + volleyError);
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(Attendance.this);
        rQueue.add(request);


        usersAttendance.setOnItemClickListener(new AdapterView.OnItemClickListener() {



            @Override
            public void onItemClick(final AdapterView<?> parent, View view, final int position, final long id) {

                final Firebase reference = new Firebase("https://attendanceapp-d5b2d.firebaseio.com/user"+UserDetails.username );
                final CharSequence[] items1 = {
                        "Message", "Take Attendance", "Delete"
                };

                AlertDialog.Builder builder1 = new AlertDialog.Builder(Attendance.this);
                builder1.setTitle("Would you like to: ")
                        .setSingleChoiceItems(items1, -1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                switch (which) {

                                    case 0:
                                        UserDetails.chatWith = al.get(position);
                                        startActivity(new Intent(Attendance.this, MainActivity.class));
                                        break;

                                    case 1:


                                        final CharSequence[] items = {
                                                "Present", "Late", "Absent"
                                        };


                                        AlertDialog.Builder builder = new AlertDialog.Builder(Attendance.this);
                                        builder.setTitle("Student's Attendance")
                                                .setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {

                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        // TODO Auto-generated method stub
                                                        switch (which) {
                                                            case 0:
                                                                parent.getChildAt(position).setBackgroundColor(Color.GREEN);
                                                                break;
                                                            case 1:
                                                                parent.getChildAt(position).setBackgroundColor(Color.YELLOW);
                                                                break;
                                                            case 2:
                                                                parent.getChildAt(position).setBackgroundColor(Color.RED);
                                                                break;

                                                        }

                                                    }
                                                });


                                        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int id) {
                                            }
                                        });

                                        AlertDialog alert = builder.create();
                                        alert.show();
                                        break;


                                    case 2:
                                     // TODO Some codes for deletion of item


                                                break;


                                }


                            }
                        });

                AlertDialog alert = builder1.create();
                alert.show();

            }





        });
    }

            public void addMessageBox(String message, int type) {
                TextView textView = new TextView(Attendance.this);
                textView.setText(message);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                lp.setMargins(0, 0, 0, 10);
                textView.setLayoutParams(lp);

                if (type == 1) {
                    textView.setBackgroundResource(R.drawable.rounded_corner1);
                } else {
                    textView.setBackgroundResource(R.drawable.rounded_corner2);
                }
            }

            public void doOnSuccess(String s) {
                try {
                    JSONObject obj = new JSONObject(s);

                    Iterator i = obj.keys();
                    String key = "";

                    while (i.hasNext()) {
                        key = i.next().toString();

                        if (!key.equals(username)) {
                            al.add(key);
                        }

                        totalUsers++;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (totalUsers <= 1) {
                    noUsersText.setVisibility(View.VISIBLE);
                    usersAttendance.setVisibility(View.GONE);
                } else {
                    noUsersText.setVisibility(View.GONE);
                    usersAttendance.setVisibility(View.VISIBLE);
                    usersAttendance.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, al));
                }

                pd.dismiss();
            }

}
