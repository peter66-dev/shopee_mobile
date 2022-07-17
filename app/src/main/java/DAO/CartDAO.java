package DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import Database.MyDatabase;
import Model.Cart;

public class CartDAO {
    private final MyDatabase mydata;

    public CartDAO(Context context) {
//        this.mydata = new MyDatabase(context);
        this.mydata = MyDatabase.getInstance(context);
    }

    public Cart getCartById(int id) {
        Cart cart = null;
        SQLiteDatabase db = mydata.getReadableDatabase();
        Cursor cs = db.rawQuery("select * from CARTS where cartId =?", new String[]{id + ""});
        if (cs != null && cs.getCount() > 0) {
            cs.moveToFirst();
            int cartId = cs.getInt(0);
            int userId = cs.getInt(1);
            int isPaid = cs.getInt(2);
            cart = new Cart(cartId, userId, isPaid);
        }
        cs.close();
        db.close();
        return cart;
    }

    public List<Cart> getAllCartsByUserId(int userId) {
        List<Cart> list = new ArrayList<>();
        SQLiteDatabase db = mydata.getReadableDatabase();
        Cursor cs = db.rawQuery("select * from CARTS where userId =?", new String[]{userId + ""});
        if (cs != null && cs.getCount() > 0) {
            cs.moveToFirst();
            int cartId = cs.getInt(0);
            int isPaid = cs.getInt(2);
            list.add(new Cart(cartId, userId, isPaid));
            cs.moveToNext();
        }
        cs.close();
        db.close();
        return list;
    }

    public List<Cart> getUnpaidCartsByUserId(int userId) {
        List<Cart> list = new ArrayList<>();
        SQLiteDatabase db = mydata.getReadableDatabase();
        Cursor cs = db.rawQuery("select * from CARTS where userId =? and IsPaid=?", new String[]{userId + "", 0 + ""});
        if (cs != null && cs.getCount() > 0) {
            cs.moveToFirst();
            while (!cs.isAfterLast()) {
                int cartId = cs.getInt(0);
                int isPaid = cs.getInt(2);
                list.add(new Cart(cartId, userId, isPaid));
                cs.moveToNext();
            }
        }
        cs.close();
        db.close();
        return list;
    }

    public List<Cart> getAllCarts() {
        List<Cart> list = new ArrayList<>();
        SQLiteDatabase db = mydata.getReadableDatabase();
        Cursor cs = db.rawQuery("select * from CARTS", null);
        if (cs != null && cs.getCount() > 0) {
            cs.moveToFirst();
            while (!cs.isAfterLast()) {
                int cartId = cs.getInt(0);
                int userId = cs.getInt(1);
                int isPaid = cs.getInt(2);
                list.add(new Cart(cartId, userId, isPaid));
                cs.moveToNext();
            }
        }
        cs.close();
        db.close();
        return list;
    }

    public int createCart(int userId, int isPaid) {
        SQLiteDatabase db = mydata.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("UserId", userId);
        contentValues.put("IsPaid", isPaid);
        long row = db.insert("CARTS", null, contentValues);
        db.close();
        return (int) row;
    }

    public boolean updatePaymentStatusByCartId(int cartId, int isPaid) { // Chỉ được update status isPaid: 0 1
        boolean check = false;
        if (isPaid == 0 && isPaid == 1) {
            SQLiteDatabase db = mydata.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("IsPaid", isPaid);
            check = db.update("CARTS", contentValues, "CartId=?", new String[]{cartId + ""}) > 0;
            db.close();
        }
        return check;
    }

    public boolean deleteCart(int cartId) {
        SQLiteDatabase db = mydata.getWritableDatabase();
        boolean success = db.delete("CARTS", "CartId = ?", new String[]{String.valueOf(cartId)}) > 0;
        db.close();
        return success;
    }

    public boolean changeStatusIsPaid(int cartId) { // khi user đủ điều kiện thanh toán thì set IsPaid = 1 (Đã thanh toán!)
        SQLiteDatabase db = mydata.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("IsPaid", 1);
        boolean update = db.update("CARTS", contentValues, "CartId=?", new String[]{String.valueOf(cartId)}) > 0;
        db.close();
        return update;
    }

    public List<Cart> getPaidCartsByUserId(int userId) {
        List<Cart> list = new ArrayList<>();
        SQLiteDatabase db = mydata.getReadableDatabase();
        Cursor cs = db.rawQuery("select * from CARTS where userId =? and IsPaid=?", new String[]{userId + "", 1 + ""});
        if (cs != null && cs.getCount() > 0) {
            cs.moveToFirst();
            while (!cs.isAfterLast()) {
                int cartId = cs.getInt(0);
                int isPaid = cs.getInt(2);
                list.add(new Cart(cartId, userId, isPaid));
                cs.moveToNext();
            }
        }
        cs.close();
        db.close();
        return list;
    }

}
