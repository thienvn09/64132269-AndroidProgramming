package thien.com.final_kidedu;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.card.MaterialCardView;

public class HomeFragment extends Fragment {

    private MaterialCardView cvMonToanHome, cvMonTiengAnhHome;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        // Ánh xạ các CardView từ layout fragment_home.xml
        cvMonToanHome = view.findViewById(R.id.cvMonToan_home);
        cvMonTiengAnhHome = view.findViewById(R.id.cvMonTiengAnh_home);

        // Xử lý sự kiện click cho CardView Môn Toán
        if (cvMonToanHome != null) {
            cvMonToanHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Load DanhSachGameToanFragment
                    loadFragment(new DanhSachGameToanFragment(), true); // true: thêm vào back stack
                }
            });
        }

        // Xử lý sự kiện click cho CardView Môn Tiếng Anh
        if (cvMonTiengAnhHome != null) {
            cvMonTiengAnhHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Load DanhSachGameTiengAnhFragment
                    loadFragment(new DanhSachGameTiengAnhFragment(), true); // true: thêm vào back stack
                }
            });
        }

        return view;
    }

    // Phương thức tiện ích để load  Fragment
    private void loadFragment(Fragment fragment, boolean addToBackStack) {
        if (getActivity() != null) { // Đảm bảo Fragment đã được gắn vào một Activity
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            if (addToBackStack) {

                fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());
            }
            // Thêm hiệu ứng chuyển Fragment
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            fragmentTransaction.commit();
        }
    }
}
