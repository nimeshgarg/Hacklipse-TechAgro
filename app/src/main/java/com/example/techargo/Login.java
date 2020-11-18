package com.example.techargo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    private Button btn_login;
    private EditText pass, email;
    private ProgressBar bar;
    private FirebaseAuth mAuth;

    @Override
    public void onBackPressed() {
        finishAffinity();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btn_login = findViewById(R.id.btn_login);
        email = findViewById(R.id.email_login);
        pass = findViewById(R.id.pass_login);
        bar = findViewById(R.id.bar_login);
        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(),Main_page.class));
        }
        btn_login.setOnClickListener(v -> {
            bar.setVisibility(View.VISIBLE);
            if(isEmailValid(email.getText())){
                btn_login.setEnabled(false);
                mAuth.signInWithEmailAndPassword(email.getText().toString(),pass.getText().toString()).addOnCompleteListener(task -> {
                    if(!task.isSuccessful()){
                        Toast.makeText(Login.this, "Invalid Login Credentials!!", Toast.LENGTH_SHORT).show();
                        pass.setText("");
                        btn_login.setEnabled(true);
                        bar.setVisibility(View.INVISIBLE);
                    }else{
                        Toast.makeText(Login.this, "Login Successful Completed", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),Main_page.class));
                    }
                });
            }else{
                Toast.makeText(Login.this, "Invalid Email Format!!", Toast.LENGTH_SHORT).show();
                pass.setText("");
                bar.setVisibility(View.INVISIBLE);
            }
        });
    }
    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}