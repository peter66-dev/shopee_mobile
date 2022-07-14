package DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import Database.MyDatabase;
import Model.Cart;
import Model.CartDetails;
import Model.User;

public class CartDetailDAO {
    MyDatabase mydata;

    public CartDetailDAO(Context context) {
        this.mydata = new MyDatabase(context);
    }

    /*
     * lấy ra những cart details mà chưa được trả tiền
     * input: ArrayList<Cart> carts
     * output: List<CartDetails>
     * */
    public List<CartDetails> getCartDetailsUnpaidByListCartsUnPaid(ArrayList<Cart> carts) { // lấy list carts unpaid bên cartDAO trước(getUnpaidCartsByUserId) rồi truyền vô đây!!!
        List<CartDetails> list = new ArrayList<>();
        SQLiteDatabase db = mydata.getReadableDatabase();
        Cursor cs = db.rawQuery("select * from CARTS where cartId in (?)", new String[]{convertToString(carts)});
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
        }
        return check;
    }

    private String convertToString(ArrayList<Cart> list) {
        String result = "";
        for (int i = 0; i < list.size(); i++) {
            if (i == list.size() - 1) {
                result += list.get(i).getCartId() + "";
            } else {
                result += list.get(i).getCartId() + ", ";
            }
        }
        return result;
    }
}
