package com.avos.demo;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.avos.avoscloud.AVAnalytics;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.SaveCallback;

public class ViewTodo extends Activity {

  private TextView nameText;
  private TextView priceNumber;
  private TextView contentText;
  private String objectId;
  private AVImageView imgView;



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

              if (content != null) {
                contentText.setText(content);
                priceNumber.setText(price);
                nameText.setText(name);

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

        //Todo product = new Todo();
        //product.setObjectId(objectId);
        // 通过Fetch获取content内容
        //product.fetchIfNeededInBackground();

        //AVFile file = product.getImg();
        //imgView.setAVFile(file);
        //imgView.loadInBackground();


        if (content != null) {
          contentText.setText(content);
          priceNumber.setText(price);
          nameText.setText(name);
        }
      }
    }


  }

}
