package com.lectinua.memo.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.lectinua.memo.R;
import com.lectinua.memo.database.DatabaseManager;
import com.lectinua.memo.databinding.ActivityMainBinding;
import com.lectinua.memo.fragment.MemoListFragment;
import com.lectinua.memo.fragment.OneMemoFragment;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private InputMethodManager imm;
    private DatabaseManager databaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();
    }

    @Override
    protected void onResume() {
        super.onResume();

        openMemoList();
    }

    private void init(){
        animationSet();

        Log.i("prac", "ac:" + this);

        binding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);
        binding.setActivity(this);

        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(binding.editInputSearch.getWindowToken(), 0);

        databaseManager = new DatabaseManager(getApplicationContext(),
                "MY_MEMO.db", null, 1);

        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        oneMemoFragment = new com.lectinua.memo.fragment.OneMemoFragment();
        memoListFragment = new com.lectinua.memo.fragment.MemoListFragment();
        findListFragment = new com.lectinua.memo.fragment.MemoListFragment();

        openMemoList();
    }

    private final long FINISH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;

    @Override
    public void onBackPressed() {
        if(view_more_open){
            view_moreClose();
        }
        else if(frag_num == 1 || frag_num == 3){
            openMemoList();
        }
        else if(view_search_open){
            view_searchClose();
        }
        else{
            long tempTime = System.currentTimeMillis();
            long intervalTime = tempTime - backPressedTime;
            if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime) {
                super.onBackPressed();
            } else {
                backPressedTime = tempTime;
                Toast.makeText(this, "한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private int frag_num;
    private Fragment fragment;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private OneMemoFragment oneMemoFragment;
    private MemoListFragment memoListFragment, findListFragment;

    //region MemoUI
    public void btn_newMemo(View view){
        NewMemo();
        view_moreClose();
    }

    private void NewMemo(){
        Intent intent = new Intent(MainActivity.this, NewMemoActivity.class);
        startActivity(intent);
    }

    public void openOneMemo(int index){
        frag_num = 1;
        binding.tvHome.setText(R.string.home);
        oneMemoFragment.setActivity(this);
        oneMemoFragment.setIndex(index);
        fragment = oneMemoFragment;
        openFragment();
    }
    public void openMemoList(){
        frag_num = 2;
        binding.tvHome.setText(R.string.title);
        memoListFragment.setActivity(this);
        memoListFragment.search("");
        fragment = memoListFragment;
        openFragment();
    }
    public void findMemoList(String search){
        frag_num = 3;
        binding.tvHome.setText(R.string.home);
        findListFragment.setActivity(this);
        findListFragment.search(search);
        fragment = findListFragment;
        openFragment();
    }
    private void openFragment(){
        transaction = manager.beginTransaction();
        transaction.replace(R.id.view_memoList, fragment).commitAllowingStateLoss();
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }
    //endregion

    //region MainUI
    public void home(View view){
        binding.tvHome.setText(R.string.title);
        openMemoList();
    }

    private boolean view_more_open, view_more_interactable = true;
    public void btn_more(View view){
        if(!view_more_open){
            if(view_more_interactable){
                view_more_interactable = false;
            }else{
                return;
            }
            binding.viewMore.startAnimation(right_viewMore);
            binding.viewMore.setVisibility(View.VISIBLE);
            binding.viewMoreBack.startAnimation(show);
            binding.viewMoreBack.setVisibility(View.VISIBLE);
            binding.viewMoreClose.setVisibility(View.VISIBLE);
            binding.btnNewMemo.setVisibility(View.VISIBLE);
        }
    }
    public void btn_closeMore(View view){
        view_moreClose();
    }
    private void view_moreClose(){
        binding.viewMore.startAnimation(left_viewMore);
        binding.viewMore.setVisibility(View.GONE);
        binding.viewMoreBack.startAnimation(hide);
        binding.viewMoreBack.setVisibility(View.GONE);
        binding.viewMoreClose.setVisibility(View.GONE);
        binding.btnNewMemo.setVisibility(View.GONE);
    }

    private boolean view_search_open;
    public void btn_search(View view){
        if(view_more_open){
            return;
        }

        if(!view_search_open){
            view_search_open = true;
            binding.viewSearch.startAnimation(show);
            binding.viewSearch.setVisibility(View.VISIBLE);
            binding.viewMain.startAnimation(hide);
            binding.viewMain.setVisibility(View.GONE);
            binding.btnSearch.animate().translationX(-100).start();
            binding.btnSearchClose.startAnimation(show);
            binding.btnSearchClose.setVisibility(View.VISIBLE);

            binding.btnSearch.setTranslationZ(10);
            binding.btnSearchClose.setTranslationZ(20);

            binding.editInputSearch.setEnabled(true);
            imm.showSoftInput(binding.editInputSearch, 0);
            binding.editInputSearch.requestFocus();
        }else{
            findMemoList(binding.editInputSearch.getText().toString());
        }
    }
    public void btn_searchClose(View view){
        if(view_more_open){
            return;
        }

        binding.editInputSearch.setText("");
        view_searchClose();
    }
    private void view_searchClose(){
        if(view_search_open){
            view_search_open = false;
            binding.viewSearch.startAnimation(hide);
            binding.viewSearch.setVisibility(View.GONE);
            binding.viewMain.startAnimation(show);
            binding.viewMain.setVisibility(View.VISIBLE);
            binding.btnSearch.animate().translationX(0).start();
            binding.btnSearchClose.startAnimation(hide);
            binding.btnSearchClose.setVisibility(View.GONE);

            binding.btnSearch.setTranslationZ(20);
            binding.btnSearchClose.setTranslationZ(10);

            imm.hideSoftInputFromWindow(binding.editInputSearch.getWindowToken(), 0);
            //binding.editInputSearch.setInputType(InputType.TYPE_NULL);
            binding.editInputSearch.requestFocus();
            binding.editInputSearch.setEnabled(false);
        }
    }

    private Animation left_viewMore, right_viewMore, show, hide;
    private void animationSet(){
        left_viewMore = AnimationUtils.loadAnimation(this, R.anim.left_view_more);
        right_viewMore = AnimationUtils.loadAnimation(this, R.anim.right_view_more);
        show = AnimationUtils.loadAnimation(this, R.anim.show);
        hide = AnimationUtils.loadAnimation(this, R.anim.hide);

        left_viewMore.setAnimationListener(anim_viewMore);
        right_viewMore.setAnimationListener(anim_viewMore);
    }

    private Animation.AnimationListener anim_viewMore = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            if(view_more_open){
                view_more_open = false;
                view_more_interactable = true;
            }else{
                view_more_open = true;
            }
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };
    //endregion
}
