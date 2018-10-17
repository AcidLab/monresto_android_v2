package com.monresto.acidlabs.monresto.UI.Delivery;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.monresto.acidlabs.monresto.Config;
import com.monresto.acidlabs.monresto.Model.Dish;
import com.monresto.acidlabs.monresto.Model.ShoppingCart;
import com.monresto.acidlabs.monresto.Model.User;
import com.monresto.acidlabs.monresto.R;
import com.monresto.acidlabs.monresto.Service.Restaurant.RestaurantService;
import com.monresto.acidlabs.monresto.Service.User.UserAsyncResponse;
import com.monresto.acidlabs.monresto.Service.User.UserService;
import com.monresto.acidlabs.monresto.UI.Checkout.CheckoutActivity;
import com.monresto.acidlabs.monresto.UI.User.LoginActivity;
import com.monresto.acidlabs.monresto.UI.User.SelectAddressActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DeliveryActivity extends AppCompatActivity implements UserAsyncResponse{
    private RestaurantService restaurantService;
    private UserService userService;
    private int quantity = 1;

    @BindView(R.id.dish_quantity_add)
    ImageView dish_quantity_add;
    @BindView(R.id.dish_quantity_reduce)
    ImageView dish_quantity_reduce;
    @BindView(R.id.dish_quantity)
    TextView dish_quantity;

    @BindView(R.id.text_comments)
    EditText text_comments;
    @BindView(R.id.btnOrder)
    Button btnOrder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);
        ButterKnife.bind(this);

        userService = new UserService(this);

        dish_quantity_add.setOnClickListener(view -> {
            quantity++;
            dish_quantity.setText(Integer.toString(quantity));
        });

        dish_quantity_reduce.setOnClickListener(view -> {
            if (Integer.valueOf(dish_quantity.getText().toString()) > 1) {
                quantity--;
                dish_quantity.setText(Integer.toString(quantity));
            }
        });

        btnOrder.setOnClickListener(e -> {
            if(User.getInstance() == null){
                Intent intent = new Intent(this, LoginActivity.class);
                intent.putExtra("ask_for_select", true);
                startActivityForResult(intent, Config.REQUEST_CODE_CHECKOUT);
            }
            else{
                Intent intent = new Intent(this, SelectAddressActivity.class);
                startActivityForResult(intent, Config.REQUEST_CODE_CHECKOUT);
            }
        });

    }

    void submit(){
        int restoID = 370;
        int dishID = 25101;
        String promo = "";
        int paymentID = 2;
        int optionOrderID = 1;
        int deliveryTime = 1;
        String hour = "";

        JSONArray orders = new JSONArray();
        JSONObject item = new JSONObject();

        String comment = text_comments.getText().toString();
        quantity = Integer.valueOf(dish_quantity.getText().toString());

        Dish dish = new Dish(25101, "", 1);
        dish.setRestoID(370);
        ShoppingCart shoppingCart = ShoppingCart.createInstance();
        shoppingCart.addToCart(dish, quantity, null, null, comment);

        ShoppingCart.setInstance(shoppingCart);
        Intent intent = new Intent(this, CheckoutActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==Config.REQUEST_CODE_CHECKOUT)
            submit();
    }

    @Override
    public void onSubmitOrder(boolean success){
        if(success)
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Failed: check logs for order response", Toast.LENGTH_SHORT).show();

    }
}
