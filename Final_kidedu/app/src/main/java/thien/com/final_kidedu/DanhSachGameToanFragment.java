package thien.com.final_kidedu;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.android.material.card.MaterialCardView;

public class DanhSachGameToanFragment extends Fragment {

    private MaterialCardView cvGameToan1, cvGameToan2, cvGameToan3, cvGameToan4;
    private ImageView ivBack;

    public DanhSachGameToanFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_danh_sach_game_toan, container, false);

        ivBack = view.findViewById(R.id.ivBackFromGameListToan);
        cvGameToan1 = view.findViewById(R.id.cvGameToan1);
        cvGameToan2 = view.findViewById(R.id.cvGameToan2);
        cvGameToan3 = view.findViewById(R.id.cvGameToan3);
        cvGameToan4 = view.findViewById(R.id.cvGameToan4);

        ivBack.setOnClickListener(v -> {
            // Quay lại HomeFragment hoặc Fragment trước đó
            if (getActivity() != null) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        cvGameToan1.setOnClickListener(v -> {
            // test bo lắng nghe
            Toast.makeText(getActivity(), "Mở game Đếm Sao Nhanh", Toast.LENGTH_SHORT).show();
            // Tạo DemSaoNhanhGameFragment
             loadFragment(new DemSaoNhanhGameFragment(), true);
        });

        cvGameToan2.setOnClickListener(v -> {
            Toast.makeText(getActivity(), "Mở game Phép Tính Bong Bóng", Toast.LENGTH_SHORT).show();
             loadFragment(new PhepTinhBongBongGameFragment(), true);
        });

        cvGameToan3.setOnClickListener(v -> {
            Toast.makeText(getActivity(), "Mở game Tìm Số Bí Ẩn", Toast.LENGTH_SHORT).show();
            loadFragment(new TimSoBiAnGameFragment(), true);
        });

        cvGameToan4.setOnClickListener(v -> {
            Toast.makeText(getActivity(), "Mở game Xếp Hình Thông Minh", Toast.LENGTH_SHORT).show();
             loadFragment(new XepHinhGameFragment(), true);
        });

        return view;
    }

    private void loadFragment(Fragment fragment, boolean addToBackStack) {
        if (getActivity() != null) {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            // Sử dụng R.id.fragment_container từ GiaoDienChinhActivity
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            if (addToBackStack) {
                fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());
            }
            fragmentTransaction.commit();
        }
    }
}
