package com.example.myshopee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.FragmentTransaction;

import android.animation.Animator;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myshopee.MyUtils.CircleAnimationUtil;
import com.example.myshopee.MyUtils.CommonUtils;
import com.example.myshopee.my_interface.IClickAđToCartListener;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

import DAO.CartDAO;
import DAO.CartDetailDAO;
import Model.Cart;
import Model.CartDetails;
import Model.Product;
import Model.User;

public class ProductDetail extends AppCompatActivity {
    IClickAđToCartListener iClickAđToCartListener;
    private CartDAO cartDAO;
    private CartDetailDAO cartDetailDAO;
    private Product currentProduct;
    private List<Cart> unpaidCartsExisting;
    private User currentUser;
    private int totalNumberOfQuantityInCart = 0;
    private HashMap<Integer, CartDetails> getAllCartDetailsUnpaid;

    private boolean shouldRefreshOnResume = false;
    private ImageView productImg;
    private ImageView productImgClone;
    private TextView productName;
    private TextView productDescription;
    private TextView productPrice;
    private TextView productSold;
    private TextView quantityInStock;
    private Button btnCare;
    private Button btnAddToCart;
    private Button btnBuy;
    private Button btnBack;
    private ImageView imageViewCart;
    private TextView quantityInCart;


    private void mapping() {
        productImg = findViewById(R.id.productImg);
        productImgClone = findViewById(R.id.productImgClone);
//        ImageView view_animation = findViewById(R.id.view_animation);
//        ImageView view_end_animation = findViewById(R.id.view_end_animation);
        productName = findViewById(R.id.productName);
        productDescription = findViewById(R.id.productDescription);
        productPrice = findViewById(R.id.productPrice);
        productSold = findViewById(R.id.productSold);
        quantityInStock = findViewById(R.id.quantityInStock);
        btnCare = findViewById(R.id.btnCare);
        btnAddToCart = findViewById(R.id.btnAddToCart);
        btnBuy = findViewById(R.id.btnBuy);
        btnBack = findViewById(R.id.btnBack);
        imageViewCart = findViewById(R.id.imageViewCart);
        quantityInCart = findViewById(R.id.txvQuantityInCart);


    }

    private void refreshInResumeState() {
        unpaidCartsExisting = cartDAO.getUnpaidCartsByUserId(currentUser.getUserId());
        getAllCartDetailsUnpaid = cartDetailDAO.getCartDetailsUnpaidByListCartsUnPaid_HashMap(unpaidCartsExisting);

        totalNumberOfQuantityInCart = cartDetailDAO.totalNumberOfProductsInUnpaidCarts(getAllCartDetailsUnpaid);

        // Log for debug
        Log.d(String.valueOf(ProductDetail.this), "The number of unpaid carts that exist: " + String.valueOf(unpaidCartsExisting.size()));
        Log.d(String.valueOf(ProductDetail.this), "The total quantity in the cart: " + String.valueOf(totalNumberOfQuantityInCart));

        // Init for cart
        if(totalNumberOfQuantityInCart > 0) {
            quantityInCart.setText(String.valueOf(totalNumberOfQuantityInCart));
        }
        else {
            quantityInCart.setText("0");
            quantityInCart.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        mapping();
        cartDAO = new CartDAO(this);
        cartDetailDAO = new CartDetailDAO(this);

        currentUser = CommonUtils.getCurrentUser(ProductDetail.this);
        refreshInResumeState();

        btnBack.setOnClickListener(v -> onBackPressed());

        imageViewCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductDetail.this, CartActivity.class);
                startActivity(intent);
            }
        });

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            DecimalFormat formatter = new DecimalFormat("#,###");

            currentProduct = (Product) bundle.get("obj_product");
            Log.d(String.valueOf(ProductDetail.this), "CurrentProduct in details view: " + currentProduct.toString());
            productName.setText(productName.getText().toString() + currentProduct.getProductName());
            productPrice.setText(formatter.format(currentProduct.getPrice()));
            quantityInStock.setText("Số lượng trong kho: " + currentProduct.getQuantity());
            productDescription.setText(currentProduct.getDescription());
            productSold.setText("Đã bán: " + currentProduct.getSoldQuantity());
            productImg.setImageResource(getImageId(this.getApplicationContext(), currentProduct.getImage()));
            productImgClone.setImageResource(getImageId(this.getApplicationContext(), currentProduct.getImage()));

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
//                String CHANNEL_ID = "channel_id";
//                CharSequence name = "channel_name";
//
//                Bundle bundle = getIntent().getExtras();
//                if (bundle != null) {
//                    Product pro = (Product) bundle.get("obj_product");
//                    User current_user = (User) bundle.get("current_user");
////                    Toast.makeText(ProductDetail.this, current_user.getUserName() + " đã mua " + pro.getProductName(), Toast.LENGTH_SHORT).show();
////                    iClickAđToCartListener.onClickAddToCart(current_user, pro);
//                    view_animation.setImageResource(R.drawable.product1);
//                    AnimationUtil.translateAnimation(view_animation, view_animation, view_end_animation, new Animation.AnimationListener() {
//                        @Override
//                        public void onAnimationStart(Animation animation) {
//
//                        }
//
//                        @Override
//                        public void onAnimationEnd(Animation animation) {
//                            btnAddToCart.setBackgroundResource(R.color.gray);
//                            btnAddToCart.setEnabled(false);
//                        }
//
//                        @Override
//                        public void onAnimationRepeat(Animation animation) {
//
//                        }
//                    });
//                }
//
//                Context context = getApplicationContext();
//                int important = NotificationManager.IMPORTANCE_HIGH;
//                PendingIntent pe = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
//
//                Notification builder = new NotificationCompat.Builder(context, CHANNEL_ID)
//                        .setSmallIcon(R.mipmap.ic_launcher)
//                        .setContentTitle("Shopee message")
//                        .setContentText("Bạn đã thêm sản phẩm này vào giỏ hàng!")
//                        .setChannelId(CHANNEL_ID)
//                        .setContentIntent(pe)
//                        .setAutoCancel(true)
//                        .build();
//
//                NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                    NotificationChannel mychannel = new NotificationChannel(CHANNEL_ID, name, important);
//                    manager.createNotificationChannel(mychannel);
//                }
//                manager.notify(0, builder);

                showCartDialog();
            }
        });
    }

    public static int getImageId(Context context, String imageName) {
        Log.i("[PETER MESSAGE]", "Image source:  " + imageName);
        Log.i("[PETER MESSAGE]", "Image index:  " + context.getResources().getIdentifier("drawable/" + imageName, null, context.getPackageName()));
        return context.getResources().getIdentifier("drawable/" + imageName, null, context.getPackageName());
    }

    // Show bottom sheet dialog cart when user click Add To Cart
    private void showCartDialog() {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_bottom_sheet_dialog_cart);
        // Mapping views in layout_bottom_sheet_dialog_cart to Objects
        ImageView imageViewThumbnail = dialog.findViewById(R.id.imageViewThumbnail);
        TextView txvPrice = dialog.findViewById(R.id.txvPrice);
        TextView txvQuantity = dialog.findViewById(R.id.txvQuantity);
        TextView txvQuantityInStock = dialog.findViewById(R.id.txvQuantityInStock);
        Button btnDecreaseQuantity = dialog.findViewById(R.id.btnDecreaseQuantity);
        btnDecreaseQuantity.setEnabled(false);
        Button btnIncreaseQuantity = dialog.findViewById(R.id.btnIncreaseQuantity);
        Button btnAddToCart_FromCartDialog = dialog.findViewById(R.id.cart_dialog_btnAddToCart);
        Button btnCloseBottomDialog = dialog.findViewById(R.id.btnCloseBottomDialog);

        DecimalFormat formatter = new DecimalFormat("#,###");

        txvPrice.setText(formatter.format(currentProduct.getPrice()));
        txvQuantityInStock.setText("Kho: " + currentProduct.getQuantity());
        imageViewThumbnail.setImageResource(getImageId(this.getApplicationContext(), currentProduct.getImage()));

        // Exit dialog when user click X
        btnCloseBottomDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnIncreaseQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentQuantity = Integer.valueOf(txvQuantity.getText().toString().trim());
                currentQuantity += 1;
                txvQuantity.setText(String.valueOf(currentQuantity));
                if(currentQuantity > 1) {
                    btnDecreaseQuantity.setEnabled(true);
                }
            }
        });

        btnDecreaseQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentQuantity = Integer.valueOf(txvQuantity.getText().toString().trim());
                currentQuantity -= 1;
                txvQuantity.setText(String.valueOf(currentQuantity));
                if(currentQuantity == 1) {
                    btnDecreaseQuantity.setEnabled(false);
                }
            }
        });


        btnAddToCart_FromCartDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 * After choosing the quantity of the product, the user clicks to btnAddToCart in dialog.
                 * It will trigger the event of animation for that action.
                 * When the animation ends, start performing operations to add products to the cart.
                 */
                FrameLayout destView = (FrameLayout) findViewById(R.id.cartFrameLayout);

                new CircleAnimationUtil().attachActivity(ProductDetail.this).setTargetView(productImgClone).setMoveDuration(1000).setDestView(destView).setAnimationListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        // Check whether the product already exists in the cart
                        int quantityChosen = Integer.valueOf(txvQuantity.getText().toString().trim());

                        if(getAllCartDetailsUnpaid.containsKey(currentProduct.getProductId())) {
                            Log.d(String.valueOf(ProductDetail.this), "getAllCartDetailsUnpaid contains ProductId: " + currentProduct.getProductId());
                            Log.d(String.valueOf(ProductDetail.this), "getAllCartDetailsUnpaid get value with ProductId: " + getAllCartDetailsUnpaid.get(currentProduct.getProductId()).toString());
                            CartDetails existsCartDetails = getAllCartDetailsUnpaid.get(currentProduct.getProductId());
                            existsCartDetails.setQuantity(existsCartDetails.getQuantity() + quantityChosen);
                            cartDetailDAO.updateCartDetail(existsCartDetails);
                            getAllCartDetailsUnpaid.put(currentProduct.getProductId(), existsCartDetails);
                        }
                        else {
                            // create new cart for user
                            int cartIdInserted = cartDAO.createCart(currentUser.getUserId(), 0);
                            if(cartIdInserted > 0) {
                                boolean addCartDetails = cartDetailDAO.createCartDetail(cartIdInserted, currentProduct.getProductId(), quantityChosen);
                                if(!addCartDetails) {
                                    Log.d(String.valueOf(ProductDetail.this), "Failed to add product to cart details after created new cart");
                                } else {
                                    CartDetails cartInserted = cartDetailDAO.getCartDetailsById(cartIdInserted);
                                    getAllCartDetailsUnpaid.put(currentProduct.getProductId(), cartInserted);
                                }
                            }
                            else {
                                Log.d(String.valueOf(ProductDetail.this), "Failed to create new Cart");
                            }
                        }
                        int currentQuantityInCart = Integer.valueOf(quantityInCart.getText().toString().trim());
                        currentQuantityInCart += quantityChosen;
                        quantityInCart.setText(String.valueOf(currentQuantityInCart));
                        quantityInCart.setVisibility(View.VISIBLE);



                        dialog.dismiss();
                        Toast.makeText(ProductDetail.this, "Added To Cart Successfully", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                }).startAnimation();

            }
        });



        Window window = dialog.getWindow();
        if(window == null) {
            return;
        }

        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttributes =window.getAttributes();
        windowAttributes.gravity = Gravity.BOTTOM;
        windowAttributes.windowAnimations =R.style.DialogAnimation;
        window.setAttributes(windowAttributes);



        dialog.show();

    }

    @Override
    protected void onPause() {
        super.onPause();
        shouldRefreshOnResume = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(shouldRefreshOnResume) {
            Log.d(String.valueOf(ProductDetail.this), "Start Refresh on Resume");
            refreshInResumeState();
            shouldRefreshOnResume = false;
        }
//        Toast.makeText(this, "OnResume", Toast.LENGTH_SHORT).show();
    }
}