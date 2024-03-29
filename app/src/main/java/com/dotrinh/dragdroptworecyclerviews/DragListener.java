package com.dotrinh.dragdroptworecyclerviews;

import static com.dotrinh.dragdroptworecyclerviews.tool.LogUtil.LogI;

import android.view.DragEvent;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DragListener implements View.OnDragListener {

    private boolean isDropped = false;
    private Listener listener;

    DragListener(Listener listener) {
        this.listener = listener;
    }

    @Override
    public boolean onDrag(View myView, DragEvent event) {
        // LogI("event.getAction() " + event.getAction());
        switch (event.getAction()) {
            case 1: {
                LogI("ACTION_DRAG_STARTED bat dau keo vao khu vuc view lang nghe");
                break;
            }
            case 2: {
                LogI("ACTION_DRAG_LOCATION di chuyen trong view");
                break;
            }
            case 3: {
                LogI("ACTION_DROP drop vao view thanh cong");
                isDropped = true;
                int positionTarget = -1;

                View viewSource = (View) event.getLocalState();
                int viewId = myView.getId();
                final int frame_layout_cell = R.id.frame_layout_cell;
                final int tvEmptyListTop = R.id.tvEmptyListTop;
                final int tvEmptyListBottom = R.id.tvEmptyListBottom;
                final int recyclerViewTop = R.id.recyclerViewTop;
                final int recyclerViewBottom = R.id.recyclerViewBottom;

                switch (viewId) {
                    case frame_layout_cell:
                    case tvEmptyListTop:
                    case tvEmptyListBottom:
                    case recyclerViewTop:
                    case recyclerViewBottom: {
                        // Xac dinh target
                        RecyclerView target;
                        switch (viewId) {
                            case tvEmptyListTop:
                            case recyclerViewTop: {
                                target = (RecyclerView) myView.getRootView().findViewById(recyclerViewTop);
                                break;
                            }
                            case tvEmptyListBottom:
                            case recyclerViewBottom: {
                                target = (RecyclerView) myView.getRootView().findViewById(recyclerViewBottom);
                                break;
                            }
                            default: {
                                target = (RecyclerView) myView.getParent();
                                positionTarget = (int) myView.getTag();
                            }
                        }
                        if (viewSource != null) {
                            RecyclerView source = (RecyclerView) viewSource.getParent();

                            //src
                            ListAdapter adapter_source = (ListAdapter) source.getAdapter();
                            int positionSource = (int) viewSource.getTag();
                            int sourceId = source.getId();
                            String list = adapter_source.getList().get(positionSource);
                            List<String> listSource = adapter_source.getList();
                            listSource.remove(positionSource);
                            adapter_source.updateList(listSource);
                            adapter_source.notifyDataSetChanged();

                            //target
                            ListAdapter adapter_target = (ListAdapter) target.getAdapter();
                            List<String> customListTarget = adapter_target.getList();
                            if (positionTarget >= 0) {
                                customListTarget.add(positionTarget, list);
                            } else {
                                customListTarget.add(list);
                            }
                            adapter_target.updateList(customListTarget);
                            adapter_target.notifyDataSetChanged();

                            if (sourceId == recyclerViewBottom && adapter_source.getItemCount() < 1) {
                                listener.setEmptyListBottom(true);
                            }
                            if (viewId == tvEmptyListBottom) {
                                listener.setEmptyListBottom(false);
                            }
                            if (sourceId == recyclerViewTop && adapter_source.getItemCount() < 1) {
                                listener.setEmptyListTop(true);
                            }
                            if (viewId == tvEmptyListTop) {
                                listener.setEmptyListTop(false);
                            }
                        }
                        break;
                    }
                }
                break;
            }
            case 4: {
                LogI("ACTION_DRAG_ENDED ket thuc thanh cong va ko thanh cong");
                break;
            }
            case 5: {
                LogI("ACTION_DRAG_ENTERED vào view");
                break;
            }
            case 6: {
                LogI("ACTION_DRAG_EXITED ra khỏi view");
                break;
            }
        }
        if (!isDropped && event.getLocalState() != null) {
            ((View) event.getLocalState()).setVisibility(View.VISIBLE);
        }
        return true;
    }

    interface Listener {
        void setEmptyListTop(boolean visibility);

        void setEmptyListBottom(boolean visibility);
    }
}