package vn.edu.huflit.clothes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import vn.edu.huflit.clothes.models.Cart;

public class CartHelper {

    Context context;
    String dbName = "Cart.db";

    public CartHelper(Context context) {
        this.context = context;
        CreateTable();
    }


    private SQLiteDatabase openDB() {
        return context.openOrCreateDatabase(dbName, Context.MODE_PRIVATE, null);
    }

    private void closeDB(SQLiteDatabase db) {
        db.close();
    }

    private void CreateTable() {
        SQLiteDatabase db = openDB();

        String sqlFavorites = "CREATE TABLE IF NOT EXISTS tblCart(" +
                "ID TEXT NOT NULL PRIMARY KEY," +
                "Image TEXT," +
                "Name TEXT," +
                "Price INTEGER," +
                "Description TEXT," +
                "Size TEXT," +
                "Qty INTEGER," +
                "Total INTEGER);";
        db.execSQL(sqlFavorites);
        closeDB(db);
    }

    public List<Cart> getAllProductCart() {
        SQLiteDatabase db = openDB();
        ArrayList<Cart> arr = new ArrayList<>();
        String sql = "select * from tblCart";
        Cursor csr = db.rawQuery(sql, null);
        if (csr != null) {
            if (csr.moveToFirst()) {
                do {
                    String id = csr.getString(0);
                    String image = csr.getString(1);
                    String name = csr.getString(2);
                    String price = csr.getString(3);
                    String description = csr.getString(4);
                    String size = csr.getString(5);
                    String qty = csr.getString(6);
                    String total = csr.getString(7);
                    arr.add(new Cart(id, image, name, Integer.parseInt(price), description, size, Integer.parseInt(qty), Integer.parseInt(total)));
                } while (csr.moveToNext());
            }
        }
        closeDB(db);
        return arr;
    }

    public void insertCart(String id, String image, String name, String price, String description, String size, String qty, String total) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID", id);
        contentValues.put("Image", image);
        contentValues.put("Name", name);
        contentValues.put("Price", price);
        contentValues.put("Description", description);
        contentValues.put("Size", size);
        contentValues.put("Qty", qty);
        contentValues.put("Total", total);
        SQLiteDatabase db = openDB();
        db.insert("tblCart", null, contentValues);
        closeDB(db);
    }

    public void deleteRow(String id) {
        SQLiteDatabase db = openDB();
        db.delete("tblCart", "ID = ?", new String[]{id});
        closeDB(db);
    }

    public void changeQty(String id,String qty,String total) {
        SQLiteDatabase db = openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Qty",qty);
        contentValues.put("Total",total);
        db.update("tblCart",contentValues,"ID= ? ",new String[]{id});
        closeDB(db);
    }
}
