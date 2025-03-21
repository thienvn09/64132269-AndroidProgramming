package thien.com.baitaplamthem_denamtruoc;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Cau2Activity extends AppCompatActivity {
    EditText inputA,inputB,OutPut;
    Button btnT;
    public String getInputA(){
        return inputA.getText().toString();
    }
    public String getInputB()
    {
        return inputB.getText().toString();
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cau2);
        btnT = findViewById(R.id.btnT);
        inputA = findViewById(R.id.inputA);
        inputB = findViewById(R.id.inputB);
        OutPut = findViewById(R.id.OutPut);
        btnT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a,b;
                a = getInputA();
                b = getInputB();
                String kq = a+b;
                OutPut.setText(kq);
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}