package thien.com.tuongk_3;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.concurrent.ThreadLocalRandom;

public class Cau4 extends AppCompatActivity {
    Button btnnut1,btnnut2,btnnut3,btnnut4,btnnut5,btnnut6,btnnut7,btnnut8,btnnut9,btn10,btnCheck;
    EditText edtOutPut,inputA,InputB,edtThongBao;
    String a,b;
    int randomInt = ThreadLocalRandom.current().nextInt(1,5);
    public void setInputA(String a)
    {
        inputA.setText(a);
    }
    public void setInputB(String b)
    {
        InputB.setText(b);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cau4);
        btnnut1 = findViewById(R.id.btn1);
        btnnut2 = findViewById(R.id.btn2);
        btnnut3 = findViewById(R.id.btn3);
        btnnut4 = findViewById(R.id.btn4);
        btnnut5 = findViewById(R.id.btn5);
        btnnut6 = findViewById(R.id.btn6);
        btnnut7 = findViewById(R.id.btn7);
        btnnut8 = findViewById(R.id.btn8);
        btnnut9 = findViewById(R.id.btn9);
        btnCheck = findViewById(R.id.btnCheck);
        edtOutPut = findViewById(R.id.edtOutPut);
        inputA = findViewById(R.id.inputA);
        InputB = findViewById(R.id.InputB);
        edtThongBao = findViewById(R.id.edtThongBao);
        btnnut1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtOutPut.setText("1");
            }
        });
        btnnut2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtOutPut.setText("2");
            }
        });
        btnnut3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtOutPut.setText("3");
            }
        });
        btnnut4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtOutPut.setText("4");
            }
        });
        btnnut5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtOutPut.setText("5");
            }
        });
        btnnut6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtOutPut.setText("6");
            }
        });
        btnnut7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtOutPut.setText("7");
            }
        });
        btnnut8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtOutPut.setText("8");
            }
        });
        btnnut9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtOutPut.setText("9");
            }
        });
        a = String.valueOf(randomInt);
        b = String.valueOf(randomInt);
        setInputA(String.valueOf(a));
        setInputB(String.valueOf(b));
        int kq = Integer.parseInt(a) + Integer.parseInt(b);
        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtOutPut.getText().toString().equals(String.valueOf(kq))) {
                    edtThongBao.setText("Đúng rồi");
                    /*Toast.makeText(MainActivity.this,"dung roi",Toast.LENGTH_SHORT).show();*/
                }
                else{
                    edtThongBao.setText("Sai rồi");
                    /*Toast.makeText(MainActivity.this,"sai roi",Toast.LENGTH_SHORT).show();*/
                }
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
