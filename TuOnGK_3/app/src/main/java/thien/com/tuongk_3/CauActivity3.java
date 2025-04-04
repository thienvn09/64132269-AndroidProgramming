package thien.com.tuongk_3;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CauActivity3 extends AppCompatActivity {
    LayoutLV adapter;
    ArrayList<LandScape> duLieus;
    RecyclerView recyclerView;

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cau3);

        duLieus=getData();

        recyclerView = findViewById(R.id.LV);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);

        adapter = new LayoutLV(this,duLieus);
        recyclerView.setAdapter(adapter);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public ArrayList<LandScape> getData(){
        ArrayList<LandScape> dsDul = new ArrayList<LandScape>();
        LandScape Thien = new LandScape("Thien","dambuts");
        LandScape Thien1 = new LandScape("Thien1","dambuts");
        LandScape Thien2 = new LandScape("Thien2","dambuts");
        LandScape Thien3 = new LandScape("Thien3","dambuts");
        LandScape Thien4 = new LandScape("Thien4","dambuts");
        LandScape Thien5 = new LandScape("Thien5","dambuts");
        LandScape Thien6 = new LandScape("Thien6","dambuts");
        LandScape Thien7 = new LandScape("Thien7","dambuts");
        dsDul.add(Thien);
        dsDul.add(Thien1);
        dsDul.add(Thien2);
        dsDul.add(Thien3);
        dsDul.add(Thien4);
        dsDul.add(Thien5);
        dsDul.add(Thien6);
        dsDul.add(Thien7);
        return dsDul;

    }
}