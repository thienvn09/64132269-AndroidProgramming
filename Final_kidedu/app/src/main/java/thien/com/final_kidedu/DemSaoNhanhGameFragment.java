package thien.com.final_kidedu;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button; // Sử dụng Button hoặc MaterialButton tùy theo layout
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton; // Nếu dùng MaterialButton

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class DemSaoNhanhGameFragment extends Fragment {

    private ImageView ivBackFromGame;
    private TextView tvGameTitle;
    private TextView tvScoreValue;
    private FrameLayout starDisplayArea;
    private MaterialButton btnAnswer1, btnAnswer2, btnAnswer3; // Hoặc Button

    private int currentScore = 0;
    private int correctAnswer;
    private final Random random = new Random();
    private final Handler handler = new Handler(Looper.getMainLooper());
    private final List<ImageView> displayedStars = new ArrayList<>();

    private static final int MIN_STARS = 2;
    private static final int MAX_STARS = 9; // Số lượng sao tối đa
    private static final long DISPLAY_DURATION_MS = 1500; // Thời gian hiển thị sao (1.5 giây)

    public DemSaoNhanhGameFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dem_sao_nhanh_game, container, false);

        ivBackFromGame = view.findViewById(R.id.ivBackFromGame);
        tvGameTitle = view.findViewById(R.id.tvGameTitle);
        tvScoreValue = view.findViewById(R.id.tvScoreValue);
        starDisplayArea = view.findViewById(R.id.star_display_area);
        btnAnswer1 = view.findViewById(R.id.btnAnswer1);
        btnAnswer2 = view.findViewById(R.id.btnAnswer2);
        btnAnswer3 = view.findViewById(R.id.btnAnswer3);

        if (tvGameTitle != null) {
            tvGameTitle.setText("Game: Đếm Sao Nhanh");
        }
        updateScoreDisplay();

        if (ivBackFromGame != null) {
            ivBackFromGame.setOnClickListener(v -> {
                if (getActivity() != null) {
                    getActivity().getSupportFragmentManager().popBackStack();
                }
            });
        }

        btnAnswer1.setOnClickListener(v -> checkAnswer(Integer.parseInt(btnAnswer1.getText().toString())));
        btnAnswer2.setOnClickListener(v -> checkAnswer(Integer.parseInt(btnAnswer2.getText().toString())));
        btnAnswer3.setOnClickListener(v -> checkAnswer(Integer.parseInt(btnAnswer3.getText().toString())));

        // Bắt đầu vòng chơi đầu tiên
        // Đợi FrameLayout sẵn sàng để lấy kích thước
        starDisplayArea.post(this::startNewRound);


        return view;
    }

    private void startNewRound() {
        clearStars();
        disableAnswerButtons();

        correctAnswer = random.nextInt(MAX_STARS - MIN_STARS + 1) + MIN_STARS;
        displayStars(correctAnswer);

        // Ẩn sao sau một khoảng thời gian và hiển thị đáp án
        handler.postDelayed(() -> {
            clearStars(); // Ẩn sao
            populateAnswerButtons();
            enableAnswerButtons();
        }, DISPLAY_DURATION_MS);
    }

    private void displayStars(int numberOfStars) {
        if (starDisplayArea.getWidth() == 0 || starDisplayArea.getHeight() == 0) {
            // Nếu khu vực hiển thị chưa có kích thước, thử lại sau một chút
            starDisplayArea.post(() -> displayStars(numberOfStars));
            return;
        }

        for (int i = 0; i < numberOfStars; i++) {
            ImageView starView = new ImageView(getContext());
            starView.setImageResource(R.drawable.ic_star);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );

            // Đảm bảo sao không bị che khuất hoặc ra ngoài lề quá nhiều
            int starSize = getResources().getDimensionPixelSize(R.dimen.star_size); // Định nghĩa star_size trong dimens.xml, ví dụ 48dp
            int maxWidth = starDisplayArea.getWidth() - starSize;
            int maxHeight = starDisplayArea.getHeight() - starSize;

            if (maxWidth <=0 || maxHeight <=0) { // Nếu khu vực quá nhỏ, đặt ở giữa
                params.leftMargin = starDisplayArea.getWidth() / 2 - starSize / 2;
                params.topMargin = starDisplayArea.getHeight() / 2 - starSize / 2;
            } else {
                params.leftMargin = random.nextInt(maxWidth);
                params.topMargin = random.nextInt(maxHeight);
            }

            starView.setLayoutParams(params);
            starDisplayArea.addView(starView);
            displayedStars.add(starView);
        }
    }

    private void clearStars() {
        for (ImageView starView : displayedStars) {
            starDisplayArea.removeView(starView);
        }
        displayedStars.clear();
    }

    private void populateAnswerButtons() {
        List<Integer> answers = new ArrayList<>();
        answers.add(correctAnswer);

        // Tạo 2 đáp án sai
        while (answers.size() < 3) {
            int wrongAnswerOffset = random.nextInt(3) + 1; // Sai lệch 1, 2, hoặc 3
            int wrongAnswer;
            if (random.nextBoolean()) { // Ngẫu nhiên cộng hoặc trừ
                wrongAnswer = correctAnswer + wrongAnswerOffset;
            } else {
                wrongAnswer = correctAnswer - wrongAnswerOffset;
            }

            // Đảm bảo đáp án sai khác đáp án đúng và không âm, và không trùng nhau
            if (wrongAnswer > 0 && wrongAnswer != correctAnswer && !answers.contains(wrongAnswer)) {
                answers.add(wrongAnswer);
            } else if (correctAnswer > 3) { // Nếu đáp án đúng lớn, thử tạo số nhỏ hơn hẳn
                if (correctAnswer - wrongAnswerOffset -1 > 0 && !answers.contains(correctAnswer - wrongAnswerOffset -1))
                    answers.add(correctAnswer - wrongAnswerOffset -1);
            } else { // Nếu đáp án đúng nhỏ, thử tạo số lớn hơn hẳn
                if (!answers.contains(correctAnswer + wrongAnswerOffset +1))
                    answers.add(correctAnswer + wrongAnswerOffset +1);
            }
            // Xử lý trường hợp đặc biệt để luôn có 3 đáp án khác nhau
            if (answers.size() < 3 && !answers.contains(correctAnswer + answers.size() +1 )) answers.add(correctAnswer + answers.size() +1);
            if (answers.size() < 3 && correctAnswer - answers.size() -1 > 0 && !answers.contains(correctAnswer - answers.size() -1 )) answers.add(correctAnswer - answers.size() -1);

        }


        Collections.shuffle(answers); // Xáo trộn các đáp án

        if (answers.size() >= 3) {
            btnAnswer1.setText(String.valueOf(answers.get(0)));
            btnAnswer2.setText(String.valueOf(answers.get(1)));
            btnAnswer3.setText(String.valueOf(answers.get(2)));
        } else {
            // Xử lý trường hợp không tạo đủ 3 đáp án khác nhau (hiếm khi xảy ra với logic trên)
            // Hoặc đơn giản là đặt các giá trị mặc định và đảm bảo một trong số đó là đúng
            btnAnswer1.setText(String.valueOf(correctAnswer));
            btnAnswer2.setText(String.valueOf(correctAnswer + 1));
            btnAnswer3.setText(String.valueOf(correctAnswer + 2 > 0 ? correctAnswer + 2 : 1));
        }
    }

    private void checkAnswer(int selectedAnswer) {
        disableAnswerButtons(); // Vô hiệu hóa nút ngay khi chọn
        if (selectedAnswer == correctAnswer) {
            currentScore++;
            Toast.makeText(getActivity(), "Đúng rồi!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Sai rồi! Đáp án là " + correctAnswer, Toast.LENGTH_SHORT).show();
            // Có thể trừ điểm hoặc không
        }
        updateScoreDisplay();

        // Chờ một chút trước khi bắt đầu vòng mới
        handler.postDelayed(this::startNewRound, 1000); // 1 giây sau bắt đầu vòng mới
    }

    private void updateScoreDisplay() {
        if (tvScoreValue != null) {
            tvScoreValue.setText(String.valueOf(currentScore));
        }
    }

    private void disableAnswerButtons() {
        btnAnswer1.setEnabled(false);
        btnAnswer2.setEnabled(false);
        btnAnswer3.setEnabled(false);
    }

    private void enableAnswerButtons() {
        btnAnswer1.setEnabled(true);
        btnAnswer2.setEnabled(true);
        btnAnswer3.setEnabled(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Dọn dẹp handler để tránh memory leak
        handler.removeCallbacksAndMessages(null);
    }
}
