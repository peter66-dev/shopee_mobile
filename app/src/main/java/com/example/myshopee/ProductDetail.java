package com.example.myshopee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myshopee.MyUtils.AnimationUtil;
import com.example.myshopee.my_interface.IClickAđToCartListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import Model.Product;
import Model.User;

public class ProductDetail extends AppCompatActivity {
    IClickAđToCartListener iClickAđToCartListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        ImageView productImg = findViewById(R.id.productImg);
        ImageView view_animation = findViewById(R.id.view_animation);
        ImageView view_end_animation = findViewById(R.id.view_end_animation);
        TextView productName = findViewById(R.id.productName);
        TextView productDescription = findViewById(R.id.productDescription);
        TextView productPrice = findViewById(R.id.productPrice);
        TextView productSold = findViewById(R.id.productSold);
        TextView quantityInStock = findViewById(R.id.quantityInStock);
        Button btnCare = findViewById(R.id.btnCare);
        Button btnAddToCart = findViewById(R.id.btnAddToCart);
        Button btnBuy = findViewById(R.id.btnBuy);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Product clickedProduct = (Product) bundle.get("obj_product");

            productName.setText("Tên sản phẩm: " + clickedProduct.getProductName());
            productPrice.setText("Giá bán: " + clickedProduct.getPrice() + "đ");
            quantityInStock.setText("Số lượng trong kho: " + clickedProduct.getQuantity());
            productDescription.setText(clickedProduct.getDescription());
            productSold.setText("Đã bán: " + clickedProduct.getSoldQuantity());
            productImg.setImageResource(getImageId(this.getApplicationContext(), clickedProduct.getImage()));
        }

        Intent intent = new Intent(this, MainActivity.class);

        btnCare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String CHANNEL_ID = "channel_id";
                CharSequence name = "channel_name";

                Context context = getApplicationContext();
                int important = NotificationManager.IMPORTANCE_HIGH;
                PendingIntent pe = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

                Notification builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Shopee message")
                        .setContentText("Bạn đã quan tâm sản phẩm này!")
                        .setChannelId(CHANNEL_ID)
                        .setContentIntent(pe)
                        .setAutoCancel(true)
                        .build();

                NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel mychannel = new NotificationChannel(CHANNEL_ID, name, important);
                    manager.createNotificationChannel(mychannel);
                }
                manager.notify(0, builder);
            }
        });

        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String CHANNEL_ID = "channel_id";
                CharSequence name = "channel_name";

                Bundle bundle = getIntent().getExtras();
                if (bundle != null) {
                    Product pro = (Product) bundle.get("obj_product");
                    User current_user = (User) bundle.get("current_user");
//                    Toast.makeText(ProductDetail.this, current_user.getUserName() + " đã mua " + pro.getProductName(), Toast.LENGTH_SHORT).show();
//                    iClickAđToCartListener.onClickAddToCart(current_user, pro);
                    view_animation.setImageResource(R.drawable.product1);
                    AnimationUtil.translateAnimation(view_animation, view_animation, view_end_animation, new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            btnAddToCart.setBackgroundResource(R.color.gray);
                            btnAddToCart.setEnabled(false);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                }

                Context context = getApplicationContext();
                int important = NotificationManager.IMPORTANCE_HIGH;
                PendingIntent pe = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

                Notification builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Shopee message")
                        .setContentText("Bạn đã thêm sản phẩm này vào giỏ hàng!")
                        .setChannelId(CHANNEL_ID)
                        .setContentIntent(pe)
                        .setAutoCancel(true)
                        .build();

                NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel mychannel = new NotificationChannel(CHANNEL_ID, name, important);
                    manager.createNotificationChannel(mychannel);
                }
                manager.notify(0, builder);
            }
        });
    }

    public static int getImageId(Context context, String imageName) {
        Log.i("[PETER MESSAGE]", "Image source:  " + imageName);
        Log.i("[PETER MESSAGE]", "Image index:  " + context.getResources().getIdentifier("drawable/" + imageName, null, context.getPackageName()));
        return context.getResources().getIdentifier("drawable/" + imageName, null, context.getPackageName());
    }
}