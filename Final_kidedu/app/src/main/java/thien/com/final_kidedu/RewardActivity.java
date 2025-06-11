package thien.com.final_kidedu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class RewardActivity extends AppCompatActivity {
    private Button buttonYay;
    private TextView textViewAnswerMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reward);

        buttonYay = findViewById(R.id.buttonYay);
        textViewAnswerMessage = findViewById(R.id.textViewAnswerMessage);

        // Lấy Intent đã khởi động Activity này
        Intent intentFromPrevious = getIntent();

        // Lấy dữ liệu được gửi kèm với key "DapAn"
        String answer = intentFromPrevious.getStringExtra("DapAn");

        // Hiển thị câu chúc mừng với đáp án
        if (answer != null && !answer.isEmpty()) {
            textViewAnswerMessage.setText("Bạn vừa đoán đúng từ: \"" + answer + "\"");
        }

        // Quay về MainActivity khi nhấn nút
        buttonYay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToMain = new Intent(RewardActivity.this, MainActivity.class);
                intentToMain.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intentToMain);
                finish(); // Kết thúc RewardActivity để không bị quay lại
            }
        });
    }
}
