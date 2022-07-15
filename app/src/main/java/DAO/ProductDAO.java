package DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import Database.MyDatabase;
import Model.Product;
import Model.User;

public class ProductDAO {
    MyDatabase db;

    public ProductDAO(Context context) {
        this.db = new MyDatabase(context);
    }

    public Product getProductById(int proid) {
        Product pro = null;
        SQLiteDatabase read = db.getReadableDatabase();
        Cursor cs = read.rawQuery("select * from PRODUCTS where ProductId =?", new String[]{String.valueOf(proid)});
        if (cs != null && cs.getCount() > 0) {
            cs.moveToFirst();
            String name = cs.getString(1);
            double price = cs.getDouble(2);
            int quantity = cs.getInt(3);
            int soldQuantity = cs.getInt(4);
            String desc = cs.getString(5);
            String image = cs.getString(6);
            pro = new Product(proid, name, price, quantity, soldQuantity, desc, image);
        }
        return pro;
    }

    public ArrayList<Product> loadAllProducts() {
        ArrayList<Product> list = new ArrayList<>();
        SQLiteDatabase dao = db.getReadableDatabase();
        Cursor cs = dao.rawQuery("select * from PRODUCTS where Quantity > 0", null);
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

    public boolean updateProduct(int id, int quantity) {
        boolean check = false;
        ContentValues cv = new ContentValues();
        cv.put("Quantity", quantity);
        SQLiteDatabase dao = db.getWritableDatabase();
        check = dao.update("PRODUCTS", cv, "ProductId = ?", new String[]{id + ""}) > 0;
        return check;
    }

    public boolean checkQuantityInStock(int proid, int quantity) {
        boolean check = false;
        SQLiteDatabase read = db.getReadableDatabase();
        Cursor cs = read.rawQuery("select * from PRODUCTS where productid =?", new String[]{String.valueOf(proid)});
        if (cs != null && cs.getCount() > 0) {
            cs.moveToFirst();
            int quantityInStock = cs.getInt(3);
            check = quantity <= quantityInStock;
        }
        return check;
    }

    public boolean buyMethod(int id, int buyQuantity) { // trừ quantity in stock & cộng soldQuantity
        boolean check = false;
        Product pro = getProductById(id);
        if (pro != null) {
            ContentValues cv = new ContentValues();
            cv.put("Quantity", pro.getQuantity() - buyQuantity);
            cv.put("SoldQuantity", pro.getSoldQuantity() + buyQuantity);
            SQLiteDatabase dao = db.getWritableDatabase();
            check = dao.update("PRODUCTS", cv, "ProductId = ?", new String[]{String.valueOf(id)}) > 0;
        }
        return check;
    }
}
