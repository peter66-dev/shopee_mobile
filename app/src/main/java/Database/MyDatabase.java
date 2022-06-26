package Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDatabase extends SQLiteOpenHelper {

    public MyDatabase(@Nullable Context context) { // tạo db
        super(context, "lab9", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Create table user
//        String sqlUser = "CREATE TABLE USER (Username Text primary key not null, Password text not null)";
//        db.execSQL(sqlUser);
//        db.execSQL("INSERT INTO USER values('phuxiteo3', 'user@123')");
//        db.execSQL("INSERT INTO USER values('peter', 'user@123')");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
//        db.execSQL("DROP TABLE IF EXISTS USER");
//        onCreate(db);
    }

}
