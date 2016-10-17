package com.example.spotifysample.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.spotifysample.R;
import com.example.spotifysample.SpotifySampleApplication;
import com.example.spotifysample.spotifyResponseModels.Image;
import com.example.spotifysample.spotifyResponseModels.itemModels.AlbumItem;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by Bharath on 08/10/16.
 */
public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.ViewHolder> {

    private ArrayList<AlbumItem> listOfAlbums;
    private OnItemClickListener mItemClickListener;

    public interface OnItemClickListener{
        void OnItemClick(String albumName, String albumId);
    }

    public AlbumsAdapter(ArrayList<AlbumItem> albums, OnItemClickListener listener){
        this.listOfAlbums = albums;
        this.mItemClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_each_album_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        AlbumItem currentAlbum = listOfAlbums.get(position);

        holder.bind(currentAlbum.getName(), currentAlbum.getImages().get(0), currentAlbum.getId(), mItemClickListener);
    }

    @Override
    public int getItemCount() {
        return listOfAlbums.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView albumCover;
        private TextView albumName;
        private TextView albumId;

        public ViewHolder(View itemView) {
            super(itemView);

            albumCover = (ImageView) itemView.findViewById(R.id.each_album_cover);
            albumName = (TextView) itemView.findViewById(R.id.each_album_name);
            albumId = (TextView) itemView.findViewById(R.id.each_album_id);
        }

        public void bind(final String albumNameValue, Image albumImage, final String Id, final OnItemClickListener listener){

            albumName.setText(albumNameValue);
            albumId.setText(Id);

            ImageLoader.getInstance().displayImage(albumImage.getUrl(), albumCover,
                    SpotifySampleApplication.getDisplayOptions());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.OnItemClick(albumNameValue, Id);
                }
            });
        }
    }
}
