package thien.com.example.may_tinh_dongian;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private EditText txt_SoA,txt_soB;
    private Button btnTinh;
    private TextView txt_KQ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        // Ánh xạ giao diện
        txt_SoA = findViewById(R.id.txt_soA);
        txt_soB = findViewById(R.id.editTextNumber2);
        btnTinh = findViewById(R.id.btnTinh);
        txt_KQ = findViewById(R.id.txt_KQ);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public String getSoA() {
        return txt_SoA.getText().toString();
    }
    public void setSoA(String value)
    {
        value=txt_SoA.getText().toString();
    }
    public String getSoB() {
        return txt_soB.getText().toString();
    }

    public void setSoB(String value) {
        txt_soB.setText(value);
    }
    public String getKetQua() {
        return txt_KQ.getText().toString();
    }

    public void setKetQua(String value) {
        txt_KQ.setText(value);
    }

    public void XuLyCong(View view)
    {
        double a = Double.parseDouble(getSoA());
        double b = Double.parseDouble(getSoB());
        double KQ_Cong = a+b;
        setKetQua("kết quả "+KQ_Cong);
    }
 /*   public void XulyTru(View view)
    {
        double a = Double.parseDouble(getSoA());
        double b = Double.parseDouble(getSoB());
        double KQ_Cong = a+b;
        setKetQua("kết quả "+KQ_Cong);
    }*/



}