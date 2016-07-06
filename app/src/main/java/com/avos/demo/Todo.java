package com.avos.demo;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;

@AVClassName(Todo.TODO_CLASS)
public class Todo extends AVObject {

  static final String TODO_CLASS = "Item";
  private static final String CONTENT_KEY = "content";
  private static final String price_KEY = "price";
  private static final String name_KEY = "name";
  private static final String img_key ="img";

  public String getName() {
    return this.getString(name_KEY);
  }
  public void setName(String content) {
    this.put(name_KEY, content);
  }

  public String getPrice() {
    return this.getString(price_KEY);
  }
  public void setPrice(String content) {
    this.put(price_KEY, content);
  }

  public String getContent() {
    return this.getString(CONTENT_KEY);
  }
  public void setContent(String content) {
    this.put(CONTENT_KEY, content);
  }

  public AVFile getImg() {
    return this.getAVFile(img_key);
  }
  public void setImg(AVFile content) {
    this.put(img_key, content);
  }
}
