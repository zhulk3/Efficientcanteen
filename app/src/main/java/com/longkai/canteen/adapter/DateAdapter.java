package com.longkai.canteen.adapter;


import java.util.List;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.longkai.canteen.R;
import com.longkai.canteen.pojo.ChooseDate;

public class DateAdapter extends RecyclerView.Adapter<DateAdapter.ViewHolder> {
  private List<ChooseDate>mDateList;
  private int choiceDate;

  public DateAdapter(List<ChooseDate> dateList) {
    mDateList=dateList;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.date_item,parent,false);
    final ViewHolder holder = new ViewHolder(view);
    holder.chooseView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        int position=holder.getAdapterPosition();

        holder.chooseView.setBackgroundColor(Color.rgb(0, 0, 0));
        System.out.println(mDateList.get(choiceDate)+"hha");
        holder.date.setTextSize(14);
        holder.weekday.setTextSize(14);
        holder.chooseView.setBackgroundColor(Color.rgb(255, 0, 0));
      }
    });

    return holder;
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    ChooseDate chooseDate = mDateList.get(position);
    holder.date.setText(chooseDate.getDate());
    holder.weekday.setText(chooseDate.getWeekday());
  }

  @Override
  public int getItemCount() {
    return mDateList.size();
  }

  static class ViewHolder extends RecyclerView.ViewHolder {
    TextView weekday;
    TextView date;
    View chooseView;

    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      chooseView = itemView;
      weekday = (TextView) itemView.findViewById(R.id.weekday);
      date= (TextView)itemView.findViewById(R.id.date);
    }
  }
}

