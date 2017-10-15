package kr.co.landvibe.handicraft.furniture.add.adapter;


import android.app.Activity;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import kr.co.landvibe.handicraft.listener.OnItemClickListener;
import kr.co.landvibe.handicraft.listener.OnItemLongClickListener;

public class ImageListAdapter extends RecyclerView.Adapter<ImageListViewHolder> {
    private Activity mActivity;
    private List<Uri> imageList = new ArrayList<>();
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    public ImageListAdapter(Activity activity) {
        mActivity = activity;
    }

    @Override
    public ImageListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ImageListViewHolder(mActivity, parent, mOnItemClickListener, mOnItemLongClickListener);
    }

    @Override
    public void onBindViewHolder(ImageListViewHolder holder, int position) {
        int realPosition = imageList.size() - holder.getAdapterPosition() - 1; //reverse
        holder.onBind(imageList.get(realPosition));
    }

    @Override
    public int getItemCount() {
        if (imageList == null) {
            return 0;
        }
        return imageList.size();
    }

    public void notifyAdapter() {
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        mOnItemLongClickListener = listener;
    }

    public void setListData(List<Uri> list) {
        this.imageList.clear();
        this.imageList.addAll(list);
    }

    public void addData(Uri uri) {
        this.imageList.add(uri);
        notifyDataSetChanged();
    }

    public void removeData(int index){
        this.imageList.remove(index);
        notifyDataSetChanged();
    }

    public List<Uri> getImageList(){
        return this.imageList;
    }
}
