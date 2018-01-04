package com.bg.lib.RecycleViewDemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bg.lib.R;
import com.bg.library.UI.Activity.PresenterActivity;
import com.bg.library.UI.View.DragRecyclerView.DragClassicFrameLayout;
import com.bg.library.UI.View.DragRecyclerView.DragDefaultHandler;
import com.bg.library.UI.View.DragRecyclerView.DragFrameLayout;
import com.bg.library.UI.View.DragRecyclerView.DragNoneHandler;
import com.bg.library.UI.View.DragRecyclerView.DragRefreshLayout;
import com.bg.library.UI.View.DragRecyclerView.loadmore.OnLoadMoreListener;
import com.bg.library.UI.View.DragRecyclerView.recyclerview.RecyclerAdapterWithHF;
import com.bg.library.Utils.Log.LogUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DragRecyclerViewDemoActivity extends PresenterActivity {
    DragRefreshLayout dragRefreshLayout;
    RecyclerView mRecyclerView;
    private List<String> mData = new ArrayList<String>();
    private RecyclerAdapter adapter;
    private RecyclerAdapterWithHF mAdapter;
    Handler handler = new Handler();
    Map<String, BitmapRegionDecoder> map;
    LruCache<String, Bitmap> bitmapLruCache;

    int page = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_recycler_view);
        map = new HashMap<>();
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024 / 1024);
        bitmapLruCache = new LruCache<String, Bitmap>(maxMemory / 6) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight() / 1024 / 1024;
            }
        };

        dragRefreshLayout = (DragRefreshLayout) findViewById(R.id.test_recycler_view_frame);
        dragRefreshLayout.setDurationToCloseHeader(500);
        mRecyclerView = (RecyclerView) findViewById(R.id.test_recycler_view);
        init();
    }

    private void init() {
        adapter = new RecyclerAdapter(this, mData);
        mAdapter = new RecyclerAdapterWithHF(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        dragRefreshLayout.postDelayed(new Runnable() {

            @Override
            public void run() {
                dragRefreshLayout.autoRefresh(true);
            }
        }, 150);

        dragRefreshLayout.setDragHandler(new DragDefaultHandler() {

            @Override
            public void onRefreshBegin(DragFrameLayout frame) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dragRefreshLayout.refreshComplete();
                    }
                }, 2200);
            }

            @Override
            public void onHeaderClose(DragFrameLayout frame) {
                page = 0;
                mData.clear();
                try {
                    String[] images = getAssets().list("test");
                    for (String image : images) {
                        mData.add("test/" + image);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mAdapter.notifyDataSetChanged();
                dragRefreshLayout.setLoadMoreEnable(true);
                Toast.makeText(DragRecyclerViewDemoActivity.this, "onHeaderClose", Toast.LENGTH_SHORT).show();
            }
        });

        dragRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {

            @Override
            public void loadMore() {
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        mData.add(new String("  RecyclerView item  - add " + page));
                        mAdapter.notifyDataSetChanged();
                        dragRefreshLayout.loadMoreComplete(true);
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
        private Map<Integer, int[]> indexMap;
        private List<ImageItem> items;

        public RecyclerAdapter(Context context, List<String> data) {
            super();
            inflater = LayoutInflater.from(context);
            datas = data;
            items = new ArrayList<>();
        }

        @Override
        public int getItemCount() {
            items.clear();
            for (int i = 0; i < datas.size(); i++) {
                try {
                    String path = datas.get(i);
                    InputStream is = DragRecyclerViewDemoActivity.this.getAssets().open(path);
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    BitmapFactory.decodeStream(is, null, options);
                    int width = options.outWidth;
                    int height = options.outHeight;
                    if (height > width * 2) {
                        int size = height / width;
                        for (int j = 0; j < size; j++) {
                            ImageItem item = new ImageItem();
                            item.index = i;
                            items.add(item);
                            item.rect = new Rect(0, j * width, width, (j + 1) * width);
                        }
                        int last = height % width;
                        if (last > 0) {
                            ImageItem item = new ImageItem();
                            item.index = i;
                            items.add(item);
                            item.rect = new Rect(0, size * width, width, height);
                        }
                    } else {
                        ImageItem item = new ImageItem();
                        item.index = i;
                        item.rect = new Rect(0, 0, width, height);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return items.size();
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
            ChildViewHolder holder = (ChildViewHolder) viewHolder;
            ImageItem item = items.get(position);
            String path = datas.get(item.index);
            BitmapRegionDecoder regionDecoder = map.get(path);
            if (regionDecoder == null || regionDecoder.isRecycled()) {
                try {
                    regionDecoder = BitmapRegionDecoder.newInstance(DragRecyclerViewDemoActivity.this.getAssets().open(path), true);
                    map.put(path, regionDecoder);
                    LogUtils.d("创建BitmapRegionDecoder：" + path);
                } catch (IOException e) {
                    e.printStackTrace();
                    holder.item.setBackgroundColor(Color.RED);
                }
            }
            String key = item.path + "_" + position;
            Bitmap bitmap = bitmapLruCache.get(key);
            if (bitmap == null) {
                if (regionDecoder != null) {
                    bitmap = regionDecoder.decodeRegion(item.rect, null);
                    bitmapLruCache.put(key, bitmap);
                }
            }
            holder.item.setRect(item.rect);
            holder.item.setImageBitmap(bitmap);
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewHolder, int position) {
//            View view = inflater.inflate(R.layout.listitem_layout, null);
            CutImageView iv = new CutImageView(DragRecyclerViewDemoActivity.this);
            return new ChildViewHolder(iv);
        }

    }

    public class ChildViewHolder extends RecyclerView.ViewHolder {
        public CutImageView item;

        public ChildViewHolder(View view) {
            super(view);
            item = (CutImageView) view;
        }

    }

    class ImageItem {
        String path;
        int index;
        Rect rect;
    }

}
