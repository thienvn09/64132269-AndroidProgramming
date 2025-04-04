package thien.com.luyentapgk_lan2;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class LandScanPAdapter extends RecyclerView.Adapter<LandScanPAdapter.TaoLao> {
    Context context;
    ArrayList<LandScape> CONCAC;

    public LandScanPAdapter(ArrayList<LandScape> CONCAC, Context context) {
        this.CONCAC = CONCAC;
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ArrayList<LandScape> getCONCAC() {
        return CONCAC;
    }
    public void setCONCAC(ArrayList<LandScape> CONCAC) {
        this.CONCAC = CONCAC;
    }
    @NonNull
    @Override
    public TaoLao onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater DUMAMAY = LayoutInflater.from(context);
        View CAYVAILZ = DUMAMAY.inflate(R.layout.item_land,parent,false);
         TaoLao XAMLZ = new TaoLao(CAYVAILZ);
        return XAMLZ;
    }
    @Override
    public void onBindViewHolder(@NonNull TaoLao holder, int position) {
        LandScape DIMEMAY = CONCAC.get(position);
        String CUONGHOHOTITLE = DIMEMAY.getTen();
        String LINHCOPYDI = DIMEMAY.getLinkHinh();
        // laays text
        holder.txt1.setText(CUONGHOHOTITLE);
        // lay anh
        String packageName = holder.itemView.getContext().getPackageName();
        int HinhID = holder.itemView.getResources().getIdentifier(LINHCOPYDI,"mipmap",packageName);
        holder.hinhj.setImageResource(HinhID);
    }

    @Override
    public int getItemCount() {
        return CONCAC.size();
    }

    public class TaoLao extends RecyclerView.ViewHolder{
        TextView txt1;
        ImageView hinhj;


        public TaoLao(@NonNull View itemView) {
            super(itemView);
            txt1 = itemView.findViewById(R.id.txt);
            hinhj = itemView.findViewById(R.id.hinh);
        }
    }
}
