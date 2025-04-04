package thigk2.LeHoangThien;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class ActivityChucNang3 extends AppCompatActivity {
    ListView  dsMon;
    ArrayList<String> monHoc = new ArrayList<String>();
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chuc_nang3);
        dsMon = findViewById(R.id.dsMon);
        monHoc.add("Tin học đại cương");
        monHoc.add("Lập trình java");
        monHoc.add("Phát triển ứng dụng web");
        monHoc.add("Khai phá dữ liệu lớn");
        monHoc.add("kinh tế chính trị mác-Lê nin");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, monHoc);
        dsMon.setAdapter(adapter);
        dsMon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String mon = monHoc.get(position);
                Intent in = new Intent(ActivityChucNang3.this,ActivityChucNang3a.class);
                in.putExtra("mon",mon);
                startActivity(in);
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}