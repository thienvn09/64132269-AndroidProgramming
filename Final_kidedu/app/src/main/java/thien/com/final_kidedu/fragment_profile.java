package thien.com.final_kidedu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import Data.AppDatabase;
import Data.UserDao;
import Model.User;


public class fragment_profile extends Fragment {

    private TextView tvProfileName, tvProfileGender, tvProfileSchool, tvProfileClass;
    private UserDao userDao;

    public fragment_profile() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        tvProfileName = view.findViewById(R.id.tvProfileName);
        tvProfileGender = view.findViewById(R.id.tvProfileGender);
        tvProfileSchool = view.findViewById(R.id.tvProfileSchool);
        tvProfileClass = view.findViewById(R.id.tvProfileClass);

        if(getContext() != null) {
            AppDatabase db = AppDatabase.getDatabase(getContext().getApplicationContext());
            userDao = db.userDao();
        }

        loadUserProfile();

        return view;
    }

    private void loadUserProfile() {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            // Lấy người dùng cuối cùng được đăng ký làm ví dụ
            User user = userDao.getLastRegisteredUser(); // Cần thêm phương thức này vào UserDao

            if (getActivity() != null) {
                getActivity().runOnUiThread(() -> {
                    if (user != null) {
                        tvProfileName.setText(user.name);
                        tvProfileGender.setText(user.gender);
                        tvProfileSchool.setText(user.school);
                        tvProfileClass.setText(user.clas);
                    } else {
                        Toast.makeText(getActivity(), "Không tìm thấy thông tin người dùng", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}