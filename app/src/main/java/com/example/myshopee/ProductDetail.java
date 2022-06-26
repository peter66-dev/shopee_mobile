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
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ProductDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        ImageView detailImg = findViewById(R.id.proImg);
        TextView productName = findViewById(R.id.productName);
        TextView productPrice = findViewById(R.id.productPrice);
        FloatingActionButton btnCall = findViewById(R.id.phoneIcon);
        Button btnCare = findViewById(R.id.btnCare);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Product curPro = (Product) bundle.get("obj_product");
//            Toast.makeText(this, curPro.getName(), Toast.LENGTH_SHORT).show();
            // Chưa làm layout của product_detail
            productName.setText(curPro.getName());
            productPrice.setText(curPro.getPrice() + "");
            detailImg.setImageResource(curPro.getImgSrc());
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
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                    NotificationChannel mychannel = new NotificationChannel(CHANNEL_ID, name, important);
                    manager.createNotificationChannel(mychannel);
                }
                manager.notify(0, builder);
            }
        });

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = "0971775169";
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + phoneNumber));
                startActivity(intent);
            }
        });

    }
}