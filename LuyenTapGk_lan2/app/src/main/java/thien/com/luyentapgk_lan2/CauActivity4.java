package thien.com.luyentapgk_lan2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CauActivity4 extends AppCompatActivity {
    LandScanPAdapter LINHCOPYNAYNE1;
    ArrayList<LandScape> LINHCOPYNAYNE2;
    RecyclerView LINHCOPYNAYNE3;
    // ghi dòng 20 vào
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cau4);

        LINHCOPYNAYNE2 = getDAta();

        LINHCOPYNAYNE3 = findViewById(R.id.HEHE);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        LINHCOPYNAYNE3.setLayoutManager(layoutManager);

        LINHCOPYNAYNE1 = new LandScanPAdapter(LINHCOPYNAYNE2,this);

        LINHCOPYNAYNE3.setAdapter(LINHCOPYNAYNE1);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    ArrayList<LandScape> getDAta(){
        ArrayList<LandScape> dsDULieu = new ArrayList<LandScape>();
        // khai báo một class sau đó nhập dữ liệu cho nó
        LandScape THIENBIDEPTRAi = new LandScape("SIEU PHAM THIEN","dambuts");
        dsDULieu.add(THIENBIDEPTRAi);
        return dsDULieu;
    }
}