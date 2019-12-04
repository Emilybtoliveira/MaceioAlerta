package br.emily.maceioalerta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

import java.util.Objects;

public class SignInActivity extends AppCompatActivity {

    private TextInputEditText mEmailView;
    private TextInputEditText mPasswordView;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        FirebaseApp.initializeApp(this);
        this.mAuth = FirebaseAuth.getInstance();

        this.mEmailView = findViewById(R.id.signin_email);
        this.mPasswordView = findViewById(R.id.signin_password);

        MaterialButton mSignInButton = findViewById(R.id.signin_sign_in);
        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        MaterialButton mSignUpButton = findViewById(R.id.signin_sign_up);
        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
            }
        });
    }

    private void signIn() {
        if (!Objects.requireNonNull(this.mEmailView.getText()).toString().equals("")) {
            if (!Objects.requireNonNull(this.mPasswordView.getText()).toString().equals("")) {
                this.mAuth.signInWithEmailAndPassword(Objects.requireNonNull(this.mEmailView.getText()).toString(),
                        Objects.requireNonNull(this.mPasswordView.getText()).toString())
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Login succeed.",
                                            Toast.LENGTH_SHORT).show();
                                    finish();
                                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));

                                } else {
                                    Toast.makeText(getApplicationContext(), "Login failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(getApplicationContext(), "Invalid password.",
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Invalid email.",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
