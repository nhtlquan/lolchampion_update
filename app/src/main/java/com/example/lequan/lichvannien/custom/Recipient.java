package com.example.lequan.lichvannien.custom;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class Recipient implements Parcelable {
    public static final Creator<Recipient> CREATOR = new C12591();
    String lookupKey;
    String name;
    String number;

    static class C12591 implements Creator<Recipient> {
        C12591() {
        }

        public Recipient createFromParcel(Parcel source) {
            return new Recipient(source);
        }

        public Recipient[] newArray(int size) {
            return new Recipient[size];
        }
    }

    private Recipient(Parcel in) {
        this.name = in.readString();
        this.number = in.readString();
        this.lookupKey = in.readString();
    }

    public String toString() {
        return Recipient.class.getSimpleName() + "[name = " + this.name + ", number = " + this.number + ", key = " + this.lookupKey + "]";
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.number);
        dest.writeString(this.lookupKey);
    }
}
