package com.example.glucometer.views;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.glucometer.R;
import com.example.glucometer.entities.Entry;
import com.example.glucometer.entities.LogEntry;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.List;

public class LogEntryAdapter extends RecyclerView.Adapter<LogEntryAdapter.LogEntryViewHolder> implements HeaderItemDecoration.StickyHeaderInterface {

    List<Entry> list;

    public LogEntryAdapter(List<Entry> list) {
        this.list = list;
    }

    @Override
    public LogEntryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView;
        switch (viewType) {
            case 1:
                itemView = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.log_header_entry, viewGroup, false);
                return new LogEntryViewHolder(itemView);
            case 2:
                itemView = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.stats_entry, viewGroup, false);
                return new LogEntryViewHolder(itemView);
            default:
                itemView = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.log_entry, viewGroup, false);
                return new LogEntryViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull LogEntryViewHolder holder, int i) {
        Entry entryItem = list.get(i);
        if (entryItem.getViewType() == 0 ) {
            LogEntry entry = (LogEntry)entryItem;
            if (entry.getGlucose() != 0) {
                holder.glucose_value.setText("" + entry.getGlucose());
                holder.glucose_label.setText("mg/dL");
            } else {
                holder.glucose_value.setText("-");
                //holder.glucose_value.setTextColor(holder.glucose_value.getTextColors().withAlpha(20));
                holder.glucose_label.setText("");
            }

            if (entry.getCarbs() != 0) {
                holder.carbs_label.setText("g");
                holder.carbs_value.setText("" + entry.getCarbs());
            } else {
                holder.carbs_label.setText("");
                holder.carbs_value.setText("-");
            }
            if (entry.getBolus() != 0) {
                holder.bolus_value.setText("" + entry.getBolus());
                holder.bolus_label.setText("units");
            } else {
                holder.bolus_value.setText("-");
                holder.bolus_label.setText("");
            }
            if (entry.getBasal() != 0) {
                holder.basal_value.setText("" + entry.getBasal());
                holder.basal_label.setText("units");
            } else {
                holder.basal_value.setText("-");
                holder.basal_label.setText("");
            }

            holder.notes.setText(entry.getNotes());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("h:mm a");
            holder.time.setText(simpleDateFormat.format(entry.getDate()));
        }
        else if (entryItem.getViewType() == 1){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM d");
            holder.time.setText(simpleDateFormat.format(entryItem.getDate()));
        } else {
            Log.d("stuff", "here");
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getHeaderPositionForItem(int itemPosition) {
        int headerPosition = 0;
        do {
            if (this.isHeader(itemPosition)) {
                headerPosition = itemPosition;
                break;
            }
            itemPosition -= 1;
        } while (itemPosition >= 0);
        return headerPosition;
    }

    @Override
    public int getHeaderLayout(int headerPosition) {
        return R.layout.log_header_entry;
    }

    @Override
    public void bindHeaderData(View header, int headerPosition) {
        TextView time = header.findViewById(R.id.time);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM d");
        time.setText(simpleDateFormat.format(list.get(headerPosition).getDate()));
    }

    @Override
    public boolean isHeader(int itemPosition) {
        return list.get(itemPosition).getViewType() == 1;
    }

    public class LogEntryViewHolder extends RecyclerView.ViewHolder {
        private TextView glucose_value;
        private TextView glucose_label;
        private TextView basal_value;
        private TextView carbs_value;
        private TextView carbs_label;
        private TextView basal_label;
        private TextView bolus_value;
        private TextView bolus_label;
        private TextView time;
        private TextView notes;
        private ImageButton sell;
        public LogEntryViewHolder(@NonNull View itemView) {
            super(itemView);
            glucose_value = itemView.findViewById(R.id.glucose_value);
            glucose_label = itemView.findViewById(R.id.glucose_label);
            carbs_label = itemView.findViewById(R.id.carbs_label);
            carbs_value = itemView.findViewById(R.id.carbs_value);
            basal_value = itemView.findViewById(R.id.basal_value);
            basal_label = itemView.findViewById(R.id.basal_label);
            bolus_value = itemView.findViewById(R.id.bolus_value);
            bolus_label = itemView.findViewById(R.id.bolus_label);
            time = itemView.findViewById(R.id.time);
            notes = itemView.findViewById(R.id.notes);
            sell = itemView.findViewById(R.id.sell_item_button);
            if (sell != null) {
                sell.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        notes.setText("hello");
                        time.setText("10:30AM");
                    }
                });
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).getViewType();
    }
}
