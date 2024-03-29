package DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.myshopee.MyUtils.CommonUtils;

import java.util.ArrayList;

import Database.MyDatabase;
import Model.User;

public class AccountDAO {
    private final MyDatabase mydata;

    public AccountDAO(Context context) {

//        this.mydata = new MyDatabase(context);
        this.mydata = MyDatabase.getInstance(context);

    }

    public User moneyRecharge(User currentUser, Double money) {
        SQLiteDatabase db = mydata.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("Budget", currentUser.getBudget() + money);
        int row = db.update("ACCOUNTS", values, "UserId=?", new String[]{String.valueOf(currentUser.getUserId())});
        db.close();
        return getUserById(row);
    }

    public User updateInfo(User currentUser) {
        SQLiteDatabase db = mydata.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("Username", currentUser.getUserName());
        values.put("Address", currentUser.getAddress());
        values.put("Phone", currentUser.getPhone());
        int row = db.update("ACCOUNTS", values, "UserId=?", new String[]{String.valueOf(currentUser.getUserId())});
        db.close();
        return getUserById(row);
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
        db.close();
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
        db.close();
        return acc;
    }

    // Update password
    public boolean updatePassword(User user) {
        SQLiteDatabase db = mydata.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("password", user.getPassword());
        int row = db.update("ACCOUNTS", values, "username=?", new String[]{user.getUserName()});
        db.close();
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
        db.close();
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
        db.close();
        return row > 0;
    }

    // delete a user
    public boolean delete(String username) {
        SQLiteDatabase db = mydata.getWritableDatabase();
        int row = db.delete("ACCOUNTS", "username=?", new String[]{username});
        db.close();
        return row > 0;
    }

    public boolean checkBudget(int userid, double totalBill) {
        boolean check = false;
        SQLiteDatabase read = mydata.getReadableDatabase();
        Cursor cs = read.rawQuery("select * from ACCOUNTS where UserId =?", new String[]{String.valueOf(userid)});
        if (cs != null && cs.getCount() > 0) {
            cs.moveToFirst();
            double budget = cs.getDouble(4);
            Log.d(String.valueOf(AccountDAO.this), "Get current budget from accountDAO: " + CommonUtils.getReadableCostFromDouble(budget));

            check = totalBill <= budget;
            Log.d(String.valueOf(AccountDAO.this), "Check budget from accountDAO: " + check);
        }
        cs.close();
        read.close();
        return check;
    }

    public boolean subBudget(int userid, double totalBill) {
        User user = getUserById(userid);
        SQLiteDatabase db = mydata.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Budget", user.getBudget() - totalBill);
        boolean update = db.update("ACCOUNTS", contentValues, "UserId=?", new String[]{String.valueOf(userid)}) > 0;
        db.close();
        return update;
    }
}
