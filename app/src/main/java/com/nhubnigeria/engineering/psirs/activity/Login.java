package com.nhubnigeria.engineering.psirs.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.nhubnigeria.engineering.psirs.R;
import com.nhubnigeria.engineering.psirs.utils.CheckConnection;
import com.nhubnigeria.engineering.psirs.utils.DismissKeyboard;
import com.nhubnigeria.engineering.psirs.utils.SessionManager;
import com.nhubnigeria.engineering.psirs.utils.ViewDialog;

public class Login extends AppCompatActivity {

    private EditText et_phone, et_password;
    public ProgressBar progressBar;
    private Button btn_reset, btn_login, btn_signup;
    private static final String PREFS_NAME = "LoginPrefs";
    SessionManager session;
    ViewDialog alert = new ViewDialog();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        session = new SessionManager(getApplicationContext());

        et_phone = (EditText) findViewById(R.id.login_phone);
        et_password = (EditText) findViewById(R.id.login_password);
        progressBar = (ProgressBar) findViewById(R.id.progress_Bar);
        btn_reset = (Button) findViewById(R.id.reset_password);
        btn_login = (Button) findViewById(R.id.loginbtn);
        btn_signup = (Button) findViewById(R.id.signup);


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DismissKeyboard.dismissKeyboard(Login.this);

                String etPhone = et_phone.getText().toString();
                final String etPassword = et_password.getText().toString();

                if (etPhone.trim().length() > 0 && etPassword.trim().length() > 0) {

                    if (etPhone.equals("07036414710") && etPassword.equals("sam")) {

                        if (CheckConnection.getInstance(getApplicationContext())
                                .checkInternetConnection()){
                            Toast.makeText(Login.this, "Connected",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Login.this, "Not connected",
                                    Toast.LENGTH_SHORT).show();
                        }
                        // Creating user login session
                        session.createLoginSession("07036414710","Sam");
                        startActivity(new Intent(Login.this, MainActivity.class));
                        finish();
                    } else{
                        // phone / password doesn't match
                        alert.showDialog(Login.this, "Login failed..Phone" +
                                "/Password is incorrect");
                    }
                }else{
                    // user didn't enter username or password
                    alert.showDialog(Login.this, "Login failed..Please enter " +
                            "username and password");
                }

            }
        });

        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, PasswordReset.class));
                finish();
            }
        });

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Signup.class));
                finish();
            }
        });
    }


}
