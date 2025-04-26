package thien.com.kidedu_project_cuoiky;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {
    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private Button btnNext;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        // khởi tạo
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        // thiết lập adtaer
        AdapterFg adapterFg = new AdapterFg(this);
        viewPager.setAdapter(adapterFg);
        // tắt tính năng vuốt
        viewPager.setUserInputEnabled(false);
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
        }).attach();
       viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
           @Override
           public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
               super.onPageScrolled(position, positionOffset, positionOffsetPixels);
               if(position == 2)
               {
                   Button btnNext = findViewById(R.id.btnNext);
                   btnNext.setVisibility(View.VISIBLE);
               }
               else
               {
                   Button btnNext = findViewById(R.id.btnNext);
                   btnNext.setVisibility(View.GONE);
               }
           }
       });
       btnNext.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               int checkItem = viewPager.getCurrentItem();
               if(checkItem < 2)
               {
                   viewPager.setCurrentItem(checkItem+1);
               }
               else
               {
                   Intent intent = new Intent(MainActivity.this, DangNhapActivity.class);
                   finish();
               }
           }
       });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main),(v, insets) -> {
            Insets insets1 = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(insets1.left,insets1.top,insets1.right,insets1.bottom);
            return WindowInsetsCompat.CONSUMED;
        });
        }
}