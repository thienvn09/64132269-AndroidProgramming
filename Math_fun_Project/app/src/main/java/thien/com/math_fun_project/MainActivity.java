package thien.com.math_fun_project;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        BottomNavigationView botmenu = findViewById(R.id.botmenu);
        botmenu.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fg_DangChon = null;
                int itemID = item.getItemId();
                if(itemID == R.id.title_toan)
                {
                    fg_DangChon = new ToanFragment();
                }
                else if(itemID == R.id.title_tienganh)
                {
                    fg_DangChon = new TiengAnhFragment();
                }
                else if(itemID == R.id.title_setting)
                {
                    fg_DangChon = new SettingFragment();
                }
                if(fg_DangChon != null){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fgmain, fg_DangChon).commit();
                    return true;
                }
                return true;
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}