package Thien.com.btlt_nghenhac;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PhatNhac extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_phat_nhac);

        ImageView imgCD = findViewById(R.id.imgCD);
        // Tạo hiệu ứng xoay tròn 360 độ liên tục
        ObjectAnimator animator = ObjectAnimator.ofFloat(imgCD, "rotation", 0f, 360f);
        animator.setDuration(3000); // 3 giây xoay hết 1 vòng
        animator.setRepeatCount(ObjectAnimator.INFINITE); // Lặp vô hạn
        animator.setInterpolator(new LinearInterpolator()); // Chuyển động đều
        animator.start(); // Bắt đầu xoay


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}