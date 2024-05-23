package com.example.electronicjournal.trash.vp_rv;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.electronicjournal.Main.App;
import com.example.electronicjournal.R;
import com.example.electronicjournal.database.entity.Profit;

import java.util.List;

public class ProfitAdapterRV  extends RecyclerView.Adapter<ProfitAdapterRV.viewholder>{

    List<Profit> items;
    Context context;

    public ProfitAdapterRV(List<Profit> items) {
        this.items = items;
    }


    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();

        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_profits, parent, false);

        return new viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, @SuppressLint("RecyclerView") int position) {
        holder.amountOfProfitTV.setText(String.valueOf(items.get(position).getAmount()));
        holder.dateOfProfitTV.setText(items.get(position).getDate());
        holder.nameOfProfitTV.setText(items.get(position).getName());

        holder.deleteProfitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Profit profitToDelete = items.get(position);
                items.remove(profitToDelete);

                App.getInstance()
                        .getProfitsDbRepository()
                        .deleteProfit(profitToDelete);

                notifyItemRemoved(position);

            }
        });
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {

        TextView dateOfProfitTV;
        TextView amountOfProfitTV;
        TextView nameOfProfitTV;
        Button deleteProfitBtn;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            dateOfProfitTV = itemView.findViewById(R.id.date_of_profit_TV);
            amountOfProfitTV = itemView.findViewById(R.id.amount_of_profit_TV);
            nameOfProfitTV = itemView.findViewById(R.id.name_of_profit_TV);

            deleteProfitBtn = itemView.findViewById(R.id.delete_profit_BTN);
        }
    }
}
