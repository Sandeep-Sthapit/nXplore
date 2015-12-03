package com.sthapit.sandy.finalncell;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity{

    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();

    private static final String LOGIN_URL = "http://10.100.0.201/login.php";
    private static final String REGISTER_URL = "http://10.100.0.201/register.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    EditText password ,username;
    String user,pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final Button button_login = (Button)findViewById(R.id.bt_visibility);
        password = (EditText) findViewById(R.id.et_Password);
        username = (EditText) findViewById(R.id.et_Username);

        button_login.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String user = username.getText().toString();
                        String pass = password.getText().toString();
                        switch(v.getId()){
                            case R.id.bt_Login:
                                new LoginUser().execute();
                                break;
                            case R.id.bt_signup:
                                new CreateUser().execute();
                                break;
                            default:
                                break;
                        }

                    }
                }
        );

        button_login.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    v.setPressed(true);
                    password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    v.setPressed(false);
                    password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                }
                return true;
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class LoginUser extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(LoginActivity.this);
            pDialog.setMessage("Login in...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product deleted
            pDialog.dismiss();
            if (file_url != null){
                Toast.makeText(LoginActivity.this, file_url, Toast.LENGTH_LONG).show();
            }

        }

        @Override
        protected String doInBackground(String... para) {
            // TODO Auto-generated method stub
            int success=0;

            try {
                JSONObject jSon = new JSONObject();
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("username", user));
                params.add(new BasicNameValuePair("password", pass));
                params.add(new BasicNameValuePair("request", "login"));

                jSon = jsonParser.makeHttpRequest(
                        LOGIN_URL, "POST", params);
                success = jSon.getInt(TAG_SUCCESS);

                if (success == 1){
                    SharedPreferences sp = PreferenceManager
                            .getDefaultSharedPreferences(LoginActivity.this);
                    SharedPreferences.Editor edit = sp.edit();
                    edit.putString("username", user);
                    edit.commit();

                    //Intent i = new Intent(LoginActivity.this, items.class);
                    //startActivity(i);
                    return jSon.getString(TAG_MESSAGE);
                }else{
                    return jSon.getString(TAG_MESSAGE);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

    }

    class CreateUser extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(LoginActivity.this);
            pDialog.setMessage("Creating User...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product deleted
            pDialog.dismiss();
            if (file_url != null){
                Toast.makeText(LoginActivity.this, file_url, Toast.LENGTH_LONG).show();
            }

        }

        @Override
        protected String doInBackground(String... para) {
            // TODO Auto-generated method stub
            int success=0;

            try {
                JSONObject jSon = new JSONObject();
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("username", user));
                params.add(new BasicNameValuePair("password", pass));
                params.add(new BasicNameValuePair("request", "register"));
                //params.add(new(BasicNameValuePair("email","email")));
                jSon = jsonParser.makeHttpRequest(
                        REGISTER_URL, "POST", params);
                success = jSon.getInt(TAG_SUCCESS);

                if (success == 1){
                    SharedPreferences sp = PreferenceManager
                            .getDefaultSharedPreferences(LoginActivity.this);
                    SharedPreferences.Editor edit = sp.edit();
                    edit.putString("username", user);
                    edit.commit();
                    //Intent i = new Intent(LoginActivity.this, items.class);
                    //startActivity(i);
                    return jSon.getString(TAG_MESSAGE);
                }else{
                    return jSon.getString(TAG_MESSAGE);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

    }

}

