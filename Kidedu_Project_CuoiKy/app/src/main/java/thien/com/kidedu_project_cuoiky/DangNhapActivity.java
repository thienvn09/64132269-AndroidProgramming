package thien.com.kidedu_project_cuoiky;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.UUID;

public class DangNhapActivity extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private EditText edtUsername, edtPassword;
    private ImageView imgCat, imgDog, imgRabbit, imgOwl;
    private Button btnLogin, btnRegister;
    private TextView txtTitle, txtChooseAvatar;
    private LinearLayout avatarContainer;
    private String selectedAvatar = null;
    private ImageView lastSelectedAvatar = null;
    private boolean isRegisterMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dang_nhap);
        // khởi tạo database
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        // ánh xạ qua màng hình
        txtTitle = findViewById(R.id.txtTitle);
        txtChooseAvatar = findViewById(R.id.txtChooseAvatar);
        avatarContainer = findViewById(R.id.avatar_container);
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        imgCat = findViewById(R.id.imgCat);
        imgDog = findViewById(R.id.imgDog);
        imgRabbit = findViewById(R.id.imgRabbit);
        imgOwl = findViewById(R.id.imgOwl);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        // Xử lý khi bấm vào ảnh đại diện
        imgCat.setOnClickListener(v -> ChonAnh("cat", imgCat));
        imgDog.setOnClickListener(v -> ChonAnh("dog", imgDog));
        imgRabbit.setOnClickListener(v -> ChonAnh("rabbit", imgRabbit));
        imgOwl.setOnClickListener(v -> ChonAnh("owl", imgOwl));

        // Xử lý nút Đăng nhập
        btnLogin.setOnClickListener(v -> {
            isRegisterMode = false;
            txtTitle.setText("Chào bạn nhỏ, hãy đăng nhập nào!");
            txtChooseAvatar.setVisibility(View.GONE);
            avatarContainer.setVisibility(View.GONE);
            Login();
        });

        // Xử lý nút Đăng ký
        btnRegister.setOnClickListener(v -> {
            if (!isRegisterMode) {
                // Chuyển sang chế độ đăng ký
                isRegisterMode = true;
                txtTitle.setText("Chào bạn nhỏ, hãy đăng ký nào!");
                txtChooseAvatar.setVisibility(View.VISIBLE);
                avatarContainer.setVisibility(View.VISIBLE);
            } else {
                // Thực hiện đăng ký
                registerUser();
            }
        });

    }

    private void ChonAnh(String Tenanh, ImageView anh) {
        if (lastSelectedAvatar != null && lastSelectedAvatar != anh) {
            lastSelectedAvatar.setScaleX(1.0f);
            lastSelectedAvatar.setScaleY(1.0f);
            lastSelectedAvatar.setAlpha(1.0f);
        }
        selectedAvatar = Tenanh;
        lastSelectedAvatar = anh;
        anh.setScaleX(1.2f);
        anh.setScaleY(1.2f);
        anh.setAlpha(0.8f);
    }

    private void Login() {
        String username = edtUsername.getText().toString().trim();
        String pass = edtPassword.getText().toString().trim();
        if (username != null) {
            Toast.makeText(this, "Bé ơi, hãy nhập tên nhé!", Toast.LENGTH_SHORT).show();
        }
        if (pass != null) {
            Toast.makeText(this, "Bé ơi, hãy nhập mật khẩu nhé!", Toast.LENGTH_SHORT).show();
        }
        databaseReference.orderByChild("username").equalTo(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Toast.makeText(DangNhapActivity.this, "bé ơi tên này có người nhập đăng ký rồi", Toast.LENGTH_SHORT).show();
                } else {
                    String userID = UUID.randomUUID().toString();
                    String time = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault()).format(new Date());
                    // đưa dữ liệu lên
                    HashMap<String, Object> datauser = new HashMap<>();
                    datauser.put("username", username);
                    datauser.put("password", pass);
                    datauser.put("avatar", selectedAvatar);
                    datauser.put("time", time);
                    databaseReference.child(userID).setValue(datauser)
                            .addOnSuccessListener(aVoid -> {
                                SharedPreferences pre = getSharedPreferences("user", MODE_PRIVATE);
                                SharedPreferences.Editor editor = pre.edit();
                                editor.putString("username", username);
                                editor.putString("username", username);
                                editor.putString("avatar", selectedAvatar);
                                editor.putString("userId", userID);
                                editor.apply();
                                // chuyển màng
                                Intent in = new Intent(DangNhapActivity.this, TrangChuActivity.class);
                                startActivity(in);
                                finish();
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(DangNhapActivity.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                            });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DangNhapActivity.this, "Lỗi: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void registerUser() {
        String username = edtUsername.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        // Kiểm tra thông tin đầu vào
        if (username.isEmpty()) {
            Toast.makeText(this, "Bé ơi, hãy nhập tên nhé!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.isEmpty()) {
            Toast.makeText(this, "Bé ơi, hãy nhập mật khẩu nhé!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (selectedAvatar == null) {
            Toast.makeText(this, "Bé ơi, hãy chọn ảnh đại diện nhé!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Kiểm tra xem tên đã tồn tại trên Firebase chưa
        databaseReference.orderByChild("username").equalTo(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Toast.makeText(DangNhapActivity.this, "Tên này đã được đăng ký, bé hãy chọn tên khác nhé!", Toast.LENGTH_SHORT).show();
                } else {
                    // Đăng ký thành công
                    String userId = UUID.randomUUID().toString();
                    String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());

                    HashMap<String, String> userData = new HashMap<>();
                    userData.put("username", username);
                    userData.put("password", password);
                    userData.put("avatar", selectedAvatar);
                    userData.put("timestamp", timestamp);

                    databaseReference.child(userId).setValue(userData)
                            .addOnSuccessListener(aVoid -> {
                                // Lưu thông tin vào SharedPrefereces
                                SharedPreferences prefs = getSharedPreferences("KidEduPrefs", MODE_PRIVATE);
                                SharedPreferences.Editor editor = prefs.edit();
                                editor.putString("username", username);
                                editor.putString("avatar", selectedAvatar);
                                editor.putString("userId", userId);
                                editor.apply();

                                // Chuyển sang TrangChuActivity
                                Intent intent = new Intent(DangNhapActivity.this, TrangChuActivity.class);
                                startActivity(intent);
                                finish();
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(DangNhapActivity.this, "Lỗi khi đăng ký: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(DangNhapActivity.this, "Lỗi: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}