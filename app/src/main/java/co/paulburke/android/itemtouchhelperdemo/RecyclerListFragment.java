package co.paulburke.android.itemtouchhelperdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import co.paulburke.android.itemtouchhelperdemo.helper.SimpleItemTouchHelperCallback;

/**
 * @author Paul Burke (ipaulpro)
 */
public class RecyclerListFragment extends Fragment {

    private ItemTouchHelper mItemTouchHelper;

    public RecyclerListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//         ★★★关键之处在于RecyclerListAdapter的写法
        RecyclerListAdapter adapter = new RecyclerListAdapter(new RecyclerListAdapter.OnDragStartListener() {
            @Override
            public void onDragStarted(RecyclerView.ViewHolder viewHolder) {
//                开始拖拽了
                mItemTouchHelper.startDrag(viewHolder);
            }
        });

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
//        setHasFixedSize()方法用来使RecyclerView保持固定的大小，该信息被用于自身的优化。
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        /**
         * ★★★★★★★★★★★★★★★★★★★★★★★
         * 要使用ItemTouchHelper，你需要创建一个ItemTouchHelper.Callback。
         * 这个接口可以让你监听“move(上下移动)”与 “swipe（左右滑动）”事件。这里还是
         * ★控制view被选中
         * 的状态以及★重写默认动画的地方。
         *
         * 如果你只是想要一个基本的实现，有一个
         * 帮助类可以使用：SimpleCallback,但是为了了解其工作机制，我们还是自己实现。
         */
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
//        将定义好的mItemTouchHelper应用于我们的recyclerView，使得recyclerView获得move和swipe的效果
        mItemTouchHelper.attachToRecyclerView(recyclerView);

    }

}
