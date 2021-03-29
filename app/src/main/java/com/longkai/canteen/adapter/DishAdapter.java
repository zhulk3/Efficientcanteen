package com.longkai.canteen.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.longkai.canteen.R;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.longkai.canteen.db.Dish;

public class DishAdapter extends ArrayAdapter<Dish> {
  private int resourceId;

  public DishAdapter(Context context, int textViewResourceId,
      List<Dish> objects) {
    super(context, textViewResourceId, objects);
    resourceId = textViewResourceId;
  }

  @NonNull
  @Override
  public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
    Dish dish = getItem(position);
    View view;
    ViewHolder viewHolder;
    if (convertView == null) {
      view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
      viewHolder = new ViewHolder();
      viewHolder.image = (ImageView) view.findViewById(R.id.image);
      viewHolder.name = (TextView) view.findViewById(R.id.name);
      viewHolder.price = (TextView) view.findViewById(R.id.price);
      viewHolder.type = (TextView) view.findViewById(R.id.type);
      viewHolder.intro=(TextView)view.findViewById(R.id.intro);
      view.setTag(viewHolder);

    } else {
      view = convertView;
      viewHolder = (ViewHolder) view.getTag();
    }
    viewHolder.name.setText(dish.getName());
    viewHolder.image.setImageResource(R.drawable.dpr);
    viewHolder.type.setText(dish.getType());
    Integer num = dish.getNum();
//    viewHolder.stock.setText("剩余"+num.toString()+"份");
    Double price = dish.getPrice();
    viewHolder.price.setText( price.toString()+"元");
    viewHolder.intro.setText(dish.getIntro());
    return view;
  }

  class ViewHolder {
    ImageView image;
    TextView name;
    TextView type;
    TextView price;
//    TextView stock;
    TextView intro;
  }
}
