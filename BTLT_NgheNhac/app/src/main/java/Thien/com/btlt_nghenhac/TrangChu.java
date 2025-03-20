package Thien.com.btlt_nghenhac;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class TrangChu extends AppCompatActivity {
    public TextView txtChaoMung;
    public ListView listDsbaihat;
    public ArrayList<String> dsbaihat = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_trang_chu);
        // ánh xạ
        txtChaoMung = findViewById(R.id.txtChaoMung);
        listDsbaihat = findViewById(R.id.listDsbaihat);
        String tenDn =getIntent().getStringExtra("123");
        txtChaoMung.setText( "chúc " + tenDn + " một ngày tốt lành ");


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dsbaihat.add("Không Thể Say - Ca sĩ : Hiếu Monday");
        dsbaihat.add("Âm Thầm Bên Em- Ca sĩ : G-Dragon Việt Nam");

        // dùng adapter để truyền dữ liệu vào listview
        ArrayAdapter<String> adapterlistDsbaihat = new ArrayAdapter<>(this, android.R.layout.simple_list_item_activated_1, dsbaihat);
        listDsbaihat.setAdapter(adapterlistDsbaihat);

        // bắt sự kiện khi chạm vào màng hình
        listDsbaihat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String BaiHatDuocchon = dsbaihat.get(position);

                Intent intent = new Intent(TrangChu.this, PhatNhac.class);
                intent.putExtra("1",BaiHatDuocchon);
                startActivity(intent);
            }
        });

    }
}