package com.example.attendancemanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class TeacherActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    TextView headername,headeremail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);

        headeremail=(TextView)findViewById(R.id.header_email);
//        headername=(TextView)findViewById(R.id.header_name);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
//            String name = user.getDisplayName();
            String email = user.getEmail();

//            headername.setText(name);
//            headeremail.setText(email);

            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            String uid = user.getUid();
        }




        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //calling drawer
        drawer=findViewById(R.id.drawer_layout_teacher);
        NavigationView navigationView=findViewById(R.id.nav_view_teacher);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);

        //rotating hamburger
        toggle.syncState();
        //first activity will be attendance activity
        if(savedInstanceState==null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_teacher, new RegisterFragment()).commit();
            navigationView.setCheckedItem(R.id.register_student);
        }


    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);

        }
        else{
            super.onBackPressed();}
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.register_student:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_teacher,new RegisterFragment()).commit();

                break;
            case R.id.details:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_teacher,new DetailsFragment()).commit();
                break;
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(this,MainActivity.class));
                break;
        }
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }
}
