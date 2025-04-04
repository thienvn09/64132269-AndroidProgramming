package thien.com.tuongk_3;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Mang3a extends AppCompatActivity {
    ImageView hinh;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mang3a);
        hinh = findViewById(R.id.imgHinhA);
        textView = findViewById(R.id.txtget);

        String item = getIntent().getStringExtra("title");
        String linkHinh = getIntent().getStringExtra("linkHinh");
        setTitle(item);
        String packageName = getPackageName();
        int resID = getResources().getIdentifier(linkHinh, "mipmap", packageName);
        findViewById(R.id.imgHinhA).setBackgroundResource(resID);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}