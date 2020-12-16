package cs431.inventory.objects;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.gson.Gson;
import com.google.zxing.WriterException;
import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.google.android.gms.vision.L.TAG;


public class Item implements Parcelable {

    private int pkid;
    private String name;
    private String brand;
    private String description;
    private int quantity;
    private double price;
    private double weight;
    private Bitmap qrCode;
    private ArrayList<Category> categories;

    public Item(String name, String brand, String description, int quantity, double price, double weight) {
        this.name = name;
        this.brand = brand;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.weight = weight;
        // pkid is initialied to -1, when we get it back from the database it gets updated with an accurate value
        this.pkid = -1;
        this.categories = new ArrayList<>();

        //generate QR code bitmap
        //this.qrCode = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.qr_code);
        String inputValue = ""+"pkid: "+this.pkid+"\n"+"name: "+this.name+"\n"+"brand: "+this.brand+"\n"+
        "desc: "+this.description+"\n"+"quantity: "+this.quantity+"\n"+"price: "+this.price+
                "\n"+"weight: "+this.weight+"\n"+"cats: "+this.categories.toString();
        //TODO: figure out the dimension parameter
        QRGEncoder qrgEncoder = new QRGEncoder(
                inputValue, null,
                QRGContents.Type.TEXT, 1 );
        try {
            this.qrCode = qrgEncoder.encodeAsBitmap();
        } catch (WriterException e) {
            Log.v(TAG, e.toString());
        }
    }

    // Parcelable components
    public int describeContents() {
        return 0;
    }
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(name);
        out.writeInt(quantity);
        out.writeInt(pkid);
        out.writeParcelable(qrCode, flags);
        out.writeString(brand);
        out.writeString(description);
        out.writeDouble(price);
        out.writeDouble(weight);
        out.writeTypedList(categories);
    }
    public static final Parcelable.Creator<Item> CREATOR = new Parcelable.Creator<Item>() {
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };
    private Item(Parcel in) {
        name = in.readString();
        quantity = in.readInt();
        pkid = in.readInt();
        qrCode = in.readParcelable(null);
        brand = in.readString();
        description = in.readString();
        price = in.readDouble();
        weight = in.readDouble();
        categories = in.createTypedArrayList(Category.CREATOR);
    }

    public int getId() { return pkid; }

    public void setId(int pkid) {
        this.pkid = pkid; }

    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
       this.weight = weight;
    }

    public Bitmap getQrCode() {
        return qrCode;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQrCode(Bitmap qrCode) {
        this.qrCode = qrCode;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void addCatergory(Category cat) {
        this.categories.add(cat);
    }

    public boolean deleteCatergory(Category cat) {
        if(categories.contains(cat)){
            this.categories.remove(cat);
            return true; }
        return false;
    }

    public ArrayList<Category> getCatergories() {
        return categories;
    }

    public void setCatergories(ArrayList<Category> catList) {
        this.categories = catList;
    }

    public String getCatString() {
        Gson gson = new Gson();
        return gson.toJson(this.categories);
    }
}
