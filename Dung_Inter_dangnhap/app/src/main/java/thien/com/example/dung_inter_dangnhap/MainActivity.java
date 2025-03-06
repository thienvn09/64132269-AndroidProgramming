package thien.com.example.dung_inter_dangnhap;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        // khai báo
        Button btnLogin = findViewById(R.id.btnLogin);
        // xử lý sự kiện
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển sang XuLylogin activity
                Intent intent = new Intent(MainActivity.this, XuLylogin.class);
                startActivity(intent);
            }
        });

    }
    // su dung inter tuong minh
//    public void ChuyenSangTrangDN(View v){
//        Intent intent =new Intent(MainActivity.this,XuLylogin.class);
//        startActivity(intent);
//    }

}