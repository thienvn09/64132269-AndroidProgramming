package thien.com.final_kidedu;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.os.Handler;
import android.os.Looper;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GhepChuThanhTuGameFragment extends Fragment {

    private ImageView ivBackFromWordJumbleGame, ivWordJumbleImageClue;
    private TextView tvWordJumbleGameTitle, tvWordJumbleScoreValue, tvFormedWord;
    private FlexboxLayout flexboxJumbledLetters;
    private Button btnClearWord, btnCheckWordJumble;

    private int currentScore = 0;
    private final Random random = new Random();
    private final Handler handler = new Handler(Looper.getMainLooper());

    private List<WordData> wordDataList;
    private WordData currentWordData;
    private List<Button> letterButtons; // Lưu trữ các nút chữ cái để có thể kích hoạt lại
    private StringBuilder formedWordBuilder;
    // Lớp nội để chứa thông tin mỗi từ
    private static class WordData {
        String englishWord;
        int imageResId;

        WordData(String englishWord, int imageResId) {
            this.englishWord = englishWord;
            this.imageResId = imageResId;
        }
    }

    public GhepChuThanhTuGameFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ghep_chu_thanh_tu_game, container, false);

        ivBackFromWordJumbleGame = view.findViewById(R.id.ivBackFromWordJumbleGame);
        tvWordJumbleGameTitle = view.findViewById(R.id.tvWordJumbleGameTitle);
        tvWordJumbleScoreValue = view.findViewById(R.id.tvWordJumbleScoreValue);
        ivWordJumbleImageClue = view.findViewById(R.id.ivWordJumbleImageClue);
        tvFormedWord = view.findViewById(R.id.tvFormedWord);
        flexboxJumbledLetters = view.findViewById(R.id.flexboxJumbledLetters);
        btnClearWord = view.findViewById(R.id.btnClearWord);
        btnCheckWordJumble = view.findViewById(R.id.btnCheckWordJumble);

        letterButtons = new ArrayList<>();
        formedWordBuilder = new StringBuilder();

        prepareWordData();
        updateScoreDisplay();

        if (ivBackFromWordJumbleGame != null) {
            ivBackFromWordJumbleGame.setOnClickListener(v -> {
                if (getActivity() != null) {
                    getActivity().getSupportFragmentManager().popBackStack();
                }
            });
        }

        btnClearWord.setOnClickListener(v -> clearFormedWord());
        btnCheckWordJumble.setOnClickListener(v -> checkWord());

        if (wordDataList != null && !wordDataList.isEmpty()) {
            startNewRound();
        } else {
            Toast.makeText(getActivity(), "Không có từ nào để chơi!", Toast.LENGTH_LONG).show();
        }

        return view;
    }

    private void prepareWordData() {
        wordDataList = new ArrayList<>();
        // Thêm các WordData vào đây
        wordDataList.add(new WordData("APPLE", R.mipmap.img_tao));
        wordDataList.add(new WordData("DOG", R.mipmap.img_dog));
        wordDataList.add(new WordData("CAT", R.mipmap.img_cat));
        wordDataList.add(new WordData("SUN", R.mipmap.img_sun));
        wordDataList.add(new WordData("BOOK", R.mipmap.img_book));
        // ... Thêm các từ khác ...
        Collections.shuffle(wordDataList);
    }

    private void startNewRound() {
        if (wordDataList.isEmpty()) {
            Toast.makeText(getActivity(), "Đã hoàn thành tất cả các từ!", Toast.LENGTH_LONG).show();
            if (getActivity() != null) {
                 getActivity().getSupportFragmentManager().popBackStack();
            }
            return;
        }

        clearFormedWord(); // Xóa từ đã ghép ở vòng trước
        currentWordData = wordDataList.remove(0); // Lấy và xóa từ đầu tiên khỏi danh sách

        ivWordJumbleImageClue.setImageResource(currentWordData.imageResId);

        // Xáo trộn chữ cái và hiển thị
        String word = currentWordData.englishWord.toUpperCase();
        List<Character> characters = new ArrayList<>();
        for (char c : word.toCharArray()) {
            characters.add(c);
        }
        Collections.shuffle(characters);

        flexboxJumbledLetters.removeAllViews();
        letterButtons.clear();

        for (Character c : characters) {
            Button letterButton = new Button(getContext());
            letterButton.setText(String.valueOf(c));
            letterButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
            letterButton.setTypeface(null, Typeface.BOLD);
            // Style cho button (ví dụ: bo góc, màu sắc)
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(8, 8, 8, 8);
            letterButton.setLayoutParams(params);
            letterButton.setBackgroundResource(R.drawable.letter_button_background); // Tạo drawable này
            // Ví dụ: shape với bo góc và màu nền

            letterButton.setOnClickListener(v -> {
                Button clickedButton = (Button) v;
                formedWordBuilder.append(clickedButton.getText().toString());
                tvFormedWord.setText(formedWordBuilder.toString());
                clickedButton.setEnabled(false); // Vô hiệu hóa nút đã chọn
                clickedButton.setAlpha(0.5f); // Làm mờ nút đã chọn
            });
            flexboxJumbledLetters.addView(letterButton);
            letterButtons.add(letterButton);
        }
    }

    private void clearFormedWord() {
        formedWordBuilder.setLength(0);
        tvFormedWord.setText("");
        for (Button btn : letterButtons) {
            btn.setEnabled(true);
            btn.setAlpha(1.0f);
        }
    }

    private void checkWord() {
        String formed = formedWordBuilder.toString();
        String correct = currentWordData.englishWord.toUpperCase();

        if (formed.equals(correct)) {
            currentScore++;
            Toast.makeText(getActivity(), "Chính xác! Giỏi quá!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Chưa đúng rồi, thử lại nhé!", Toast.LENGTH_SHORT).show();
        }
        updateScoreDisplay();

        // Chờ một chút rồi bắt đầu vòng mới
        handler.postDelayed(this::startNewRound, 1500);
    }

    private void updateScoreDisplay() {
        if (tvWordJumbleScoreValue != null) {
            tvWordJumbleScoreValue.setText(String.valueOf(currentScore));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        handler.removeCallbacksAndMessages(null);
    }
}
