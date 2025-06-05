package thien.com.final_kidedu;

import android.media.MediaPlayer;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.os.Handler;
import android.os.Looper;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class NgheVaChamGameFragment extends Fragment {

    private ImageView ivBackFromListenTapGame;
    private TextView tvListenTapGameTitle, tvListenTapScoreValue;
    private ImageButton btnPlayWordSound;
    private ImageView ivListenChoice1, ivListenChoice2, ivListenChoice3, ivListenChoice4;
    private List<ImageView> choiceImageViews;

    private int currentScore = 0;
    private final Random random = new Random();
    private final Handler handler = new Handler(Looper.getMainLooper());

    private List<WordItem> wordList;
    private WordItem currentWordToGuess;
    private TextToSpeech textToSpeech;
    private boolean ttsInitialized = false;

    // Lớp nội để chứa thông tin mỗi từ
    private static class WordItem {
        String englishWord;
        int imageResId; // ID của drawable hình ảnh
        int soundResId; // Tùy chọn: ID của file âm thanh trong res/raw

        WordItem(String englishWord, int imageResId, int soundResId) {
            this.englishWord = englishWord;
            this.imageResId = imageResId;
            this.soundResId = soundResId;
        }
    }

    public NgheVaChamGameFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nghe_va_cham_game, container, false);

        ivBackFromListenTapGame = view.findViewById(R.id.ivBackFromListenTapGame);
        tvListenTapGameTitle = view.findViewById(R.id.tvListenTapGameTitle);
        tvListenTapScoreValue = view.findViewById(R.id.tvListenTapScoreValue);
        btnPlayWordSound = view.findViewById(R.id.btnPlayWordSound);
        ivListenChoice1 = view.findViewById(R.id.ivListenChoice1);
        ivListenChoice2 = view.findViewById(R.id.ivListenChoice2);
        ivListenChoice3 = view.findViewById(R.id.ivListenChoice3);
        ivListenChoice4 = view.findViewById(R.id.ivListenChoice4);

        choiceImageViews = new ArrayList<>();
        choiceImageViews.add(ivListenChoice1);
        choiceImageViews.add(ivListenChoice2);
        choiceImageViews.add(ivListenChoice3);
        choiceImageViews.add(ivListenChoice4);

        initializeTextToSpeech();
        prepareWordList();
        updateScoreDisplay();

        if (ivBackFromListenTapGame != null) {
            ivBackFromListenTapGame.setOnClickListener(v -> {
                if (getActivity() != null) {
                    getActivity().getSupportFragmentManager().popBackStack();
                }
            });
        }

        btnPlayWordSound.setOnClickListener(v -> playCurrentWordSound());

        for (ImageView imageView : choiceImageViews) {
            imageView.setOnClickListener(this::onImageChoiceClicked);
        }

        if (!wordList.isEmpty()) {
            startNewRound();
        } else {
            Toast.makeText(getActivity(), "Không có từ nào để học!", Toast.LENGTH_LONG).show();
            btnPlayWordSound.setEnabled(false);
        }

        return view;
    }

    private void initializeTextToSpeech() {
        textToSpeech = new TextToSpeech(getContext(), status -> {
            if (status == TextToSpeech.SUCCESS) {
                int result = textToSpeech.setLanguage(Locale.US);
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.e("TTS", "Ngôn ngữ không được hỗ trợ");
                    ttsInitialized = false;
                } else {
                    ttsInitialized = true;
                    // Phát âm từ đầu tiên sau khi TTS sẵn sàng
                    if(currentWordToGuess != null){
                        playCurrentWordSound();
                    }
                }
            } else {
                Log.e("TTS", "Khởi tạo TextToSpeech thất bại");
                ttsInitialized = false;
            }
        });
    }

    private void prepareWordList() {
        wordList = new ArrayList<>();
        // Thêm các WordItem
        wordList.add(new WordItem("dog", R.mipmap.img_dog, R.raw.dog_sound));
        wordList.add(new WordItem("cat", R.mipmap.img_cat, R.raw.cat_sound));
        wordList.add(new WordItem("duck", R.mipmap.img_duck, R.raw.bird_sound));
        wordList.add(new WordItem("dopphin", R.mipmap.img_caheo, R.raw.dopping_sound));
    /*    wordList.add(new WordItem("apple", R.mipmap.img_tao, R.raw.apple_sound));
        wordList.add(new WordItem("book", R.mipmap.img_book, R.raw.book_sound));
        wordList.add(new WordItem("car", R.mipmap.img_car, R.raw.car_sound)); // Cần thêm icon và sound
        wordList.add(new WordItem("sun", R.mipmap.img_sun, R.raw.sun_sound)); // Cần thêm icon và sound*/

        if (wordList.size() < 4) {
            // Cần ít nhất 4 từ để hiển thị 4 lựa chọn khác nhau
            Toast.makeText(getActivity(), "Cần thêm từ vựng cho game!", Toast.LENGTH_LONG).show();
            // Xử lý trường hợp này, ví dụ: không bắt đầu game
            return;
        }
        Collections.shuffle(wordList);
    }

    private void startNewRound() {
        if (wordList.isEmpty()) return;

        // Chọn một từ ngẫu nhiên để đoán (và đảm bảo nó khác từ trước đó nếu có thể)
        WordItem newWordToGuess;
        if(wordList.size() == 1 && currentWordToGuess != null){ // Nếu chỉ có 1 từ và đã đoán rồi thì không làm gì
            playCurrentWordSound(); // Phát lại từ đó
            return;
        }
        do {
            newWordToGuess = wordList.get(random.nextInt(wordList.size()));
        } while (wordList.size() > 1 && newWordToGuess == currentWordToGuess); // Đảm bảo từ mới khác từ cũ nếu có nhiều hơn 1 từ
        currentWordToGuess = newWordToGuess;


        // Chuẩn bị 4 lựa chọn hình ảnh, bao gồm 1 hình đúng và 3 hình sai
        List<WordItem> choices = new ArrayList<>();
        choices.add(currentWordToGuess); // Thêm từ đúng

        List<WordItem> tempList = new ArrayList<>(wordList);
        tempList.remove(currentWordToGuess); // Xóa từ đúng khỏi danh sách tạm để lấy từ sai
        Collections.shuffle(tempList);

        for (int i = 0; i < 3 && i < tempList.size(); i++) { // Lấy 3 từ sai
            choices.add(tempList.get(i));
        }

        // Nếu không đủ 4 lựa chọn (ví dụ: wordList có ít hơn 4 từ), cần xử lý thêm
        // Hiện tại, nếu wordList.size() < 4 thì đã có thông báo ở prepareWordList()

        Collections.shuffle(choices); // Xáo trộn các lựa chọn

        // Hiển thị hình ảnh lên các ImageView
        for (int i = 0; i < choiceImageViews.size() && i < choices.size(); i++) {
            choiceImageViews.get(i).setImageResource(choices.get(i).imageResId);
            choiceImageViews.get(i).setTag(choices.get(i)); // Lưu WordItem vào tag để kiểm tra
            choiceImageViews.get(i).setEnabled(true); // Kích hoạt lại nút
            choiceImageViews.get(i).setAlpha(1.0f); // Đặt lại alpha
        }
        // Nếu choices.size() < choiceImageViews.size(), ẩn các ImageView thừa
        for (int i = choices.size(); i < choiceImageViews.size(); i++) {
            choiceImageViews.get(i).setImageDrawable(null);
            choiceImageViews.get(i).setTag(null);
            choiceImageViews.get(i).setEnabled(false);
        }
        // Phát âm từ cần đoán
        playCurrentWordSound();
    }

    private void playCurrentWordSound() {
        if (currentWordToGuess == null) return;

        if (ttsInitialized && currentWordToGuess.englishWord != null && !currentWordToGuess.englishWord.isEmpty()) {
            textToSpeech.speak(currentWordToGuess.englishWord, TextToSpeech.QUEUE_FLUSH, null, currentWordToGuess.englishWord + "_utteranceId");
        } else if (currentWordToGuess.soundResId != 0 && getContext() != null) {
            try {
                MediaPlayer mediaPlayer = MediaPlayer.create(getContext(), currentWordToGuess.soundResId);
                if (mediaPlayer != null) {
                    mediaPlayer.setOnCompletionListener(MediaPlayer::release);
                    mediaPlayer.start();
                } else {
                    Log.e("ListenTapSound", "MediaPlayer.create trả về null cho resId: " + currentWordToGuess.soundResId);
                }
            } catch (Exception e) {
                Log.e("ListenTapSound", "Lỗi phát âm thanh", e);
            }
        } else {
            Log.i("ListenTapSound", "Không có TTS hoặc soundResId cho: " + currentWordToGuess.englishWord);
        }
    }

    private void onImageChoiceClicked(View view) {
        ImageView clickedImageView = (ImageView) view;
        WordItem selectedWord = (WordItem) clickedImageView.getTag();

        if (selectedWord == null || currentWordToGuess == null) return;

        // Vô hiệu hóa các lựa chọn để tránh click nhiều lần
        for(ImageView iv : choiceImageViews) {
            iv.setEnabled(false);
            iv.setAlpha(0.5f); // Làm mờ các lựa chọn
        }


        if (selectedWord.englishWord.equals(currentWordToGuess.englishWord)) {
            currentScore++;
            Toast.makeText(getActivity(), "Đúng rồi! Đó là " + currentWordToGuess.englishWord, Toast.LENGTH_SHORT).show();
            // TODO: Thêm hiệu ứng khi đúng (ví dụ: hình ảnh được chọn nhấp nháy màu xanh)
            clickedImageView.setAlpha(1.0f); // Làm nổi bật lựa chọn đúng
        } else {
            Toast.makeText(getActivity(), "Sai rồi! Từ đúng là " + currentWordToGuess.englishWord, Toast.LENGTH_SHORT).show();
            // TODO: Thêm hiệu ứng khi sai (ví dụ: hình ảnh được chọn nhấp nháy màu đỏ, và làm nổi bật hình đúng)
            // Tìm và làm nổi bật hình đúng
            for(ImageView iv : choiceImageViews) {
                WordItem item = (WordItem) iv.getTag();
                if(item != null && item.englishWord.equals(currentWordToGuess.englishWord)) {
                    iv.setAlpha(1.0f); // Làm nổi bật hình đúng
                    // Có thể thêm hiệu ứng nhấp nháy ở đây
                    break;
                }
            }
        }
        updateScoreDisplay();

        // Chờ một chút rồi bắt đầu vòng mới
        handler.postDelayed(this::startNewRound, 2000); // 2 giây sau bắt đầu vòng mới
    }

    private void updateScoreDisplay() {
        if (tvListenTapScoreValue != null) {
            tvListenTapScoreValue.setText(String.valueOf(currentScore));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
            ttsInitialized = false;
            textToSpeech = null;
        }
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (textToSpeech != null) {
            textToSpeech.stop();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (textToSpeech == null) {
            initializeTextToSpeech();
        } else if (!ttsInitialized && currentWordToGuess != null) {
            // Nếu TTS đã được tạo nhưng chưa init thành công, và có từ để phát
            // thử phát lại khi resume (nếu TTS đã sẵn sàng trong initializeTextToSpeech)
            playCurrentWordSound();
        }
    }
}
