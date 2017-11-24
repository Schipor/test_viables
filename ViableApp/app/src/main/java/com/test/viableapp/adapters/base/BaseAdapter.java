package com.test.viableapp.adapters.base;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAdapter<Item> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Item> mItems;

    public abstract ArrayList<Item> setFilterLogic(List<Item> originalList, String query);

    public BaseAdapter(ArrayList<Item> items) {
        mItems = new ArrayList<>(items);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    //region Filter
    private Item removeItem(int position) {
        final Item item = mItems.remove(position);
        notifyItemRemoved(position);
        return item;
    }

    private void addItem(int position, Item item) {
        mItems.add(position, item);
        notifyItemInserted(position);
    }

    private void moveItem(int fromPosition, int toPosition) {
        final Item item = mItems.remove(fromPosition);
        mItems.add(toPosition, item);
        notifyItemMoved(fromPosition, toPosition);
    }

    private void animateTo(List<Item> items) {
        applyAndAnimateRemovals(items);
        applyAndAnimateAdditions(items);
        applyAndAnimateMovedItems(items);
    }

    private void applyAndAnimateRemovals(List<Item> newItems) {
        for (int i = mItems.size() - 1; i >= 0; i--) {
            final Item item = mItems.get(i);
            if (!newItems.contains(item)) {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(List<Item> newItems) {
        for (int i = 0, count = newItems.size(); i < count; i++) {
            final Item item = newItems.get(i);
            if (!mItems.contains(item)) {
                addItem(i, item);
            }
        }
    }

    private void applyAndAnimateMovedItems(List<Item> newITems) {
        for (int toPosition = newITems.size() - 1; toPosition >= 0; toPosition--) {
            final Item item = newITems.get(toPosition);
            final int fromPosition = mItems.indexOf(item);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }

    protected void filter(List<Item> originalList, String query, Runnable callback) {
        query = query.toLowerCase();
        animateTo(setFilterLogic(originalList, query));
        if (callback != null) callback.run();
    }
    //endregion

    protected void refreshList(List<Item> list, Runnable callback) {
        mItems.clear();
        mItems.addAll(list);
        notifyDataSetChanged();

        if (callback != null) callback.run();
    }

    public ArrayList<Item> getItems() {
        return mItems;
    }
}
