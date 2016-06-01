package co.paulburke.android.itemtouchhelperdemo.helper;

import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Interface to notify an item ViewHolder of relevant callbacks from {@link
 * android.support.v7.widget.helper.ItemTouchHelper.Callback}.
 *
 * @author Paul Burke (ipaulpro)
 */
public interface ItemTouchHelperViewHolder {

    /**
     * Called when the {@link ItemTouchHelper} first registers an item as being moved or swiped.
     * Implementations should update the item view to indicate it's active state.
     * 当ItemTouchHelper将拖拽/侧滑注册（作用）到条目上时，实现该方法去更新这个条目的视图效果，
     * 以暗示该条目正在被操作
     *
     */
    void onItemSelected();


    /**
     * Called when the {@link ItemTouchHelper} has completed the move or swipe, and the active item
     * state should be cleared.
     * 当ItemTouchHelper完成了拖拽/侧滑时，激活的条目效果应该被消除
     */
    void onItemClear();
}
