package thien.com.example.tinhchisobmi;

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
    private EditText Txt_ChieuCao,txt_CanNang;
    private Button Tinh;
    private TextView KQ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        Txt_ChieuCao = findViewById(R.id.Txt_ChieuCao);
        txt_CanNang = findViewById(R.id.txt_CanNang);
        Tinh = findViewById(R.id.Tinh);
        KQ = findViewById(R.id.KQ);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public String getTxt_ChieuCao()
    {
        return Txt_ChieuCao.getText().toString();

    }
    public String gettxt_CanNang()
    {
        return txt_CanNang.getText().toString();
    }
    public void setKQ(String value )
    {
        KQ.setText(value);
    }
    public void Tinh1(View v)
    {
        double CanNang = Double.parseDouble(gettxt_CanNang());
        double ChieuCao = Double.parseDouble(gettxt_CanNang());
        double DMI = (CanNang)/(ChieuCao*ChieuCao);
        setKQ("chi so DMI cua ban la "+ String.valueOf(DMI));

    }
}