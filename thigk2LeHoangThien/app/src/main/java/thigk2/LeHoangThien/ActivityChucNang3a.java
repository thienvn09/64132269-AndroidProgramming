package thigk2.LeHoangThien;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ActivityChucNang3a extends AppCompatActivity {
    TextView txtTB;
    public TextView getTxtTB() {
        return txtTB;
    }
    public void setTxtTB(TextView txtTB) {
        this.txtTB = txtTB;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chuc_nang3a);
        txtTB = findViewById(R.id.txtTB);
        String mon1 = getIntent().getStringExtra("mon");
        txtTB.setText("môn học bạn vừa chọn là " + mon1);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}