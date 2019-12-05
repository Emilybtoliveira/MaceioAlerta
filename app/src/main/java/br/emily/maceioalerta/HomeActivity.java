package br.emily.maceioalerta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import br.emily.maceioalerta.fragments.MapsFragment;
import br.emily.maceioalerta.fragments.OccurrencesFragment;
import br.emily.maceioalerta.fragments.ProfileFragment;

public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private BottomNavigationView mNavigationView;

    private boolean noReplaceFragment;

    private static final String MAPS_FRAGMENT = "MAPS_FRAGMENT";
    private static final String OCCURRENCE_FRAGMENT = "OCCURRENCES_FRAGMENT";
    private static final String PROFILE_FRAGMENT = "PROFILE_FRAGMENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        this.mAuth = FirebaseAuth.getInstance();

        mNavigationView = findViewById(R.id.bottom_navigation);
        mNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        managerFragment(new MapsFragment(), "MAPS_FRAGMENT");
    }

    protected void onResume() {
        super.onResume();
        if (this.mAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(getApplicationContext(), SignInHubActivity.class));
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_map: {
                    if (!noReplaceFragment) {
                        managerFragment(new MapsFragment(), "MAPS_FRAGMENT");
                    }
                    return true;
                }
                case R.id.navigation_occurrence: {
                    if (!noReplaceFragment) {
                        managerFragment(new OccurrencesFragment(), "OCCURRENCES_FRAGMENT");
                    }
                    return true;
                }
                case R.id.navigation_profile: {
                    if (!noReplaceFragment) {
                        managerFragment(new ProfileFragment(), "PROFILE_FRAGMENT");
                    }
                    return true;
                }
            }

            return false;
        }
    };

    private void managerFragment(Fragment fragment, String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.containerForFragment, fragment, tag);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment mMapsFragment = fragmentManager.findFragmentByTag(MAPS_FRAGMENT);
        if(mMapsFragment != null) {
            if (mMapsFragment.isVisible()) {
                finish();
                return;
            }
        }
        super.onBackPressed();
        managerIconsOfBottomNavigation(fragmentManager);
    }

    private void managerIconsOfBottomNavigation(FragmentManager fragmentManager) {
        Fragment mMapsFragment = fragmentManager.findFragmentByTag(MAPS_FRAGMENT);
        if(mMapsFragment != null) {
            if (mMapsFragment.isVisible()) {
                noReplaceFragment = true;
                mNavigationView.setSelectedItemId(R.id.navigation_map);
                noReplaceFragment = false;
            }
        }

        Fragment mOccurrencesFragment = fragmentManager.findFragmentByTag(OCCURRENCE_FRAGMENT);
        if(mOccurrencesFragment != null) {
            if (mOccurrencesFragment.isVisible()) {
                noReplaceFragment = true;
                mNavigationView.setSelectedItemId(R.id.navigation_occurrence);
                noReplaceFragment = false;
            }
        }

        Fragment mProfileFragment = fragmentManager.findFragmentByTag(PROFILE_FRAGMENT);
        if(mProfileFragment != null) {
            if (mProfileFragment.isVisible()) {
                noReplaceFragment = true;
                mNavigationView.setSelectedItemId(R.id.navigation_profile);
                noReplaceFragment = false;
            }
        }
    }
}
