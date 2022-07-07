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

    public int checkLogin(String username, String password) {
        int role = 0;
        SQLiteDatabase db = mydata.getReadableDatabase();
        //bug here
        Cursor cs = db.rawQuery("select * from ACCOUNTS where Username =? and Password=?", new String[]{username, password});
        if (cs.getCount() > 0) {
            cs.moveToFirst();
            role = cs.getInt(3);
        }
        return role;
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
            list.add(new User(userId, username, password, role));
            cs.moveToNext();
        }
        cs.close();
        return list;
    }

    // add a new user
    public boolean create(String username, String password, int roleId) {
        SQLiteDatabase db = mydata.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Username", username);
        contentValues.put("Password", password);
        contentValues.put("RoleId", roleId);
        long row = db.insert("ACCOUNTS", null, contentValues);
        return row > 0;
    }

    // delete a user
    public boolean delete(String username) {
        SQLiteDatabase db = mydata.getReadableDatabase();
        int row = db.delete("ACCOUNTS", "username=?", new String[]{username});
        return row > 0;
    }
}
