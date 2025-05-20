package thien.com.math_fun_project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ToanFragment extends Fragment {

    private RecyclerView recyclerView;
    private AdapterBaiTap adapter;
    private ArrayList<BaiTap> dsBaiTap;

    public ToanFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_toan, container, false);

        // Khởi tạo RecyclerView
        recyclerView = view.findViewById(R.id.recycler_view_bai_tap);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 5)); // 5 cột

        // Tạo danh sách 6 bài tập
        dsBaiTap = new ArrayList<>();
        
       /* dsBaiTap.add(new BaiTap("img_baimot", "img_baimot"));
        dsBaiTap.add(new BaiTap("img_baihai", "ic_exercise_2"));
        dsBaiTap.add(new BaiTap("img_baiba", "ic_exercise_3"));
        dsBaiTap.add(new BaiTap("img_baibon", "ic_exercise_4"));
        *//*dsBaiTap.add(new BaiTap("Bài tập 5", "ic_exercise_5"));
        dsBaiTap.add(new BaiTap("Bài tập 6", "ic_exercise_6"));*/

        // Gán Adapter cho RecyclerView
        adapter = new AdapterBaiTap(getContext(), dsBaiTap);
        recyclerView.setAdapter(adapter);

        return view;
    }
}