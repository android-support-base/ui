

### ListView局部更新
```
//        RecyclerView.ViewHolder  viewHolder = (ViewHolder) mRecyclerView.findViewHolderForAdapterPosition(currentSelectPosition + getHeaderLayoutCount());
//        if (viewHolder != null && viewHolder instanceof ViewHolder) {
//            ViewHolder holder = (ViewHolder) viewHolder;
//            updateItem(holder);
//        }
//
//         viewHolder = mRecyclerView.findViewHolderForAdapterPosition(position + getHeaderLayoutCount());
//        if (viewHolder != null && viewHolder instanceof ViewHolder) {
//            ViewHolder holder = (ViewHolder) viewHolder;
//            updateItem(holder);
//        }
```
