package com.nada.clothes;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        Button Btn = findViewById(R.id.register_btn);
        final EditText InputName = findViewById(R.id.register_username_input);
        final EditText  InputEmail= findViewById(R.id.register_email_input);
        final EditText InputPassword = findViewById(R.id.register_password_input);



        Btn.setOnClickListener(V -> {
            {


            String username = InputName.getText().toString();
            String Email= InputEmail.getText().toString();
            String password = InputPassword.getText().toString();
            DatabaseReference databaseReference;

            FirebaseDatabase db =FirebaseDatabase.getInstance("https://my-application-8f996-default-rtdb.firebaseio.com/");
            databaseReference  = db.getReference();

            if (TextUtils.isEmpty(username))
            {
                Toast.makeText(CreateActivity.this, "Please write your username...", Toast.LENGTH_SHORT).show();
            }
            else if (TextUtils.isEmpty(Email))
            {
                Toast.makeText(CreateActivity.this, "Please write your email...", Toast.LENGTH_SHORT).show();}
            else if (TextUtils.isEmpty(password))
            {
                Toast.makeText(CreateActivity.this, "Please write your password...", Toast.LENGTH_SHORT).show();
            }
            else{

                databaseReference.child(username).setValue().addOnSuccessListener(suc->
                {
                    Toast.makeText(this, "inserted", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(CreateActivity.this, LoginActivity.class);
                    startActivity(intent);
                }).addOnFailureListener(er->
                {
                    Toast.makeText(this, "not inserted"+er.getMessage(), Toast.LENGTH_SHORT).show();});}




        });
    }
}
