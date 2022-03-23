package vn.edu.huflit.clothes.models;

import java.io.Serializable;
import java.util.List;

public class Product implements Serializable {
    private String _id;
    private String nameProduct;
    private int price;
    private Description description;
    private List<Size> size;

    public Product(String _id, String nameProduct, int price, Description description, List<Size> size) {
        this._id = _id;
        this.nameProduct = nameProduct;
        this.price = price;
        this.description = description;
        this.size = size;
    }


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public List<Size> getSize() {
        return size;
    }

    public void setSize(List<Size> size) {
        this.size = size;
    }

    public Description getDescription() {
        return description;
    }

    public String[] getImageList(){
        return description.getImageList();
    }

    public void setDescription(Description description) {
        this.description = description;
    }
}

class Size implements Serializable {
    private String sizeName;
    private int qty;

    public Size(String sizeName, int qty) {
        this.sizeName = sizeName;
        this.qty = qty;
    }

    public String getSizeName() {
        return sizeName;
    }

    public void setSizeName(String sizeName) {
        this.sizeName = sizeName;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}

class Description implements Serializable {
    private String[] imageList;
    private String productDes;
    private int pirce;
    private String type;
    private String collection;

    public Description(String[] imageList, String productDes, int pirce, String type, String collection) {
        this.imageList = imageList;
        this.productDes = productDes;
        this.pirce = pirce;
        this.type = type;
        this.collection = collection;
    }

    public String[] getImageList() {
        return imageList;
    }

    public void setImageList(String[] imageList) {
        this.imageList = imageList;
    }

    public String getProductDes() {
        return productDes;
    }

    public void setProductDes(String productDes) {
        this.productDes = productDes;
    }

    public int getPirce() {
        return pirce;
    }

    public void setPirce(int pirce) {
        this.pirce = pirce;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }
}

