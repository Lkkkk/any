package com.example.admin.any;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kk on 2017/12/29.
 * 描述：
 * 玄幻代码,切莫乱改
 */

public class Book implements Parcelable {

  private int bookId;
  private String bookName;

  protected Book(Parcel in) {
    bookId = in.readInt();
    bookName = in.readString();
  }


  public static final Creator<Book> CREATOR = new Creator<Book>() {
    @Override public Book createFromParcel(Parcel in) {
      return new Book(in);
    }

    @Override public Book[] newArray(int size) {
      return new Book[size];
    }
  };

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(bookId);
    dest.writeString(bookName);
  }
}
