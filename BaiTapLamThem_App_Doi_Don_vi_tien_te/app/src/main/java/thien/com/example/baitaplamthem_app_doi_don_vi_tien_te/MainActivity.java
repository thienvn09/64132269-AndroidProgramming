package thien.com.example.baitaplamthem_app_doi_don_vi_tien_te;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private EditText edit_input_amount;
    private Spinner spinner_from, spinner_to;
    private TextView edit_output_amount;
    private Button btn_convert;

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

        edit_input_amount = findViewById(R.id.edit_input_amount);
        edit_output_amount = findViewById(R.id.edit_output_amount);
        spinner_from = findViewById(R.id.spinner_from);
        spinner_to = findViewById(R.id.spinner_to);
        btn_convert = findViewById(R.id.btn_convert);

        edit_output_amount.setFocusable(false);
        edit_output_amount.setClickable(false);

        String[] currencies = {"USD", "VND", "EUR", "JPY", "GBP"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, currencies);
        spinner_from.setAdapter(adapter);
        spinner_to.setAdapter(adapter);

        btn_convert.setOnClickListener(v -> convertCurrency());


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void convertCurrency() {
        String Tienin = spinner_from.getSelectedItem().toString();
        String Tienout = spinner_to.getSelectedItem().toString();
        String amountStr = edit_input_amount.getText().toString().trim();

        if (amountStr.isEmpty()) {
            edit_output_amount.setText("Vui lòng nhập số tiền hợp lệ");
            return;
        }

        try {
            double amount = Double.parseDouble(amountStr);
            double fromRate = exchangeRates.get(Tienin);
            double toRate = exchangeRates.get(Tienout);
            double result = amount * (toRate / fromRate);

            NumberFormat formatter = new DecimalFormat("#,###.##");
            edit_output_amount.setText(String.format("%s %s = %s %s", amount, Tienin, formatter.format(result), Tienout));
        } catch (NumberFormatException e) {
            edit_output_amount.setText("Lỗi: Nhập số hợp lệ!");
        }
    }
}
