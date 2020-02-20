package com.lectinua.memo.list;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter {

    ArrayList<ListItem> items=new ArrayList<>();

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ListView view=new ListView(parent.getContext());
        ListItem item=items.get(position);

        view.setTitle(item.getTitle());
        view.setContent(item.getContent());

        return view;
    }

    public void addItem(ListItem listItem){
        items.add(listItem);
    }
}
