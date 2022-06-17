package vn.edu.huflit.clothes.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import vn.edu.huflit.clothes.R;
import vn.edu.huflit.clothes.Utils.ImageAPI;
import vn.edu.huflit.clothes.models.Bill;

public class BillHistoryAdapter extends RecyclerView.Adapter<BillHistoryAdapter.ViewHolder> {
    private List<Bill> list;
    private Context context;

    public BillHistoryAdapter(Context context, List<Bill> list) {
        this.list = list;
        this.context = context;
    }

    public List<Bill> getList() {
        return list;
    }

    public void setList(List<Bill> list) {
        this.list = list;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bill_history, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Bill bill = list.get(position);
        if (bill == null) {
            return;
        }
        holder.billId.setText("Bill #" + bill.get_id());
        holder.billTotal.setText("Total: " +Integer.toString(bill.getTotal())+".000đ");
        holder.billQty.setText("Qty: " +Integer.toString(bill.getQtyProduct()));
        holder.billMethod.setText("Method "+bill.getPaymentMethod());
        if (bill.getStatus()) {
            holder.billStatus.setImageResource(R.drawable.confirm_icon);
        } else {
            holder.billStatus.setImageResource(R.drawable.deny_icon);
        }
        String billCreatedDate = new SimpleDateFormat("E, MMM dd yyyy").format(bill.getCreatedAt());
        holder.billDate.setText(billCreatedDate.toString());
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView billId, billTotal, billQty, billMethod, billDate;
        private ImageView billStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            billId = itemView.findViewById(R.id.bill_history_id);
            billTotal = itemView.findViewById(R.id.bill_history_total);
            billQty = itemView.findViewById(R.id.bill_history_qty);
            billMethod = itemView.findViewById(R.id.bill_history_method);
            billDate = itemView.findViewById(R.id.bill_history_date);
            billStatus = itemView.findViewById(R.id.bill_history_status);
        }
    }

}
