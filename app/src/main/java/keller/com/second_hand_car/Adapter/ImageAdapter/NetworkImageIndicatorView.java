package keller.com.second_hand_car.Adapter.ImageAdapter;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.panxw.android.imageindicator.ImageIndicatorView;

import java.util.List;

public class NetworkImageIndicatorView extends ImageIndicatorView {
    private DisplayImageOptions mDisplayImageOptions = new DisplayImageOptions.Builder()
            .cacheOnDisk(true).build();
    private ImageLoader imageLoader = ImageLoader.getInstance();
    public NetworkImageIndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NetworkImageIndicatorView(Context context) {
        super(context);
    }

    /**
     * set image url list
     */
    @Override
    public void setupLayoutByImageUrl(List<String> urlList) {
        imageLoader.init(ImageLoaderConfiguration.createDefault(getContext()));
        //清楚缓存
//        imageLoader.clearDiskCache();
//        imageLoader.clearMemoryCache();
        for (String url : urlList) {
            ImageView imageView = new ImageView(getContext());
            imageLoader.displayImage(url, imageView, mDisplayImageOptions);
            addViewItem(imageView);
        }
    }

//    public static void initImageLoader(Context context) {
//    //解决ImageLoader must be init with configuration before using
//        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
//                context).threadPriority(Thread.NORM_PRIORITY - 2)
//                .denyCacheImageMultipleSizesInMemory()
//                .tasksProcessingOrder(QueueProcessingType.LIFO)
//                .writeDebugLogs()
//                .build();
//
//        ImageLoader.getInstance().init(config);
//    }


}