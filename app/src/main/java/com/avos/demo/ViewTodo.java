
package com.avos.demo;

//Detailed Page for product

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.avos.avoscloud.AVAnalytics;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.SaveCallback;

public class ViewTodo extends Activity {

  private TextView nameText;
  private TextView priceNumber;
  private TextView contentText;
  private TextView contactName;
  private TextView phone;
  private TextView location;
  private String objectId;
  private AVImageView imgView;
  private Button backBtn;



  @Override
  protected void onPause() {
    super.onPause();
    // 页面统计，结束
    AVAnalytics.onPause(this);
  }

  @Override
  protected void onResume() {
    super.onResume();
    // 页面统计，开始
    AVAnalytics.onResume(this);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.view_todo);
    //setTitle(R.string.create_todo);
    setTitle("商品信息");


    contentText = (TextView) findViewById(R.id.content);
    nameText = (TextView) findViewById(R.id.name);
    priceNumber = (TextView) findViewById(R.id.price);
    contactName = (TextView) findViewById(R.id.storename);
    phone = (TextView) findViewById(R.id.storephone);
    location = (TextView) findViewById(R.id.location);


    imgView =(AVImageView)findViewById(R.id.img);
    backBtn=(Button)findViewById(R.id.backBtn);
    backBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });


    Intent intent = getIntent();
    // 通过搜索结果打开
    if (intent.getAction() == Intent.ACTION_VIEW) {
      // 如果是VIEW action，我们通过getData获取URI
      Uri uri = intent.getData();
      String path = uri.getPath();
      int index = path.lastIndexOf("/");
      if (index > 0) {
        // 获取objectId
        objectId = path.substring(index + 1);
        GetCallback<AVObject> getCallback=new GetCallback<AVObject>() {
          @Override
          public void done(AVObject todo, AVException arg1) {
            if (todo != null) {
              String name = todo.getString("name");
              String price = todo.getString("price");
              String content = todo.getString("content");
              String storeName = todo.getString("contactName");
              String phonenum = todo.getString("phoneNum");
              String local = todo.getString("location");

              AVFile file = todo.getAVFile("img");

              if (content != null) {
                contentText.setText(content);
                priceNumber.setText(price);
                nameText.setText(name);
                contactName.setText(storeName);
                phone.setText(phonenum);
                location.setText(local);

                imgView.setAVFile(file);
                imgView.loadInBackground();
              }
            }
          }
        };
        AVService.fetchTodoById(objectId, getCallback);
      }
    } else {
      // 通过ListView点击打开
      Bundle extras = getIntent().getExtras();
      if (extras != null) {
        String name = extras.getString("name");
        String price = extras.getString("price");
        String content = extras.getString("content");
        objectId = extras.getString("objectId");

        // 获取objectId
        GetCallback<AVObject> getCallback=new GetCallback<AVObject>() {
          @Override
          public void done(AVObject todo, AVException arg1) {
            if (todo != null) {
              //String name = todo.getString("name");
              //String price = todo.getString("price");
              //String content = todo.getString("content");
              String storeName = todo.getString("contactName");
              String phonenum = todo.getString("phoneNum");
              String local = todo.getString("location");

              //  contentText.setText(content);
              //   priceNumber.setText(price);
              //   nameText.setText(name);
              contactName.setText(storeName);
              phone.setText(phonenum);
              location.setText(local);


              AVFile file = todo.getAVFile("img");
              imgView.setAVFile(file);
              imgView.loadInBackground();
            }
          }
        };
        AVService.fetchTodoById(objectId, getCallback);


        if (content != null) {
          contentText.setText(content);
          priceNumber.setText(price);
          nameText.setText(name);

        }

      }
    }


  }

}
