package DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Database.MyDatabase;
import Model.Cart;
import Model.CartDetails;
import Model.User;

public class CartDetailDAO {
    private  final MyDatabase mydata;

    public CartDetailDAO(Context context) {

//        this.mydata = new MyDatabase(context);
        this.mydata = MyDatabase.getInstance(context);

    }

    /*
     * lấy ra những cart details mà chưa được trả tiền
     * input: ArrayList<Cart> carts
     * output: List<CartDetails>
     * */
    public List<CartDetails> getCartDetailsUnpaidByListCartsUnPaid(List<Cart> carts) { // lấy list carts unpaid bên cartDAO trước(getUnpaidCartsByUserId) rồi truyền vô đây!!!
        List<CartDetails> list = new ArrayList<>();
        SQLiteDatabase db = mydata.getReadableDatabase();
        Cursor cs = db.rawQuery("select * from CARTDETAILS where CartId in(" + convertToString(carts) + ")", null);
        if (cs != null && cs.getCount() > 0) {
            cs.moveToFirst();
            while (!cs.isAfterLast()) {
                int id = cs.getInt(0);
                int cartId = cs.getInt(1);
                int productId = cs.getInt(2);
                int quantity = cs.getInt(3);
                list.add(new CartDetails(id, cartId, productId, quantity));
                cs.moveToNext();
            }
        }
        cs.close();
        db.close();
        return list;
    }

    public CartDetails getCartDetailsById(int cartDetailId) {
        CartDetails cartDetails = new CartDetails();
        SQLiteDatabase db = mydata.getReadableDatabase();
        Cursor cs = db.rawQuery("select * from CARTDETAILS where CartId in(" + cartDetailId + ")", null);
        if (cs != null && cs.getCount() > 0) {
            cs.moveToFirst();
            if (!cs.isAfterLast()) {
                int id = cs.getInt(0);
                int cartId = cs.getInt(1);
                int productId = cs.getInt(2);
                int quantity = cs.getInt(3);
                cartDetails.setCartDetailId(cs.getInt(0));
                cartDetails.setCartId(cs.getInt(1));
                cartDetails.setProductId(cs.getInt(2));
                cartDetails.setQuantity(cs.getInt(3));
            }
        }
        cs.close();
        db.close();
        return cartDetails;
    }

    // Tổng sản lượng sản phẩm ở mỗi giỏ hàng chưa thanh toán
    public int totalNumberOfProductsInUnpaidCarts(HashMap<Integer, CartDetails> unpaidCartDetails) {
        int quantity = 0;
        for (Map.Entry<Integer, CartDetails> set : unpaidCartDetails.entrySet()) {
            quantity += set.getValue().getQuantity();
        }
        return quantity;
    }

    public int totalNumberOfProductsInUnpaidCarts(List<CartDetails> unpaidCartDetails) {
        int quantity = 0;
        for (CartDetails cartDetail : unpaidCartDetails) {
            quantity += cartDetail.getQuantity();
        }
        return quantity;
    }



    public HashMap<Integer, CartDetails> getCartDetailsUnpaidByListCartsUnPaid_HashMap(List<Cart> carts) { // lấy list carts unpaid bên cartDAO trước(getUnpaidCartsByUserId) rồi truyền vô đây!!!
        HashMap<Integer, CartDetails> list = new HashMap<>();
        SQLiteDatabase db = mydata.getReadableDatabase();
        Cursor cs = db.rawQuery("select * from CARTDETAILS where CartId in(" + convertToString(carts) + ")", null);
//        if (cs != null && cs.getCount() > 0) {
        cs.moveToFirst();
        while (!cs.isAfterLast()) {
            int id = cs.getInt(0);
            int cartId = cs.getInt(1);
            int productId = cs.getInt(2);
            int quantity = cs.getInt(3);

            CartDetails details = new CartDetails(id, cartId, productId, quantity);
            list.put(productId, details);
            Log.d(String.valueOf(CartDetailDAO.this), "Key: " + String.valueOf(productId) + ", details: " + details.toString());
            cs.moveToNext();
        }
//        }

        cs.close();
        db.close();
        return list;
    }

    /*
     * default quantity in database: 1
     * output: true when success, false when fail
     * */
    public boolean createCartDetail(int cartId, int productId, int quantity) {
        boolean check = false;
        if (quantity >= 1) {
            SQLiteDatabase db = mydata.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("CartId", cartId);
            contentValues.put("ProductId", productId);
            contentValues.put("Quantity", quantity);
            check = db.insert("CARTDETAILS", null, contentValues) > 0;
            db.close();
        }
        return check;
    }

    private String convertToString(List<Cart> list) {
        String result = "";
        for (int i = 0; i < list.size(); i++) {
            if (i == list.size() - 1) {
                result += list.get(i).getCartId() + "";
            } else {
                result += list.get(i).getCartId() + ", ";
            }
        }
        Log.d(String.valueOf(CartDetailDAO.this), "Convert to String: " + result);
        return result;
    }

    public void updateCartDetail(CartDetails cartDetails) {
        SQLiteDatabase db = mydata.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Id", cartDetails.getCartDetailId());
        values.put("CartId", cartDetails.getCartId());
        values.put("ProductId", cartDetails.getProductId());
        values.put("Quantity", cartDetails.getQuantity());

        db.update("CARTDETAILS", values, "Id = ?", new String[]{String.valueOf(cartDetails.getCartDetailId())});
        db.close();
    }

    public boolean deleteCartDetails(int id) {
        SQLiteDatabase db = mydata.getWritableDatabase();
        boolean delete = db.delete("CARTDETAILS", "id = ?", new String[]{String.valueOf(id)}) > 0;
        db.close();
        return delete;
    }



}
