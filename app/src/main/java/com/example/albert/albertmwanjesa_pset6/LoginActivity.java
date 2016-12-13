package com.example.albert.albertmwanjesa_pset6;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Map;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {


    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("user_tag", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d("user_tag", "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
        alreadyLoggedIn();
        setContentView(R.layout.activity_login);


    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    public void startApp(View view){
        int iD = view.getId();

        mEmailView = (EditText) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);

        if(iD == R.id.email_sign_in_button){
            signIn(mEmailView.getText().toString(), mPasswordView.getText().toString());
        }else if(iD == R.id.email_register_button){
            createAccount(mEmailView.getText().toString(), mPasswordView.getText().toString());
        }
    }

    private void createAccount(final String email, final String passWord){
        mAuth.createUserWithEmailAndPassword(email, passWord)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("create_user", "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(LoginActivity.this, "Authentication successful.",
                                    Toast.LENGTH_SHORT).show();
                            rememberUser(email, passWord);
                            Intent startIntent = new Intent(LoginActivity.this, GarageListActivity.class);
                            startActivity(startIntent);
                            finish();
                        }

                        // ...
                    }
                });
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
    public void signIn(final String email, final String passWord) {
        mAuth.signInWithEmailAndPassword(email, passWord)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("signin_user", "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w("logged_in", "signInWithEmail", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(LoginActivity.this, "Authentication successful.",
                                    Toast.LENGTH_SHORT).show();
                            rememberUser(email, passWord);
                            Intent startIntent = new Intent(LoginActivity.this, GarageListActivity.class);
                            startActivity(startIntent);
                            finish();
                        }

                        // ...
                    }
                });
    }

    private void alreadyLoggedIn(){
        SharedPreferences loginPrefs = getSharedPreferences("settings", getApplicationContext().MODE_PRIVATE);
        Map allEntries = loginPrefs.getAll();
        if(!allEntries.isEmpty()){
            String email = loginPrefs.getString("email", "");
            String passWord = loginPrefs.getString("passWord", "");
            signIn(email, passWord);
        }
    }

    private void rememberUser(String email, String passWord){
        SharedPreferences loginPrefs = getSharedPreferences("settings", getApplicationContext().MODE_PRIVATE);
        SharedPreferences.Editor editor = loginPrefs.edit();
        editor.putString("email", email);
        editor.putString("passWord", passWord);
        editor.commit();
    }

}

