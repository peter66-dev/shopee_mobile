package DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import Database.MyDatabase;
import Model.Product;

public class ProductDAO {
    MyDatabase db;

    public ProductDAO(Context context) {
        this.db = new MyDatabase(context);
    }

    public ArrayList<Product> loadAllProducts() {
        ArrayList<Product> list = new ArrayList<>();
        SQLiteDatabase dao = db.getReadableDatabase();
        Cursor cs = dao.rawQuery("select * from PRODUCTS", null);
        cs.moveToFirst();
        while (!cs.isAfterLast()) {
            int proId = cs.getInt(0);
            String proName = cs.getString(1);
            double price = cs.getDouble(2);
            int quantity = cs.getInt(3);
            int soldQuantity = cs.getInt(4);
            String description = cs.getString(5);
            String image = cs.getString(6);
            list.add(new Product(proId, proName, price, quantity, soldQuantity, description, image));
            cs.moveToNext();
        }
        cs.close();
        return list;
    }


}
