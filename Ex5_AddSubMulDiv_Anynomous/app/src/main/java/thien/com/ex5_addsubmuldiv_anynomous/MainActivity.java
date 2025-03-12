package thien.com.ex5_addsubmuldiv_anynomous;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private TextView txt_soA,txt_soB,txt_KQ;
    public Button  btnTinh,btnTinhNhan,btnTinhChia,btnTinhTru;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        txt_soA = findViewById(R.id.txt_soA);
        txt_KQ = findViewById(R.id.txt_KQ);
        txt_soB = findViewById(R.id.txt_soB);
        btnTinh = findViewById(R.id.btnTinh);
        btnTinhTru = findViewById(R.id.btnTinhTru);
        btnTinhNhan = findViewById(R.id.btnTinhNhan);
        btnTinhChia = findViewById(R.id.btnTinhChia);
        double a = Double.parseDouble(getTxt_soA());
        double b = Double.parseDouble(getTxt_soB());
        double kqCong = a+b;
        double kqTru = a-b;
        double kqNhan =a*b;
        double kqchia = a/b;
        btnTinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTxt_KQ(Double.toString(kqCong));
            }
        });
        btnTinhNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTxt_KQ(Double.toString(kqNhan));
            }
        });
        btnTinhTru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTxt_KQ(Double.toString(kqTru));
            }
        });
        btnTinhChia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(b == 0){
                    setTxt_KQ("không thể chia cho 0");
                }
                setTxt_KQ(Double.toString(kqchia));
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }

    public String getTxt_soA()
    {
        return txt_soA.getText().toString();
    }
    public String getTxt_soB()
    {
        return txt_soB.getText().toString();
    }
    public void setTxt_KQ(String kq)
    {
        txt_KQ.setText(kq);
    }
}