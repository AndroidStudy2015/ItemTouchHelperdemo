package co.paulburke.android.itemtouchhelperdemo.helper;

import android.support.v7.widget.RecyclerView;

/**
 * Interface to notify a {@link RecyclerView.Adapter} of moving and dismissal event from a {@link
 * android.support.v7.widget.helper.ItemTouchHelper.Callback}.
 *
 * @author Paul Burke (ipaulpro)
 */
public interface ItemTouchHelperAdapter {

    /**
     * Called when an item has been dragged far enough to trigger a move. This is called every time
     * an item is shifted, and not at the end of a "drop" event.
     *
     *当一个条目被拖拽移动时，该方法将被调用，当条目被移动时，该方法都会被调用，而不是在拖拽结尾才被调用
     *
     * @param fromPosition The start position of the moved item.
     * @param toPosition   Then end position of the moved item.
     * @see RecyclerView#getAdapterPositionFor(RecyclerView.ViewHolder)
     * @see RecyclerView.ViewHolder#getAdapterPosition()
     */

    void onItemMove(int fromPosition, int toPosition);


    /**
     * Called when an item has been dismissed by a swipe.
     *
     * 当左右滑动条目时，调用该方法
     *
     * @param position The position of the item dismissed.
     * @see RecyclerView#getAdapterPositionFor(RecyclerView.ViewHolder)
     * @see RecyclerView.ViewHolder#getAdapterPosition()
     */
    void onItemDismiss(int position);
}
