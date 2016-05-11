package com.example.ahish.dataretrieval;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.internal.Utility;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UserProfile extends AppCompatActivity {


    String name,gender,id,s = "";
    TextView Name,Gender,FList;
    ImageView cover;

    Button FLcall;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Bundle bundle = getIntent().getExtras();
        name = bundle.getString("name");
        gender = bundle.getString("gender");
        id = bundle.getString("id");
        Name = (TextView) findViewById(R.id.name);
        FList = (TextView) findViewById(R.id.FLlist);
        FLcall = (Button) findViewById(R.id.FLcall);
        final StringBuilder st = new StringBuilder(s);


        FLcall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GraphRequest(
                        AccessToken.getCurrentAccessToken(),
                        "/me/taggable_friends",
                        null,
                        HttpMethod.GET,
                        new GraphRequest.Callback() {
                            public void onCompleted(GraphResponse response) {
                                JSONObject jarr1=response.getJSONObject();
                                JSONArray jarr= null;
                                try {
                                    jarr = jarr1.getJSONArray("data");

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                int x=1;
                                for (int i = 0; i < jarr.length(); i++) {
                                    try {
                                        //Toast.makeText(getApplicationContext(),jarr.getJSONObject(i).getString("name"),Toast.LENGTH_SHORT).show();
                                        st.append(x+". ");
                                        st.append(""+jarr.getJSONObject(i).getString("name"));
                                        st.append(System.getProperty("line.separator"));
                                        x++;
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                                FList.setText(""+st.toString());

                            }
                        }
                ).executeAsync();

            }
        });
        cover = (ImageView)findViewById(R.id.cover);
        Gender = (TextView) findViewById(R.id.Gender);
        Name.setText(name);
        //Gender.setText(gender);
        Glide.with(this).load("http://graph.facebook.com/"+id+"/picture?type=large").into(cover);
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Added to Friend list", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }
}
