package Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDatabase extends SQLiteOpenHelper {

    public MyDatabase(@Nullable Context context) { // tạo db
        super(context, "shopeeDB4", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String tblRoles = "CREATE TABLE \"ROLES\" ( \"RoleId\"\tINTEGER NOT NULL, \"RoleName\"\tTEXT NOT NULL UNIQUE, PRIMARY KEY(\"RoleId\") )";
        String tblAccounts = "CREATE TABLE \"ACCOUNTS\" ( \"UserId\"\tINTEGER NOT NULL, \"Username\"\tTEXT NOT NULL, \"Password\"\tTEXT NOT NULL, \"RoleId\"\tINTEGER NOT NULL DEFAULT 1, FOREIGN KEY(\"RoleId\") REFERENCES \"ROLES\"(\"RoleId\"), PRIMARY KEY(\"UserId\") )";
        String tblProducts = "CREATE TABLE \"PRODUCTS\" (\"ProductId\"\tINTEGER NOT NULL, \"ProductName\"\tTEXT NOT NULL, \"Price\"\tNUMERIC NOT NULL DEFAULT 10000 CHECK(\"Price\" > 1000), \"Quantity\"\tINTEGER NOT NULL DEFAULT 0 CHECK(\"Quantity\" > 0), \"SoldQuantity\"\tINTEGER NOT NULL DEFAULT 0 CHECK(\"SoldQuantity\" > 0), \"Description\"\tTEXT, \"Image\"\tTEXT, PRIMARY KEY(\"ProductId\"))";

        db.execSQL(tblRoles);
        db.execSQL(tblAccounts);
        db.execSQL(tblProducts);

        db.execSQL("INSERT INTO ROLES VALUES(1, 'USER')");
        db.execSQL("INSERT INTO ROLES VALUES(2, 'ADMIN')");

        db.execSQL("INSERT INTO ACCOUNTS(UserId, Username, Password, RoleId) VALUES(1, 'user', 'user', 1)");
        db.execSQL("INSERT INTO ACCOUNTS(UserId, Username, Password, RoleId) VALUES(2, 'admin', 'admin', 2)");

        db.execSQL("INSERT INTO PRODUCTS(ProductName, Price, Quantity, SoldQuantity, Description, Image) VALUES('Áo thun', 320000, 1200 , 4520,'Áo này rất phù hợp với style!','product1')");
        db.execSQL("INSERT INTO PRODUCTS(ProductName, Price, Quantity, SoldQuantity, Description, Image) VALUES('Áo thun', 120000, 1200 , 130,'Áo này rất phù hợp với style!','product2')");
        db.execSQL("INSERT INTO PRODUCTS(ProductName, Price, Quantity, SoldQuantity, Description, Image) VALUES('Áo thun', 540000, 3100 , 3220,'Áo này rất phù hợp với style!','product3')");
        db.execSQL("INSERT INTO PRODUCTS(ProductName, Price, Quantity, SoldQuantity, Description, Image) VALUES('Áo thun', 130000, 130 , 32,'Áo này rất phù hợp với style!','product4')");
        db.execSQL("INSERT INTO PRODUCTS(ProductName, Price, Quantity, SoldQuantity, Description, Image) VALUES('Áo thun', 350000, 1000 , 3200,'Áo này rất phù hợp với style!','product5')");
        db.execSQL("INSERT INTO PRODUCTS(ProductName, Price, Quantity, SoldQuantity, Description, Image) VALUES('Áo thun', 980000, 1200 , 1220,'Áo này rất phù hợp với style!','product6')");
        db.execSQL("INSERT INTO PRODUCTS(ProductName, Price, Quantity, SoldQuantity, Description, Image) VALUES('Áo thun', 780000, 3200 , 780,'Áo này rất phù hợp với style!','product7')");
        db.execSQL("INSERT INTO PRODUCTS(ProductName, Price, Quantity, SoldQuantity, Description, Image) VALUES('Áo thun', 490000, 1200 , 1200,'Áo này rất phù hợp với style!','product8')");
        db.execSQL("INSERT INTO PRODUCTS(ProductName, Price, Quantity, SoldQuantity, Description, Image) VALUES('Áo thun', 190000, 3200 , 320,'Áo này rất phù hợp với style!','product9')");
        db.execSQL("INSERT INTO PRODUCTS(ProductName, Price, Quantity, SoldQuantity, Description, Image) VALUES('Áo thun', 230000, 1200 , 100,'Áo này rất phù hợp với style!','product10')");
        db.execSQL("INSERT INTO PRODUCTS(ProductName, Price, Quantity, SoldQuantity, Description, Image) VALUES('Áo thun', 220000, 1200 , 170,'Áo này rất phù hợp với style!','p11')");
        db.execSQL("INSERT INTO PRODUCTS(ProductName, Price, Quantity, SoldQuantity, Description, Image) VALUES('Áo thun', 320000, 320 , 800,'Áo này rất phù hợp với style!','p12')");
        db.execSQL("INSERT INTO PRODUCTS(ProductName, Price, Quantity, SoldQuantity, Description, Image) VALUES('Áo thun', 450000, 100 , 140,'Áo này rất phù hợp với style!','p13')");
        db.execSQL("INSERT INTO PRODUCTS(ProductName, Price, Quantity, SoldQuantity, Description, Image) VALUES('Áo thun', 560000, 1000 , 1700,'Áo này rất phù hợp với style!','p14')");
        db.execSQL("INSERT INTO PRODUCTS(ProductName, Price, Quantity, SoldQuantity, Description, Image) VALUES('Áo thun', 870000, 800 , 1700,'Áo này rất phù hợp với style!','p15')");
        db.execSQL("INSERT INTO PRODUCTS(ProductName, Price, Quantity, SoldQuantity, Description, Image) VALUES('Áo thun', 120000, 120 , 140,'Áo này rất phù hợp với style!','p16')");
        db.execSQL("INSERT INTO PRODUCTS(ProductName, Price, Quantity, SoldQuantity, Description, Image) VALUES('Áo thun', 510000, 140 , 110,'Áo này rất phù hợp với style!','p17')");
        db.execSQL("INSERT INTO PRODUCTS(ProductName, Price, Quantity, SoldQuantity, Description, Image) VALUES('Áo thun', 170000, 110 , 100,'Áo này rất phù hợp với style!','p18')");
        db.execSQL("INSERT INTO PRODUCTS(ProductName, Price, Quantity, SoldQuantity, Description, Image) VALUES('Áo thun', 910000, 1700 , 120,'Áo này rất phù hợp với style!','p19')");
        db.execSQL("INSERT INTO PRODUCTS(ProductName, Price, Quantity, SoldQuantity, Description, Image) VALUES('Áo thun', 170000, 100 , 1000,'Áo này rất phù hợp với style!','p20')");
        db.execSQL("INSERT INTO PRODUCTS(ProductName, Price, Quantity, SoldQuantity, Description, Image) VALUES('Áo thun', 180000, 190 , 800,'Áo này rất phù hợp với style!','p21')");
        db.execSQL("INSERT INTO PRODUCTS(ProductName, Price, Quantity, SoldQuantity, Description, Image) VALUES('Áo thun', 810000, 110 , 100,'Áo này rất phù hợp với style!','p22')");
        db.execSQL("INSERT INTO PRODUCTS(ProductName, Price, Quantity, SoldQuantity, Description, Image) VALUES('Áo thun', 320000, 200 , 120,'Áo này rất phù hợp với style!','p23')");
        db.execSQL("INSERT INTO PRODUCTS(ProductName, Price, Quantity, SoldQuantity, Description, Image) VALUES('Áo thun', 110000, 10 , 110,'Áo này rất phù hợp với style!','p24')");
        db.execSQL("INSERT INTO PRODUCTS(ProductName, Price, Quantity, SoldQuantity, Description, Image) VALUES('Áo thun', 880000, 100 , 120,'Áo này rất phù hợp với style!','p25')");
        db.execSQL("INSERT INTO PRODUCTS(ProductName, Price, Quantity, SoldQuantity, Description, Image) VALUES('Áo thun', 880000, 100 , 190,'Áo này rất phù hợp với style!','p26')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS ACCOUNTS");
        db.execSQL("DROP TABLE IF EXISTS ROLES");
        db.execSQL("DROP TABLE IF EXISTS PRODUCTS");
        onCreate(db);
    }
}
