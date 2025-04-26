package thien.com.kidedu_project_cuoiky;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.viewPager);
        AdapterFg adapterFg = new AdapterFg(this);
        viewPager.setAdapter(adapterFg);
        setupView();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
   public void setupView(){
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                if(position == 2){
                    findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(MainActivity.this, DangNhapActivity.class));
                            finish();
                        }
                    });
                }
            }
        });
        for(int i=0;i<3;i++){
            final int ViTri = 1;
            View fragmentView = viewPager.getChildAt(ViTri);
            if(fragmentView != null)
            {
                tabLayout = fragmentView.findViewById(R.id.tabLayout);
                if(tabLayout != null){
                    new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
                    }).attach();
                }
                Button btnnext = fragmentView.findViewById(R.id.btn1);
                if(btnnext != null){
                    btnnext.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int checkItem = viewPager.getCurrentItem()+1;
                            if(checkItem < 3){
                                viewPager.setCurrentItem(checkItem);
                            }
                        }
                    });
                }
            }
        }
   }

}