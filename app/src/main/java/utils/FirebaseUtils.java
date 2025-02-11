package utils;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Firebase Utility Class
 * Manages Firebase Authentication and Firestore instances.
 */
public class FirebaseUtils {
    private static FirebaseAuth auth = FirebaseAuth.getInstance();
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();

    /**
     * Get Firebase Authentication instance
     */
    public static FirebaseAuth getAuth() {
        return auth;
    }

    /**
     * Get Firestore database instance
     */
    public static FirebaseFirestore getDatabase() {
        return db;
    }

    /**
     * Get currently logged-in user
     */
    public static FirebaseUser getCurrentUser() {
        return auth.getCurrentUser();
    }

    /**
     * Logout the user
     */
    public static void logoutUser() {
        auth.signOut();
    }
}
