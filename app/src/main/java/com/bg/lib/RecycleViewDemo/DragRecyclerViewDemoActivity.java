package com.bg.lib.RecycleViewDemo;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bg.lib.R;
import com.bg.library.UI.Activity.PresenterActivity;
import com.bg.library.UI.View.DragRecyclerView.DragClassicFrameLayout;
import com.bg.library.UI.View.DragRecyclerView.DragDefaultHandler;
import com.bg.library.UI.View.DragRecyclerView.DragFrameLayout;
import com.bg.library.UI.View.DragRecyclerView.loadmore.OnLoadMoreListener;
import com.bg.library.UI.View.DragRecyclerView.recyclerview.RecyclerAdapterWithHF;

import java.util.ArrayList;
import java.util.List;

public class DragRecyclerViewDemoActivity extends PresenterActivity {
    DragClassicFrameLayout dragClassicFrameLayout;
    RecyclerView mRecyclerView;
    private List<String> mData = new ArrayList<String>();
    private RecyclerAdapter adapter;
    private RecyclerAdapterWithHF mAdapter;
    Handler handler = new Handler();

    int page = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_recycler_view);

        dragClassicFrameLayout = (DragClassicFrameLayout) findViewById(R.id.test_recycler_view_frame);
        mRecyclerView = (RecyclerView) findViewById(R.id.test_recycler_view);
        init();
    }

    private void init() {
        adapter = new RecyclerAdapter(this, mData);
        mAdapter = new RecyclerAdapterWithHF(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        dragClassicFrameLayout.postDelayed(new Runnable() {

            @Override
            public void run() {
                dragClassicFrameLayout.autoRefresh(true);
            }
        }, 150);

        dragClassicFrameLayout.setDragHandler(new DragDefaultHandler() {

            @Override
            public void onRefreshBegin(DragFrameLayout frame) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page = 0;
                        mData.clear();
                        for (int i = 0; i < 17; i++) {
                            mData.add(new String("  RecyclerView item  -" + i));
                        }
                        mAdapter.notifyDataSetChanged();
                        dragClassicFrameLayout.refreshComplete();
                        dragClassicFrameLayout.setLoadMoreEnable(true);
                    }
                }, 1500);
            }
        });

        dragClassicFrameLayout.setOnLoadMoreListener(new OnLoadMoreListener() {

            @Override
            public void loadMore() {
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        mData.add(new String("  RecyclerView item  - add " + page));
                        mAdapter.notifyDataSetChanged();
                        dragClassicFrameLayout.loadMoreComplete(true);
                        page++;
                        Toast.makeText(DragRecyclerViewDemoActivity.this, "load more complete", Toast.LENGTH_SHORT).show();
                    }
                }, 1000);
            }
        });
    }

    public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private List<String> datas;
        private LayoutInflater inflater;

        public RecyclerAdapter(Context context, List<String> data) {
            super();
            inflater = LayoutInflater.from(context);
            datas = data;
        }

        @Override
        public int getItemCount() {
            return datas.size();
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
            ChildViewHolder holder = (ChildViewHolder) viewHolder;
            holder.itemTv.setText(datas.get(position));
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewHolder, int position) {
            View view = inflater.inflate(R.layout.listitem_layout, null);
            return new ChildViewHolder(view);
        }

    }

    public class ChildViewHolder extends RecyclerView.ViewHolder {
        public TextView itemTv;

        public ChildViewHolder(View view) {
            super(view);
            itemTv = (TextView) view;
        }

    }

}
