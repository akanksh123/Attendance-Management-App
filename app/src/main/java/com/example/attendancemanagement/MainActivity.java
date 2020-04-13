package com.example.attendancemanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.SignInAccount;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends AppCompatActivity {
//    static final  int GOOGLE_SIGN=13;
//    private  FirebaseAuth mAuth;
//    ProgressBar progressBar;
//    GoogleSignInClient mGoogleSignInClient;
//    SignInButton signInbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container,new StudentFragment()).commit();
//        mAuth=FirebaseAuth.getInstance();
//        progressBar=(ProgressBar)findViewById(R.id.progress_bar);
//        signInbtn=(SignInButton) findViewById(R.id.googleSign);
//        GoogleSignInOptions googleSignInOptions=new GoogleSignInOptions.Builder()
//                                                    .requestIdToken("283837998773-vp0hkc1im42enqt8os2oar0hsdoo76m6.apps.googleusercontent.com")
//                                                    .requestEmail().build();
//
//        mGoogleSignInClient= GoogleSignIn.getClient(this,googleSignInOptions);
//
//        signInbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                signIn();
//            }
//        });


    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener=new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectedFragment=null;
            switch (menuItem.getItemId()){
                case R.id.nav_student:
                    selectedFragment=new StudentFragment();
                    break;
                case R.id.nav_teacher:
                    selectedFragment=new TeacherFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container,selectedFragment).commit();
            return true;
        }
    };

//    private void signIn() {
//        progressBar.setVisibility(View.VISIBLE);
//        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
//        startActivityForResult(signInIntent, GOOGLE_SIGN);
//    }
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == GOOGLE_SIGN) {
//            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//            try {
//                // Google Sign In was successful, authenticate with Firebase
//                GoogleSignInAccount account = task.getResult(ApiException.class);
//
//                firebaseAuthWithGoogle(account);
//            } catch (ApiException e) {
//              Log.w("TAG","Gogle ssign in failed",e);
//            }
//        }
//    }
//
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
//
//    private void updateUI(FirebaseUser user) {
//        if(user!=null){
//            startActivity(new Intent(this,HomeActivity.class));
//        }
//        else{
//            Toast.makeText(this,"User login failed",Toast.LENGTH_SHORT).show();
//        }
//    }
//    @Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        updateUI(currentUser);
//    }
}
