package DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import Database.MyDatabase;
import Model.User;

public class UserDAO {
    MyDatabase mydata;

    public UserDAO(Context context) {
        this.mydata = new MyDatabase(context);
    }

    public boolean checkLogin(User user) {
        SQLiteDatabase db = mydata.getReadableDatabase();
        //bug here
        Cursor cs = db.rawQuery("select * from USER where username =? and password=?", new String[]{user.getUserName(), user.getPassword()});
        if (cs.getCount() <= 0) {
            return false;
        }
        return true;
    }

    // Update password
    public boolean updatePassword(User user) {
        SQLiteDatabase db = mydata.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("password", user.getPassword());
        int row = db.update("USER", values, "username=?", new String[]{user.getUserName()});
        return row > 0;
    }

    // Read data
    public ArrayList<User> getAllUser() {
        ArrayList<User> list = new ArrayList<>();
        SQLiteDatabase db = mydata.getReadableDatabase();
        Cursor cs = db.rawQuery("select * from USER", null);
        cs.moveToFirst();
        while (!cs.isAfterLast()) {
            String username = cs.getString(0);
            String password = cs.getString(1);
            list.add(new User(username, password));
            cs.moveToNext();
        }
        cs.close();
        return list;
    }

    // add a new user
    public boolean create(User user) {
        SQLiteDatabase db = mydata.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Username", user.getUserName());
        contentValues.put("Password", user.getPassword());
        long row = db.insert("USER", null, contentValues);
        return row > 0;
    }

    // delete a user
    public boolean delete(String username) {
        SQLiteDatabase db = mydata.getReadableDatabase();
        int row = db.delete("USER", "username=?", new String[]{username});
        return row > 0;
    }
}
