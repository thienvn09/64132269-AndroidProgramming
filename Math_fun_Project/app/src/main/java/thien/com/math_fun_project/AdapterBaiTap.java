package thien.com.math_fun_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterBaiTap extends RecyclerView.Adapter<AdapterBaiTap.itemBaiTap>{
    Context context;
    ArrayList<BaiTap> dsBaiTap;
    public AdapterBaiTap(Context context, ArrayList<BaiTap> dsBaiTap) {
        this.context = context;
        this.dsBaiTap = dsBaiTap;
    }

    @NonNull
    @Override
    public itemBaiTap onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater cai_bom = LayoutInflater.from(context);
        View vItem = cai_bom.inflate(R.layout.item_monhoc,parent,false);
        itemBaiTap itemBaiTap = new itemBaiTap(vItem);
        return itemBaiTap;
    }

    @Override
    public void onBindViewHolder(@NonNull itemBaiTap holder, int position) {
        // lấy đối tượng hiển thị
        BaiTap baitaphienthi = dsBaiTap.get(position);
        // trích thông tinh
        String caption = baitaphienthi.getTitle_BaiHoc();
        String TenAnh = baitaphienthi.getImg_BaiHoc();
        // đặt vào các trường thông tinh
        holder.title_BaiHoc.setText(caption);
        // đặt ảnh
        String packagename = holder.itemView.getContext().getPackageName();
        int img_ID = holder.itemView.getContext().getResources().getIdentifier(TenAnh,"mipmap",packagename);
        holder.img_BaiHoc.setImageResource(img_ID);
    }

    @Override
    public int getItemCount() {
        return dsBaiTap.size();
    }

    class itemBaiTap extends RecyclerView.ViewHolder implements  View.OnClickListener{
        ImageView img_BaiHoc;
        TextView title_BaiHoc;

      public itemBaiTap(@NonNull View itemView) {
          super(itemView);
          img_BaiHoc = itemView.findViewById(R.id.img_BaiHoc);
          title_BaiHoc = itemView.findViewById(R.id.title_BaiHoc);
          itemView.setOnClickListener(this);
      }

        @Override
        public void onClick(View v) {

        }
    }

}

