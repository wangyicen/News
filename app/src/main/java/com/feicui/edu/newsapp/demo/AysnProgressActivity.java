package com.feicui.edu.newsapp.demo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.feicui.edu.newsapp.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.IOException;
import java.io.InputStream;

public class AysnProgressActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private TextView textView;
    private static final String IMG_URL = "http://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d&word=%E7%BD%97%E5%BF%97%E7%A5%A5&step_word=&hs=0&pn=1&spn=0&di=269806109570&pi=0&rn=1&tn=baiduimagedetail&is=0%2C0&istype=2&ie=utf-8&oe=utf-8&in=&cl=2&lm=-1&st=-1&cs=4142256668%2C2012929949&os=2456556667%2C3885637048&simid=0%2C0&adpicid=0&ln=3928&fr=&fmq=1477464973228_R&fm=index&ic=undefined&s=undefined&se=&sme=&tab=0&width=&height=&face=undefined&ist=&jit=&cg=star&bdtype=11&oriquery=&objurl=http%3A%2F%2Fdy.imgs.duominuo.com%2Fnews%2Fe4%2Ff5%2Fe4f57a271c4c478c11580c395f3f568b.jpg&fromurl=ippr_z2C%24qAzdH3FAzdH3Fooo_z%26e3B4wg2275_z%26e3Bv54AzdH3Fgjofccdc0c_z%26e3Bip4s&gsm=0&rpstart=0&rpnum=0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aysn_progress);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        textView = (TextView) findViewById(R.id.textView);

        MyTask task = new MyTask();
        task.execute(IMG_URL);

    }

    class MyTask extends AsyncTask<String, Integer, Bitmap>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //显示进度条
            progressBar.setVisibility(View.VISIBLE);
            //更改文字
            textView.setText("开始下载图片...");
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            //设置进度条
            progressBar.setProgress(values[0]);
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            //创建参数对象
            HttpParams param = new BasicHttpParams();
            //设置属性
            ConnManagerParams.setMaxTotalConnections(param, 8);
            //从连接池中取出连接超时
            ConnManagerParams.setTimeout(param, 3000);
            //网络与服务器连接超时
            HttpConnectionParams.setConnectionTimeout(param, 2000);
            //Socket读数据的时间超时
            HttpConnectionParams.setSoTimeout(param, 4000);
            DefaultHttpClient client = new DefaultHttpClient();
            HttpGet hg = new HttpGet(params[0]);
            try {
                HttpResponse res = client.execute(hg);
                if (res.getStatusLine().getStatusCode() == 200){
                    HttpEntity entity = res.getEntity();
                    InputStream is = entity.getContent();
                    //先获取图片的最大字节
                    long max = entity.getContentLength();
                    int count = 0;
                    while (count < max){
                        count += 100;
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        publishProgress((int)(count / (float)max * 100));
                    }
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    return bitmap;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            //下载结束
            progressBar.setVisibility(View.VISIBLE);
            textView.setText("图片下载成功...");
        }
    }
}
