package thigk2.LeHoangThien;

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

public class ActivityChucNang2 extends AppCompatActivity {
    Button Tinh;
    EditText txtCK, Output, txtGK;

    public EditText getTxtCK() {
        return txtCK;
    }

    public void setTxtCK(EditText txtCK) {
        this.txtCK = txtCK;
    }

    public EditText getOutput() {
        return Output;
    }

    public void setOutput(EditText output) {
        Output = output;
    }

    public EditText getTxtGK() {
        return txtGK;
    }

    public void setTxtGK(EditText txtGK) {
        this.txtGK = txtGK;
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chuc_nang2);
        Tinh = findViewById(R.id.btnTinh);
        txtCK = findViewById(R.id.txtCk);
        Output = findViewById(R.id.Output);
        txtGK = findViewById(R.id.txtGK);
        Tinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double a = Double.parseDouble(txtGK.getText().toString());
                Double b = Double.parseDouble(txtCK.getText().toString());
                Double c = (a * 0.5) + (b * 0.5);
                Output.setText(c.toString());
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}