package com.veinhorn.scrollgalleryview.loader.picasso;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.veinhorn.scrollgalleryview.loader.MediaLoader;

import java.io.File;

/**
 * Created by veinhorn on 2/4/18.
 */

public class PicassoImageLoader implements MediaLoader {
    private String url;
    private Integer thumbnailWidth;
    private Integer thumbnailHeight;
    private boolean isFile = false;
    public PicassoImageLoader(String url) {
        this.url = url;
    }

    public PicassoImageLoader(String url,boolean isFile) {
        this.url = url;
        this.isFile = isFile;
    }

    public PicassoImageLoader(String url, Integer thumbnailWidth, Integer thumbnailHeight) {
        this.url = url;
        this.thumbnailWidth = thumbnailWidth;
        this.thumbnailHeight = thumbnailHeight;
    }

    @Override
    public boolean isImage() {
        return true;
    }

    @Override
    public void loadMedia(Context context, final ImageView imageView, final MediaLoader.SuccessCallback callback) {
        if(isFile){
            File file = new File(url);
            if(file.exists()){
                Picasso.with(context)
                        .load(file)
                        .placeholder(R.drawable.placeholder_image)
                        .into(imageView, new ImageCallback(callback));
            }else{
                Log.d("PicassoImageLoader","Failed to load Image. File not found. URL =="+
                        url+" and isFile=="+isFile);
            }
        }else{
            Picasso.with(context)
                    .load(url)
                    .placeholder(R.drawable.placeholder_image)
                    .into(imageView, new ImageCallback(callback));
        }

    }

    @Override
    public void loadThumbnail(Context context, final ImageView thumbnailView, final MediaLoader.SuccessCallback callback) {
        if(isFile){
            File file = new File(url);
            if(file.exists()){
                Picasso.with(context)
                        .load(file)
                        .resize(thumbnailWidth == null ? 100 : thumbnailWidth,
                                thumbnailHeight == null ? 100 : thumbnailHeight)
                        .placeholder(R.drawable.placeholder_image)
                        .centerInside()
                        .into(thumbnailView, new ImageCallback(callback));
            }else{
                Log.d("PicassoImageLoader","Failed to load Thumbnail. File not found. URL =="+
                        url+" and isFile=="+isFile);
            }
        }else{
            Picasso.with(context)
                    .load(url)
                    .resize(thumbnailWidth == null ? 100 : thumbnailWidth,
                            thumbnailHeight == null ? 100 : thumbnailHeight)
                    .placeholder(R.drawable.placeholder_image)
                    .centerInside()
                    .into(thumbnailView, new ImageCallback(callback));
        }

    }

    private static class ImageCallback implements Callback {
        private final MediaLoader.SuccessCallback callback;

        public ImageCallback(SuccessCallback callback) {
            this.callback = callback;
        }

        @Override
        public void onSuccess() {
            callback.onSuccess();
        }

        @Override
        public void onError() {

        }
    }
}
