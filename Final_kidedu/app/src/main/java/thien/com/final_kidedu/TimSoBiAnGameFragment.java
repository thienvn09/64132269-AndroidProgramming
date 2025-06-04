package thien.com.final_kidedu;

import android.graphics.Color;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class TimSoBiAnGameFragment extends Fragment {

    private ImageView ivBackFromFindNumberGame;
    private TextView  tvFindNumberScoreValue;
    private LinearLayout llNumberSequence;
    private MaterialButton btnFindNumberAnswer1, btnFindNumberAnswer2, btnFindNumberAnswer3;

    private int currentScore = 0;
    private int correctAnswer;
    private final Random random = new Random();
    private final Handler handler = new Handler(Looper.getMainLooper());

    private static final int SEQUENCE_LENGTH = 5; // Độ dài của dãy số
    private static final int MAX_START_NUMBER = 10; // Số bắt đầu tối đa
    private static final int MAX_DIFFERENCE = 5;   // Độ chênh lệch tối đa giữa các số


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tim_so_bi_an_game, container, false);

        ivBackFromFindNumberGame = view.findViewById(R.id.ivBackFromFindNumberGame);

        tvFindNumberScoreValue = view.findViewById(R.id.tvFindNumberScoreValue);
        llNumberSequence = view.findViewById(R.id.llNumberSequence);
        btnFindNumberAnswer1 = view.findViewById(R.id.btnFindNumberAnswer1);
        btnFindNumberAnswer2 = view.findViewById(R.id.btnFindNumberAnswer2);
        btnFindNumberAnswer3 = view.findViewById(R.id.btnFindNumberAnswer3);

        updateScoreDisplay();

        if (ivBackFromFindNumberGame != null) {
            ivBackFromFindNumberGame.setOnClickListener(v -> {
                if (getActivity() != null) {
                    getActivity().getSupportFragmentManager().popBackStack();
                }
            });
        }

        btnFindNumberAnswer1.setOnClickListener(v -> checkAnswer(Integer.parseInt(btnFindNumberAnswer1.getText().toString())));
        btnFindNumberAnswer2.setOnClickListener(v -> checkAnswer(Integer.parseInt(btnFindNumberAnswer2.getText().toString())));
        btnFindNumberAnswer3.setOnClickListener(v -> checkAnswer(Integer.parseInt(btnFindNumberAnswer3.getText().toString())));

        startNewRound();

        return view;
    }

    private void startNewRound() {
        disableAnswerButtons();
        generateAndDisplaySequence();
        populateAnswerButtons();
        enableAnswerButtons();
    }

    private void generateAndDisplaySequence() {
        llNumberSequence.removeAllViews(); // Xóa dãy số cũ

        int startNumber = random.nextInt(MAX_START_NUMBER) + 1;
        int difference = random.nextInt(MAX_DIFFERENCE) + 1; // Chênh lệch từ 1 đến MAX_DIFFERENCE
        boolean isIncreasing = random.nextBoolean(); // Dãy tăng hoặc giảm

        if (!isIncreasing && startNumber - (SEQUENCE_LENGTH - 1) * difference <= 0) {
            // Nếu là dãy giảm và có khả năng số bị âm/0, thì chuyển thành dãy tăng
            // hoặc điều chỉnh startNumber/difference để đảm bảo dương
            isIncreasing = true;
            if (startNumber < (SEQUENCE_LENGTH -1) * difference){
                startNumber = (SEQUENCE_LENGTH -1) * difference + random.nextInt(3)+1;
            }
        }


        int missingPosition = random.nextInt(SEQUENCE_LENGTH); // Vị trí số bị thiếu (0 đến SEQUENCE_LENGTH-1)
        List<String> sequenceDisplay = new ArrayList<>();

        for (int i = 0; i < SEQUENCE_LENGTH; i++) {
            int currentValue;
            if (isIncreasing) {
                currentValue = startNumber + (i * difference);
            } else {
                currentValue = startNumber - (i * difference);
            }

            if (i == missingPosition) {
                correctAnswer = currentValue;
                sequenceDisplay.add("?");
            } else {
                sequenceDisplay.add(String.valueOf(currentValue));
            }
        }

        // Hiển thị dãy số lên UI
        for (int i = 0; i < sequenceDisplay.size(); i++) {
            TextView numberView = new TextView(getContext());
            numberView.setText(sequenceDisplay.get(i));
            numberView.setTextSize(32); // sp
            numberView.setPadding(16, 8, 16, 8); // Thêm padding
            numberView.setGravity(Gravity.CENTER);

            if (sequenceDisplay.get(i).equals("?")) {
                numberView.setTextColor(ContextCompat.getColor(getContext(), R.color.mau_do)); // Màu đỏ cho dấu ?

                numberView.setTypeface(null, android.graphics.Typeface.BOLD);
            } else {
                numberView.setTextColor(Color.BLACK);
            }
            llNumberSequence.addView(numberView);
            // Thêm dấu phẩy giữa các số, trừ số cuối cùng
            if (i < sequenceDisplay.size() - 1) {
                TextView commaView = new TextView(getContext());
                commaView.setText(",");
                commaView.setTextSize(32); // sp
                commaView.setPadding(0, 8, 16, 8);
                commaView.setTextColor(Color.BLACK);
                llNumberSequence.addView(commaView);
            }
        }
    }


    private void populateAnswerButtons() {
        List<Integer> answers = new ArrayList<>();
        answers.add(correctAnswer);

        while (answers.size() < 3) {
            int wrongAnswer;
            do {
                int offset = random.nextInt(MAX_DIFFERENCE * 2) + 1; // Sai lệch lớn hơn chút
                if (random.nextBoolean()) {
                    wrongAnswer = correctAnswer + offset;
                } else {
                    wrongAnswer = correctAnswer - offset;
                }
            } while (wrongAnswer <= 0 || answers.contains(wrongAnswer));
            answers.add(wrongAnswer);
        }

        Collections.shuffle(answers);

        if (answers.size() >= 3) {
            btnFindNumberAnswer1.setText(String.valueOf(answers.get(0)));
            btnFindNumberAnswer2.setText(String.valueOf(answers.get(1)));
            btnFindNumberAnswer3.setText(String.valueOf(answers.get(2)));
        } else {
            // Fallback (hiếm khi xảy ra)
            btnFindNumberAnswer1.setText(String.valueOf(correctAnswer));
            btnFindNumberAnswer2.setText(String.valueOf(correctAnswer + 1));
            btnFindNumberAnswer3.setText(String.valueOf(correctAnswer - 1 > 0 ? correctAnswer -1 : correctAnswer + 2 ));
        }
    }

    private void checkAnswer(int selectedAnswer) {
        disableAnswerButtons();
        if (selectedAnswer == correctAnswer) {
            currentScore++;
            Toast.makeText(getActivity(), "Chính xác!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Sai rồi! Số đúng là " + correctAnswer, Toast.LENGTH_SHORT).show();
        }
        updateScoreDisplay();
        handler.postDelayed(this::startNewRound, 1500); // Chờ 1.5 giây rồi bắt đầu vòng mới
    }

    private void updateScoreDisplay() {
        if (tvFindNumberScoreValue != null) {
            tvFindNumberScoreValue.setText(String.valueOf(currentScore));
        }
    }

    private void disableAnswerButtons() {
        btnFindNumberAnswer1.setEnabled(false);
        btnFindNumberAnswer2.setEnabled(false);
        btnFindNumberAnswer3.setEnabled(false);
    }

    private void enableAnswerButtons() {
        btnFindNumberAnswer1.setEnabled(true);
        btnFindNumberAnswer2.setEnabled(true);
        btnFindNumberAnswer3.setEnabled(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        handler.removeCallbacksAndMessages(null);
    }
}
