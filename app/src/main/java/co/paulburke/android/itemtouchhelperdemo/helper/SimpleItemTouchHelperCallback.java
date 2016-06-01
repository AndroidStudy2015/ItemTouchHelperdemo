package co.paulburke.android.itemtouchhelperdemo.helper;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * An implementation of {@link ItemTouchHelper.Callback} that enables basic drag & drop and
 * swipe-to-dismiss. Drag events are automatically started by an item long-press.<br/>
 * </br/>
 * Expects the <code>RecyclerView.Adapter</code> to react to {@link
 * ItemTouchHelperAdapter} callbacks and the <code>RecyclerView.ViewHolder</code> to implement
 * {@link ItemTouchHelperViewHolder}.
 *
 * 接下来的两个是onMove()和onSwiped()，用于通知底层数据的更新。
 *
 * @author Paul Burke (ipaulpro)
 */
public class SimpleItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private final ItemTouchHelperAdapter mAdapter;

    public SimpleItemTouchHelperCallback(ItemTouchHelperAdapter adapter) {
        mAdapter = adapter;
    }

    /**
     * 要支持长按RecyclerView item进入拖动操作，
     * 你必须在isLongPressDragEnabled()方法中返回true。
     * 或者，也可以调用ItemTouchHelper.startDrag(RecyclerView.ViewHolder)
     * 方法来开始一个拖动。
     */
    @Override
    public boolean isLongPressDragEnabled() {

        return true;//返回true表示支持长按开始拖拽
    }

    /**
     * 而要在view任意位置触摸事件发生时启用滑动操作，
     * 则直接在sItemViewSwipeEnabled()中返回true就可以了。
     * 或者，你也主动调用ItemTouchHelper.startSwipe(RecyclerView.ViewHolder)
     * 来开始滑动操作。
     */
    @Override
    public boolean isItemViewSwipeEnabled() {

        return true;//返回true表示支持左右滑动
    }

    /**
     * ItemTouchHelper可以让你轻易得到一个事件的方向。
     * 你需要重写getMovementFlags()方法来指定可以支持
     * 的拖放和滑动的方向。
     * 使用helperItemTouchHelper.makeMovementFlags(int, int)
     * 来构造返回的flag。这里我们启用了上下左右两种方向。
     * 注：上下为拖动（drag），左右为滑动（swipe）。
     */
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        final int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(dragFlags, swipeFlags);//表示支持上下拖拽和左右滑动
//        return makeMovementFlags(dragFlags, 0);//第二项为0，表示你支持拖拽，不支持左右滑动
    }

    /**
     * Called when ItemTouchHelper wants to move the dragged item from its old position to
     * the new position.
     * 当移动一个条目时被调用
     * <p>
     * If this method returns true, ItemTouchHelper assumes {@code viewHolder} has been moved
     * to the adapter position of {@code target} ViewHolder
     * ({@link RecyclerView.ViewHolder#getAdapterPosition()
     * ViewHolder#getAdapterPosition()}).
     * 如果该方法返回true，认定条目已经移动到了新的位置
     * <p>
     * If you don't support drag & drop, this method will never be called.
     * 如果你不支持拖拽，此方法不会被调用
     *
     * @param recyclerView The RecyclerView to which ItemTouchHelper is attached to.
     *                     ItemTouchHelper所附着的RecyclerView
     * @param source   The ViewHolder which is being dragged by the user.
     *                 正在被用户拖拽的位置
     * @param target       The ViewHolder over which the currently active item is being
     *                     dragged.
     *                     当前正在被拖拽的条目所经过的位置
     * @return True if the {@code viewHolder} has been moved to the adapter position of
     * {@code target}.
     * @see #onMoved(RecyclerView, RecyclerView.ViewHolder, int, RecyclerView.ViewHolder, int, int, int)
     */


    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder source, RecyclerView.ViewHolder target) {
        mAdapter.onItemMove(source.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }
    /**
     * Called when a ViewHolder is swiped by the user.
     * 当被左右滑动时，调用该方法
     * 注意：这里的ViewHolder统一翻译成条目，通过ViewHolder可以得到关于条目的所有属性（如：位置等等）
     * <p>
     * If you are returning relative directions ( {START} , {END}) from the
     * {@link #getMovementFlags(RecyclerView, RecyclerView.ViewHolder)} method, this method
     * will also use relative directions. Otherwise, it will use absolute directions.
     * 当你返回一个相对方向时（由getMovementFlags()所返回的方向），该方法使用的是相对方向，否则使用绝对方向
     * <p>
     * If you don't support swiping, this method will never be called.
     * 如果你不支持左右滑动，该方法不会被调用
     * <p>
     * ItemTouchHelper will keep a reference to the View until it is detached from
     * RecyclerView.
     * ItemTouchHelper将会持有View的引用，直到ItemTouchHelper不再附着在RecyclerView上时
     * As soon as it is detached, ItemTouchHelper will call
     * {@link #clearView(RecyclerView, RecyclerView.ViewHolder)}.
     * 当不再附着时，ItemTouchHelper会调用clearView方法
     *
     * @param viewHolder The ViewHolder which has been swiped by the user.
     *                   用户拖拽的条目
     * @param direction  The direction to which the ViewHolder is swiped. It is one of
     *                   {UP}, {DOWN},
     *                   {LEFT} or {RIGHT}. If your
     *                   {@link #getMovementFlags(RecyclerView, RecyclerView.ViewHolder)}
     *                   method
     *                   returned relative flags instead of {LEFT} / {RIGHT};
     *                   `direction` will be relative as well. ({START} or {
     *                   END}).
     *                   条目被左右滑动的方向，他是上、下、左、右中的一个，如果getMovementFlags返回了
     *                   相对标志flags代替了左、右方向，将使用这个返回的方向
     *
     */
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        mAdapter.onItemDismiss(viewHolder.getAdapterPosition());
    }
    /**
     * Called when the ViewHolder swiped or dragged by the ItemTouchHelper is changed.
     * 当条目被改变（拖拽、侧滑）时，该方法被调用
     * <p/>
     * If you override this method, you should call super.
     * 如果你重写该方法，你应该调用super
     *
     * @param viewHolder  The new ViewHolder that is being swiped or dragged. Might be null if
     *                    it is cleared.
     *                    正在被拖拽/侧滑的条目，可能为null，如果他被清除掉
     * @param actionState One of {@link ItemTouchHelper#ACTION_STATE_IDLE},
     *                    {@link ItemTouchHelper#ACTION_STATE_SWIPE} or
     *                    {@link ItemTouchHelper#ACTION_STATE_DRAG}.
     *                    活动状态：空闲、侧滑、拖拽，根据这个判断不同状态下的操作
     *
     * @see #clearView(RecyclerView, RecyclerView.ViewHolder)
     */
    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {//只要这个条目不是空闲状态（即：拖拽或者侧滑）
            ItemTouchHelperViewHolder itemViewHolder = (ItemTouchHelperViewHolder) viewHolder;
            itemViewHolder.onItemSelected();//这里改变选中时的条目的状态
        }

        super.onSelectedChanged(viewHolder, actionState);
    }
    /**
     * Called by the ItemTouchHelper when the user interaction with an element is over and it
     * also completed its animation.
     * 当ItemTouchHelper已经完成他的动画时（即用户与条目交互完成后），该方法被调用
     * <p>
     * This is a good place to clear all changes on the View that was done in
     * {@link #onSelectedChanged(RecyclerView.ViewHolder, int)},
     * {@link #onChildDraw(Canvas, RecyclerView, RecyclerView.ViewHolder, float, float, int,
     * boolean)} or
     * {@link #onChildDrawOver(Canvas, RecyclerView, RecyclerView.ViewHolder, float, float, int, boolean)}.
     * 这是一个很好的地方，用来清除所有条目动画完成后的效果（可能你在onSelectedChanged()、onChildDraw()
     * onChildDrawOver()时赋予了条目很多效果，在这个方法里，统统把这些效果清除掉）
     *
     * @param recyclerView The RecyclerView which is controlled by the ItemTouchHelper.
     * @param viewHolder   The View that was interacted by the user.
     */
    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);

        ItemTouchHelperViewHolder itemViewHolder = (ItemTouchHelperViewHolder) viewHolder;
        itemViewHolder.onItemClear();//在这里处理清除掉条目效果
    }
}
