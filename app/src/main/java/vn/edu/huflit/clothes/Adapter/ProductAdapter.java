package vn.edu.huflit.clothes.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import vn.edu.huflit.clothes.R;
import vn.edu.huflit.clothes.models.Product;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private List<Product> list;
    private Listener listener;
    private Context context;

    public ProductAdapter(Context context) {
        this.context = context;
    }

    public List<Product> getList() {
        return list;
    }

    public void setList(List<Product> list) {
        this.list = list;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_card, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = list.get(position);
        if (product == null) {
            return;
        }

        holder.productName.setText(product.getNameProduct());
        Picasso.get().load(product.getImageList()[0]).into(holder.productImage);
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                listener.onClick(product);
//                Intent intent = new Intent(context, product.class);
//                intent.putExtra("id", movieItem.id);
//                intent.putExtra("type", movieItem.media_type);
//                context.startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView productImage;
        private TextView productName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.productImage);
            productName = itemView.findViewById(R.id.productName);
        }
    }

    public interface Listener {
        void onClick(Product product);
    }
}