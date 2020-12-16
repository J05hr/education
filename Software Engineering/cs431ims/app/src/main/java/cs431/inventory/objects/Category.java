package cs431.inventory.objects;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.Comparator;

public class Category implements Parcelable {
    private String name;
    private Category next;

    public Category(String name) {
        this.name = name;
        this.next = null;
    }
    public Category(){
        this.name = "";
        this.next = null;
    }

    // Parcelable components
    public int describeContents() {
        return 0;
    }
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(name);
    }
    public static final Parcelable.Creator<Category> CREATOR = new Parcelable.Creator<Category>() {
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };
    private Category(Parcel in) {
        name = in.readString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category next() {
        return next;
    }

    public void setNext(Category next) {
        this.next = next;
    }

    @NonNull
    @Override
    // Used by the category dropdown list in the filter dialog in the inventory listing
    public String toString() {
        return this.name;
    }
}

class SortCategoryByName implements Comparator<Category> {
    // Used for sorting in ascending order of
    // roll number
    @Override
    public int compare(Category o1, Category o2) {
        // TODO Auto-generated method stub
        String s1 = o1.getName().toUpperCase(), s2 = o2.getName().toUpperCase();
        return s1.compareTo(s2);
    }
}