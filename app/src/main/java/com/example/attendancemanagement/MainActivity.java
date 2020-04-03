package com.example.attendancemanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends AppCompatActivity {
    static final  int GOOGLE_SIGN=123;
    private  FirebaseAuth mAuth;
    ProgressBar progressBar;
    GoogleSignInClient mGoogleSignInClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        mAuth=FirebaseAuth.getInstance();
//        progressBar=(ProgressBar)findViewById(R.id.progress_bar);
//
//        GoogleSignInOptions googleSignInOptions=new GoogleSignInOptions.Builder()
//                                                    .requestIdToken(getString(R.string.default_web_client_id))
//                                                    .requestEmail().build();
//
//        mGoogleSignInClient= GoogleSignIn.getClient(this,googleSignInOptions);
        startActivity(new Intent(this,HomeActivity.class));
    }
//    private void signIn() {
//        progressBar.setVisibility(View.VISIBLE);
//        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
//        startActivityForResult(signInIntent, GOOGLE_SIGN);
//    }
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);

//        if (requestCode == GOOGLE_SIGN) {
//            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//            try {
//                // Google Sign In was successful, authenticate with Firebase
//                GoogleSignInAccount account = task.getResult(ApiException.class);
//                if(account!=null)
//                firebaseAuthWithGoogle(account);
//            } catch (ApiException e) {
//              e.printStackTrace();
//            }
//        }
//    }

//    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
//    Log.d("TAG","firebase auth with google"+account.getId());
//        AuthCredential credential= GoogleAuthProvider.getCredential(account.getIdToken(),null);
//    mAuth.signInWithCredential(credential)
//    .addOnCompleteListener(this,task->{
//        if(task.isSuccessful()){
//            progressBar.setVisibility(View.INVISIBLE);
//            Log.d("TAG","signin successful");
//            FirebaseUser user=mAuth.getCurrentUser();
//            updateUI(user);
//        }else{
//            progressBar.setVisibility(View.INVISIBLE);
//            Log.d("TAG","Unsuccessful");
//            updateUI(null);
//        }
//    });
//    }

//    private void updateUI(FirebaseUser user) {
//        if(user!=null){}
//        else{}
//    }
//    @Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        updateUI(currentUser);
//    }
}
