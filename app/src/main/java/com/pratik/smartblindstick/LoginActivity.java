package com.pratik.smartblindstick;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private final AppCompatActivity activity = LoginActivity.this;

    NestedScrollView nestedScrollView;

    TextInputLayout textInputLayoutEmail;
    TextInputLayout textInputLayoutPassword;

    TextInputEditText textInputEditTextEmail;
    TextInputEditText textInputEditTextPassword;

    AppCompatButton appCompatButtonLogin;

    AppCompatTextView textViewLinkRegister;

    InputValidation inputValidation;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        databaseHelper = new DatabaseHelper(activity);
        inputValidation = new InputValidation(activity);

        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);

        textInputLayoutEmail = (TextInputLayout) findViewById(R.id.textInputLayoutEmail);
        textInputLayoutPassword = (TextInputLayout) findViewById(R.id.textInputLayoutPassword);

        textInputEditTextEmail = (TextInputEditText) findViewById(R.id.textInputEditTextEmail);
        textInputEditTextPassword = (TextInputEditText) findViewById(R.id.textInputEditTextPassword);

        appCompatButtonLogin = (AppCompatButton) findViewById(R.id.appCompatButtonLogin);

        textViewLinkRegister = (AppCompatTextView) findViewById(R.id.textViewLinkRegister);

        appCompatButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                verifyFromSQLite();

            }
        });

        textViewLinkRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentRegister = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intentRegister);
            }
        });


    }

    private void verifyFromSQLite() {
        if (!inputValidation.isInputEditTextFilled(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextEmail(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextPassword, textInputLayoutPassword, getString(R.string.error_message_password))) {
            return;
        }

        if (databaseHelper.checkUser(textInputEditTextEmail.getText().toString().trim()
                , textInputEditTextPassword.getText().toString().trim())) {
            emptyInputEditText();
            Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
            Intent accountsIntent = new Intent(getApplicationContext(), BlindStickActivity.class);
            startActivity(accountsIntent);


        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.error_valid_email_password), Toast.LENGTH_SHORT).show();
        }
    }

    private void emptyInputEditText() {
        textInputEditTextEmail.setText(null);
        textInputEditTextPassword.setText(null);
    }

}