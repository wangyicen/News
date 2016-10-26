package com.feicui.edu.newsapp.junit;

import android.test.AndroidTestCase;

import com.feicui.edu.newsapp.base.utils.NetUtils;
import com.feicui.edu.newsapp.biz.ParserNews;

/**
 * Created by Administrator on 2016/10/24 0024.
 */
public class Test extends AndroidTestCase {
    public void testParserNews(){
        String json = NetUtils.httpGet(NetUtils.BASE_PATH +
                "news_list?ver="+ NetUtils.VERSION +"&subid=1&dir=1&nid=1id&stamp=20140321&cnt=20");
        ParserNews.parserNews(json);
    }

}
