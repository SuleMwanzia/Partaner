package com.example.partaner;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    private EditText etEmail,etPassword,etPassword2;
    private Button btnSignUp,btnLogIn;
    private ProgressDialog pd;
    private FirebaseAuth mAuth;
    private LinearLayout parentLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        mAuth=FirebaseAuth.getInstance();
        pd=new ProgressDialog(this);
        pd.setTitle("Hold on...");
        pd.setMessage("Creating an account for you...");
        etEmail= findViewById(R.id.etEmail);
        etPassword= findViewById(R.id.etPassword);
        etPassword2= findViewById(R.id.etPassword2);
        btnSignUp= findViewById(R.id.btnSignUp);
        parentLayout= findViewById(R.id.parent);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String password2 = etPassword2.getText().toString().trim();
                if (!email.contains("@") && TextUtils.isEmpty(email) && email.length() <7) {
                    givemesometoast(etEmail, "Oops! Invalid Email...");
                } else if (!TextUtils.isEmpty(password) && password.length() < 7 || !TextUtils.isEmpty(password2) && password2.length() < 7) {
                    if (password.equals(password2) && password.length() >= 7) {
                        pd.show();
                        mAuth.createUserWithEmailAndPassword(email, password2).addOnCompleteListener(new OnCompleteListener<AuthResult>() {


                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    pd.dismiss();
                                    finish();

                                    Pair[] pairs = new Pair[1];
                                    pairs[0] = new Pair<View, String>(etEmail, "etTransition");

                                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(RegisterActivity.this, pairs);
                                    startActivity(new Intent(RegisterActivity.this, ProfileActivity.class), options.toBundle());

                                } else {
                                    pd.dismiss();

                                    Toast.makeText(RegisterActivity.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        //Toast.makeText(RegisterActivity.this, "Correct Credentials", Toast.LENGTH_SHORT).show();

                        //givemesometoast("Your credential inputs are correct");
                    } else {
                        pd.dismiss();
                        givemesometoast(etPassword, "Oops! Passwords Don't Match...");
                    }
                } else {
                    givemesometoast(etPassword, "Invalid Password");
                }
            }
        });
    }
    private void givemesometoast(EditText eText, String s) { eText.setError(s); eText.requestFocus();}


    public  void  loginme(View view) {
        Pair[] pairs=new Pair[1];
        pairs[0]=new Pair<View, String>(etEmail, "etTransition");

        ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(RegisterActivity.this,pairs);

        startActivity(new Intent(RegisterActivity.this,LoginActivity.class),options.toBundle());
        finish();
    }

}
