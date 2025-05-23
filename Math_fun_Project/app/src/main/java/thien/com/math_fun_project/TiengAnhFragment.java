package thien.com.math_fun_project;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TiengAnhFragment extends Fragment implements HinhAdapter.OnButtonClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;
    private HinhAdapter adapter;
    private List<HinhItem> gameItems;
    private TextView tvTime, tvTimer, tvScore;
    private ProgressBar progressBar;
    private Button btnBack;
    private CountDownTimer countDownTimer;
    private int currentScore = 0;
    private int currentQuestionIndex = 0;
    private static final int TOTAL_QUESTIONS = 10; // Tổng số câu hỏi

    public TiengAnhFragment() {
        // Required empty public constructor
    }

    public static TiengAnhFragment newInstance(String param1, String param2) {
        TiengAnhFragment fragment = new TiengAnhFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tieng_anh, container, false);

        // Khởi tạo các view
        tvTimer = view.findViewById(R.id.tvTimer);
        tvScore = view.findViewById(R.id.tvScore);
        progressBar = view.findViewById(R.id.progressBar);
        btnBack = view.findViewById(R.id.btnBack);
        recyclerView = view.findViewById(R.id.tienganh);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        // Khởi tạo timer (3 giây mỗi câu hỏi)
        startTimer();

        // Cập nhật điểm số ban đầu
        tvScore.setText("🏆 " + currentScore);

        // Cập nhật thanh tiến độ
        updateProgressBar();

        // Nút Quay lại
        btnBack.setOnClickListener(v -> {
            if (getActivity() != null) {
                getActivity().onBackPressed();
            }
        });

        // Tạo dữ liệu mẫu
        gameItems = new ArrayList<>();
        gameItems.add(new HinhItem(R.mipmap.english_icon, "fox", new String[]{"f", "o", "x"}));

        // Thiết lập adapter
        adapter = new HinhAdapter(gameItems, this);
        recyclerView.setAdapter(adapter);

        // Cuộn đến câu hỏi hiện tại
        recyclerView.scrollToPosition(currentQuestionIndex);

        return view;
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tvTimer.setText("⏰ " + (millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                Toast.makeText(getContext(), "Hết thời gian! Chuyển câu tiếp theo.", Toast.LENGTH_SHORT).show();
                currentQuestionIndex++;
                if (currentQuestionIndex < gameItems.size()) {
                    recyclerView.scrollToPosition(currentQuestionIndex);
                    startTimer();
                } else {
                    Toast.makeText(getContext(), "Hoàn thành!", Toast.LENGTH_SHORT).show();
                }
            }
        }.start();
    }

    private void updateProgressBar() {
        int progress = (currentQuestionIndex + 1) * 100 / TOTAL_QUESTIONS;
        progressBar.setProgress(progress);
    }

    @Override
    public void onButtonClick(int itemPosition, String selectedAnswer, boolean isCorrect) {
        if (itemPosition != currentQuestionIndex) {
            return; // Chỉ cho phép trả lời câu hỏi hiện tại
        }

        if (isCorrect) {
            Toast.makeText(getContext(), "Đúng rồi!", Toast.LENGTH_SHORT).show();
            currentScore++;
            tvScore.setText("🏆 " + currentScore);
            countDownTimer.cancel();
            currentQuestionIndex++;
            if (currentQuestionIndex < gameItems.size()) {
                recyclerView.scrollToPosition(currentQuestionIndex);
                startTimer();
            } else {
                Toast.makeText(getContext(), "Hoàn thành!", Toast.LENGTH_SHORT).show();
            }
            updateProgressBar();
        } else {
            Toast.makeText(getContext(), "Sai rồi, thử lại!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}