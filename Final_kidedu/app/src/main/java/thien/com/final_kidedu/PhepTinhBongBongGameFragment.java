package thien.com.final_kidedu;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class PhepTinhBongBongGameFragment extends Fragment {

    private ImageView ivBackFromBubbleGame;
    private TextView tvBubbleGameTitle, tvBubbleScoreValue; // tvBubbleScoreLabel đã bị xóa khỏi khai báo vì không dùng trong code Java
    private FrameLayout bubble_game_area; // Giữ tên biến từ layout
    private TextView tvDropTarget1, tvDropTarget2, tvDropTarget3;

    private int DienHienTai = 0; // Giữ tên biến của bạn
    private final Random randoms = new Random(); // Giữ tên biến của bạn
    private final Handler VongLapGame = new Handler(Looper.getMainLooper()); // Giữ tên biến của bạn
    private final List<View> activeBubbles = new ArrayList<>();

    private boolean DoiCauTraLoi = false; // Giữ tên biến của bạn
    private int DapAn; // Đáp án đúng của bong bóng vừa được làm vỡ

    private static final long ThoiGianDoiCau = 3500; // Thời gian giữa mỗi lần tạo bong bóng mới
    private static final long ThoiGianBongBay = 10000; // Thời gian để bong bóng bay hết màn hình
    private static final int MAX_SoBong = 9; // Giá trị tối đa cho một toán hạng
    private static final int MIN_SoBong = 1; // Giá trị tối thiểu cho một toán hạng

    // Class để lưu thông tin của một phép tính
    private static class MathBT { // Giữ tên class của bạn
        String CauHoi;
        int DapAn; // Đáp án đúng

        MathBT(String CauHoi, int DapAn) {
            this.CauHoi = CauHoi;
            this.DapAn = DapAn;
        }
    }

    public PhepTinhBongBongGameFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_phep_tinh_bong_bong_game, container, false);

        ivBackFromBubbleGame = view.findViewById(R.id.ivBackFromBubbleGame);
        tvBubbleGameTitle = view.findViewById(R.id.tvBubbleGameTitle);
        tvBubbleScoreValue = view.findViewById(R.id.tvBubbleScoreValue);
        bubble_game_area = view.findViewById(R.id.bubble_game_area);
        tvDropTarget1 = view.findViewById(R.id.tvDropTarget1);
        tvDropTarget2 = view.findViewById(R.id.tvDropTarget2);
        tvDropTarget3 = view.findViewById(R.id.tvDropTarget3);

        updateDiemDisplay();
        LamMoiDrop();

        if (ivBackFromBubbleGame != null) {
            ivBackFromBubbleGame.setOnClickListener(v -> {
                if (getActivity() != null) {
                    getActivity().getSupportFragmentManager().popBackStack();
                }
            });
        }

        tvDropTarget1.setOnClickListener(v -> handleDropTargetClick(tvDropTarget1));
        tvDropTarget2.setOnClickListener(v -> handleDropTargetClick(tvDropTarget2));
        tvDropTarget3.setOnClickListener(v -> handleDropTargetClick(tvDropTarget3));

        bubble_game_area.post(this::starGameLoop); // Đổi tên hàm cho đúng

        return view;
    }

    private void starGameLoop() { // Đổi tên hàm cho đúng
        spawnBubble();
        // Kích hoạt vòng lặp game
        VongLapGame.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isAdded() && !isRemoving() && getView() != null) {
                    spawnBubble();
                    VongLapGame.postDelayed(this, ThoiGianDoiCau + randoms.nextInt(1500));
                }
            }
        }, ThoiGianDoiCau + randoms.nextInt(1500));
    }

    private MathBT generateMathProblem() {
        int So1 = randoms.nextInt(MAX_SoBong - MIN_SoBong + 1) + MIN_SoBong;
        int So2 = randoms.nextInt(MAX_SoBong - MIN_SoBong + 1) + MIN_SoBong;
        int Kq;
        String BaiTap;

        if (randoms.nextBoolean() || So1 < So2) {
            Kq = So1 + So2;
            BaiTap = So1 + " + " + So2;
        } else {
            Kq = So1 - So2;
            BaiTap = So1 + " - " + So2;
        }
        return new MathBT(BaiTap, Kq);
    }

    private void populateDropTargets(int correctAnswerParam) { // Đổi tên tham số để tránh nhầm lẫn
        List<Integer> answers = new ArrayList<>();
        answers.add(correctAnswerParam);
        while (answers.size() < 3) {
            int wrongAnswer;
            do {
                int offset = randoms.nextInt(5) + 1;
                if (randoms.nextBoolean()) {
                    wrongAnswer = correctAnswerParam + offset;
                } else {
                    wrongAnswer = correctAnswerParam - offset;
                }
            } while (wrongAnswer <= 0 || answers.contains(wrongAnswer));
            answers.add(wrongAnswer);
        }
        Collections.shuffle(answers);

        if (answers.size() >= 3) {
            tvDropTarget1.setText(String.valueOf(answers.get(0)));
            tvDropTarget2.setText(String.valueOf(answers.get(1)));
            tvDropTarget3.setText(String.valueOf(answers.get(2)));
        } else {
            // Fallback (hiếm khi xảy ra với logic trên)
            tvDropTarget1.setText(String.valueOf(correctAnswerParam));
            tvDropTarget2.setText(String.valueOf(correctAnswerParam + 1));
            tvDropTarget3.setText(String.valueOf(correctAnswerParam + 2 > 0 ? correctAnswerParam + 2 : 1));
        }
    }

    private void spawnBubble() {
        if (getContext() == null || bubble_game_area.getWidth() == 0 || bubble_game_area.getHeight() == 0) {
            if (isAdded() && getView() != null) {
                bubble_game_area.postDelayed(this::spawnBubble, 100);
            }
            return; // QUAN TRỌNG: return ở đây để tránh lỗi
        }

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View bubbleView = inflater.inflate(R.layout.layout_bubble_item, bubble_game_area, false);
        TextView tvBubble = bubbleView.findViewById(R.id.tv_bubble_text);

        final MathBT mathbt = generateMathProblem();
        tvBubble.setText(mathbt.CauHoi); // SỬA: Hiển thị câu hỏi, không phải đáp án
        bubbleView.setTag(mathbt);       // SỬA: Lưu cả đối tượng MathBT

        bubbleView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int bubbleWidth = bubbleView.getMeasuredWidth();
        int bubbleHeight = bubbleView.getMeasuredHeight();

        if (bubbleWidth == 0 || bubbleHeight == 0) {
            Log.e("BubbleGame", "Bubble view size is zero after measure.");
            // Không gọi đệ quy spawnBubble ở đây nữa để tránh vòng lặp vô hạn nếu layout có vấn đề
            return;
        }

        int StarX = randoms.nextInt(Math.max(1, bubble_game_area.getWidth() - bubbleWidth));
        bubbleView.setX(StarX);
        bubbleView.setY(bubble_game_area.getHeight()); // Bắt đầu từ dưới cùng

        bubble_game_area.addView(bubbleView); // Thêm vào khu vực game
        activeBubbles.add(bubbleView);

        // SỬA: Animation bay từ dưới lên trên
        ObjectAnimator animator = ObjectAnimator.ofFloat(bubbleView, "translationY", bubble_game_area.getHeight(), (float) -bubbleHeight);
        animator.setDuration(ThoiGianBongBay + randoms.nextInt(2000));
        animator.setInterpolator(new LinearInterpolator());
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (isAdded() && getView() != null) {
                    bubble_game_area.removeView(bubbleView);
                    activeBubbles.remove(bubbleView);
                }
            }
        });
        animator.start();

        bubbleView.setOnClickListener(v -> {
            if (DoiCauTraLoi) {
                return;
            }
            MathBT clickedProblem = (MathBT) v.getTag(); // Lấy lại đối tượng MathBT
            if (clickedProblem != null) {
                DapAn = clickedProblem.DapAn; // SỬA: Lấy đáp án đúng từ clickedProblem
                DoiCauTraLoi = true;
                populateDropTargets(DapAn);

                animator.cancel();
                bubble_game_area.removeView(bubbleView); // Xóa khỏi khu vực game
                activeBubbles.remove(bubbleView);
                Toast.makeText(getActivity(), "Phép tính: " + clickedProblem.CauHoi, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handleDropTargetClick(TextView selectedTarget) {
        if (!DoiCauTraLoi) {
            return;
        }
        try {
            int selectedAnswer = Integer.parseInt(selectedTarget.getText().toString());
            if (selectedAnswer == DapAn) {
                DienHienTai++;
                Toast.makeText(getActivity(), "Đúng rồi!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Sai rồi! Đáp án là " + DapAn, Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException e) {
            Toast.makeText(getActivity(), "Vui lòng làm vỡ bong bóng trước!", Toast.LENGTH_SHORT).show();
            return; // Không reset nếu chưa chọn bong bóng
        }

        updateDiemDisplay();
        DoiCauTraLoi = false; // Reset lại để có thể chọn bong bóng mới
        LamMoiDrop(); // Làm mới các ô đích
        // Không gọi spawnBubble ở đây nữa, vòng lặp game sẽ tự xử lý
    }

    private void LamMoiDrop() {
        tvDropTarget1.setText("?");
        tvDropTarget2.setText("?");
        tvDropTarget3.setText("?");
    }

    private void updateDiemDisplay() {
        if (tvBubbleScoreValue != null) {
            tvBubbleScoreValue.setText(String.valueOf(DienHienTai));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        VongLapGame.removeCallbacksAndMessages(null);
        for (View bubble : new ArrayList<>(activeBubbles)) {
            if (bubble.getAnimation() != null) {
                bubble.getAnimation().cancel();
            }
            if (bubble.getParent() != null) {
                ((ViewGroup) bubble.getParent()).removeView(bubble);
            }
        }
        activeBubbles.clear();
    }
}
