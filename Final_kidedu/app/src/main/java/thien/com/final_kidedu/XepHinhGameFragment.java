package thien.com.final_kidedu;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class XepHinhGameFragment extends Fragment {

    private ImageView ivBackFromSortGame;
    private TextView tvSortGameTitle, tvSortScoreValue, tvSortInstruction;
    private LinearLayout llUnsortedItems, llChosenOrder;
    private Button btnCheckOrder, btnResetOrder;

    private int currentScore = 0;
    private final Handler handler = new Handler(Looper.getMainLooper());

    // Lớp nội để đại diện cho một item cần sắp xếp
    private static class SortableItem {
        int id; // Để xác định duy nhất item (ví dụ: 0, 1, 2)
        int value; // Giá trị để sắp xếp (ví dụ: 1=nhỏ, 2=vừa, 3=lớn)
        int imageResId; // ID hình ảnh của item

        SortableItem(int id, int value, int imageResId) {
            this.id = id;
            this.value = value;
            this.imageResId = imageResId;
        }
    }

    private List<SortableItem> currentItems; // Danh sách các item của vòng hiện tại
    private List<SortableItem> correctOrder; // Thứ tự đúng
    private List<SortableItem> chosenOrderList; // Thứ tự bé đã chọn
    private List<ImageView> unsortedImageViews; // Lưu trữ các ImageView chưa sắp xếp

    private static final int NUMBER_OF_ITEMS_TO_SORT = 3; // Số lượng item cần sắp xếp

    public XepHinhGameFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_xep_hinh_game, container, false);

        ivBackFromSortGame = view.findViewById(R.id.ivBackFromSortGame);
        tvSortGameTitle = view.findViewById(R.id.tvSortGameTitle);
        tvSortScoreValue = view.findViewById(R.id.tvSortScoreValue);
        tvSortInstruction = view.findViewById(R.id.tvSortInstruction);
        llUnsortedItems = view.findViewById(R.id.llUnsortedItems);
        llChosenOrder = view.findViewById(R.id.llChosenOrder);
        btnCheckOrder = view.findViewById(R.id.btnCheckOrder);
        btnResetOrder = view.findViewById(R.id.btnResetOrder);

        chosenOrderList = new ArrayList<>();
        unsortedImageViews = new ArrayList<>();

        updateScoreDisplay();

        if (ivBackFromSortGame != null) {
            ivBackFromSortGame.setOnClickListener(v -> {
                if (getActivity() != null) {
                    getActivity().getSupportFragmentManager().popBackStack();
                }
            });
        }

        btnCheckOrder.setOnClickListener(v -> checkOrder());
        btnResetOrder.setOnClickListener(v -> resetCurrentAttempt());

        startNewRound();

        return view;
    }

    private void startNewRound() {
        // 1. Tạo dữ liệu
        currentItems = generateSortableItems();
        correctOrder = new ArrayList<>(currentItems);
        // Sắp xếp correctOrder theo giá trị (value) để biết thứ tự đúng
        Collections.sort(correctOrder, Comparator.comparingInt(item -> item.value));

        // 2. Reset các danh sách và UI
        chosenOrderList.clear();
        clearChosenOrderArea();

        // 3. Hiển thị các item chưa sắp xếp
        displayUnsortedItems();
    }

    private List<SortableItem> generateSortableItems() {
        List<SortableItem> items = new ArrayList<>();
        //  Tạo 3 hình vuông với kích thước khác nhau
        items.add(new SortableItem(0, 1, R.drawable.ic_square_small)); // id=0, value=1 (nhỏ)
        items.add(new SortableItem(1, 2, R.drawable.ic_square_medium)); // id=1, value=2 (vừa)
        items.add(new SortableItem(2, 3, R.drawable.ic_square_large)); // id=2, value=3 (lớn)
        // Xáo trộn danh sách để hiển thị ngẫu nhiên
        Collections.shuffle(items);
        return items;
    }

    private void displayUnsortedItems() {
        llUnsortedItems.removeAllViews();
        unsortedImageViews.clear();

        if (getContext() == null) return;

        for (SortableItem item : currentItems) {
            ImageView itemView = new ImageView(getContext());
            itemView.setImageResource(item.imageResId);
            itemView.setTag(item); // Lưu đối tượng item vào tag của ImageView

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            int margin = getResources().getDimensionPixelSize(R.dimen.sort_item_margin);
            params.setMargins(margin, margin, margin, margin);
            itemView.setLayoutParams(params);

            itemView.setClickable(true);
            itemView.setFocusable(true);
            itemView.setBackgroundResource(R.drawable.item_selectable_background);

            itemView.setOnClickListener(v -> onItemClicked(v));

            llUnsortedItems.addView(itemView);
            unsortedImageViews.add(itemView);
        }
    }

    private void onItemClicked(View view) {
        ImageView clickedView = (ImageView) view;
        SortableItem clickedItem = (SortableItem) clickedView.getTag();

        chosenOrderList.add(clickedItem);
        addChosenItemToView(clickedItem);

        // Làm mờ và vô hiệu hóa ImageView đã chọn
        clickedView.setAlpha(0.4f);
        clickedView.setClickable(false);

        // Kích hoạt nút "Kiểm Tra" khi đã chọn đủ số lượng
        if (chosenOrderList.size() == NUMBER_OF_ITEMS_TO_SORT) {
            btnCheckOrder.setEnabled(true);
        }
    }

    private void addChosenItemToView(SortableItem item) {
        if (getContext() == null) return;
        ImageView chosenView = new ImageView(getContext());
        chosenView.setImageResource(item.imageResId);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        params.gravity = Gravity.CENTER;
        int margin = getResources().getDimensionPixelSize(R.dimen.sort_item_margin);
        params.setMargins(margin, 0, margin, 0);
        chosenView.setLayoutParams(params);
        llChosenOrder.addView(chosenView);
    }

    private void clearChosenOrderArea() {
        llChosenOrder.removeAllViews();
        btnCheckOrder.setEnabled(false);
    }

    private void resetCurrentAttempt() {
        chosenOrderList.clear();
        clearChosenOrderArea();

        // Kích hoạt lại các ImageView
        for (ImageView iv : unsortedImageViews) {
            iv.setAlpha(1.0f);
            iv.setClickable(true);
        }
    }

    private void checkOrder() {
        btnCheckOrder.setEnabled(false); // Vô hiệu hóa nút sau khi nhấn
        boolean isCorrect = true;
        for (int i = 0; i < NUMBER_OF_ITEMS_TO_SORT; i++) {
            if (chosenOrderList.get(i).id != correctOrder.get(i).id) {
                isCorrect = false;
                break;
            }
        }
        if (isCorrect) {
            currentScore++;
            Toast.makeText(getActivity(), "Tuyệt vời! Em đã xếp đúng rồi!", Toast.LENGTH_SHORT).show();
            updateScoreDisplay();
            // Chờ một chút rồi bắt đầu vòng mới
            handler.postDelayed(this::startNewRound, 2000);
        } else {
            Toast.makeText(getActivity(), "Chưa đúng rồi! Hãy thử làm lại nhé!", Toast.LENGTH_SHORT).show();
            // Chờ một chút rồi cho bé làm lại
            handler.postDelayed(this::resetCurrentAttempt, 2000);
        }
    }

    private void updateScoreDisplay() {
        if (tvSortScoreValue != null) {
            tvSortScoreValue.setText(String.valueOf(currentScore));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        handler.removeCallbacksAndMessages(null);
    }
}
