package thien.com.kidedu_project_cuoiky;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class AdapterFg extends FragmentStateAdapter {
    public AdapterFg(@NonNull FragmentActivity fragmentActivity){
        super(fragmentActivity);
    }
    @NonNull
    @Override
    public Fragment createFragment(int position){
        switch (position){
            case 0:
                return new LoginFragment1();
            case 1:
                return new LoginFragment2();
            case 2:
                return new LoginFragment3();
            default:
                return new LoginFragment1();
        }
    }
    @Override
    public int getItemCount() {
        return 3; // Số lượng trang onboarding
    }
}