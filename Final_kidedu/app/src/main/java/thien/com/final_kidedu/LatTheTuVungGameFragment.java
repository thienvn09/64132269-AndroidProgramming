package thien.com.final_kidedu;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.media.MediaPlayer;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.os.Handler; // Mặc dù không dùng trực tiếp trong code này, có thể cần cho các game khác
import android.os.Looper;  // Mặc dù không dùng trực tiếp trong code này
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class LatTheTuVungGameFragment extends Fragment {

    private ImageView ivBackFromFlashcardGame;
    private TextView tvFlashcardGameTitle, tvFlashcardScoreValue;
    // FrameLayout flashcardContainer; // Không cần tham chiếu trực tiếp nếu chỉ xử lý cardFront, cardBack
    private MaterialCardView cardFront, cardBack;
    private ImageView ivFlashcardImage;
    private TextView tvFlashcardHint, tvFlashcardEnglishWord, tvFlashcardPronunciation;
    private ImageButton btnPlaySound;
    private Button btnNextFlashcard;

    private AnimatorSet frontAnim, backAnim;
    private boolean isFrontVisible = true;

    private List<FlashcardItem> flashcardList;
    private int currentCardIndex = 0;
    private int learnedCount = 0; // Đếm số thẻ đã "qua"

    private TextToSpeech textToSpeech;
    private boolean ttsInitialized = false;


    // Lớp nội để chứa thông tin mỗi thẻ
    private static class FlashcardItem {
        String vietnameseHint;    // Gợi ý/từ tiếng Việt
        String englishWord;       // Từ tiếng Anh
        String pronunciation;     // Phiên âm (tùy chọn)
        int imageResId;           // ID của drawable hình ảnh (mặt trước)
        int soundResId;           // Tùy chọn: ID của file âm thanh trong res/raw cho từ tiếng Anh

        FlashcardItem(String vietnameseHint, String englishWord, String pronunciation, int imageResId, int soundResId) {
            this.vietnameseHint = vietnameseHint;
            this.englishWord = englishWord;
            this.pronunciation = pronunciation;
            this.imageResId = imageResId;
            this.soundResId = soundResId;
        }
    }

    public LatTheTuVungGameFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lat_the_tu_vung_game, container, false);

        // Ánh xạ Views
        ivBackFromFlashcardGame = view.findViewById(R.id.ivBackFromFlashcardGame);
        tvFlashcardGameTitle = view.findViewById(R.id.tvFlashcardGameTitle);
        tvFlashcardScoreValue = view.findViewById(R.id.tvFlashcardScoreValue);
        // flashcardContainer = view.findViewById(R.id.flashcard_container); // Không cần nếu chỉ tương tác với cardFront/Back
        cardFront = view.findViewById(R.id.card_front);
        cardBack = view.findViewById(R.id.card_back);
        ivFlashcardImage = view.findViewById(R.id.ivFlashcardImage);
        tvFlashcardHint = view.findViewById(R.id.tvFlashcardHint);
        tvFlashcardEnglishWord = view.findViewById(R.id.tvFlashcardEnglishWord);
        tvFlashcardPronunciation = view.findViewById(R.id.tvFlashcardPronunciation);
        btnPlaySound = view.findViewById(R.id.btnPlaySound);
        btnNextFlashcard = view.findViewById(R.id.btnNextFlashcard);

        // Khởi tạo TextToSpeech
        initializeTextToSpeech();

        // Chuẩn bị dữ liệu thẻ
        prepareFlashcards();

        // Load animation lật thẻ
        if (getContext() != null) {
            float scale = getContext().getResources().getDisplayMetrics().density;
            cardFront.setCameraDistance(8000 * scale);
            cardBack.setCameraDistance(8000 * scale);

            try {
                frontAnim = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), R.animator.card_flip_left_out);
                backAnim = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), R.animator.card_flip_left_in);
            } catch (Exception e) {
                Log.e("FlashcardAnim", "Lỗi load animator: " + e.getMessage());
                Toast.makeText(getContext(), "Lỗi hiệu ứng lật thẻ", Toast.LENGTH_SHORT).show();
            }
        }

        // Xử lý sự kiện click
        if (ivBackFromFlashcardGame != null) {
            ivBackFromFlashcardGame.setOnClickListener(v -> {
                if (getActivity() != null) {
                    getActivity().getSupportFragmentManager().popBackStack();
                }
            });
        }

        View.OnClickListener flipClickListener = v -> flipCard();
        cardFront.setOnClickListener(flipClickListener);
        cardBack.setOnClickListener(flipClickListener);

        btnPlaySound.setOnClickListener(v -> playCurrentWordSound());
        btnNextFlashcard.setOnClickListener(v -> showNextCard());

        // Hiển thị thẻ đầu tiên
        if (flashcardList != null && !flashcardList.isEmpty()) {
            displayCard(currentCardIndex);
        } else {
            Toast.makeText(getActivity(), "Không có thẻ nào để học!", Toast.LENGTH_LONG).show();
            // Vô hiệu hóa nút nếu không có thẻ
            btnNextFlashcard.setEnabled(false);
            cardFront.setClickable(false);
            cardBack.setClickable(false);
        }
        updateScoreDisplay();

        return view;
    }

    private void initializeTextToSpeech() {
        textToSpeech = new TextToSpeech(getContext(), status -> {
            if (status == TextToSpeech.SUCCESS) {
                int result = textToSpeech.setLanguage(Locale.US);
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.e("TTS", "Ngôn ngữ Tiếng Anh (Mỹ) không được hỗ trợ cho TTS.");
                    ttsInitialized = false;
                } else {
                    ttsInitialized = true;
                }
            } else {
                Log.e("TTS", "Khởi tạo TextToSpeech thất bại, status: " + status);
                ttsInitialized = false;
            }
        });
    }

    private void prepareFlashcards() {
        flashcardList = new ArrayList<>();
        // Thêm các từ vựng vào đây
        flashcardList.add(new FlashcardItem("Quả Táo", "Apple", "/ˈæp.əl/", R.drawable.ic_sample_apple, 0)); // 0 nếu dùng TTS
        flashcardList.add(new FlashcardItem("Con Chó", "Dog", "/dɒɡ/", R.drawable.ic_sample_dog, 0));
        flashcardList.add(new FlashcardItem("Màu Đỏ", "Red", "/rɛd/", R.drawable.ic_sample_red_color, 0));
        flashcardList.add(new FlashcardItem("Quyển Sách", "Book", "/bʊk/", R.drawable.ic_book, 0)); // Thêm icon ic_book
        flashcardList.add(new FlashcardItem("Mèo Con", "Cat", "/kæt/", R.drawable.ic_cat, 0)); // Thêm icon ic_cat

        if (!flashcardList.isEmpty()) {
            Collections.shuffle(flashcardList); // Xáo trộn thứ tự thẻ
        }
    }

    private void displayCard(int index) {
        if (flashcardList == null || index < 0 || index >= flashcardList.size()) {
            Log.e("DisplayCard", "Index không hợp lệ hoặc flashcardList null/rỗng.");
            return;
        }

        FlashcardItem currentItem = flashcardList.get(index);

        // Mặt trước
        tvFlashcardHint.setText(currentItem.vietnameseHint);
        if (currentItem.imageResId != 0) {
            try {
                ivFlashcardImage.setImageResource(currentItem.imageResId);
                ivFlashcardImage.setVisibility(View.VISIBLE);
            } catch (Exception e) {
                Log.e("DisplayCard", "Lỗi load hình ảnh mặt trước: " + e.getMessage());
                ivFlashcardImage.setVisibility(View.GONE); // Ẩn nếu lỗi
            }
        } else {
            ivFlashcardImage.setVisibility(View.GONE);
        }

        // Mặt sau
        tvFlashcardEnglishWord.setText(currentItem.englishWord);
        if (currentItem.pronunciation != null && !currentItem.pronunciation.isEmpty()) {
            tvFlashcardPronunciation.setText(currentItem.pronunciation);
            tvFlashcardPronunciation.setVisibility(View.VISIBLE);
        } else {
            tvFlashcardPronunciation.setVisibility(View.GONE);
        }

        // Đảm bảo thẻ luôn bắt đầu ở mặt trước khi hiển thị thẻ mới
        flipCardInstantlyToFront();
    }

    private void flipCard() {
        if (!isAdded() || frontAnim == null || backAnim == null) {
            Log.w("FlipCard", "Fragment không được gắn hoặc animator null.");
            return;
        }

        if (isFrontVisible) {
            frontAnim.setTarget(cardFront);
            backAnim.setTarget(cardBack);
            cardBack.setVisibility(View.VISIBLE); // Hiển thị mặt sau ngay trước khi animation
            frontAnim.start();
            backAnim.start();
            isFrontVisible = false;
        } else {
            // Khi lật từ mặt sau về mặt trước
            // Animation 'out' cho mặt sau, 'in' cho mặt trước
            frontAnim.setTarget(cardBack); // cardBack sẽ lật ra (out)
            backAnim.setTarget(cardFront); // cardFront sẽ lật vào (in)
            cardFront.setVisibility(View.VISIBLE); // Hiển thị mặt trước ngay trước khi animation
            frontAnim.start();
            backAnim.start();
            isFrontVisible = true;
        }
    }

    private void flipCardInstantlyToFront() {
        // Đặt lại trạng thái hiển thị mà không có animation
        // Đảm bảo các thuộc tính này không làm ảnh hưởng đến animation sau đó
        // Bằng cách đặt giá trị ban đầu cho animation
        cardFront.setAlpha(1f);
        cardFront.setRotationY(0f);
        cardFront.setVisibility(View.VISIBLE);

        cardBack.setAlpha(1f); // Sẽ được ẩn bởi animation 'in' nếu cần
        cardBack.setRotationY(0f); // Hoặc giá trị ban đầu của animation 'in' nếu nó bắt đầu từ 180
        cardBack.setVisibility(View.GONE);
        isFrontVisible = true;
    }

    private void playCurrentWordSound() {
        if (flashcardList == null || currentCardIndex < 0 || currentCardIndex >= flashcardList.size()) return;
        FlashcardItem currentItem = flashcardList.get(currentCardIndex);

        if (ttsInitialized && currentItem.englishWord != null && !currentItem.englishWord.isEmpty()) {
            textToSpeech.speak(currentItem.englishWord, TextToSpeech.QUEUE_FLUSH, null, currentItem.englishWord + "_utteranceId");
        } else if (currentItem.soundResId != 0 && getContext() != null) {
            try {
                MediaPlayer mediaPlayer = MediaPlayer.create(getContext(), currentItem.soundResId);
                if (mediaPlayer != null) {
                    mediaPlayer.setOnCompletionListener(MediaPlayer::release);
                    mediaPlayer.start();
                } else {
                    Log.e("FlashcardSound", "MediaPlayer.create trả về null cho resId: " + currentItem.soundResId);
                    Toast.makeText(getActivity(), "Lỗi file âm thanh", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Log.e("FlashcardSound", "Lỗi phát âm thanh", e);
                Toast.makeText(getActivity(), "Không thể phát âm thanh", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Toast.makeText(getActivity(), "Không có âm thanh cho từ này", Toast.LENGTH_SHORT).show();
            Log.i("FlashcardSound", "Không có TTS hoặc soundResId cho: " + currentItem.englishWord);
        }
    }

    private void showNextCard() {
        if (flashcardList == null || flashcardList.isEmpty()) return;

        // Đánh dấu thẻ hiện tại đã được "học" (ít nhất là đã xem)
        // Chỉ tăng learnedCount nếu thẻ hiện tại chưa được tính là đã học
        // và currentCardIndex hợp lệ
        if (currentCardIndex < flashcardList.size() && currentCardIndex >= learnedCount) {
            learnedCount = currentCardIndex + 1;
        }


        currentCardIndex++;
        if (currentCardIndex >= flashcardList.size()) {
            currentCardIndex = 0; // Quay lại thẻ đầu tiên
            learnedCount = 0; // Reset số thẻ đã học nếu quay vòng
            Toast.makeText(getActivity(), "Đã xem hết các thẻ! Bắt đầu lại từ đầu.", Toast.LENGTH_SHORT).show();
        }
        displayCard(currentCardIndex);
        updateScoreDisplay();
    }

    private void updateScoreDisplay() {
        if (tvFlashcardScoreValue != null && flashcardList != null) {
            tvFlashcardScoreValue.setText(learnedCount + "/" + flashcardList.size());
        } else if (tvFlashcardScoreValue != null) {
            tvFlashcardScoreValue.setText("0/0");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Dọn dẹp TextToSpeech
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
            ttsInitialized = false;
            textToSpeech = null; // Giúp GC dọn dẹp
        }
        Log.d("LatTheFragment", "onDestroyView called");
    }

    @Override
    public void onPause() {
        super.onPause();
        // Cân nhắc dừng TextToSpeech nếu đang nói khi Fragment pause
        if (textToSpeech != null) {
            textToSpeech.stop();
        }
        Log.d("LatTheFragment", "onPause called");
    }

    @Override
    public void onResume() {
        super.onResume();
        // Nếu TextToSpeech chưa được khởi tạo (ví dụ sau khi quay lại từ màn hình khác)
        // thì thử khởi tạo lại.
        if (textToSpeech == null) {
            initializeTextToSpeech();
        }
        Log.d("LatTheFragment", "onResume called");
    }
}
