package com.dotrinh.dragdroptworecyclerviews;

import android.content.ClipData;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {

    private List<String> list;
    private DragListener.Listener listener;

    ListAdapter(List<String> list, DragListener.Listener listener) {
        this.list = list;
        this.listener = listener;
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        holder.text.setText(list.get(position));
        holder.frameLayoutCell.setTag(position);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    List<String> getList() {
        return list;
    }

    void updateList(List<String> list) {
        this.list = list;
    }

    DragListener getDragInstance() {
        if (listener != null) {
            return new DragListener(listener);
        } else {
            Log.e("ListAdapter", "Listener wasn't initialized!");
            return null;
        }
    }

    class ListViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text)
        TextView text;
        @BindView(R.id.frame_layout_cell)
        FrameLayout frameLayoutCell;

        @RequiresApi(api = Build.VERSION_CODES.N)
        ListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            frameLayoutCell.setOnLongClickListener(myView -> {
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(myView);
                myView.startDragAndDrop(data, shadowBuilder, myView, 0);
                return true;
            });
            frameLayoutCell.setOnDragListener(new DragListener(listener));
        }
    }

    // @Override
    // public boolean onTouch(View myView, MotionEvent event) {
    //     LogI("onTouch: " + myView.getId());
    //     switch (event.getAction()) {
    //         case MotionEvent.ACTION_DOWN:
    //             ClipData data = ClipData.newPlainText("", "");
    //             View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(myView);
    //             if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
    //                 myView.startDragAndDrop(data, shadowBuilder, myView, 0);
    //             } else {
    //                 myView.startDrag(data, shadowBuilder, myView, 0);
    //             }
    //             return true;
    //     }
    //     return false;
    // }
}
