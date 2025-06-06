package thien.com.final_kidedu;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.gridlayout.widget.GridLayout;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MatchingPairsGameFragment extends Fragment {

    private ImageView ivBackFromMatchingGame;
    private TextView tvMatchingGameTitle, tvMatchingScoreValue;
    private GridLayout gridMatchingCards;

    private int pairsFound = 0;
    private int totalPairs = 0;
    private final Handler handler = new Handler(Looper.getMainLooper());

    private List<CardItem> cardList;
    private List<View> cardViews;

    private CardItem firstFlippedCardItem = null;
    private View firstFlippedCardView = null;
    private boolean isChecking = false; // Cờ để ngăn người dùng lật thêm thẻ khi đang kiểm tra

    // Lớp nội để chứa thông tin mỗi thẻ
    private static class CardItem {
        int id;           // ID duy nhất của thẻ
        int pairId;       // ID của cặp, hai thẻ cùng một cặp sẽ có cùng pairId
        String cardText;  // Chữ trên thẻ (nếu có)
        int imageResId;   // ID hình ảnh trên thẻ (nếu có)
        boolean isFlipped = false;
        boolean isMatched = false;

        CardItem(int id, int pairId, String cardText, int imageResId) {
            this.id = id;
            this.pairId = pairId;
            this.cardText = cardText;
            this.imageResId = imageResId;
        }
    }

    public MatchingPairsGameFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_matching_pairs_game, container, false);

        ivBackFromMatchingGame = view.findViewById(R.id.ivBackFromMatchingGame);
        tvMatchingGameTitle = view.findViewById(R.id.tvMatchingGameTitle);
        tvMatchingScoreValue = view.findViewById(R.id.tvMatchingScoreValue);
        gridMatchingCards = view.findViewById(R.id.gridMatchingCards);

        if (ivBackFromMatchingGame != null) {
            ivBackFromMatchingGame.setOnClickListener(v -> {
                if (getActivity() != null) {
                    getActivity().getSupportFragmentManager().popBackStack();
                }
            });
        }

        prepareCardData();
        // Đợi GridLayout sẵn sàng để thêm các thẻ
        gridMatchingCards.post(this::createAndDisplayCards);

        return view;
    }

    private void prepareCardData() {
        cardList = new ArrayList<>();
        // Thêm 6 cặp thẻ (tổng 12 thẻ)
        // Cặp 1: Apple
        cardList.add(new CardItem(1, 1, "Apple", 0)); // Thẻ chữ
        cardList.add(new CardItem(2, 1, null, R.mipmap.img_tao)); // Thẻ hình
        // Cặp 2: Dog
        cardList.add(new CardItem(3, 2, "Dog", 0));
        cardList.add(new CardItem(4, 2, null, R.mipmap.img_dog));
        // Cặp 3: Cat
        cardList.add(new CardItem(5, 3, "Cat", 0));
        cardList.add(new CardItem(6, 3, null, R.mipmap.img_cat));
        // Cặp 4: Sun
        cardList.add(new CardItem(7, 4, "Sun", 0));
        cardList.add(new CardItem(8, 4, null, R.mipmap.img_sun));
        // Cặp 5: Book
        cardList.add(new CardItem(9, 5, "Book", 0));
        cardList.add(new CardItem(10, 5, null, R.mipmap.img_book));
        // Cặp 6: Car
        cardList.add(new CardItem(11, 6, "Car", 0));
        cardList.add(new CardItem(12, 6, null, R.mipmap.img_car));

        totalPairs = cardList.size() / 2;
        updateScoreDisplay();
        Collections.shuffle(cardList);
    }

    private void createAndDisplayCards() {
        gridMatchingCards.removeAllViews();
        cardViews = new ArrayList<>();

        for (int i = 0; i < cardList.size(); i++) {
            CardItem cardItem = cardList.get(i);
            // Sử dụng layout inflater để tạo view thẻ từ một layout XML riêng (ví dụ: R.layout.layout_matching_card)
            View cardView = getLayoutInflater().inflate(R.layout.layout_matching_card, gridMatchingCards, false);
            cardView.setTag(cardItem);

            // Set content cho mặt trước (ban đầu ẩn)
            TextView cardText = cardView.findViewById(R.id.tvCardContent);
            ImageView cardImage = cardView.findViewById(R.id.ivCardContent);

            if (cardItem.cardText != null) {
                cardText.setText(cardItem.cardText);
                cardText.setVisibility(View.VISIBLE);
                cardImage.setVisibility(View.GONE);
            } else {
                cardImage.setImageResource(cardItem.imageResId);
                cardImage.setVisibility(View.VISIBLE);
                cardText.setVisibility(View.GONE);
            }

            cardView.setOnClickListener(this::onCardClicked);

            // Thiết lập GridLayout.LayoutParams
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 0;
            params.height = 0;
            params.setMargins(8, 8, 8, 8);
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1, 1f);
            params.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1, 1f);
            cardView.setLayoutParams(params);

            gridMatchingCards.addView(cardView);
            cardViews.add(cardView);
        }
    }

    private void onCardClicked(View cardView) {
        CardItem clickedCardItem = (CardItem) cardView.getTag();
        if (isChecking || clickedCardItem.isFlipped || clickedCardItem.isMatched) {
            return; // Không làm gì nếu đang kiểm tra, hoặc thẻ đã được lật/ghép đúng
        }

        flipCard(cardView, true); // Lật ngửa thẻ
        clickedCardItem.isFlipped = true;

        if (firstFlippedCardItem == null) {
            // Đây là thẻ đầu tiên được lật trong một lượt
            firstFlippedCardItem = clickedCardItem;
            firstFlippedCardView = cardView;
        } else {
            // Đây là thẻ thứ hai, tiến hành kiểm tra
            isChecking = true; // Bắt đầu kiểm tra, chặn lật thêm
            if (firstFlippedCardItem.pairId == clickedCardItem.pairId) {
                // Hai thẻ khớp nhau
                firstFlippedCardItem.isMatched = true;
                clickedCardItem.isMatched = true;
                pairsFound++;
                updateScoreDisplay();
                Toast.makeText(getActivity(), "Đúng rồi!", Toast.LENGTH_SHORT).show();

                // Reset để chuẩn bị cho lượt lật tiếp theo
                firstFlippedCardItem = null;
                firstFlippedCardView = null;
                isChecking = false;

                // Kiểm tra hoàn thành game
                if (pairsFound == totalPairs) {
                    Toast.makeText(getActivity(), "Hoàn thành! Em giỏi quá!", Toast.LENGTH_LONG).show();
                    // Có thể thêm nút "Chơi lại"
                }
            } else {
                // Hai thẻ không khớp, lật úp lại sau một chút
                handler.postDelayed(() -> {
                    flipCard(firstFlippedCardView, false);
                    flipCard(cardView, false);
                    firstFlippedCardItem.isFlipped = false;
                    clickedCardItem.isFlipped = false;

                    // Reset
                    firstFlippedCardItem = null;
                    firstFlippedCardView = null;
                    isChecking = false;
                }, 1000); // Chờ 1 giây
            }
        }
    }

    private void flipCard(View cardView, boolean showFront) {
        // Bạn sẽ cần animation lật thẻ tương tự game Flashcard
        // Ở đây chỉ làm logic ẩn/hiện đơn giản
        View cardBack = cardView.findViewById(R.id.viewCardBack);
        View cardFront = cardView.findViewById(R.id.viewCardFront); // FrameLayout chứa text và image

        if (showFront) {
            cardBack.setVisibility(View.INVISIBLE);
            cardFront.setVisibility(View.VISIBLE);
        } else {
            cardBack.setVisibility(View.VISIBLE);
            cardFront.setVisibility(View.INVISIBLE);
        }
    }

    private void updateScoreDisplay() {
        if (tvMatchingScoreValue != null) {
            tvMatchingScoreValue.setText(pairsFound + "/" + totalPairs);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        handler.removeCallbacksAndMessages(null);
    }
}
