package com.longkai.canteen.fragment;
//遇到timeput就卸载重联

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.ExecutionException;

import org.jetbrains.annotations.NotNull;
import org.litepal.LitePal;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.longkai.canteen.LoginActivity;
import com.longkai.canteen.R;
import com.longkai.canteen.adapter.DateAdapter;
import com.longkai.canteen.adapter.DishAdapter;
import com.longkai.canteen.db.Canteen;
import com.longkai.canteen.db.Dish;
import com.longkai.canteen.json.Inserted;
import com.longkai.canteen.json.Orderinfo;
import com.longkai.canteen.pojo.ChooseDate;
import com.longkai.canteen.util.HttpUtil;
import com.longkai.canteen.util.Utility;
import com.longkai.canteen.view.AddView;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class DishFragment extends BaseFragment {
  public static final String ARGS_TITLE = "args_title";
  private static final String TAG = "DishFragment";
  private Button chooseCanteen;
  private static List<Canteen> mCanteenList;
  private static List<Dish> storeDishes;
  private AlertDialog mAlertDialog;
  private String[] canteenItems;
  private static int canteenChoice = -1;
  private Button orderRulers;
  public static final int chooseDateSize = 7;
  private static List<ChooseDate> mChooseDateList = new ArrayList<>();
  private RecyclerView mRecyclerView;
  private TextView lunchTextView;
  private TextView dinnerTextView;
  private static int whichMeal = -1;
  private static int whichDay = -1;
  private static List<Dish> mDishList;
  private ListView mListView;
  private FloatingActionButton mFloatingActionButton;
  private static DishAdapter mDishAdapter;
  @BindView(R.id.addView)
  AddView adderview;


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
    mRecyclerView = view.findViewById(R.id.recycler_view);
    lunchTextView = view.findViewById(R.id.lunch);
    dinnerTextView = view.findViewById(R.id.dinner);
    mListView = view.findViewById(R.id.dishes_layout);
    mFloatingActionButton = view.findViewById(R.id.fab);
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
        whichMeal = 0;
        dinnerTextView.setBackgroundColor(Color.parseColor("#bfbfbf"));
        lunchTextView.setBackgroundColor(Color.parseColor("#f4ea2a"));
        filterDishes();
      }
    });
    dinnerTextView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        whichMeal = 1;
        lunchTextView.setBackgroundColor(Color.parseColor("#bfbfbf"));
        dinnerTextView.setBackgroundColor(Color.parseColor("#f4ea2a"));
        filterDishes();
      }
    });

    mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Inserted inserted = new Inserted();
        Orderinfo orderinfo = new Orderinfo();
        for (int i = 0; i < mDishAdapter.getCount(); i++) {
          LinearLayout linearLayout = (LinearLayout) mListView.getAdapter().getView(i,
              null, null);
          TextView textView = linearLayout.findViewById(R.id.tv_count);
          Integer num = Integer.parseInt(textView.getText().toString());
          TextView price = linearLayout.findViewById(R.id.price);
          if (num > 0) {
            inserted.setNum(num);
            String s = (String) price.getText();
            Double p = Double.parseDouble((String) s.subSequence(0, s.length() - 1));
            inserted.setPrice(p);
            double t = p * num;
            inserted.setTotalprice(t);
            orderinfo.setUid(LoginActivity.id);
            orderinfo.setStatus("未处理");
            orderinfo.setOrderprice(t);
            Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String curDate = sdf.format(c.getTime());
            orderinfo.setOrdertime(curDate);
            String url = "http://172.26.61.134:8081/canteen-ordering-system/orderinfo/commitOrder";
            HttpUtil.sendHttpRequestWithPost(url, inserted, orderinfo, new Callback() {
              @Override
              public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d(TAG, "onFailure: ");
                e.printStackTrace();
              }

              @Override
              public void onResponse(@NotNull Call call, @NotNull Response response)
                  throws IOException {
                Log.d(TAG, "onResponse: " + response);
              }
            });
          }
        }
        Toast.makeText(getContext(), "订单已经提交", Toast.LENGTH_SHORT).show();
      }
    });


    initChooseDate();

    DateAdapter dateAdapter = new DateAdapter(mChooseDateList);
    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
    layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
    mRecyclerView.setLayoutManager(layoutManager);
    mRecyclerView.setAdapter(dateAdapter);

    mDishList = new ArrayList<>();
    for (Dish dish : storeDishes) {
      mDishList.add(dish);
    }
    mDishAdapter = new DishAdapter(getActivity(), R.layout.dish_item_layout, mDishList);
    mListView.setAdapter(mDishAdapter);
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
    List<String> temp = new ArrayList<>();
    if (mCanteenList != null && mCanteenList.size() > 0) {
      for (Canteen canteen : mCanteenList) {
        temp.add(canteen.getName());
      }
      canteenItems = temp.toArray(new String[temp.size()]);
    } else {
      String url = "http://172.26.61.134:8081/canteen-ordering-system/canteen/getCanteen";
      queryCanteenListFromServer(url);
    }
  }

  public void showSingleAlertDialog() {
    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getContext());
    alertBuilder.setTitle("选择就餐食堂");
    alertBuilder.setSingleChoiceItems(canteenItems, 0, new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialogInterface, int i) {
        canteenChoice = i;//选择了哪个食堂
        chooseCanteen.setText(canteenItems[canteenChoice]);
        filterDishes();
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
    for (int i = 0; i < chooseDateSize; i++) {
      calendar.add(Calendar.DATE, 1);
      String date = format.format(calendar.getTime());
      ChooseDate chooseDate = new ChooseDate();
      chooseDate.setDate(date.substring(8, 10));
      chooseDate.setWeekday(date.substring(12, 15));
      System.out.println(chooseDate + "hello");
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

  private void readDishListFromLocal() {
    storeDishes = LitePal.findAll(Dish.class);
    for (Dish dish : storeDishes) {
      System.out.println(dish + "dish");
    }
    if (storeDishes.size() <= 0) {
      String url = "http://172.26.61.134:8081/canteen-ordering-system/productinfo/all";
      queryDishListFromServer(url);
    }
  }

  public void loadDishPic(Dish dish) {
    String url =
        "http://172.26.61.134:8081/canteen-ordering-system/productinfo/imageDownload?fileName=" +
            dish.getPic();

    HttpUtil.sendHttpRequest(url, new Callback() {
      @Override
      public void onFailure(@NotNull Call call, @NotNull IOException e) {
        e.printStackTrace();
        Log.d(TAG, "onFailure: " + "获取图片失败");
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
        System.out.println("gegege" + dish.getDishName().getBounds());
        Log.d(TAG, "onResponse: " + "获取图片成功");
      }
    });
  }

  public Drawable getDrawableGlide(String url) {
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

  public static void setWhichDay(int whichDay) {
    DishFragment.whichDay = whichDay;
  }


  public static List<Canteen> getCanteenList() {
    return mCanteenList;
  }

  public static void setCanteenList(List<Canteen> mCanteenList) {
    DishFragment.mCanteenList = mCanteenList;
  }

  public static int getCanteenChoice() {
    return canteenChoice;
  }

  public static List<ChooseDate> getChooseDateList() {
    return mChooseDateList;
  }

  public static void filterDishes() {
    mDishList.clear();
    if (canteenChoice != -1) {
      for (Dish dish : storeDishes) {
        if (dish.getWhichCanteens() == canteenChoice) {
          mDishList.add(dish);
        }
      }
    } else {
      for (Dish dish : storeDishes) {
        mDishList.add(dish);
      }
    }
    if (whichDay != -1) {
      for (Dish dish : mDishList) {
        if (dish.getWhichday() != whichDay) {
          mDishList.remove(dish);
        }
      }
    }
    if (whichMeal != -1) {
      for (Dish dish : mDishList) {
        if (dish.getWhichMeal() != whichMeal) {
          mDishList.remove(dish);
        }
      }
    }
    mDishAdapter.notifyDataSetChanged();
  }
}
