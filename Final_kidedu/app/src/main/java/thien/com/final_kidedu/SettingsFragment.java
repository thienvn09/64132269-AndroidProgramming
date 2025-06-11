package thien.com.final_kidedu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class SettingsFragment extends Fragment {

    private SwitchMaterial switchSound;
    private Button btnLogout;
    public static final String PREFS_NAME = "KidEduPrefs";
    public static final String SOUND_KEY = "sound_enabled";

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        switchSound = view.findViewById(R.id.switchSound);
        btnLogout = view.findViewById(R.id.btnLogout);

        // Load trạng thái âm thanh đã lưu
        loadSoundSetting();

        switchSound.setOnCheckedChangeListener((buttonView, isChecked) -> {
            saveSoundSetting(isChecked);
            if (isChecked) {
                Toast.makeText(getActivity(), "Âm thanh đã được bật", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Âm thanh đã được tắt", Toast.LENGTH_SHORT).show();
            }
        });

        btnLogout.setOnClickListener(v -> {
            if (getActivity() != null) {
                Intent intent = new Intent(getActivity(), DangNhapActivity.class);
                // Xóa tất cả các activity trước đó và tạo một task mới
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return view;
    }

    private void saveSoundSetting(boolean isEnabled) {
        if (getActivity() != null) {
            SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean(SOUND_KEY, isEnabled);
            editor.apply();
        }
    }

    private void loadSoundSetting() {
        if (getActivity() != null) {
            SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            // Giá trị mặc định là true (bật âm thanh)
            boolean soundEnabled = settings.getBoolean(SOUND_KEY, true);
            switchSound.setChecked(soundEnabled);
        }
    }
}
