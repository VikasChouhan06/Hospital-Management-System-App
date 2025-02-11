package utils;


import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.AuthResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

/**
 * Firebase Authentication Manager
 * Handles user login, registration, and logout.
 */
public class FirebaseAuthManager {
    private static final String TAG = "FirebaseAuthManager";
    private FirebaseAuth auth;

    public FirebaseAuthManager() {
        auth = FirebaseAuth.getInstance();
    }

    /**
     * Get the currently logged-in user
     */
    public FirebaseUser getCurrentUser() {
        return auth.getCurrentUser();
    }

    /**
     * Register a new user with email and password
     */
    public void registerUser(String email, String password, Activity activity, AuthCallback callback) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = auth.getCurrentUser();
                            callback.onSuccess(user);
                        } else {
                            Log.e(TAG, "Registration Failed: " + task.getException().getMessage());
                            callback.onFailure(task.getException().getMessage());
                        }
                    }
                });
    }

    /**
     * Login user with email and password
     */
    public void loginUser(String email, String password, Activity activity, AuthCallback callback) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = auth.getCurrentUser();
                            callback.onSuccess(user);
                        } else {
                            Log.e(TAG, "Login Failed: " + task.getException().getMessage());
                            callback.onFailure(task.getException().getMessage());
                        }
                    }
                });
    }

    /**
     * Logout the current user
     */
    public void logoutUser() {
        auth.signOut();
    }

    /**
     * Callback interface for authentication responses
     */
    public interface AuthCallback {
        void onSuccess(FirebaseUser user);
        void onFailure(String errorMessage);
    }
}
