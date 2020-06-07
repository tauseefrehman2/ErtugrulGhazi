package com.example.ertugrulghazi.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ertugrulghazi.fragment.DownloadFragment;
import com.example.ertugrulghazi.fragment.DramasFragment;
import com.example.ertugrulghazi.R;
import com.example.ertugrulghazi.fragment.FavoriteFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*XML connectivity*/
        drawerLayout = findViewById(R.id.mainActivity_drawerLayout);
        toolbar = findViewById(R.id.mainActivity_toolbar);
        toolbar.setTitle("Ertugrul Ghazi");
        setSupportActionBar(toolbar);

        navigationView = findViewById(R.id.mainActivity_nav_view);

        BottomNavigationView bottomNav = findViewById(R.id.home_bottom2_nav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        bottomNav.setSelectedItemId(R.id.home_bottom_nav);


        /*Drawer layout code*/
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout,
                toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        /*Set on item listener on item*/
        navigationView.setNavigationItemSelectedListener(this);
        if (savedInstanceState == null) {

            getSupportFragmentManager().beginTransaction().replace(R.id.mainActivity_container,
                    new DramasFragment()).commit();

            navigationView.setCheckedItem(R.id.nav_home_menu);
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_home_menu:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.mainActivity_container, new DramasFragment()).commit();
                break;
            case R.id.nav_aboutUs_menu:
                startActivity(new Intent(this, AboutUsActivity.class));
                finish();
                break;
            case R.id.nav_moreApps_menu:
                moreApps();
                break;
            case R.id.nav_policy_menu:
                startActivity(new Intent(MainActivity.this, PrivacyPolicyActivity.class));
                finish();
                break;
            case R.id.nav_share_menu:
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String shareBody = "your body here";
                String shareSub = "your subject here";
                intent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
                intent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(intent, "Share using"));
                break;
            case R.id.nav_exit_menu:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Exit app").setMessage("Do you want from app").setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        finish();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.show();

                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }


    //bottom navigation click listener
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    //pending jobs
                    switch (item.getItemId()) {
                        case R.id.home_bottom_nav:
                            getSupportFragmentManager().beginTransaction().replace(R.id.mainActivity_container, new DramasFragment()).commit();
                            break;
                        case R.id.download_bottom_nav:
                            getSupportFragmentManager().beginTransaction().replace(R.id.mainActivity_container, new DownloadFragment()).commit();
                            break;
                        case R.id.favorite_bottom_nav:
                            getSupportFragmentManager().beginTransaction().replace(R.id.mainActivity_container, new FavoriteFragment()).commit();
                            break;
                    }
                    return true;
                }
            };

    private void moreApps() {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/developer?id=cloud09&hl=en")));
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/developer?id=cloud09&hl=en")));
        }
    }
}
