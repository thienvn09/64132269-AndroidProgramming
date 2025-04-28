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
import java.util.HashMap;
import java.util.Locale;
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
        imgCat.setOnClickListener(v ->
            ChonNhanvat("MeoNhoHamHoc",imgCat)
        );
        imgdog.setOnClickListener(v ->
                ChonNhanvat("ChuChoChamChi",imgdog)
        );
        imgtho.setOnClickListener(v ->
                ChonNhanvat("ThoChamChi",imgtho)
        );
        imgcu.setOnClickListener(v ->
                ChonNhanvat("CuChamChi",imgcu)
        );
        nut.setOnClickListener(v ->
                XacNhanChon());
    }
    public void ChonNhanvat(String TenNv,ImageView imgNv) {
        if(LuuHinhanh != null && LuuHinhanh != imgNv){
            LuuHinhanh.setScaleX(1.0f);
            LuuHinhanh.setScaleY(1.0f);
            LuuHinhanh.setAlpha(1.0f);
        }
        // cập nhật hình đã chọn
        LuuTenNhanVat = TenNv;
        LuuHinhanh = imgNv;
        // hiệu ứng phòng to
        imgNv.setScaleX(1.2f);
        imgNv.setScaleY(1.2f);
        imgNv.setAlpha(0.8f);
    }
    // một bộ lắng nghe dùng cho button
    public void XacNhanChon(){
        if(LuuTenNhanVat == null){
            Toast.makeText(this,"bé ơi hãy chọn nhân vật đi",Toast.LENGTH_SHORT).show();
            return;
        }
        LuuDuLieu(LuuTenNhanVat);
        Intent intent = new Intent(DangNhapActivity.this,TrangChuActivity.class);
        startActivity(intent);
        finish();
    }
    public void LuuDuLieu(String Tennv) {
        // lấy Id ảo
        String userId = UUID.randomUUID().toString();
        // lấy thời gian hiện tại
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
        // tạo dữ liệu
        HashMap<String, String> userdata = new HashMap<>();
        userdata.put("NhanVatDangDuocChon", LuuTenNhanVat);
        userdata.put("Time", time);
        // nạp dữ liệu vào database
        data.child(userId).setValue(userdata).addOnSuccessListener(unused -> {
            Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
        }).addOnFailureListener(e -> {
            Toast.makeText(this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
        });
    }
}