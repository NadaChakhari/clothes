package com.nada.clothes;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nada.clothes.fragments.homeFragment;

public class LoginActivity extends AppCompatActivity {

    private Button joinNowButton;

    private EditText InputPassword;
    private EditText InputName;

    private Button LoginButton;
    private ProgressDialog loadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        joinNowButton = (Button) findViewById(R.id.main_join_now_btn);
        LoginButton = (Button) findViewById(R.id.login_btn);
        InputName = (EditText) findViewById(R.id.inputname);

        InputPassword = (EditText) findViewById(R.id.login_password_input);
        loadingBar = new ProgressDialog(this);


        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = InputName.getText().toString();
                String password = InputPassword.getText().toString();

                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(LoginActivity.this, "Please write your username...", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginActivity.this, "Please write your password...", Toast.LENGTH_SHORT).show();
                } else {
                    loadingBar.setTitle("Login Account");
                    loadingBar.setMessage("Please wait, while we are checking the credentials.");
                    loadingBar.setCanceledOnTouchOutside(true);
                    loadingBar.show();
                    AllowAccessToAccount(username, password);

                }


            }

        });


        joinNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, CreateActivity.class);
                startActivity(intent);
            }
        });


    }


    private void AllowAccessToAccount(final String username, final String password) {


        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance("https://veild-clothes-default-rtdb.firebaseio.com/").getReference();


        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(username)) {


                    Toast.makeText(LoginActivity.this, "Account with this " + username + " username exists.", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    final String getpass = dataSnapshot.child(username).child(password).getValue(String.class);
                    if (getpass.equals(password)) {
                        Toast.makeText(LoginActivity.this, "logged in Successfully...", Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();

                        Intent intent = new Intent(LoginActivity.this, homeFragment.class );
                        startActivity(intent);
                    } else {
                        Toast.makeText(LoginActivity.this, "Password is incorrect.", Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();

                    }

                } else {
                    Toast.makeText(LoginActivity.this, "Account with this " + username + " username don't exists.", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }


            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
