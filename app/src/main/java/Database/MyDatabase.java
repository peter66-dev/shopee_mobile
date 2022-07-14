package Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDatabase extends SQLiteOpenHelper {

    public MyDatabase(@Nullable Context context) { // tạo db
        super(context, "shopeeDB7", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String tblRoles = "CREATE TABLE \"ROLES\" ( \"RoleId\"\tINTEGER NOT NULL, \"RoleName\"\tTEXT NOT NULL UNIQUE, PRIMARY KEY(\"RoleId\") )";
        String tblAccounts = "CREATE TABLE \"ACCOUNTS\" (\"UserId\"\tINTEGER NOT NULL,\"Username\"\tTEXT NOT NULL,\"Password\"\tTEXT NOT NULL,\"RoleId\"\tINTEGER NOT NULL DEFAULT 1,\"Budget\"\tREAL NOT NULL DEFAULT 0 CHECK('Budget' > 0),\"Address\"\tTEXT DEFAULT 'N''thành phố Hồ Chí Minh''''',\"Phone\"\tTEXT,PRIMARY KEY(\"UserId\"),FOREIGN KEY(\"RoleId\") REFERENCES \"ROLES\"(\"RoleId\"));";
        String tblProducts = "CREATE TABLE \"PRODUCTS\" (\"ProductId\"\tINTEGER NOT NULL, \"ProductName\"\tTEXT NOT NULL, \"Price\"\tNUMERIC NOT NULL DEFAULT 10000 CHECK(\"Price\" > 1000), \"Quantity\"\tINTEGER NOT NULL DEFAULT 0 CHECK(\"Quantity\" > 0), \"SoldQuantity\"\tINTEGER NOT NULL DEFAULT 0 CHECK(\"SoldQuantity\" > 0), \"Description\"\tTEXT, \"Image\"\tTEXT, PRIMARY KEY(\"ProductId\"))";
        String tblCarts = "CREATE TABLE \"CARTS\" (\"CartId\"\tINTEGER NOT NULL,\"UserId\"\tINTEGER NOT NULL,\"IsPaid\"\tINTEGER NOT NULL DEFAULT 0 CHECK('IsPaid' >= 0),PRIMARY KEY(\"CartId\"));";
        String tblCartDetails = "CREATE TABLE \"CARTDETAILS\" (\"Id\"\tINTEGER NOT NULL,\"CartId\"\tINTEGER NOT NULL,\"ProductId\"\tINTEGER NOT NULL,\"Quantity\"\tINTEGER NOT NULL DEFAULT 1 CHECK('Quantity' > 0),PRIMARY KEY(\"Id\"),FOREIGN KEY(\"CartId\") REFERENCES \"ACCOUNTS\"(\"UserId\"),FOREIGN KEY(\"ProductId\") REFERENCES \"PRODUCTS\"(\"ProductId\"));";

        db.execSQL(tblRoles);
        db.execSQL(tblAccounts);
        db.execSQL(tblProducts);
        db.execSQL(tblCarts);
        db.execSQL(tblCartDetails);

        db.execSQL("INSERT INTO ROLES VALUES(1, 'USER')");
        db.execSQL("INSERT INTO ROLES VALUES(2, 'ADMIN')");

        db.execSQL("INSERT INTO ACCOUNTS(UserId, Username, Password, Budget, Address , Phone, RoleId) VALUES(1, 'phuong', '123', 5000000, 'TPHCM', '0971273712', 1)");
        db.execSQL("INSERT INTO ACCOUNTS(UserId, Username, Password, Budget, Address , Phone, RoleId) VALUES(2, 'admin', '123', 5000000, 'Ha Noi', '0931821317',2)");

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

        db.execSQL("INSERT INTO CARTS VALUES(1, 1, 0)");
        db.execSQL("INSERT INTO CARTS VALUES(2, 1, 1)");

        db.execSQL("INSERT INTO CARTDETAILS VALUES(1, 1, 1, 3)");
        db.execSQL("INSERT INTO CARTDETAILS VALUES(2, 2, 5, 2)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS ACCOUNTS");
        db.execSQL("DROP TABLE IF EXISTS ROLES");
        db.execSQL("DROP TABLE IF EXISTS PRODUCTS");
        db.execSQL("DROP TABLE IF EXISTS CARTS");
        db.execSQL("DROP TABLE IF EXISTS CARTDETAILS");
        onCreate(db);
    }
}
