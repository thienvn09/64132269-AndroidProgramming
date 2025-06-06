package thien.com.final_kidedu;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class GamesFragment extends Fragment {

    private Button btnGoToMathGames, btnGoToEnglishGames;

    public GamesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_games, container, false);

        btnGoToMathGames = view.findViewById(R.id.btnGoToMathGames);
        btnGoToEnglishGames = view.findViewById(R.id.btnGoToEnglishGames);

        btnGoToMathGames.setOnClickListener(v -> {
            loadFragment(new DanhSachGameToanFragment(), true);
        });

        btnGoToEnglishGames.setOnClickListener(v -> {
            loadFragment(new DanhSachGameTiengAnhFragment(), true);
        });

        return view;
    }

    private void loadFragment(Fragment fragment, boolean addToBackStack) {
        if (getActivity() != null) {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment); // Sử dụng ID container của bạn
            if (addToBackStack) {
                fragmentTransaction.addToBackStack(null);
            }
            fragmentTransaction.commit();
        }
    }
}
