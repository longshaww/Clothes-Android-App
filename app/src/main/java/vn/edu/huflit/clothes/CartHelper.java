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
                "ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "IDProduct TEXT NOT NULL," +
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
                    String _id = csr.getString(1);
                    String image = csr.getString(2);
                    String name = csr.getString(3);
                    String price = csr.getString(4);
                    String description = csr.getString(5);
                    String size = csr.getString(6);
                    String qty = csr.getString(7);
                    String sum = csr.getString(8);
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
                    String _id = csr.getString(1);
                    String size = csr.getString(6);
                    String qty = csr.getString(7);
                    String sum = csr.getString(8);
                    arr.add(new Cart(_id, size, Integer.parseInt(qty), Integer.parseInt(sum)));
                } while (csr.moveToNext());
            }
        }

        return arr;
    }

    public void insertCart(String _id, String image, String name, String price, String description, String size, String qty, String sum) {
        SQLiteDatabase db = openDB();
        String productQuery = "select * from tblCart where IDProduct='" + _id + "'";
        Cursor csr = db.rawQuery(productQuery, null);
        if (csr.getCount() <= 0) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("IDProduct", _id);
            contentValues.put("Image", image);
            contentValues.put("Name", name);
            contentValues.put("Price", price);
            contentValues.put("Description", description);
            contentValues.put("Size", size);
            contentValues.put("Qty", qty);
            contentValues.put("Sum", sum);
            db.insert("tblCart", null, contentValues);
        } else {
            if (csr.moveToFirst()) {
                String currentSize = csr.getString(6);
                ContentValues contentValues = new ContentValues();
                if (!size.trim().equals(currentSize.trim())) {
                    contentValues.put("IDProduct", _id);
                    contentValues.put("Image", image);
                    contentValues.put("Name", name);
                    contentValues.put("Price", price);
                    contentValues.put("Description", description);
                    contentValues.put("Size", size);
                    contentValues.put("Qty", qty);
                    contentValues.put("Sum", sum);
                    db.insert("tblCart", null, contentValues);
                } else {
                    int currentQty = Integer.parseInt(csr.getString(7));
                    int currentSum = Integer.parseInt(csr.getString(8));
                    contentValues.put("Qty", currentQty + 1);
                    contentValues.put("Sum", currentSum + Integer.parseInt(sum));
                    db.update("tblCart", contentValues, "IDProduct = ? and Size = ?", new String[]{_id, size});
                }
            }
        }
    }

    public void deleteRow(String _id, String size) {
        SQLiteDatabase db = openDB();
        db.delete("tblCart", "IDProduct = ? and Size = ?", new String[]{_id, size});
    }

    public void changeQty(String _id, String size, String qty, String sum) {
        SQLiteDatabase db = openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Qty", qty);
        contentValues.put("Sum", sum);
        db.update("tblCart", contentValues, "IDProduct = ? and Size = ?", new String[]{_id, size});
    }

    public int cartCount() {
        int cartCount = getAllProductCart().stream().mapToInt(cart -> cart.getQty()).sum();
        if (cartCount < 1) {
            return 0;
        }
        return cartCount;
    }

    public void clearCart() {
        SQLiteDatabase db = openDB();
        db.delete("tblCart", null, null);
    }
}
