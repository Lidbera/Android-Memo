package com.lectinua.memo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.lectinua.memo.activity.MainActivity;
import com.lectinua.memo.R;
import com.lectinua.memo.database.MemoItem;
import com.lectinua.memo.databinding.FragmentMemoListBinding;
import com.lectinua.memo.list.ListAdapter;
import com.lectinua.memo.list.ListItem;

import java.util.ArrayList;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

public class MemoListFragment extends Fragment {


    public MemoListFragment() {
        // Required empty public constructor
    }

    private FragmentMemoListBinding binding;
    private MainActivity activity;

    private boolean ready;

    public void setActivity(MainActivity activity){
        this.activity = activity;
    }

    @Override
    public void onResume() {
        super.onResume();

        if(ready){
            refresh();
        }
    }

    private ListAdapter adapter;
    private ArrayList<MemoItem> items;
    private String search;
    private boolean empty;

    public void refresh(){
        if(binding == null){
            return;
        }

        adapter = new ListAdapter();

        if(search.length() == 0){
            items = activity.getDatabaseManager().findAllSimpleMemos();
        }else{
            items = activity.getDatabaseManager().findSimpleMemos(search);
        }

        for (MemoItem item : items){
            String title = item.getTitle();
            if(title.length() > 15){
                title = title.substring(0, 15) + "...";
            }
            String content = item.getContent();
            if(content.length() > 20){
                content = content.substring(0, 20) + "...";
            }
            adapter.addItem(new ListItem(title, content));
        }

        empty = false;
        if(adapter.getCount() == 0){
            empty = true;
            adapter.addItem(new ListItem("메모가 없습니다.", "작성해주세요!"));
        }
        binding.listView.setAdapter(adapter);
    }

    public void search(String search){
        this.search = search;
        refresh();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_memo_list, container, false);

        binding.listView.setOnItemClickListener(listener);

        refresh();
        ready = true;

        return binding.getRoot();
    }

    private AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            if(!empty){
                MemoItem item = items.get(i);
                activity.openOneMemo(item.getIndex());
            }
        }
    };
}
