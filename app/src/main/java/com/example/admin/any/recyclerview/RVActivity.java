package com.example.admin.any.recyclerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.example.admin.any.R;
import it.gmariotti.recyclerview.adapter.AlphaAnimatorAdapter;
import it.gmariotti.recyclerview.itemanimator.ScaleInOutItemAnimator;
import it.gmariotti.recyclerview.itemanimator.SlideInOutBottomItemAnimator;
import it.gmariotti.recyclerview.itemanimator.SlideInOutLeftItemAnimator;
import it.gmariotti.recyclerview.itemanimator.SlideInOutTopItemAnimator;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kk on 2017/11/25.
 * recyclerView
 */

public class RVActivity extends AppCompatActivity implements View.OnClickListener{

  private RecyclerView mRecyclerView;
  private List<String> numberList;
  private MyAdapter myAdapter;
  private Button add,remove;
  private static final String TAG = "RVActivity";

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_recycler);
    initData();
    initView();
  }

  private void initView() {
    add = (Button) findViewById(R.id.bt_add);
    remove = (Button) findViewById(R.id.bt_remove);
    add.setOnClickListener(this);
    remove.setOnClickListener(this);
    mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
    mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    mRecyclerView.addItemDecoration(new DividerItemDecoration(RVActivity.this,DividerItemDecoration.VERTICAL_LIST));
    //mRecyclerView.setLayoutManager(new GridLayoutManager(this,4));
    //mRecyclerView.addItemDecoration(new DividerGridItemDecoration(RVActivity.this));
    myAdapter = new MyAdapter();
    mRecyclerView.setAdapter(myAdapter);
    mRecyclerView.setItemAnimator(new DefaultItemAnimator());
  }

  private void initData(){
    numberList = new ArrayList<>();
    for (int i = 'A'; i < 'z'; i++)
    {
      numberList.add("" + (char) i);
    }
  }

  @Override public void onClick(View v) {
    switch (v.getId()){
      case R.id.bt_add:
        myAdapter.addData(0);
        break;
      case R.id.bt_remove:
        myAdapter.removeData(0);
        break;
    }
  }

  private class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

    @Override public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      MyViewHolder mh = new MyViewHolder(LayoutInflater.from(RVActivity.this).inflate(R.layout.item_recycler,parent,false));
      return mh;
    }

    @Override public void onBindViewHolder(MyViewHolder holder, int position) {
      holder.tv.setText(numberList.get(position));
    }

    @Override public int getItemCount() {
      return numberList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

      TextView tv;

      public MyViewHolder(View itemView) {
        super(itemView);
        tv  = (TextView)itemView.findViewById(R.id.number);
      }
    }

    public void addData(int position){
      numberList.add(position,"insert");
      notifyItemInserted(position);

    }

    public void removeData(int position){
        numberList.remove(position);
        notifyItemRemoved(position);
    }

  }

}
