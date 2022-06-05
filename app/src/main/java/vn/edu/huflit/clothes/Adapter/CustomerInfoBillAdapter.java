package vn.edu.huflit.clothes.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;

import vn.edu.huflit.clothes.R;
import vn.edu.huflit.clothes.models.Bill;
import vn.edu.huflit.clothes.models.Customer;

public class CustomerInfoBillAdapter extends RecyclerView.Adapter<CustomerInfoBillAdapter.ViewHolder> {
    private List<Customer> list;
    private Context context;

    public CustomerInfoBillAdapter(Context context, List<Customer> list) {
        this.list = list;
        this.context = context;
    }

    public List<Customer> getList() {
        return list;
    }

    public void setList(List<Customer> list) {
        this.list = list;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_info_customer, parent, false);
        return new CustomerInfoBillAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Customer customer = list.get(position);
        if (customer == null) {
            return;
        }
        holder.nameCustomer.setText(customer.getNameCustomer());
        holder.phoneCustomer.setText(customer.getPhoneNumber());
        holder.addressCustomer.setText(customer.getAddress());
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nameCustomer, addressCustomer, phoneCustomer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameCustomer = itemView.findViewById(R.id.name_info_customer);
            addressCustomer = itemView.findViewById(R.id.address_info_customer);
            phoneCustomer = itemView.findViewById(R.id.phone_info_customer);
        }
    }
}
