package com.dotrinh.dragdroptworecyclerviews;

import static com.dotrinh.dragdroptworecyclerviews.LogUtil.LogI;

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
    public boolean onDrag(View v, DragEvent event) {
        // LogI("event.getAction() " + event.getAction());
        switch (event.getAction()) {
            case 1: {
                LogI("ACTION_DRAG_STARTED bat dau keo");
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
                int viewId = v.getId();
                final int flItem = R.id.frame_layout_item;
                final int tvEmptyListTop = R.id.tvEmptyListTop;
                final int tvEmptyListBottom = R.id.tvEmptyListBottom;
                final int rvTop = R.id.recyclerViewTop;
                final int rvBottom = R.id.recyclerViewBottom;

                switch (viewId) {
                    case flItem:
                    case tvEmptyListTop:
                    case tvEmptyListBottom:
                    case rvTop:
                    case rvBottom: {
                        RecyclerView target;
                        switch (viewId) {
                            case tvEmptyListTop:
                            case rvTop: {
                                target = (RecyclerView) v.getRootView().findViewById(rvTop);
                                break;
                            }
                            case tvEmptyListBottom:
                            case rvBottom: {
                                target = (RecyclerView) v.getRootView().findViewById(rvBottom);
                                break;
                            }
                            default: {
                                target = (RecyclerView) v.getParent();
                                positionTarget = (int) v.getTag();
                            }
                        }
                        if (viewSource != null) {
                            RecyclerView source = (RecyclerView) viewSource.getParent();

                            ListAdapter adapterSource = (ListAdapter) source.getAdapter();
                            int positionSource = (int) viewSource.getTag();
                            int sourceId = source.getId();

                            String list = adapterSource.getList().get(positionSource);
                            List<String> listSource = adapterSource.getList();

                            listSource.remove(positionSource);
                            adapterSource.updateList(listSource);
                            adapterSource.notifyDataSetChanged();

                            ListAdapter adapterTarget = (ListAdapter) target.getAdapter();
                            List<String> customListTarget = adapterTarget.getList();
                            if (positionTarget >= 0) {
                                customListTarget.add(positionTarget, list);
                            } else {
                                customListTarget.add(list);
                            }
                            adapterTarget.updateList(customListTarget);
                            adapterTarget.notifyDataSetChanged();

                            if (sourceId == rvBottom && adapterSource.getItemCount() < 1) {
                                listener.setEmptyListBottom(true);
                            }
                            if (viewId == tvEmptyListBottom) {
                                listener.setEmptyListBottom(false);
                            }
                            if (sourceId == rvTop && adapterSource.getItemCount() < 1) {
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