package com.avos.demo;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVFile;

public class TodoAdapter extends BaseAdapter {

  Context mContext;
  List<Todo> todos;

  public TodoAdapter(Context context, List<Todo> todos) {
    mContext = context;
    this.todos = todos;
  }

  @Override
  public int getCount() {
    return todos != null ? todos.size() : 0;
  }

  @Override
  public Object getItem(int position) {
    if (todos != null)
      return todos.get(position);
    else
      return null;
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
	ViewHolder holder;
	
    if (convertView == null) {
      convertView = LayoutInflater.from(mContext).inflate(R.layout.todo_row, null);
      holder = new ViewHolder();
      holder.todo = (TextView)convertView.findViewById(R.id.name);
      holder.price= (TextView)convertView.findViewById(R.id.price);
      holder.imgView =(AVImageView) convertView.findViewById(R.id.img) ;
      convertView.setTag(holder);
    }
    else
    {
    	holder = (ViewHolder)convertView.getTag();
    }
    
    Todo todo = todos.get(position);
    if (todo != null) {
      holder.todo.setText(todo.getName());
      holder.price.setText(todo.getPrice());

      AVFile file = todo.getImg();
      holder.imgView.setAVFile(file);
      holder.imgView.loadInBackground();

      //if (todo["img"] != null) {
        //if let imgUrl:NSURL = NSURL(string:cellInfo["eventPicSmall"] as !String){
        //  cell.picImg.sd_setImageWithURL(imgUrl, placeholderImage:UIImage(named:"waiting.png"))}
      //}

    }
    return convertView;
  }
  
  static class ViewHolder
  {
	  TextView todo;
      TextView price;
      AVImageView imgView;
  }
  

}
