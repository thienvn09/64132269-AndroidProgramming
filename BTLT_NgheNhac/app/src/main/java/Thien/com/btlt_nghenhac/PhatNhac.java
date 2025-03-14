package Thien.com.btlt_nghenhac;

import android.animation.ObjectAnimator;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PhatNhac extends AppCompatActivity {
    public TextView txtTitleBH;
    MediaPlayer music;
    Button btnPlay, btnStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_phat_nhac);

        // Nhận dữ liệu từ Intent
        txtTitleBH = findViewById(R.id.txtTitleBH);
        String TieuDeBaiHat = getIntent().getStringExtra("1");
        txtTitleBH.setText(TieuDeBaiHat);

        // Phát nhạc
        music = MediaPlayer.create(this, R.raw.song1_hieu_monday);

        // Lấy ID của nút
        btnPlay = findViewById(R.id.btnPlay);
        btnStop = findViewById(R.id.btnStop);

        // Quay hình CD
        ImageView imgCD = findViewById(R.id.imgCD);
        ObjectAnimator animator = ObjectAnimator.ofFloat(imgCD, "rotation", 0f, 360f);
        animator.setDuration(3000); // 3 giây quay 1 vòng
        animator.setRepeatCount(ObjectAnimator.INFINITE); // Lặp vô hạn
        animator.setInterpolator(new LinearInterpolator()); // Chuyển động đều

        // Xử lý sự kiện phát nhạc
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animator.start();
                if (music == null) { // Nếu đã bị giải phóng, tạo lại
                    music = MediaPlayer.create(PhatNhac.this, R.raw.song1_hieu_monday);
                }
                music.start();
            }
        });

        // Xử lý sự kiện dừng nhạc
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (music.isPlaying()) {
                    music.pause();
                    music.seekTo(0); // Đưa về đầu bài hát
                    music.release();
                    animator.pause();
                }
            }
        });

        // Áp dụng insets cho layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (music != null) {
            music.release();
            music = null;
        }
    }
}
