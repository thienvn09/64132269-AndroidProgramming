package thien.com.tuongk_3;

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

public class Cau1Activity extends AppCompatActivity {
    EditText inputA,inputB,OutPut,inPutC;

    Button btnTinh,btnTb;

    public EditText getOutPut() {
        return OutPut;
    }

    public void setOutPut(EditText outPut) {
        OutPut = outPut;
    }

    public EditText getInputA() {
        return inputA;
    }

    public void setInputA(EditText inputA) {
        this.inputA = inputA;
    }

    public EditText getInputB() {
        return inputB;
    }

    public void setInputB(EditText inputB) {
        this.inputB = inputB;
    }

    public EditText getInPutC() {
        return inPutC;
    }

    public void setInPutC(EditText inPutC) {
        this.inPutC = inPutC;
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cau1);
        inputA = findViewById(R.id.inPutA);
        inputB = findViewById(R.id.inPutB);
        OutPut = findViewById(R.id.txtOut);
        btnTinh = findViewById(R.id.btnTinh);
        inPutC = findViewById(R.id.inPutC);
        btnTb = findViewById(R.id.btnTb);
        btnTb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double a = Integer.parseInt(getInputA().getText().toString());
                double b = Integer.parseInt(getInputB().getText().toString());
                double c = Integer.parseInt(getInPutC().getText().toString());
                double d = 0.2*a + 0.3*b + 0.5*c;
                OutPut.setText(String.valueOf(d));
            }
        });
        btnTinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a = "30";
                String b = "4";
                String c="1975";

                if(getInputA().getText().toString().equals(a) && getInputB().getText().toString().equals(b) && getInPutC().getText().toString().equals(c)){
                    OutPut.setText("Đúng rồi");
                }
                else{
                    OutPut.setText("Sai rồi");
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