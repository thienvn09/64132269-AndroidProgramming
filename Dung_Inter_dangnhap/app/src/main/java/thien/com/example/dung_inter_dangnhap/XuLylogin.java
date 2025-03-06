package thien.com.example.dung_inter_dangnhap;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class XuLylogin extends AppCompatActivity {
    private EditText inputTk,inputMK,etxtThongBao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_xu_lylogin);
        inputTk = findViewById(R.id.inputTk);
        inputMK =findViewById(R.id.inputMK);
        etxtThongBao=findViewById(R.id.etxtThongBao);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public String getInputTk(){
        return inputTk.getText().toString();
    }
    public String getInputMK()
    {
        return inputMK.getText().toString();
    }
    Button btnDN=findViewById(R.id.btnDN);
    btnDN.setOnClickListener(new View.OnLongClickListener(){
        @Override
    })
}