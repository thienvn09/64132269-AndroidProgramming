package thien.com.kidedu_project_cuoiky;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
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
    private EditText edtTen,edtPass;
    private ImageView imgCat,imgDog,imgRabbit,imgOwl;
    private Button nut;
    private String LuuTenNhanVat = null; // dùng để lưu tên nhân vật đã chọn
    private ImageView LuuHinhanh =null; // dùng để lưu hình ảnh đã chọn
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dang_nhap);
        // khởi tạo database
        data = FirebaseDatabase.getInstance().getReference("users");
        // ánh xạ qua màng hình
        imgCat = findViewById(R.id.imgCat);
        imgDog = findViewById(R.id.imgDog);
        imgRabbit = findViewById(R.id.imgRabbit);
        imgOwl = findViewById(R.id.imgOwl);
        nut = findViewById(R.id.btnDN);
        edtTen = findViewById(R.id.edtTen);
        edtPass = findViewById(R.id.edtPass);
        // xử lý sự kiện bấm vào
        imgCat.setOnClickListener(v ->
            ChonNhanvat("MeoNhoHamHoc",imgCat)
        );
        imgDog.setOnClickListener(v ->
                ChonNhanvat("ChuChoChamChi",imgDog)
        );
        imgRabbit.setOnClickListener(v ->
                ChonNhanvat("ThoChamChi",imgRabbit)
        );
        imgOwl.setOnClickListener(v ->
                ChonNhanvat("CuChamChi",imgOwl)
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
        String Ten = edtTen.getText().toString();
        String MatKhau = edtPass.getText().toString();
        if(Ten.isEmpty()){
            Toast.makeText(this,"bé ơi hãy nhập tên của mình nha ",Toast.LENGTH_SHORT).show();
            return;
        }
        if(MatKhau.isEmpty()){
            Toast.makeText(this,"bé ơi hãy nhập mật khẩu nha ",Toast.LENGTH_SHORT).show();
            return;
        }
        if(LuuTenNhanVat == null){
            Toast.makeText(this,"bé ơi hãy chọn nhân vật đi",Toast.LENGTH_SHORT).show();
            return;
        }
        LuuDuLieu(Ten,MatKhau,LuuTenNhanVat);
        Intent intent = new Intent(DangNhapActivity.this,TrangChuActivity.class);
        startActivity(intent);
        finish();
    }
    public void LuuDuLieu(String tenNguoiDung, String matKhau, String tenNhanVat) {
        String userId = UUID.randomUUID().toString();
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());

        HashMap<String, String> userdata = new HashMap<>();
        userdata.put("Tên", tenNguoiDung);
        userdata.put("mật khẩu", matKhau);
        userdata.put("Thời gian", time);
        userdata.put("Ảnh đại diện", tenNhanVat);

        data.child(userId).setValue(userdata).addOnSuccessListener(unused -> {
            SharedPreferences share = getSharedPreferences("KidEduPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = share.edit();
            editor.putString("username", tenNguoiDung);
            editor.putString("avatar", tenNhanVat);
            editor.putString("userId", userId);
            editor.apply();

            Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(DangNhapActivity.this, TrangChuActivity.class);
            startActivity(intent);
            finish();
        }).addOnFailureListener(e -> {
            Toast.makeText(this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
        });
    }
}