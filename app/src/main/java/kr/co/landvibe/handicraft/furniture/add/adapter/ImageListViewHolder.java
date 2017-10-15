package kr.co.landvibe.handicraft.furniture.add.adapter;


import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import kr.co.landvibe.handicraft.R;
import kr.co.landvibe.handicraft.listener.OnItemClickListener;
import kr.co.landvibe.handicraft.listener.OnItemLongClickListener;

public class ImageListViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.iv_image)
    ImageView imageView;

    private Context mContext;

    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    public ImageListViewHolder(Context context, ViewGroup parent, OnItemClickListener listener, OnItemLongClickListener longClickListener) {
        super(LayoutInflater.from(context).inflate(R.layout.viewholder_image_list, parent, false));

        mContext = context;
        mOnItemClickListener = listener;
        mOnItemLongClickListener = longClickListener;

        ButterKnife.bind(this, itemView);
    }

    public void onBind(Uri uri) {
        Glide.with(mContext)
                .load(uri)
                .thumbnail(0.2f)
                .into(imageView);

        itemView.setOnClickListener(v -> mOnItemClickListener.onItemClick(uri));
        itemView.setOnLongClickListener(v -> mOnItemLongClickListener.onItemLongClick(uri));
    }
}
