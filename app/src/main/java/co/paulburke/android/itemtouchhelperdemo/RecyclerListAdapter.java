package co.paulburke.android.itemtouchhelperdemo;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import co.paulburke.android.itemtouchhelperdemo.helper.ItemTouchHelperAdapter;
import co.paulburke.android.itemtouchhelperdemo.helper.ItemTouchHelperViewHolder;

/**
 * @author Paul Burke (ipaulpro)
 * ★ RecyclerListAdapter要实现ItemTouchHelperAdapter
 *    重写onItemDismiss（删除条目）和onItemMove（移动条目）
 *
 * ★ RecyclerView.ViewHolder实现ItemTouchHelperViewHolder
 *    重写onItemSelected（条目被选中时）和onItemClear（条目被拖拽之后）
 */
public class RecyclerListAdapter extends RecyclerView.Adapter<RecyclerListAdapter.ItemViewHolder>
        implements ItemTouchHelperAdapter {

    private static final String[] STRINGS = new String[]{
            "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten"
    };

    private final List<String> mItems = new ArrayList<>();

    public RecyclerListAdapter() {
        mItems.addAll(Arrays.asList(STRINGS));
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main, parent, false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, int position) {
        holder.textView.setText(mItems.get(position));
    }

//   当条目被删除时的操作（实现ItemTouchHelperAdapter重写onItemDismiss方法）
    @Override
    public void onItemDismiss(int position) {
        mItems.remove(position);
//        通知条目已经被删除，与adapter互动
        notifyItemRemoved(position);
    }

    //   当条目被移动时的操作（实现ItemTouchHelperAdapter重写onItemMove方法）
    @Override
    public void onItemMove(int fromPosition, int toPosition) {
//        ★移动一个条目分两步：首先把该条目先移除，再考虑在对应要移动到的地方增加这个条目
        String prev = mItems.remove(fromPosition);
//        toPosition > fromPosition ? toPosition - 1 : toPosition,
//        即将拖动到的位置是否比开始拖动的位置大，
//        如果大，则在集合里在toPosition - 1处填写该条目，否则在toPosition处添加该条目
        mItems.add(toPosition > fromPosition ? toPosition - 1 : toPosition, prev);
//        通知条目被移动，与adapter互动
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder implements
            ItemTouchHelperViewHolder {

        public final TextView textView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView;
        }

        @Override
        public void onItemSelected() {
//            当选中时，背景色为灰色
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
//            当拖拽完毕后，背景色为透明
            itemView.setBackgroundColor(0);
        }
    }
}
