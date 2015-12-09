package com.sthapit.sandy.finalncell;

import android.content.Intent;
import android.graphics.Color;
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

public class LoginActivity extends AppCompatActivity {

    UserSessionManager sessionManager;
    EditText editText_username;
    EditText editText_password;
    Button button_login;
    Button button_signup;
    Button button_visibile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sessionManager = new UserSessionManager(getApplicationContext());

        button_visibile = (Button) findViewById(R.id.bt_visibility);
        button_signup = (Button) findViewById(R.id.bt_signup);
        button_login = (Button) findViewById(R.id.bt_Login);
        editText_password = (EditText) findViewById(R.id.et_Password);
        editText_username = (EditText) findViewById(R.id.et_Username);

        button_visibile.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    v.setPressed(true);
                    editText_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    v.setPressed(false);
                    editText_password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                }
                return true;
            }
        });

        button_login.setOnClickListener(new View.OnClickListener() {

                                            @Override
                                            public void onClick(View arg0) {

                                                // Get username, password from EditText
                                                String username = editText_username.getText().toString();
                                                String password = editText_password.getText().toString();
                                                if (editText_username.getText().toString().equals("")) {
                                                    Toast.makeText(LoginActivity.this, "Please enter your user name...", Toast.LENGTH_SHORT).show();
                                                } else if (editText_password.getText().toString().equals("")) {
                                                    Toast.makeText(LoginActivity.this, "Please enter your password...", Toast.LENGTH_SHORT).show();
                                                } else if (!checkLogin(editText_username.getText().toString().trim(), editText_password.getText().toString().trim())) {
                                                    Toast.makeText(LoginActivity.this, "User name or password failed!", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    goToHomeActivity();
                                                }

                                            }

                                            private void goToHomeActivity() {
                                                //saving data to Session (SharedPreferences)
                                                sessionManager.setSavedPassword(editText_password.getText().toString());
                                                sessionManager.setSavedUserName(editText_username.getText().toString());
                                                sessionManager.setUserLoggedIn(true);

                                                //go to Home Screen
                                                Intent intent = new Intent(LoginActivity.this, FoodActivity.class);
                                                startActivity(intent);
                                                finish(); //finish LoginActivity
                                            }

                                            /**
                                             * Checking input is valid?
                                             * @param userName
                                             * @param password
                                             * @return
                                             */
                                            private boolean checkLogin(String userName, String password) {
                                                String uname = "admin";
                                                String pass = "admin";
                                                if (password.equals(uname)) {
                                                    if (pass.equals(userName)) {
                                                        return true;
                                                    }
                                                }


                                                return false;
                                            }

                                        }

        );
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
}

