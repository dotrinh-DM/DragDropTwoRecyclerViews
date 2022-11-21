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
    RecyclerView recyclerViewTop;
    @BindView(R.id.recyclerViewBottom)
    RecyclerView recyclerViewBottom;
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

        recyclerViewTop.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        List<String> topList = new ArrayList<>();
        topList.add("A");
        topList.add("B");
        ListAdapter top_adapter = new ListAdapter(topList, this);
        recyclerViewTop.setAdapter(top_adapter);
        tvEmptyListTop.setOnDragListener(top_adapter.getDragInstance());
        recyclerViewTop.setOnDragListener(top_adapter.getDragInstance());


        List<String> bottomList = new ArrayList<>();
        bottomList.add("C");
        bottomList.add("D");
        ListAdapter list_adapter = new ListAdapter(bottomList, this);
        recyclerViewBottom.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewBottom.setAdapter(list_adapter);
        tvEmptyListBottom.setOnDragListener(list_adapter.getDragInstance());
        recyclerViewBottom.setOnDragListener(list_adapter.getDragInstance());

        tvEmptyListTop.setVisibility(View.GONE);
        tvEmptyListBottom.setVisibility(View.GONE);
    }

    @Override
    public void setEmptyListTop(boolean visibility) {
        tvEmptyListTop.setVisibility(visibility ? View.VISIBLE : View.GONE);
        recyclerViewTop.setVisibility(visibility ? View.GONE : View.VISIBLE);
    }

    @Override
    public void setEmptyListBottom(boolean visibility) {
        tvEmptyListBottom.setVisibility(visibility ? View.VISIBLE : View.GONE);
        recyclerViewBottom.setVisibility(visibility ? View.GONE : View.VISIBLE);
    }
}
