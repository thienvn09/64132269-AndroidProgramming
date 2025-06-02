package thien.com.math_fun_project;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class ToanBai1Activity extends AppCompatActivity {

    private TextView tvTimer, tvScore, tvSoA, tvDau, tvSoB, tvResult;
    private ProgressBar progressBar;
    private Button btnBack, btnAnswer1, btnAnswer2, btnAnswer3, btnAnswer4;
    private CountDownTimer countDownTimer;
    private int currentScore = 0;
    private int currentQuestionIndex = 0;
    private static final int TOTAL_QUESTIONS = 10;
    private List<MathSo> mathSos;
    private List<Double> answerOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_toan_bai1);

        // Ánh xạ lên layout
        tvTimer = findViewById(R.id.tvTimer);
        tvScore = findViewById(R.id.tvScore);
        tvSoA = findViewById(R.id.tvSoA);
        tvDau = findViewById(R.id.tvDau);
        tvSoB = findViewById(R.id.tvSoB);
        tvResult = findViewById(R.id.tvResult);
        progressBar = findViewById(R.id.progressBar);
        btnBack = findViewById(R.id.btnBack);
        btnAnswer1 = findViewById(R.id.btnnumber1);
        btnAnswer2 = findViewById(R.id.btnnumber2);
        btnAnswer3 = findViewById(R.id.btnnumber3);
        btnAnswer4 = findViewById(R.id.btnAnswer4);

        // Cập nhật thời gian thực
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        tvTimer.setText(sdf.format(System.currentTimeMillis()));

        // Khởi tạo danh sách bài tập
        mathSos = taoDanhSachBaiTap();
        hienThiCauHoiTiepTheo();

        // Xử lý nút Quay lại
        btnBack.setOnClickListener(v -> onBackPressed());

        // Xử lý các nút đáp án
        btnAnswer1.setOnClickListener(v -> kiemTraDapAn(0));
        btnAnswer2.setOnClickListener(v -> kiemTraDapAn(1));
        btnAnswer3.setOnClickListener(v -> kiemTraDapAn(2));
        btnAnswer4.setOnClickListener(v -> kiemTraDapAn(3));

        // Khởi động timer
        batDauTimer();
    }

    // Tạo danh sách bài tập
    private List<MathSo> taoDanhSachBaiTap() {
        List<MathSo> ds = new ArrayList<>();
        Random random = new Random();
        String[] dau = {"+", "-", "*", "/"};
        for (int i = 0; i < TOTAL_QUESTIONS; i++) {
            int soA = random.nextInt(10);
            int soB = random.nextInt(10);
            String dauNgauNhien = dau[random.nextInt(dau.length)];
            double ketQua = 0;

            switch (dauNgauNhien) {
                case "+":
                    ketQua = soA + soB;
                    break;
                case "-":
                    ketQua = soA - soB;
                    break;
                case "*":
                    ketQua = soA * soB;
                    break;
                case "/":
                    soB = random.nextInt(10) + 1; // Số B từ 1 đến 10
                    soA = soB * (random.nextInt(10) + 1); // Số A là bội của số B
                    ketQua = (double) soA / soB;
                    break;
            }
            ds.add(new MathSo(soA, soB, dauNgauNhien, ketQua));
        }
        return ds;
    }

    // Bắt đầu timer
    private void batDauTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        countDownTimer = new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tvTimer.setText("⏰ " + (millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                Toast.makeText(ToanBai1Activity.this, "Hết thời gian!", Toast.LENGTH_SHORT).show();
                chuyenCauHoiTiepTheo();
            }
        }.start();
    }

    // Cập nhật tiến trình
    private void capNhatTienTrinh() {
        progressBar.setProgress(currentQuestionIndex);
    }

    // Hiển thị câu hỏi tiếp theo
    private void hienThiCauHoiTiepTheo() {
        if (currentQuestionIndex >= TOTAL_QUESTIONS) {
            Toast.makeText(this, "Chúc mừng bạn đã hoàn thành bài tập", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ToanBai1Activity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            return;
        }

        MathSo mathSo = mathSos.get(currentQuestionIndex);
        tvSoA.setText(String.valueOf(mathSo.getSoA()));
        tvDau.setText(mathSo.getDau());
        tvSoB.setText(String.valueOf(mathSo.getSoB()));
        tvResult.setText("?");

        // Tạo 4 đáp án (1 đúng, 3 sai cho button)
        answerOptions = taoDapAn(mathSo.getKetqua());
        btnAnswer1.setText(String.valueOf(answerOptions.get(0)));
        btnAnswer2.setText(String.valueOf(answerOptions.get(1)));
        btnAnswer3.setText(String.valueOf(answerOptions.get(2)));
        btnAnswer4.setText(String.valueOf(answerOptions.get(3)));

        capNhatTienTrinh();
        batDauTimer(); // Khởi động lại timer cho câu hỏi mới
    }

    // Tạo đáp án
    private List<Double> taoDapAn(double ketQua) {
        List<Double> options = new ArrayList<>();
        Random random = new Random();
        options.add(ketQua);

        while (options.size() < 4) {
            double kqSai = ketQua + random.nextInt(10) - 5; // ±5 so với đáp án đúng
            if (kqSai != ketQua && !options.contains(kqSai)) {
                options.add(kqSai);
            }
        }
        Collections.shuffle(options);
        return options;
    }

    // Kiểm tra đáp án
    private void kiemTraDapAn(int chon) {
        double dapAnChon = answerOptions.get(chon);
        MathSo mathSo = mathSos.get(currentQuestionIndex);

        if (dapAnChon == mathSo.getKetqua()) {
            Toast.makeText(this, "Đúng rồi!", Toast.LENGTH_SHORT).show();
            currentScore++;
            tvScore.setText("🏆 " + currentScore);
            tvResult.setText(String.valueOf(mathSo.getKetqua()));
            countDownTimer.cancel();
            currentQuestionIndex++;
            new android.os.Handler().postDelayed(this::hienThiCauHoiTiepTheo, 1000); // Chờ 1 giây trước khi chuyển
        } else {
            Toast.makeText(this, "Sai rồi, thử lại!", Toast.LENGTH_SHORT).show();
        }
    }

    // Chuyển câu hỏi tiếp theo (khi hết thời gian)
    private void chuyenCauHoiTiepTheo() {
        MathSo mathSo = mathSos.get(currentQuestionIndex);
        tvResult.setText(String.valueOf(mathSo.getKetqua()));
        currentQuestionIndex++;
        hienThiCauHoiTiepTheo();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}