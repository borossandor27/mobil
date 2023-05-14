package com.example.elethangapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.elethangapplication.cat.CatFragment;
import com.example.elethangapplication.dog.DogFragment;
import com.example.elethangapplication.editProfile.EditProfileActivity;
import com.example.elethangapplication.events.EventFragment;
import com.example.elethangapplication.applications.ApplicationsFragment;
import com.example.elethangapplication.program.ProgramFragment;
import com.example.elethangapplication.aboutus.AboutUsFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private FrameLayout fragmentContainer;
    private ImageView logoHome,slide;
    private TextView udvozles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        init();

        //hambiicon
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(MainActivity.this,drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);

        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.exit){
                    AlertDialog.Builder builder = new  AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("Biztos kiszeretne jelentkezni?");
                    builder.setCancelable(true);

                    builder.setNegativeButton("Igen", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(MainActivity.this, LoadingActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });

                    builder.setPositiveButton("Nem", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
                if (id == R.id.profil){
                    Intent intent = new Intent(MainActivity.this, EditProfileActivity.class);
                    startActivity(intent);
                    finish();
                }
                return true;
            }
        });


    }


    public  void  init(){
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        fragmentContainer = findViewById(R.id.fragment_container);
        logoHome = findViewById(R.id.logoHome);
        slide = findViewById(R.id.slide);
        udvozles = findViewById(R.id.udvozles);

        AnimationDrawable animationDrawable = (AnimationDrawable) slide.getDrawable();
        animationDrawable.start();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.cat:
                //replaceFragment(new CatFragment());
                visibility();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CatFragment()).commit();
                break;
            case R.id.dog:
                //replaceFragment(new DogFragment());
                visibility();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DogFragment()).commit();
                break;

            case R.id.esemenyek:
                //replaceFragment(new EventFragment());
                visibility();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new EventFragment()).commit();
                break;
            case R.id.programok:
                //replaceFragment(new ProgramFragment());
                visibility();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProgramFragment()).commit();
                break;
            case R.id.rolunk:
                //replaceFragment(new AboutUsFragment());
                visibility();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AboutUsFragment()).commit();
                break;

            case R.id.kedvencek:
                visibility();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ApplicationsFragment()).commit();
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    private void visibility(){
        fragmentContainer.setVisibility(View.VISIBLE);
        udvozles.setVisibility(View.GONE);
        logoHome.setVisibility(View.GONE);
        slide.setVisibility(View.GONE);
    }
    /*
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }
    */
     


}