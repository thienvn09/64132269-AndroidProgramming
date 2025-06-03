package thien.com.final_kidedu;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import Data.AppDatabase;
import Data.UserDao;
import Model.User;
import util.PasswordHasher;

public class DangNhapActivity extends AppCompatActivity {
    private EditText etLoginName, etLoginPassword;
    private Button btnPerformLogin;
    private TextView tvGoToRegisterFromLogin;
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dang_nhap);
        etLoginName = findViewById(R.id.etLoginName);
        etLoginPassword = findViewById(R.id.etLoginPassword);
        btnPerformLogin = findViewById(R.id.btnPerformLogin);
        tvGoToRegisterFromLogin = findViewById(R.id.tvGoToRegisterFromLogin);
        //
        AppDatabase db = AppDatabase.getDatabase(getApplicationContext());
         this.userDao = db.userDao();
        // sử ly sự kiện đăng nhập
        btnPerformLogin.setOnClickListener(v -> sukiendangnhap());
        // sử ly sự kiện đăng ký
        tvGoToRegisterFromLogin.setOnClickListener(v -> {
            Intent intent = new Intent(DangNhapActivity.this, DangKyActivity.class);
            startActivity(intent);
        });

    }

    private void sukiendangnhap() {
        String username = etLoginName.getText().toString();
        String password = etLoginPassword.getText().toString();
        if (TextUtils.isEmpty(username)) {
            etLoginName.setError("Vui lòng nhập họ và tên");
            etLoginName.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            etLoginPassword.setError("Vui lòng nhập mật khẩu");
            etLoginPassword.requestFocus();
            return;
        }
        // Thực hiện thao tác database trên luồng nền
        AppDatabase.databaseWriteExecutor.execute(() -> {
            // Băm mật khẩu người dùng nhập vào để so sánh
            String hashedPasswordAttempt = PasswordHasher.hashPassword(password);
            if (hashedPasswordAttempt == null) {
                runOnUiThread(() -> Toast.makeText(DangNhapActivity.this, "Lỗi xử lý mật khẩu.", Toast.LENGTH_SHORT).show());
                return;
            }

            User user = userDao.dangnhap(username, hashedPasswordAttempt); // Sử dụng phương thức loginUser từ DAO

            runOnUiThread(() -> { // Quay lại UI thread để cập nhật UI
                if (user != null) {
                    Toast.makeText(DangNhapActivity.this, "Đăng nhập thành công! Chào " + user.name, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(DangNhapActivity.this, GiaoDienChinhActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(DangNhapActivity.this, "Tên đăng nhập hoặc mật khẩu không đúng.", Toast.LENGTH_LONG).show();
                    etLoginPassword.setText(""); // Xóa trường mật khẩu
                    etLoginName.requestFocus();
                }
            });
        });
    }
}