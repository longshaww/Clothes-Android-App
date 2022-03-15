//package Adapter;
//
//import android.content.Context;
//import android.content.Intent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.util.List;
//
//import vn.edu.huflit.clothes.R;
//import vn.edu.huflit.clothes.models.Product;
//
//public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
//    private List<Product> list;
//    private Listener listener;
//    private Context context;
//    private Boolean bool;
//
//    public ProductAdapter(Context context, List<Product> list, ProductAdapter.Listener listener, Boolean bool) {
//        this.context = context;
//        this.list = list;
//        this.listener = listener;
//        this.bool = bool;
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.backdrop_layout, parent, false);
//            return new ViewHolder(view);
//
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        Product product = list.get(position);
//        holder.textPopularMovie.setText(product.title);
//            ImageAPI.getCorner(product.backdrop_path, 5, holder.MovieImage);
////        holder.itemView.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                listener.onClick(product);
////                Intent intent = new Intent(context, product.class);
////                intent.putExtra("id", movieItem.id);
////                intent.putExtra("type", movieItem.media_type);
////                context.startActivity(intent);
////            }
////        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return list.size();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//        private ImageView MovieImage;
//        private TextView textPopularMovie;
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            MovieImage = itemView.findViewById(R.id.trendingImage);
//            textPopularMovie = itemView.findViewById(R.id.txtTitleItem);
//        }
//    }
//
//    public interface Listener {
//        void onClick(Product product);
//    }
//}