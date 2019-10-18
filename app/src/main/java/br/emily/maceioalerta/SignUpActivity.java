package br.emily.maceioalerta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {

    private TextInputEditText mNameView;
    private TextInputEditText mEmailView;
    private TextInputEditText mPasswordView;
    private TextInputEditText mConfirmPasswordView;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        FirebaseApp.initializeApp(this);
        this.mAuth = FirebaseAuth.getInstance();

        mNameView = findViewById(R.id.signup_name);
        mEmailView = findViewById(R.id.signup_email);
        mPasswordView = findViewById(R.id.signup_password);
        mConfirmPasswordView = findViewById(R.id.signup_confirm_password);

        MaterialButton mSignUpButton = findViewById(R.id.signup_sign_up);
        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
            }
        });
    }

    private void signUp() {
        if (!Objects.requireNonNull(this.mNameView.getText()).toString().equals("")) {
            if (!Objects.requireNonNull(this.mEmailView.getText()).toString().equals("")) {
                if (!Objects.requireNonNull(this.mPasswordView.getText()).toString().equals("")) {
                    if (Objects.requireNonNull(this.mPasswordView.getText()).toString()
                            .equals(Objects.requireNonNull(this.mConfirmPasswordView.getText()).toString())) {
                        this.mAuth.createUserWithEmailAndPassword(Objects.requireNonNull(this.mEmailView.getText()).toString(),
                                Objects.requireNonNull(this.mPasswordView.getText()).toString())
                                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(getApplicationContext(), "Registration succeed.",
                                                    Toast.LENGTH_SHORT).show();
                                            FirebaseUser user = mAuth.getCurrentUser();
                                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                                    .setDisplayName(Objects.requireNonNull(mNameView.getText()).toString())
                                                    .build();
                                            if (user != null) {
                                                user.updateProfile(profileUpdates)
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    Log.d("RegisterActivity", "User registered.");
                                                                }
                                                            }
                                                        });
                                            }
                                            finish();
                                            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Registration failed.",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                })
                                .addOnFailureListener(this, new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d("LoginActivity", "Register: OnFailure");
                                        Toast.makeText(getApplicationContext(), e.getMessage(),
                                                Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } else {
                        Toast.makeText(getApplicationContext(), "Passwords don't match.",
                                Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Invalid password.",
                            Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Invalid email.",
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Invalid name.",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
