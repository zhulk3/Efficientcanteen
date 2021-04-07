package com.longkai.canteen.view;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longkai.canteen.R;

import butterknife.OnClick;

public class AddView extends LinearLayout implements View.OnClickListener {


  private int value = 0;
  private int minValue = 0;
  private int maxValue = 10;
  private  TextView tvCount;



  public AddView(Context context, AttributeSet attrs) {
    super(context, attrs);
    View view = View.inflate(context, R.layout.change_num, this);
    
    ImageView btn_reduce = (ImageView) view.findViewById(R.id.btn_reduce);
    tvCount = (TextView) view.findViewById(R.id.tv_count);
    ImageView btn_add = (ImageView) view.findViewById(R.id.btn_add);
    btn_reduce.setOnClickListener(this);
    btn_add.setOnClickListener(this);
    //设置默认值
    setValue(value);
  }

//  @OnClick({R.id.btn_reduce, R.id.btn_add})
//  public void onViewClicked(View view) {
//    switch (view.getId()) {
//      case R.id.btn_reduce://减
//        reduce();
//        break;
//      case R.id.btn_add://加
//        add();
//        break;
//    }
//  }

  /**
   * 如果当前值大于最小值   减
   */
  private void reduce() {
    if (value > minValue) {
      value--;
    }
    setValue(value);
    if (onValueChangeListene != null) {
      onValueChangeListene.onValueChange(value);
    }
  }

  /**
   * 如果当前值小于最小值  加
   */
  private void add() {
    if (value < maxValue) {
      value++;
    }
    setValue(value);
    if (onValueChangeListene != null) {
      onValueChangeListene.onValueChange(value);
    }
  }

  //获取具体值
  public int getValue() {

    return value;
  }

  public void setValue(int value) {
    this.value = value;
    tvCount.setText(value + "");
  }

  public int getMinValue() {
    return minValue;
  }

  public void setMinValue(int minValue) {
    this.minValue = minValue;
  }

  public int getMaxValue() {
    return maxValue;
  }

  public void setMaxValue(int maxValue) {
    this.maxValue = maxValue;
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.btn_reduce://减
        reduce();
        break;
      case R.id.btn_add://加
        add();
        break;
    }
  }


  //监听回调
  public interface OnValueChangeListener {
    public void onValueChange(int value);
  }

  private OnValueChangeListener onValueChangeListene;

  public void setOnValueChangeListene(OnValueChangeListener onValueChangeListene) {
    this.onValueChangeListene = onValueChangeListene;
  }
}
