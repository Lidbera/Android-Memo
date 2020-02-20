package com.lectinua.memo.fragment;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lectinua.memo.R;
import com.lectinua.memo.activity.MainActivity;
import com.lectinua.memo.database.MemoItem;
import com.lectinua.memo.databinding.FragmentOneMemoBinding;

public class OneMemoFragment extends Fragment {


    public OneMemoFragment() {
        // Required empty public constructor
    }

    private FragmentOneMemoBinding binding;
    private MainActivity activity;

    public void setActivity(MainActivity activity){
        this.activity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_one_memo, container, false);
        binding.setFragment(this);

        setContent();

        return binding.getRoot();
    }

    private int index;
    public void setIndex(int index){
        this.index = index;
    }

    private boolean modify;
    public void ok(View view){
        if(modify){
            modify = false;
            binding.btnModify.setText(R.string.modify);
            binding.btnOk.setText(R.string.ok);
            binding.btnDelete.setVisibility(View.INVISIBLE);
            binding.btnDelete.setEnabled(false);
            binding.editText.setVisibility(View.GONE);
            binding.textView.setVisibility(View.VISIBLE);

            update();
            setContent();
        }
        else{
            activity.openMemoList();
        }
    }
    public void modify(View view){
        if(modify){
            modify = false;
            binding.btnModify.setText(R.string.modify);
            binding.btnOk.setText(R.string.ok);
            binding.btnDelete.setVisibility(View.INVISIBLE);
            binding.btnDelete.setEnabled(false);
            binding.editText.setVisibility(View.GONE);
            binding.textView.setVisibility(View.VISIBLE);
        }
        else{
            modify = true;
            binding.btnModify.setText(R.string.cancel);
            binding.btnOk.setText(R.string.change);
            binding.btnDelete.setVisibility(View.VISIBLE);
            binding.btnDelete.setEnabled(true);
            binding.editText.setVisibility(View.VISIBLE);
            binding.textView.setVisibility(View.GONE);
        }
    }
    public void delete(View view){
        activity.getDatabaseManager().delete(index);
        activity.openMemoList();
    }

    private void update(){
        activity.getDatabaseManager().update(index,
                binding.editTitle.getText().toString(),
                binding.editContent.getText().toString(),
                binding.tvDate2.getText().toString(),
                binding.tvTime2.getText().toString(),
                "");
    }

    public void setContent(){
        MemoItem item = activity.getDatabaseManager().getMemo(index);
        binding.tvTitle.setText(item.getTitle());
        binding.tvContent.setText(item.getContent());
        binding.tvDate.setText(item.getDate());
        binding.tvTime.setText(item.getTime());

        binding.editTitle.setText(item.getTitle());
        binding.editContent.setText(item.getContent());
        binding.tvDate2.setText(item.getDate());
        binding.tvTime2.setText(item.getTime());
    }
}
