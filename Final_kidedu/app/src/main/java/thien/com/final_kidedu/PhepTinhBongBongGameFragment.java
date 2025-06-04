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
    private TextView tvBubbleGameTitle, tvBubbleScoreLabel, tvBubbleScoreValue;
    private FrameLayout bubble_game_area;
    private TextView tvDropTarget1, tvDropTarget2, tvDropTarget3;
    private int DienHienTai = 0;
    private final Random randoms = new Random();
    private final Handler VongLapGame = new Handler(Looper.getMainLooper());
    private boolean DoiCauTraLoi = false;
    private int DapAn;
    private static final long ThoiGianDoiCau = 3500;
    private static final long ThoiGianBongBay = 10000;
    private static final int MAX_SoBong = 9;
    private static final int MIN_SoBong = 1;
    private static class MathBT{
        String CauHoi;
        int DapAn;
        MathBT(String CauHoi, int DapAn){
            this.CauHoi = CauHoi;
            this.DapAn = DapAn;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_phep_tinh_bong_bong_game, container, false);
        // ánh xạ các view trong layout
        ivBackFromBubbleGame = view.findViewById(R.id.ivBackFromBubbleGame);
        tvBubbleGameTitle = view.findViewById(R.id.tvBubbleGameTitle);
        tvBubbleScoreLabel = view.findViewById(R.id.tvBubbleScoreLabel);
        tvBubbleScoreValue = view.findViewById(R.id.tvBubbleScoreValue);
        bubble_game_area = view.findViewById(R.id.bubble_game_area);
        tvDropTarget1 = view.findViewById(R.id.tvDropTarget1);
        tvDropTarget2 = view.findViewById(R.id.tvDropTarget2);
        tvDropTarget3 = view.findViewById(R.id.tvDropTarget3);
        updateDiemDisplay();
        LamMoiDrop();
        // gán sự kiện cho nút quay lại
        if(ivBackFromBubbleGame != null)
        {
            ivBackFromBubbleGame.setOnClickListener(v ->{
               if(getActivity() != null){
                    getActivity().getSupportFragmentManager().popBackStack();
                }
            });
        }
       // gán sự kiện click cho các ô đích
        tvDropTarget1.setOnClickListener(v -> handleDropTargetClick(tvDropTarget1));
        tvDropTarget2.setOnClickListener(v -> handleDropTargetClick(tvDropTarget2));
        tvDropTarget3.setOnClickListener(v -> handleDropTargetClick(tvDropTarget3));

        return inflater.inflate(R.layout.fragment_phep_tinh_bong_bong_game, container, false);
    }
    // vòng lập của game
    private void starGameLoop()
    {
        spawnBubble(); // tạo bong bóng đầu tiên
    }
    private void handleDropTargetClick(TextView selectedTarget)
    {
        if(!DoiCauTraLoi)
        {
            return; // không làm gì nếu chưa có bong bóng nào được làm vỡ
        }
    }
    // gán lại sự text '?' cho các droptagerts
    private void LamMoiDrop()
    {
        tvDropTarget1.setText("?");
        tvDropTarget2.setText("?");
        tvDropTarget3.setText("?");
    }
    // cập nhật điểm
    private void updateDiemDisplay()
    {
        // kiểm tra giá trị
        if(tvBubbleScoreValue != null){
            tvBubbleScoreValue.setText(String.valueOf(DienHienTai));
        }
    }

}