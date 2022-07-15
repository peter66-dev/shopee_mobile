package DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import Database.MyDatabase;
import Model.User;

public class AccountDAO {
    MyDatabase mydata;

    public AccountDAO(Context context) {
        this.mydata = new MyDatabase(context);
    }

    public User getUserById(int userId) {
        User acc = null;
        SQLiteDatabase db = mydata.getReadableDatabase();
        Cursor cs = db.rawQuery("select * from ACCOUNTS where userid =?", new String[]{String.valueOf(userId)});
        if (cs != null && cs.getCount() > 0) {
            cs.moveToFirst();
            int role = cs.getInt(3);
            String username = cs.getString(1);
            String password = cs.getString(2);
            double budget = cs.getDouble(4);
            String address = cs.getString(5);
            String phone = cs.getString(6);
            acc = new User(userId, username, password, address, phone, budget, role);
        }
        return acc;
    }

    public User checkLogin(String username, String password) {
        User acc = null;
        SQLiteDatabase db = mydata.getReadableDatabase();
        Cursor cs = db.rawQuery("select * from ACCOUNTS where Username =? and Password=?", new String[]{username, password});
        if (cs != null && cs.getCount() > 0) {
            cs.moveToFirst();
            int userId = cs.getInt(0);
            int role = cs.getInt(3);
            double budget = cs.getDouble(4);
            String address = cs.getString(5);
            String phone = cs.getString(6);
            acc = new User(userId, username, password, address, phone, budget, role);
        }
        return acc;
    }

    // Update password
    public boolean updatePassword(User user) {
        SQLiteDatabase db = mydata.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("password", user.getPassword());
        int row = db.update("ACCOUNTS", values, "username=?", new String[]{user.getUserName()});
        return row > 0;
    }

    // Read data
    public ArrayList<User> getAllUser() {
        ArrayList<User> list = new ArrayList<>();
        SQLiteDatabase db = mydata.getReadableDatabase();
        Cursor cs = db.rawQuery("select * from ACCOUNTS", null);
        cs.moveToFirst();
        while (!cs.isAfterLast()) {
            int userId = cs.getInt(0);
            String username = cs.getString(1);
            String password = cs.getString(2);
            int role = cs.getInt(3);
            double budget = cs.getDouble(4);
            String address = cs.getString(5);
            String phone = cs.getString(6);
            list.add(new User(userId, username, password, address, phone, budget, role));
            cs.moveToNext();
        }
        cs.close();
        return list;
    }

    // add a new user
    public boolean create(String username, String password, String phone, String address) {
        SQLiteDatabase db = mydata.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Username", username);
        contentValues.put("Password", password);
        contentValues.put("Address", address.trim());
        contentValues.put("Phone", phone.trim());
        contentValues.put("Budget", 500000);
        contentValues.put("RoleId", 1);
        long row = db.insert("ACCOUNTS", null, contentValues);
        return row > 0;
    }

    // delete a user
    public boolean delete(String username) {
        SQLiteDatabase db = mydata.getWritableDatabase();
        int row = db.delete("ACCOUNTS", "username=?", new String[]{username});
        return row > 0;
    }

    public boolean checkBudget(int userid, double totalBill) {
        boolean check = false;
        SQLiteDatabase read = mydata.getReadableDatabase();
        Cursor cs = read.rawQuery("select * from ACCOUNTS where UserId =?", new String[]{String.valueOf(userid)});
        if (cs != null && cs.getCount() > 0) {
            cs.moveToFirst();
            double budget = cs.getDouble(3);
            check = totalBill <= budget;
        }
        return check;
    }

    public boolean subBudget(int userid, double totalBill) {
        SQLiteDatabase db = mydata.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        User user = getUserById(userid);
        contentValues.put("Budget", user.getBudget() - totalBill);
        return db.update("ACCOUNTS", contentValues, "userid=?", new String[]{String.valueOf(userid)}) > 0;
    }
}
