package thien.com.kidedu_project_cuoiky;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;


public class MainActivity extends AppCompatActivity {
    private ViewPager2 viewPager;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        // Khởi tạo ViewPager2
        viewPager = findViewById(R.id.viewPager);
        // Thiết lập Adapter cho ViewPager2
        AdapterFg adapterFg = new AdapterFg(this);
        viewPager.setAdapter(adapterFg);

    }
}