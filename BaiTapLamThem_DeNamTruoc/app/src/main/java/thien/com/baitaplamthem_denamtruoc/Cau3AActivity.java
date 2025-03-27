package thien.com.baitaplamthem_denamtruoc;

import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class Cau3AActivity extends AppCompatActivity {
    public ListView txtDs;
    public ArrayList<String> dsbai = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cau3_aactivity);
        txtDs = findViewById(R.id.txtDs);
        dsbai.add("Bai hat thu 1");
        dsbai.add("bai hat thu 2");
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

}