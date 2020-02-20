package com.lectinua.memo.list;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lectinua.memo.R;

public class ListView extends LinearLayout {

    private TextView title, content;

    public ListView(Context context) {
        super(context);
        init(context);
    }

    public ListView(Context context, AttributeSet attrs){
        super(context,attrs);
        init(context);
    }

    public void init(Context context){
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.listview_item,this,true);

        title = findViewById(R.id.tv_title);
        content = findViewById(R.id.tv_content);
    }

    public void setTitle(String title){
        this.title.setText(title);
    }

    public void setContent(String content){
        this.content.setText(content);
    }
}
