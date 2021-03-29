package com.longkai.canteen.fragment;
//遇到timeput就卸载重联
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.jetbrains.annotations.NotNull;
import org.litepal.LitePal;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.longkai.canteen.R;
import com.longkai.canteen.adapter.DateAdapter;
import com.longkai.canteen.adapter.DishAdapter;
import com.longkai.canteen.db.Canteen;
import com.longkai.canteen.db.Dish;
import com.longkai.canteen.pojo.ChooseDate;
import com.longkai.canteen.util.HttpUtil;
import com.longkai.canteen.util.L;
import com.longkai.canteen.util.Utility;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class DishFragment extends BaseFragment {
  public static final String ARGS_TITLE = "args_title";
  private static final String TAG = "DishFragment";
  private String mTitle;
  private Button chooseCanteen;
  private List<Canteen> mCanteenList;
  private AlertDialog mAlertDialog;
  private String[] canteenItems;
  private int canteenChoice = 0;
  private Button orderRulers;
  public static final int chooseDateSize = 7;
  private List<ChooseDate>mChooseDateList = new ArrayList<>();
  private RecyclerView mRecyclerView;
  private TextView lunchTextView;
  private TextView dinnerTextView;
  private int whichMeal=0;
  private List<Dish>mDishList;
  private ListView mListView;

  enum WEEKDAYS {星期日, 星期一, 星期二, 星期三, 星期四, 星期五, 星期六};

  public static BaseFragment newInstance(String title) {
    Bundle args = new Bundle();
    args.putString(ARGS_TITLE, title);
    BaseFragment fragment = new DishFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.dish_layout, container, false);
    chooseCanteen = view.findViewById(R.id.choose_canteen);
    orderRulers = view.findViewById(R.id.order_ruler);
    mRecyclerView=view.findViewById(R.id.recycler_view);
    lunchTextView=view.findViewById(R.id.lunch);
    dinnerTextView=view.findViewById(R.id.dinner);
    mListView=view.findViewById(R.id.dishes_layout);

    return view;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    readDishListFromLocal();
    readCanteenListFromLocal();
    chooseCanteen.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        showSingleAlertDialog();
      }
    });
    orderRulers.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        showOrderRulers();
      }
    });

    lunchTextView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        whichMeal=0;
//        dinnerTextView.setTextColor(Color.parseColor("#bfbfbf"));
//        lunchTextView.setTextColor(Color.parseColor("#f4ea2a"));
        dinnerTextView.setBackgroundColor(Color.parseColor("#bfbfbf"));
        lunchTextView.setBackgroundColor(Color.parseColor("#f4ea2a"));
      }
    });
    dinnerTextView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        whichMeal=1;
//        lunchTextView.setTextColor(Color.parseColor("#bfbfbf"));
//        dinnerTextView.setTextColor(Color.parseColor("#f4ea2a"));
        lunchTextView.setBackgroundColor(Color.parseColor("#bfbfbf"));
        dinnerTextView.setBackgroundColor(Color.parseColor("#f4ea2a"));
      }
    });
    initChooseDate();

    DateAdapter dateAdapter = new DateAdapter(mChooseDateList);
    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
    layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
    mRecyclerView.setLayoutManager(layoutManager);
    mRecyclerView.setAdapter(dateAdapter);
    DishAdapter dishAdapter = new DishAdapter(getActivity(),R.layout.dish_item_layout,mDishList);
    mListView.setAdapter(dishAdapter);
  }


  private void queryCanteenListFromServer(String url) {
    HttpUtil.sendHttpRequest(url, new Callback() {
      @Override
      public void onFailure(@NotNull Call call, @NotNull IOException e) {
        getActivity().runOnUiThread(new Runnable() {
          @Override
          public void run() {
            Toast.makeText(getContext(), "load fail", Toast.LENGTH_SHORT).show();
          }
        });
        e.printStackTrace();
        Log.d(TAG, "onFailure: ");
      }

      @Override
      public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
        String responseText = response.body().string();

        boolean result = false;
        result = Utility.handleCanteenListResponse(responseText);
        if (result) {
          new Thread(new Runnable() {
            @Override
            public void run() {
              readCanteenListFromLocal();
            }
          }).start();

        }
        Log.d(TAG, "onResponse: " + responseText);
      }
    });
  }

  private void readCanteenListFromLocal() {
    mCanteenList = LitePal.findAll(Canteen.class);
    List<String>temp=new ArrayList<>();
    if (mCanteenList!=null&&mCanteenList.size() > 0) {
      for (Canteen canteen:mCanteenList){
        temp.add(canteen.getName());
      }
      canteenItems=temp.toArray(new String[temp.size()]);
    } else {
      String url = "http://172.26.112.250:8080/canteen-ordering-system/canteen/getCanteen";
      queryCanteenListFromServer(url);
    }
  }

  public void showSingleAlertDialog() {
    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getContext());
    alertBuilder.setTitle("选择就餐食堂");
    alertBuilder.setSingleChoiceItems(canteenItems, 0, new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialogInterface, int i) {
        canteenChoice = i;
        chooseCanteen.setText(canteenItems[canteenChoice]);
        Toast.makeText(getActivity(), canteenItems[i], Toast.LENGTH_SHORT).show();
      }
    });

    alertBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialogInterface, int i) {
        mAlertDialog.dismiss();
      }
    });
    alertBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialogInterface, int i) {
        mAlertDialog.dismiss();
      }
    });
    mAlertDialog = alertBuilder.create();
    mAlertDialog.show();
  }

  private void showOrderRulers() {
    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getContext());
    alertBuilder.setTitle("订餐提示");
    alertBuilder.setMessage(R.string.ruler);
    alertBuilder.create().show();
  }

  private void initChooseDate() {
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd (EEEE)");
    for (int i =0;i<chooseDateSize;i++){
      calendar.add(Calendar.DATE,1);
      String date = format.format(calendar.getTime());
      ChooseDate chooseDate = new ChooseDate();
      chooseDate.setDate(date.substring(8,10));
      chooseDate.setWeekday(date.substring(12,15));
      System.out.println(chooseDate+"hello");
      mChooseDateList.add(chooseDate);
    }
  }

  private void queryDishListFromServer(String url) {
    HttpUtil.sendHttpRequest(url, new Callback() {
      @Override
      public void onFailure(@NotNull Call call, @NotNull IOException e) {
        e.printStackTrace();
      }

      @Override
      public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
        String responseText = response.body().string();
        boolean result = false;
        result = Utility.handleDishListResponse(responseText);
        if (result) {
          new Thread(new Runnable() {
            @Override
            public void run() {
              readDishListFromLocal();
//              for (Dish dish:mDishList){
//                if(dish.getPic()!="1721668.jpg"&&dish.getPic()!="823125.jpg"){
//                  loadDishPic(dish);
//                }
//
//              }
            }
          }).start();
        }
        Log.d(TAG, "onResponse: " + responseText);
      }
    });
  }

  private void readDishListFromLocal(){
    mDishList = LitePal.findAll(Dish.class);
    for (Dish dish:mDishList){
      System.out.println(dish+"dish");
    }
    if(mDishList.size()<=0){
      String url = "http://172.26.112.250:8080/canteen-ordering-system/productinfo/all";
      queryDishListFromServer(url);
    }
  }
  public void loadDishPic(Dish dish) {
    String url = "http://172.26.112.250:8080/canteen-ordering-system/productinfo/imageDownload?fileName="+dish.getPic();

    HttpUtil.sendHttpRequest(url, new Callback() {
      @Override
      public void onFailure(@NotNull Call call, @NotNull IOException e) {
        e.printStackTrace();
        Log.d(TAG, "onFailure: "+"获取图片失败");
      }

      @Override
      public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
        final String responseImage = response.body().string();
        System.out.println("picture");
//        SharedPreferences.Editor editor =
//            PreferenceManager.getDefaultSharedPreferences(getActivity()).edit();
//        editor.putString(dish.getPic(), responseImage);
//        editor.apply();
        dish.setDishName(getDrawableGlide(responseImage));
        System.out.println("gegege"+dish.getDishName().getBounds());
        Log.d(TAG, "onResponse: "+"获取图片成功");
      }
    });
  }
  public  Drawable getDrawableGlide(String url) {
    try {
      return Glide.with(getContext())
          .load(url)
          .submit()
          .get();
    } catch (ExecutionException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return null;
  }

}
