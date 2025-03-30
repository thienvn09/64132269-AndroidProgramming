package thien.com.baitaplamthem_denamtruoc;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Adapteritem extends RecyclerView.Adapter<Adapteritem.Item> {
    Context context;
    ArrayList<>
    @NonNull
    @Override
    public Item onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull Item holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class Item extends RecyclerView.ViewHolder{
        TextView textViewItem;
        ImageView imgView;
        public Item(@NonNull View itemView) {
            super(itemView);
            textViewItem = itemView.findViewById(R.id.textViewItem);
            imgView = itemView.findViewById(R.id.imgView);
        }
    }
}
