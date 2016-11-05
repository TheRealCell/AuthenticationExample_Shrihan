package com.example.shrihan.authenticationexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    EditText mEmailEditText;
    EditText mPasswordEditText;
    Button mLoginButton;
    Button mRegisterButton;

    FirebaseUser mCurrentUser;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            mCurrentUser  = firebaseAuth.getCurrentUser();
            if (mCurrentUser != null) {
                Log.w(TAG, "Signed In");
            } else {
                Log.w(TAG, "Signed Out");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        mAuth.addAuthStateListener(mAuthStateListener);

        mEmailEditText = (EditText) findViewById(R.id.email);
        mPasswordEditText = (EditText) findViewById(R.id.password);
        mLoginButton = (Button) findViewById(R.id.login);
        mRegisterButton = (Button) findViewById(R.id.Register);

        mAuth.signOut();
    }

    public void loginUser(View view) {
        mAuth.signInWithEmailAndPassword(
                mEmailEditText.getText().toString(),
                mPasswordEditText.getText().toString()
        ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void registerUser(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}
