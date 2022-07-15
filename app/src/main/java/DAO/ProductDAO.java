package DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import Database.MyDatabase;
import Model.CartDetails;
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

    public boolean updateProduct(int id, int quantity) {
        boolean check = false;
        ContentValues cv = new ContentValues();
        cv.put("Quantity", quantity);
        SQLiteDatabase dao = db.getWritableDatabase();
        check = dao.update("PRODUCTS", cv, "ProductId = ?", new String[]{id + ""}) > 0;
        return check;
    }

    public List<Product> loadAllProductsByListProductId(List<CartDetails> cartDetailsList) {
        List<Product> list = new ArrayList<>();
        SQLiteDatabase db = this.db.getReadableDatabase();
        Cursor cs = db.rawQuery("select * from PRODUCTS where ProductId in(" + convertToString(cartDetailsList) + ")", null);
        if (cs != null && cs.getCount() > 0) {
            cs.moveToFirst();
            while (!cs.isAfterLast()) {
                int proId = cs.getInt(0);
                String proName = cs.getString(1);
                double price = cs.getDouble(2);
                int quantity = cs.getInt(3);
                int soldQuantity = cs.getInt(4);
                String description = cs.getString(5);
                String image = cs.getString(6);
                Product product = new Product(proId, proName, price, quantity, soldQuantity, description, image);
                list.add(product);
                Log.d(String.valueOf(ProductDAO.this), "Get products from loadAllProductsByListProductId func: " + product.toString());

                cs.moveToNext();
            }
        }
        cs.close();
        return list;


    }

    private String convertToString(List<CartDetails> cartDetailsList) {
        String result = "";
        for (int i = 0; i < cartDetailsList.size(); i++) {
            if (i == cartDetailsList.size() - 1) {
                result += cartDetailsList.get(i).getCartId() + "";
            } else {
                result += cartDetailsList.get(i).getCartId() + ", ";
            }
        }
        Log.d(String.valueOf(ProductDAO.this), "Convert to String in ProductDAO: " + result);
        return result;
    }

}
