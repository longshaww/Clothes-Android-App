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
                "Sum INTEGER);";
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
                    String _id = csr.getString(0);
                    String image = csr.getString(1);
                    String name = csr.getString(2);
                    String price = csr.getString(3);
                    String description = csr.getString(4);
                    String size = csr.getString(5);
                    String qty = csr.getString(6);
                    String sum = csr.getString(7);
                    arr.add(new Cart(_id, image, name, Integer.parseInt(price), description, size, Integer.parseInt(qty), Integer.parseInt(sum)));
                } while (csr.moveToNext());
            }
        }

        return arr;
    }

    public List<Cart> responseProducts() {
        SQLiteDatabase db = openDB();
        ArrayList<Cart> arr = new ArrayList<>();
        String sql = "select * from tblCart";
        Cursor csr = db.rawQuery(sql, null);
        if (csr != null) {
            if (csr.moveToFirst()) {
                do {
                    String _id = csr.getString(0);
                    String size = csr.getString(5);
                    String qty = csr.getString(6);
                    String sum = csr.getString(7);
                    arr.add(new Cart(_id, size, Integer.parseInt(qty), Integer.parseInt(sum)));
                } while (csr.moveToNext());
            }
        }

        return arr;
    }

    public void insertCart(String _id, String image, String name, String price, String description, String size, String qty, String sum) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID", _id);
        contentValues.put("Image", image);
        contentValues.put("Name", name);
        contentValues.put("Price", price);
        contentValues.put("Description", description);
        contentValues.put("Size", size);
        contentValues.put("Qty", qty);
        contentValues.put("Sum", sum);
        SQLiteDatabase db = openDB();
        db.insert("tblCart", null, contentValues);

    }

    public void deleteRow(String _id) {
        SQLiteDatabase db = openDB();
        db.delete("tblCart", "ID = ?", new String[]{_id});

    }

    public void changeQty(String _id,String qty,String sum) {
        SQLiteDatabase db = openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Qty",qty);
        contentValues.put("Sum",sum);
        db.update("tblCart",contentValues,"ID= ? ",new String[]{_id});
    }
    public int cartCount(){
       return getAllProductCart().stream().mapToInt(cart -> cart.getQty()).sum();
    }

    public void clearCart(){
        SQLiteDatabase db = openDB();
        db.delete("tblCart",null,null);
    }
}
