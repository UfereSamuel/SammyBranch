package com.nhubnigeria.engineering.psirs.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.nhubnigeria.engineering.psirs.R;

public class PasswordReset extends AppCompatActivity {

    private EditText etPhone;
    private Button btnReset, btnBack;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);

        etPhone = (EditText) findViewById(R.id.phoneReset);
        btnReset = (Button) findViewById(R.id.btn_reset_password);
        btnBack = (Button) findViewById(R.id.btn_back);
        progressBar = (ProgressBar) findViewById(R.id.progress_Bar);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PasswordReset.this, Login.class));
                finish();
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String phone = etPhone.getText().toString().trim();

                if (TextUtils.isEmpty(phone)) {
                    etPhone.setError("Phone number cannot be empty");
                    return;
                }
                if (phone.length() != 11) {
                    etPhone.setError("Phone number is invalid");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                startActivity(new Intent(PasswordReset.this, Login.class));
                Toast.makeText(PasswordReset.this, "Password successfully sent",
                        Toast.LENGTH_SHORT).show();
                finish();

            }
        });
    }
}
