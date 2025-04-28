package thien.com.kidedu_project_cuoiky;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class DangNhapActivity extends AppCompatActivity {
    private DatabaseReference data;
    private ImageView imgCat,imgdog,imgtho,imgcu;
    private Button nut;
    private String LuuTenNhanVat = null; // dùng để lưu tên nhân vật đã chọn
    private ImageView LuuHinhanh =null; // dùng để lưu hình ảnh đã chọn
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dang_nhap);
        // khởi tạo database
        data = FirebaseDatabase.getInstance().getReference("user");
        // ánh xạ qua màng hình
        imgCat = findViewById(R.id.imgCat);
        imgdog = findViewById(R.id.imgdog);
        imgtho = findViewById(R.id.imgtho);
        imgcu = findViewById(R.id.imgcu);
        nut = findViewById(R.id.btnDN);
        // xử lý sự kiện bấm vào
        imgCat.setOnClickListener(v -> {
            ChonNhanvat("MeoNhoHamHoc",imgCat);
        });
    }
    public void ChonNhanvat(String TenNv,ImageView imgNv) {

    }
}