package thien.com.example.baitaplamthem_app_doi_don_vi_tien_te;

import android.os.Bundle;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private final HashMap<String, Double> exchangeRates = new HashMap<String, Double>() {{
        put("USD", 1.0);
        put("VND", 24000.0);
        put("EUR", 0.92);
        put("JPY", 150.5);
        put("GBP", 0.78);
    }};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
      
        Spinner spinner = findViewById(R.id.spinner_from);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}