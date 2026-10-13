package com.example.hotelbooking;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hotelbooking.databinding.ActivityLoginBinding;
import com.example.hotelbooking.repository.RepositoryProvider;
import com.example.hotelbooking.repository.UserRepository;
import com.example.hotelbooking.session.SessionManager;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new SessionManager(this);

        // Already logged in from a previous session (real-world apps don't ask every time)
        if (session.isLoggedIn()) {
            goToHome();
            return;
        }

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        UserRepository userRepository = RepositoryProvider.userRepository(this);

        binding.btnLogin.setOnClickListener(v -> {
            String name = binding.etName.getText().toString().trim();
            String email = binding.etEmail.getText().toString().trim();
            String password = binding.etPassword.getText().toString().trim();

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Enter a valid email address", Toast.LENGTH_SHORT).show();
                return;
            }
            if (password.length() < 4) {
                Toast.makeText(this, "Password should be at least 4 characters", Toast.LENGTH_SHORT).show();
                return;
            }

            binding.btnLogin.setEnabled(false);
            // First time for this email -> creates the account. Returning email -> checks the password.
            userRepository.loginOrRegister(name, email, password, success -> {
                binding.btnLogin.setEnabled(true);
                if (success) {
                    session.saveSession(email, name);
                    goToHome();
                } else {
                    Toast.makeText(LoginActivity.this, "Incorrect password for this email", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private void goToHome() {
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }
}
