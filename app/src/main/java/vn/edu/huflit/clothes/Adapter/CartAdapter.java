package vn.edu.huflit.clothes.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

import vn.edu.huflit.clothes.CartHelper;
import vn.edu.huflit.clothes.R;
import vn.edu.huflit.clothes.models.Cart;


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder>  {
    private List<Cart> list;
    private CartAdapter.Listener listener;
    private Context context;
    private CartHelper cartHelper;
    private UpdateTotal updateTotal;

    public CartAdapter(Context context, List<Cart> list, CartAdapter.Listener listener,UpdateTotal updateTotal) {
        this.list = list;
        this.context = context;
        this.listener = listener;
        cartHelper = new CartHelper(context);
        this.updateTotal = updateTotal;
    }


    public List<Cart> getList() {
        return list;
    }

    public void setList(List<Cart> list) {
        this.list = list;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_product, parent, false);
        return new CartAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Cart cart = list.get(position);
        if (cart == null) {
            return;
        }

        holder.productName.setText(cart.getName());
        holder.productSize.setText(cart.getSize());
        holder.productTotal.setText(Integer.toString(cart.getPrice() * cart.getQty()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(cart);
            }
        });
        Picasso.get().load("https:" + cart.getImage()).into(holder.productImage);
        holder.productQty.setText(Integer.toString(cart.getQty()));
        holder.deleteCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartHelper.deleteRow(cart.getId());
                list = cartHelper.getAllProductCart();
                notifyDataSetChanged();
            }
        });
        holder.increaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer increaseQty = Integer.parseInt(holder.productQty.getText().toString()) + 1;
                holder.productQty.setText(Integer.toString(increaseQty));
                Integer getTotal = cart.getPrice() * Integer.parseInt(holder.productQty.getText().toString());
                holder.productTotal.setText(Integer.toString(getTotal));
                cartHelper.changeQty(cart.getId(), holder.productQty.getText().toString(),holder.productTotal.getText().toString());
                Double total = cartHelper.getAllProductCart().stream().mapToDouble(cart -> cart.getPrice() * cart.getQty()).sum();
                updateTotal.updateCartTotal(total.toString());
            }
        });
        holder.decreaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getQty = holder.productQty.getText().toString();
                Integer decreaseQty = Integer.parseInt(getQty) - 1;
                if (decreaseQty < 1) {
                    holder.productQty.setText("1");
                } else {
                    holder.productQty.setText(Integer.toString(decreaseQty));
                }
                Integer getTotal = cart.getPrice() * Integer.parseInt(holder.productQty.getText().toString());
                holder.productTotal.setText(Integer.toString(getTotal));
                cartHelper.changeQty(cart.getId(), holder.productQty.getText().toString(),holder.productTotal.getText().toString());
                Double total = cartHelper.getAllProductCart().stream().mapToDouble(cart -> cart.getPrice() * cart.getQty()).sum();
                updateTotal.updateCartTotal(total.toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView productImage, deleteCartBtn, increaseBtn, decreaseBtn;
        private TextView productName, productSize, productTotal, productQty,totalPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.imgProductCart);
            productName = itemView.findViewById(R.id.productNameCart);
            productSize = itemView.findViewById(R.id.productSizeCart);
            productTotal = itemView.findViewById(R.id.productTotalCart);
            deleteCartBtn = itemView.findViewById(R.id.deleteCartBtn);
            increaseBtn = itemView.findViewById(R.id.increaseBtn);
            decreaseBtn = itemView.findViewById(R.id.decreaseBtn);
            productQty = itemView.findViewById(R.id.productQty);
            totalPrice = itemView.findViewById(R.id.totalPrice);
        }
    }

    public interface Listener {
        void onClick(Cart cart);
    }

    public interface UpdateTotal {
        void updateCartTotal(String total);
    }

}
