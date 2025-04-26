package thien.com.kidedu_project_cuoiky;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class LoginFragment1 extends Fragment {


    public LoginFragment1() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login1, container, false);
         Button btnNext = view.findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewPager2 viewPager = getActivity().findViewById(R.id.viewPager);
                if(viewPager != null)
                {
                    viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
                }
            }
        });
        return view;
    }
}