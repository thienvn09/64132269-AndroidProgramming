package thien.com.kidedu_project_cuoiky;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class LoginFragment3 extends Fragment {
    Button btnStart;
    public LoginFragment3() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login3, container, false);

        // Khởi tạo nút Start
        btnStart = view.findViewById(R.id.btnStart);

        // Xử lý sự kiện bấm nút Start
        btnStart.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), DangNhapActivity.class);
            startActivity(intent);
            getActivity().finish();
        });

        return view;
    }
}