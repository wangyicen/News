package com.feicui.edu.newsapp.biz;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.util.LruCache;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2016/10/26 0026.
 * 图片下载以及处理缓存类
 */
public class ImageLoader {

//    近期最少使用算法LruCache
    private LruCache<String, Bitmap> cache;
    private Context context;
    private static ImageLoader imageLoader;

    public  ImageLoader(){
        cache = new LruCache<String, Bitmap>((int)(Runtime.getRuntime().maxMemory() >> 3)){
            //计算存入的每张图片的大小
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
    }

    //单例模式
    public static ImageLoader getInstance(){

        if (imageLoader == null){
            synchronized (ImageLoader.class){
                if (imageLoader == null){
                    imageLoader = new ImageLoader();
                }
            }
        }
        return imageLoader;
    }

//    回调函数使用的借口
    public interface ImageDownloadListener{
        //回调函数
        void imageDownloadOk(Bitmap bitmap, String url);
    }
    private ImageDownloadListener imageDownloadListener;

    public void setImageDownloadListener(ImageDownloadListener imageDownloadListener){
        this.imageDownloadListener = imageDownloadListener;
    }

    public Bitmap getBitmap(String imgUrl, Context context){
        this.context = context;
        Bitmap bitmap = null;

//        判断图片的URL是否是正确的
        if (imgUrl == null || imgUrl.length() == 0){
            return bitmap;
        }
//       1 在Lru缓存中查找图片是否存在
        bitmap = getBitmapFromLruCache(imgUrl, context);
//       2 判断是否获取到图片
        if (bitmap != null){
            return bitmap;
        }
//       3 如果没有拿到图片，就从缓存中取
        bitmap = getBitmapFromFile(imgUrl);
        if (bitmap != null){
            return bitmap;
        }
//       4 从网上下载
        asynDownloadBitmap(imgUrl);

        return null;
    }

    private Bitmap getBitmapFromFile(String imgUrl) {
//        获取手机临时存储区域
        File file = context.getCacheDir();
        Log.i("News", file.getAbsolutePath());
//        如果文件不存在，则新建文件
        if (!file.exists()){
            file.mkdirs();
        }
//        截取图片的名称
        String imgName = imgUrl.substring(imgUrl.lastIndexOf("/") + 1);
        File imgFile = new File(file, imgName);
        return BitmapFactory.decodeFile(imgFile.getAbsolutePath());
    }

    private Bitmap getBitmapFromLruCache(String imgUrl, Context context) {
        return cache.get(imgUrl);
    }

    //    异步任务下载图片
    private void asynDownloadBitmap(String imageUrl){
//        创建异步任务对象
        ImageDownloadTask task = new ImageDownloadTask();
//        调用execute函数时，按顺序调用onPreExecute，doInBackground，onPostExecute
        task.execute(imageUrl);
    }

    private class ImageDownloadTask extends AsyncTask<String, Void, Bitmap>{
        private String url;
//        在执行下载工作前做准备工作
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
//        在后台执行的函数（开了一个子线程）
        @Override
        protected Bitmap doInBackground(String... params) {//类似于Thread
            //先获取URL地址
            url = params[0];
            HttpURLConnection conn = null;
            try { //下载图片
                conn = (HttpURLConnection) new URL(url).openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(3000);
                if (conn.getResponseCode() == 200){
                    InputStream is = conn.getInputStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(is);

//                    每次下载结束，就将图片存入缓存中
//                    1 存入cache中
                    saveToLruCache(bitmap, url);
//                    2 存入缓存文件
                    saveToFile(bitmap, url);

                    return bitmap;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if (conn != null){
                    conn.disconnect();//断开连接
                }
            }
            return null;
        }

//        下载结束后传递给适配器，让适配器显示
        @Override
        protected void onPostExecute(Bitmap result) {//类似于Handler
            super.onPostExecute(result);
            imageDownloadListener.imageDownloadOk(result, url);
        }
    }

    private void saveToFile(Bitmap bitmap, String url) {
//        获取手机临时存储区域
        File file = context.getCacheDir();
        Log.i("News", file.getAbsolutePath());
//        如果文件不存在，则新建文件
        if (!file.exists()){
            file.mkdirs();
        }
//        截取图片的名称
        String imgName = url.substring(url.lastIndexOf("/") + 1);
        File imgFile = new File(file, imgName);
//        Bitmap b = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
        OutputStream os = null;
        try {
            os = new FileOutputStream(imgFile);
//            将图片存入指定文件夹
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, os);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            if (os != null){
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void saveToLruCache(Bitmap bitmap, String url) {
//        如果cache中存在，则不用再存；如果不存在，就存入
        if (cache.get(url) == null){
//            存入
            cache.put(url, bitmap);
        }
    }
}
