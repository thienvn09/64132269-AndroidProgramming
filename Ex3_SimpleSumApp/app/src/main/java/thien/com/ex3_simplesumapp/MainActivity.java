package thien.com.ex3_simplesumapp;

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
    public EditText inputA,inputB,OutPut;
    public Button btnTinh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        inputA = findViewById(R.id.inputA);
        inputB = findViewById(R.id.inputB);
        OutPut = findViewById(R.id.OutPut);
        btnTinh = findViewById(R.id.btnTinh);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public String getinputA()
    {
        return inputA.getText().toString();
    }
    public String getInputB()
    {
        return inputB.getText().toString();
    }
    public void setKetQua(String value) {
        OutPut.setText(value);
    }

    public void TinhTong(View view)
    {
        double a = Double.parseDouble(getinputA());
        double b = Double.parseDouble(getInputB());
        double KQ_Cong = a+b;
        setKetQua("kết quả "+ KQ_Cong);
    }

}