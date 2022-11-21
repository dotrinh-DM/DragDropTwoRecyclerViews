package com.dotrinh.dragdroptworecyclerviews;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements DragListener.Listener {

    //region define
    @BindView(R.id.recyclerViewTop)
    RecyclerView rvTop;
    @BindView(R.id.recyclerViewBottom)
    RecyclerView rvBottom;
    @BindView(R.id.tvEmptyListTop)
    TextView tvEmptyListTop;
    @BindView(R.id.tvEmptyListBottom)
    TextView tvEmptyListBottom;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initTopRecyclerView();
        initBottomRecyclerView();

        tvEmptyListTop.setVisibility(View.GONE);
        tvEmptyListBottom.setVisibility(View.GONE);
    }

    private void initTopRecyclerView() {
        rvTop.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        List<String> topList = new ArrayList<>();
        topList.add("A");
        topList.add("B");
        // topList.add("B1");
        // topList.add("B2");
        // topList.add("B3");

        ListAdapter top_adapter = new ListAdapter(topList, this);
        rvTop.setAdapter(top_adapter);
        tvEmptyListTop.setOnDragListener(top_adapter.getDragInstance());
        rvTop.setOnDragListener(top_adapter.getDragInstance());
    }

    private void initBottomRecyclerView() {
        rvBottom.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        List<String> bottomList = new ArrayList<>();
        bottomList.add("C");
        bottomList.add("D");

        ListAdapter list_adapter = new ListAdapter(bottomList, this);
        rvBottom.setAdapter(list_adapter);
        tvEmptyListBottom.setOnDragListener(list_adapter.getDragInstance());
        rvBottom.setOnDragListener(list_adapter.getDragInstance());
    }

    @Override
    public void setEmptyListTop(boolean visibility) {
        tvEmptyListTop.setVisibility(visibility ? View.VISIBLE : View.GONE);
        rvTop.setVisibility(visibility ? View.GONE : View.VISIBLE);
    }

    @Override
    public void setEmptyListBottom(boolean visibility) {
        tvEmptyListBottom.setVisibility(visibility ? View.VISIBLE : View.GONE);
        rvBottom.setVisibility(visibility ? View.GONE : View.VISIBLE);
    }
}
