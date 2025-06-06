package thien.com.final_kidedu;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast; // Thêm Toast để test click menu
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView; // Import đúng

public class GiaoDienChinhActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giao_dien_chinh);

        bottomNavigationView = findViewById(R.id.botmenu);

        // Load HomeFragment mặc định khi Activity được tạo
        if (savedInstanceState == null) {
            loadFragment(new HomeFragment(), false);
        }

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                int itemId = item.getItemId();

                if (itemId == R.id.navigation_home) { // ID từ file menu bot_menu.xml
                    selectedFragment = new HomeFragment();
                } else if (itemId == R.id.navigation_profile) { // ID từ file menu
                     selectedFragment = new fragment_profile(); // Tạo ProfileFragment nếu có
                    Toast.makeText(GiaoDienChinhActivity.this, "Profile Đã Được Chọn", Toast.LENGTH_SHORT).show();
                } else if (itemId == R.id.navigation_games) { // ID từ file menu
                     selectedFragment = new GamesFragment();
                    Toast.makeText(GiaoDienChinhActivity.this, "Luyện Tập Đã Được Chọn", Toast.LENGTH_SHORT).show();
                } else if (itemId == R.id.navigation_settings) { // ID từ file menu
                     selectedFragment = new SettingsFragment();
                    Toast.makeText(GiaoDienChinhActivity.this, "Cài Đặt Đã Được Chọn", Toast.LENGTH_SHORT).show();
                }

                if (selectedFragment != null) {
                    loadFragment(selectedFragment, false);
                    return true;
                }
                return false;
            }
        });
    }

    private void loadFragment(Fragment fragment, boolean addToBackStack) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();
    }
}