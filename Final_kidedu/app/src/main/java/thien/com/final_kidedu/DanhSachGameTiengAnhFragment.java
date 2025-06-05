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

public class DanhSachGameTiengAnhFragment extends Fragment {

    private ImageView ivBackFromGameListTiengAnh;
    private MaterialCardView cvGameTiengAnh1, cvGameTiengAnh2, cvGameTiengAnh3, cvGameTiengAnh4;

    public DanhSachGameTiengAnhFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_danh_sach_game_tieng_anh, container, false);

        ivBackFromGameListTiengAnh = view.findViewById(R.id.ivBackFromGameListTiengAnh);
        cvGameTiengAnh1 = view.findViewById(R.id.cvGameTiengAnh1); // Lật Thẻ Từ Vựng
        cvGameTiengAnh2 = view.findViewById(R.id.cvGameTiengAnh2); // Nghe và Chạm
        cvGameTiengAnh3 = view.findViewById(R.id.cvGameTiengAnh3); // Ghép Chữ Thành Từ
        cvGameTiengAnh4 = view.findViewById(R.id.cvGameTiengAnh4); // Câu Chuyện Tương Tác

        if (ivBackFromGameListTiengAnh != null) {
            ivBackFromGameListTiengAnh.setOnClickListener(v -> {
                if (getActivity() != null) {
                    getActivity().getSupportFragmentManager().popBackStack();
                }
            });
        }

        cvGameTiengAnh1.setOnClickListener(v -> {
            // Chuyển đến LatTheTuVungGameFragment
            loadFragment(new LatTheTuVungGameFragment(), true);
        });

        cvGameTiengAnh2.setOnClickListener(v -> {
            // Chuyển đến NgheVaChamGameFragment
            loadFragment(new NgheVaChamGameFragment(), true);
        });

        cvGameTiengAnh3.setOnClickListener(v -> {
            Toast.makeText(getActivity(), "Mở game Ghép Chữ Thành Từ", Toast.LENGTH_SHORT).show();
            // loadFragment(new GhepChuThanhTuGameFragment(), true); // Tạo GhepChuThanhTuGameFragment
        });

        cvGameTiengAnh4.setOnClickListener(v -> {
            Toast.makeText(getActivity(), "Mở game Câu Chuyện Tương Tác", Toast.LENGTH_SHORT).show();
            // loadFragment(new CauChuyenTuongTacGameFragment(), true); // Tạo CauChuyenTuongTacGameFragment
        });

        return view;
    }

    private void loadFragment(Fragment fragment, boolean addToBackStack) {
        if (getActivity() != null) {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            // Sử dụng R.id.fragment_container từ GiaoDienChinhActivity (hoặc ID bạn đã đặt)
            fragmentTransaction.replace(R.id.fragment_container, fragment); // HOẶC R.id.fg_sp
            if (addToBackStack) {
                fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());
            }
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            fragmentTransaction.commit();
        }
    }
}
