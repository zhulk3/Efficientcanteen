package com.longkai.canteen.adapter;

import static com.longkai.canteen.fragment.DishFragment.getCanteenList;
import static com.longkai.canteen.fragment.DishFragment.setWhichDay;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
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

import com.longkai.canteen.db.Canteen;
import com.longkai.canteen.db.Dish;
import com.longkai.canteen.fragment.DishFragment;
import com.longkai.canteen.pojo.ChooseDate;
import com.longkai.canteen.view.AddView;

import butterknife.BindView;

public class DishAdapter extends ArrayAdapter<Dish> {
  private int resourceId;
  private static final String TAG = "DishAdapter";
  private List<Integer> mDrawableList=new ArrayList<>();
  private int imageNum=11;
  private int imageIndex=0;


  public DishAdapter(Context context, int textViewResourceId,
      List<Dish> objects) {
    super(context, textViewResourceId, objects);
    resourceId = textViewResourceId;
    initImage();
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
      viewHolder.canteen=(TextView)view.findViewById(R.id.detail_canteen);
      viewHolder.provideDate=(TextView)view.findViewById(R.id.which_day);
      viewHolder.provideTime=(TextView)view.findViewById(R.id.meal_time);
      viewHolder.addView=(AddView) view.findViewById(R.id.addView);
      viewHolder.tv_count=(TextView)view.findViewById(R.id.tv_count);
      view.setTag(viewHolder);

    } else {
      view = convertView;
      viewHolder = (ViewHolder) view.getTag();
    }
    viewHolder.name.setText(dish.getName());
    viewHolder.image.setImageResource(mDrawableList.get((imageIndex++)%imageNum));
    viewHolder.type.setText(dish.getType());
    Integer num = dish.getNum();
//    viewHolder.stock.setText("剩余"+num.toString()+"份");
    Double price = dish.getPrice();
    viewHolder.price.setText( price.toString()+"元");
    viewHolder.intro.setText(dish.getIntro());
    List<Canteen>canteenList=DishFragment.getCanteenList();
    viewHolder.canteen.setText(canteenList.get(dish.getWhichCanteens()).getName());
    if (dish.getWhichMeal()==0){
      viewHolder.provideTime.setText("中午");
    }else {
      viewHolder.provideTime.setText("晚上");
    }
    List<ChooseDate> chooseDates = DishFragment.getChooseDateList();
    viewHolder.provideDate.setText(chooseDates.get(dish.getWhichday()).getDate());
    viewHolder.addView.setOnValueChangeListene(new AddView.OnValueChangeListener() {
      @Override
      public void onValueChange(int value) {
        viewHolder.addView.setValue(value);
        viewHolder.tv_count.setText(value+"");
        Log.d(TAG, "onValueChange: "+viewHolder.tv_count.getText());
      }
    });
    return view;
  }

  class ViewHolder {
    ImageView image;
    TextView name;
    TextView type;
    TextView price;
//    TextView stock;
    TextView intro;
    TextView canteen;
    TextView provideDate;
    TextView provideTime;
    AddView addView;
    TextView tv_count;
  }

  private void initImage(){
    mDrawableList.add(R.drawable.dpr);
    mDrawableList.add(R.drawable.gbjd);
    mDrawableList.add(R.drawable.hsdqc);
    mDrawableList.add(R.drawable.hsdx);
    mDrawableList.add(R.drawable.qzjly);
    mDrawableList.add(R.drawable.tcpg);
    mDrawableList.add(R.drawable.wlj);
    mDrawableList.add(R.drawable.xb);
    mDrawableList.add(R.drawable.xswz);
    mDrawableList.add(R.drawable.xykr);
    mDrawableList.add(R.drawable.yxrs);
  }
}
