package com.example.administrator.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rv;
    private SwipeAdapter mAdapter;
    private List<String> mData;

    {
        mData = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            mData.add("this is "+ i);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAdapter=new SwipeAdapter(mData);
        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));
        mAdapter.bindToRecyclerView(rv);
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.tv_del){
                    adapter.notifyItemRemoved(position);
                    mData.remove(position);
                    mAdapter.onClose();
                }else{
                    mAdapter.onClose();
                    Collections.swap(mData,position,0);
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}
