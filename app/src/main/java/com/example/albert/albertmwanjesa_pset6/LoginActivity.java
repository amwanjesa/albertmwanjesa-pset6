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
 * Albert Mwanjesa 16/12/2016
 * A login screen that offers login via email/password.
 * Backend using Google's Firebase Authentication
 */
public class LoginActivity extends AppCompatActivity {


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
                    Log.d("user_tag", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    Log.d("user_tag", "onAuthStateChanged:signed_out");
                }
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
        int viewID = view.getId();

        EditText mEmailView = (EditText) findViewById(R.id.email);
        EditText mPasswordView = (EditText) findViewById(R.id.password);

        if(viewID == R.id.email_sign_in_button){
            signIn(mEmailView.getText().toString(), mPasswordView.getText().toString());
        }else if(viewID == R.id.email_register_button){
            createAccount(mEmailView.getText().toString(), mPasswordView.getText().toString());
        }
    }

    private void createAccount(final String email, final String passWord){
        mAuth.createUserWithEmailAndPassword(email, passWord)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("createUser", "createUser:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            if(passWord.length() < 6){
                                Toast.makeText(LoginActivity.this, R.string.min_characters,
                                        Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(LoginActivity.this, R.string.failed_login,
                                        Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(LoginActivity.this, R.string.logged_in,
                                    Toast.LENGTH_SHORT).show();
                            rememberUser(email, passWord);
                            Intent init = new Intent(LoginActivity.this, GarageListActivity.class);
                            startActivity(init);
                            finish();
                        }
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
                            Intent init = new Intent(LoginActivity.this, GarageListActivity.class);
                            startActivity(init);
                            finish();
                        }
                    }
                });
    }


    // Checked if user has logged in before, if so, no login needed
    private void alreadyLoggedIn(){
        SharedPreferences loginPrefs = getSharedPreferences("settings", this.MODE_PRIVATE);
        Map allEntries = loginPrefs.getAll();
        if(!allEntries.isEmpty()){
            String email = loginPrefs.getString("email", "");
            String passWord = loginPrefs.getString("passWord", "");
            signIn(email, passWord);
        }
    }


    // Remember users credentials so they don't to login again
    private void rememberUser(String email, String passWord){
        SharedPreferences loginPrefs = getSharedPreferences("settings", this.MODE_PRIVATE);
        SharedPreferences.Editor editor = loginPrefs.edit();
        editor.putString("email", email);
        editor.putString("passWord", passWord);
        editor.commit();
    }

}
