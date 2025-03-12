package Thien.com.btlt_nghenhac;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class TrangChu extends AppCompatActivity {
    public TextView txtChaoMung;
    ListView listDsbaihat;
    ArrayList<String> dsbaihat = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_trang_chu);

        txtChaoMung = findViewById(R.id.txtChaoMung);
        listDsbaihat = findViewById(R.id.listDsbaihat);
        String tenDn =getIntent().getStringExtra("123");
        txtChaoMung.setText(" " + tenDn + "chúc bạn một ngày tốt lành ");


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        dsbaihat.add("Không Thể Say - Ca sĩ : Hiếu Monday");
        dsbaihat.add("Âm Thầm Bên Em- Ca sĩ : G-Dragon Việt Nam");
        ArrayAdapter<String> adapterlistDsbaihat = new ArrayAdapter<>(this, android.R.layout.simple_list_item_activated_1, dsbaihat);
        listDsbaihat.setAdapter(adapterlistDsbaihat);

        listDsbaihat.setOnItemClickListener((parent, view, position, id)
        Tham số Kiểu dữ liệu	Ý nghĩa
        parent	AdapterView<?>	ListView chứa item được bấm (Chính là listDsbaihat)
        view	View	Giao diện của item được bấm (Là TextView nếu dùng simple_list_item_1.xml)
        position	int	Vị trí của item được bấm trong danh sách (dsbaihat.get(position))
        id	long	ID của item, nhưng với ArrayAdapter<String>, nó thường giống position
        listDsbaihat.setOnItemClickListener(new View)

    }
}