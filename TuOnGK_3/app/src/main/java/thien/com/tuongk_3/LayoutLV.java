package thien.com.tuongk_3;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LayoutLV extends RecyclerView.Adapter<LayoutLV.NhanDuLieu>{
    Context context;
    ArrayList<LandScape> duLieus;

    public LayoutLV(Context context, ArrayList<LandScape> duLieus) {
        this.context = context;
        this.duLieus = duLieus;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ArrayList<LandScape> getDuLieus() {
        return duLieus;
    }

    public void setDuLieus(ArrayList<LandScape> duLieus) {
        this.duLieus = duLieus;
    }

    @NonNull
    @Override
    public NhanDuLieu onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View NhanDuLieu1 = inflater.inflate(R.layout.item,parent,false);
        NhanDuLieu holder = new NhanDuLieu(NhanDuLieu1);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull NhanDuLieu holder, int position) {
        LandScape Ditme = duLieus.get(position);
        String title = Ditme.getTitle();
        String linkHinh = Ditme.getLinkHinh();
        holder.txt1.setText(title);
        String packageName = context.getPackageName();
        int resID = context.getResources().getIdentifier(linkHinh, "mipmap", packageName);

        holder.hinh.setImageResource(resID);
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, Mang3a.class);
            intent.putExtra("title",title);
            intent.putExtra("linkHinh",linkHinh);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
      return duLieus.size();
    }

    public class NhanDuLieu extends RecyclerView.ViewHolder{
        TextView txt1;
        ImageView hinh;

        public NhanDuLieu(@NonNull View itemView) {
            super(itemView);
            txt1 = itemView.findViewById(R.id.txt);
            hinh = itemView.findViewById(R.id.hinh);
        }
    }
}
