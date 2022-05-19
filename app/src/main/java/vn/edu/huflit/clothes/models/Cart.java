package vn.edu.huflit.clothes.models;

public class Cart {
    private String _id;
    private String image;
    private String name;
    private int price;
    private String description;
    private String size;
    private int qty;
    private int sum;

    public Cart(String _id, String image, String name, int price, String description, String size, int qty, int sum) {
        this._id = _id;
        this.image = image;
        this.name = name;
        this.price = price;
        this.description = description;
        this.size = size;
        this.qty = qty;
        this.sum = sum;
    }

    public Cart(String _id, String size, int qty, int sum) {
        this._id = _id;
        this.size = size;
        this.qty = qty;
        this.sum = sum;
    }

    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }


}
