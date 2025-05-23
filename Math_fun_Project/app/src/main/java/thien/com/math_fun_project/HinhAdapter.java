package thien.com.math_fun_project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HinhAdapter extends RecyclerView.Adapter<HinhAdapter.GameViewHolder> {
    private List<HinhItem> gameItems;
    private OnButtonClickListener listener;
    private List<StringBuilder> currentAnswers;

    public interface OnButtonClickListener {
        void onButtonClick(int itemPosition, String selectedAnswer, boolean isCorrect);
    }

    public HinhAdapter(List<HinhItem> gameItems, OnButtonClickListener listener) {
        this.gameItems = gameItems;
        this.listener = listener;
        this.currentAnswers = new ArrayList<>();
        for (int i = 0; i < gameItems.size(); i++) {
            currentAnswers.add(new StringBuilder());
        }
    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.baitaptienganhhinhanh, parent, false);
        return new GameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameViewHolder holder, int position) {
        HinhItem item = gameItems.get(position);
        holder.imageView.setImageResource(item.getImageResId());
        holder.textView.setText("_ ".repeat(item.getWord().length()).trim());

        // Reset trạng thái trả lời
        currentAnswers.set(position, new StringBuilder());
        updateTextView(holder, position);

        // Tạo danh sách chữ cái (đáp án + ngẫu nhiên)
        List<String> options = new ArrayList<>(List.of(item.getAnswerChars()));
        while (options.size() < 6) {
            String randomChar = String.valueOf((char) ('A' + (int)(Math.random() * 26))).toLowerCase();
            if (!options.contains(randomChar)) {
                options.add(randomChar);
            }
        }
        Collections.shuffle(options);

        // Gán chữ cái cho các nút
        String[] buttonIds = {"button1", "button2", "button3", "button4", "button5", "button6"};
        for (int i = 0; i < 6; i++) {
            Button button = holder.itemView.findViewById(
                    holder.itemView.getResources().getIdentifier(buttonIds[i], "id", holder.itemView.getContext().getPackageName())
            );
            button.setText(options.get(i));
            button.setEnabled(true);
            int finalI = i;
            button.setOnClickListener(v -> {
                String selectedChar = button.getText().toString();
                StringBuilder currentAnswer = currentAnswers.get(position);
                if (currentAnswer.length() < item.getWord().length()) {
                    currentAnswer.append(selectedChar);
                    updateTextView(holder, position);
                    if (currentAnswer.length() == item.getWord().length()) {
                        boolean isCorrect = currentAnswer.toString().equals(item.getWord());
                        listener.onButtonClick(position, currentAnswer.toString(), isCorrect);
                        if (!isCorrect) {
                            currentAnswer.setLength(0);
                            updateTextView(holder, position);
                        }
                    }
                }
            });
        }
    }

    private void updateTextView(GameViewHolder holder, int position) {
        HinhItem item = gameItems.get(position);
        StringBuilder display = new StringBuilder();
        StringBuilder currentAnswer = currentAnswers.get(position);
        for (int i = 0; i < item.getWord().length(); i++) {
            if (i < currentAnswer.length() && currentAnswer.charAt(i) == item.getWord().charAt(i)) {
                display.append(currentAnswer.charAt(i)).append(" ");
            } else {
                display.append("_ ");
            }
        }
        holder.textView.setText(display.toString().trim());
    }

    @Override
    public int getItemCount() {
        return gameItems != null ? gameItems.size() : 0;
    }

    static class GameViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public GameViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.textView);
        }
    }
}