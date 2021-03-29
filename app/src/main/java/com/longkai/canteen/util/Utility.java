package com.longkai.canteen.util;

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
import com.longkai.canteen.db.Canteen;
import com.longkai.canteen.db.Dish;
import com.longkai.canteen.db.LoginStatus;
import com.longkai.canteen.db.Type;

public class Utility {
  public static boolean handleCanteenListResponse(String response)  {
    if (!TextUtils.isEmpty(response)) {
      try {
        JSONArray allCanteens = new JSONArray(response);
        for (int i = 0;i < allCanteens.length();i++){
          JSONObject canteenObject = allCanteens.getJSONObject(i);
          Canteen canteen = new Canteen();
          canteen.setId(canteenObject.getInt("id"));
          canteen.setName(canteenObject.getString("name"));
          canteen.setAddress(canteenObject.getString("address"));
          canteen.setIsServe(canteenObject.getInt("isServe"));
          canteen.save();
        }
      }catch (Exception e){
        e.printStackTrace();
        return false;
      }
    }
    return true;
  }

  public static boolean handleDishListResponse(String response)  {
    if (!TextUtils.isEmpty(response)) {
      try {
        JSONArray allDishesList = new JSONArray(response);
        for (int i = 0;i < allDishesList.length();i++){
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
          JSONObject canteenObject = dishObject.getJSONObject("whichCanteens");
          Canteen canteen = new Canteen();
          canteen.setId(canteenObject.getInt("id"));
          canteen.setName(canteenObject.getString("name"));
          canteen.setAddress(canteenObject.getString("address"));
          canteen.setIsServe(canteenObject.getInt("isServe"));
          System.out.println(canteen);
          dish.setWhichCanteens(canteen.getId());
          dish.setWhichday(dishObject.getInt("whichday"));
          dish.save();
        }
      }catch (Exception e){
        e.printStackTrace();
        return false;
      }
    }
    return true;
  }

  public static LoginStatus handleLoginResponse(String response){
    return new Gson().fromJson(response,LoginStatus.class);
  }
}
