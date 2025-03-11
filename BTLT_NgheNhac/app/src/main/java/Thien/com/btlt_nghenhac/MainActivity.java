package Thien.com.btlt_nghenhac;

import static Thien.com.btlt_nghenhac.R.id.txtInputMK;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private EditText txtInputMK,txtInputTk;
    private Button btnDN;
    public String getTxtInputMk(){
        return txtInputMK.getText().toString();
    }
    public String getTxtInputTk()
    {
        return txtInputTk.getText().toString();
    }
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        // ánh xạ
        txtInputTk = findViewById(R.id.txtInputTK);
        txtInputMK = findViewById(R.id.txtInputMK);
        btnDN = findViewById(R.id.btnDN);
        btnDN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getTxtInputTk().equals("thien") && getTxtInputMk().equals("1"))
                {
                    Intent intent = new Intent(MainActivity.this, TrangChu.class);
                    startActivity(intent);
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