package thien.com.final_kidedu;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class DangKyActivity extends AppCompatActivity {
    private EditText etdTenDK, etSchool, etClass, etPassword;
    private RadioGroup rgGender;
    private Button btnDangKi;
    private UserDao userDao;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dang_ky);
        etdTenDK = findViewById(R.id.etdTenDK);
        rgGender = findViewById(R.id.rgGender);
        etSchool = findViewById(R.id.etSchool);
        etClass = findViewById(R.id.etClass);
        etPassword = findViewById(R.id.etPassword);
        btnDangKi = findViewById(R.id.btnDangKi);
        AppDatabase db = AppDatabase.getDatabase(getApplicationContext());
        userDao = db.userDao();
        btnDangKi.setOnClickListener(v -> sukiendangky());

    }
    private void sukiendangky()
    {
        String name = etdTenDK.getText().toString();
        String school = etSchool.getText().toString().trim();
        String className = etClass.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String gender = "";
        int selectedGenderId = rgGender.getCheckedRadioButtonId();
        if (selectedGenderId != -1) {
            RadioButton selectedRadioButton = findViewById(selectedGenderId);
            gender = selectedRadioButton.getText().toString();
        }

        // Đã loại bỏ validation cho NIS
        if (TextUtils.isEmpty(name)) {
            etdTenDK.setError("Họ và tên không được để trống");
            etdTenDK.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(gender)) {
            Toast.makeText(this, "Vui lòng chọn giới tính", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(school)) {
            etSchool.setError("Trường học không được để trống");
            etSchool.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(className)) {
            etClass.setError("Lớp không được để trống");
            etClass.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(password) || password.length() < 4) {
            etPassword.setError("Mật khẩu phải có ít nhất 4 ký tự");
            etPassword.requestFocus();
            return;
        }

        String hashedPassword = PasswordHasher.hashPassword(password);
        if (hashedPassword == null) {
            Toast.makeText(this, "Lỗi băm mật khẩu", Toast.LENGTH_SHORT).show();
            return;
        }

        User newUser = new User();
        newUser.name = name;
        newUser.gender = gender;
        newUser.school = school;
        newUser.clas = className;
        newUser.Passhash = hashedPassword;

        AppDatabase.databaseWriteExecutor.execute(() -> {userDao.Dangky(name, hashedPassword);
            runOnUiThread(() -> {
                Toast.makeText(DangKyActivity.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                finish();
            });
        });
    }
}
