package com.longkai.canteen.util;

import static org.litepal.LitePalApplication.getContext;

import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.longkai.canteen.LoginActivity;
import com.longkai.canteen.db.AdminInfo;
import com.longkai.canteen.db.Canteen;
import com.longkai.canteen.db.Dish;
import com.longkai.canteen.db.LoginStatus;
import com.longkai.canteen.db.OrderDetail;
import com.longkai.canteen.db.OrderInfo;
import com.longkai.canteen.db.Type;
import com.longkai.canteen.db.UserInfo;
import com.longkai.canteen.fragment.ToBeTakenFragment;

public class Utility {
  private static final String TAG = "Utility";

  public static boolean handleCanteenListResponse(String response) {

    if (!TextUtils.isEmpty(response)) {
      try {
        JSONArray allCanteens = new JSONArray(response);
        for (int i = 0; i < allCanteens.length(); i++) {
          JSONObject canteenObject = allCanteens.getJSONObject(i);
          Canteen canteen = new Canteen();
          canteen.setId(canteenObject.getInt("id"));
          canteen.setName(canteenObject.getString("name"));
          canteen.setAddress(canteenObject.getString("address"));
          canteen.setIsServe(canteenObject.getInt("isServe"));
          canteen.save();
        }
      } catch (Exception e) {
        e.printStackTrace();
        return false;
      }
    }
    return true;
  }

  public static boolean handleDishListResponse(String response) {
    if (!TextUtils.isEmpty(response)) {
      try {
        JSONArray allDishesList = new JSONArray(response);
        for (int i = 0; i < allDishesList.length(); i++) {
          JSONObject dishObject = allDishesList.getJSONObject(i);
          Dish dish = new Dish();
          dish.setId(dishObject.getInt("id"));
          dish.setName(dishObject.getString("name"));
          dish.setCode(dishObject.getString("code"));
          JSONObject TypeObject = dishObject.getJSONObject("type");
          Type type = new Type();
          type.setId(TypeObject.getInt("id"));
          type.setName(TypeObject.getString("name"));
          System.out.println(type);
          dish.setType(type.getName());
          dish.setPic(dishObject.getString("pic"));
          dish.setNum(dishObject.getInt("num"));
          dish.setPrice(dishObject.getDouble("price"));
          dish.setIntro(dishObject.getString("intro"));
          dish.setWhichMeal(dishObject.getInt("whichMeal"));
          JSONObject canteenObject = dishObject.getJSONObject("whichCanteens");
          Canteen canteen = new Canteen();
          canteen.setId(canteenObject.getInt("id"));
          canteen.setName(canteenObject.getString("name"));
          canteen.setAddress(canteenObject.getString("address"));
          canteen.setIsServe(canteenObject.getInt("isServe"));
          System.out.println(canteen);
          dish.setWhichCanteens(canteen.getId());
          dish.setWhichday(dishObject.getInt("whichday"));
          Log.d(TAG, "handleDishListResponse: "+dish);
          dish.save();
        }
      } catch (Exception e) {
        e.printStackTrace();
        return false;
      }
    }
    return true;
  }

  public static LoginStatus handleLoginResponse(String response) {
    return new Gson().fromJson(response, LoginStatus.class);
  }

  public static boolean handleUserInfoResponse(String response) {
    if (!TextUtils.isEmpty(response)) {
      try {
        JSONObject object = new JSONObject(response);
        UserInfo userInfo = new UserInfo();
        userInfo.setUserName(object.getString("userName"));
        userInfo.setSex(object.getString("sex"));
        userInfo.setAddress(object.getString("address"));
        userInfo.setEmail(object.getString("email"));
        userInfo.setSno(object.getString("sno"));
        JSONObject school = object.getJSONObject("school");
        userInfo.setSchool(school.getString("name"));
        userInfo.setBalance(object.getString("balance"));
        userInfo.setRealName(object.getString("realName"));
        userInfo.setRegDate(object.getString("regDate"));
        LoginActivity.id = object.getInt("id");
        Log.d(TAG, "handleUserInfoResponse: " + LoginActivity.id);
        userInfo.save();
      } catch (Exception e) {
        e.printStackTrace();
        return false;
      }
    }
    return true;
  }

  public static boolean handleOrderInfo(String response) {
    if (!TextUtils.isEmpty(response)) {
      try {
        JSONArray allOrderInfoList = new JSONArray(response);
        for (int i = 0; i < allOrderInfoList.length(); i++) {
          JSONObject object = allOrderInfoList.getJSONObject(i);
          OrderInfo orderInfo = new OrderInfo();
          orderInfo.setId(object.getInt("id"));
          orderInfo.setOrderprice(object.getDouble("orderprice"));
          orderInfo.setOrdertime(object.getString("ordertime"));
          orderInfo.setStatus(object.getString("status"));
          orderInfo.save();
        }
      } catch (Exception e) {
        e.printStackTrace();
        return false;
      }
    }
    return true;
  }

  public static boolean handleOrderDetailResponse(String response) {
    if (!TextUtils.isEmpty(response)) {
      try {
        JSONArray orderDetailList = new JSONArray(response);
        for (int i = 0; i < orderDetailList.length(); i++) {
          JSONObject orderDetailObject = orderDetailList.getJSONObject(i);
          OrderDetail orderDetail = new OrderDetail();
          JSONObject dishObject = orderDetailObject.getJSONObject("pi");
          JSONObject typeObject = dishObject.getJSONObject("type");
          JSONObject canteenObject = dishObject.getJSONObject("whichCanteens");
          orderDetail.setDishName(dishObject.getString("name"));
          orderDetail.setDishType(typeObject.getString("name"));
          orderDetail.setWhichCanteen(canteenObject.getString("name"));
          orderDetail.setTotalprice(Double.parseDouble(orderDetailObject.getString("totalprice")));
          orderDetail.setNum(Integer.parseInt(orderDetailObject.getString("num")));
          orderDetail.save();
        }
      } catch (Exception e) {
        e.printStackTrace();
        return false;
      }
    }
    return true;
  }

  public static boolean handleAdminInfo(String response) {
    if (!TextUtils.isEmpty(response)) {
      try {
        JSONArray adminInfoList = new JSONArray(response);
        for (int i = 0; i < adminInfoList.length(); i++) {
          JSONObject adminInfoObject = adminInfoList.getJSONObject(i);
          AdminInfo adminInfo = new AdminInfo();
          adminInfo.setName(adminInfoObject.getString("name"));
          adminInfo.setOffice(adminInfoObject.getString("office"));
          adminInfo.setTelephone(adminInfoObject.getString("telephone"));
          adminInfo.setEmail(adminInfoObject.getString("email"));
          adminInfo.setBeginTime(adminInfoObject.getString("beginTime"));
          adminInfo.setEndTime(adminInfoObject.getString("endTime"));
          adminInfo.save();
        }
      } catch (Exception e) {
        e.printStackTrace();
        return false;
      }
    }
    return true;
  }
}
