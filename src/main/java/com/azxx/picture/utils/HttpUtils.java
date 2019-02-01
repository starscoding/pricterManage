package com.azxx.picture.utils;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpUtils {

    private static Logger logger = LoggerFactory.getLogger(HttpUtils.class);

    public static String httpGet(String url, Map<String, String> params) {

//        URI uri = new URIBuilder().setScheme("http").setHost("").setPath("").setParameter("name", "xuzheng").build();
        StringBuffer sb = new StringBuffer();
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                String param = entry.getKey() + "=" + entry.getValue();
                sb.append(param + "&");
            }
            url = url + "?" + sb.toString();
        }
        //1.创建HttpClient
        CloseableHttpClient client = HttpClients.createDefault();
        //2.创建HttpGet
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = null;
        String content = null;

        try {
            //3.执行Get请求
            response = client.execute(httpGet);
            //4.获取响应实体
            HttpEntity entity = response.getEntity();
            //获取返回状态码
            logger.info(String.valueOf(response.getStatusLine()));
            if (entity != null) {
                content = EntityUtils.toString(entity);
                logger.info(content);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return content;
    }

    public static String httpPost(String url, Map<String, String> params) {

        //1.创建HttpClient
        CloseableHttpClient client = HttpClients.createDefault();
        //2.创建HttpGet
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse response = null;
        String content = null;
        //设置请求和传输超时时间
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(6000).setConnectTimeout(6000).build();
        httpPost.setConfig(requestConfig);
        httpPost.addHeader("User-Agent", "Mozilla/5.0");
        // 创建参数队列
        List formparams = new ArrayList();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }

        UrlEncodedFormEntity uefEntity;
        try {
            uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
            httpPost.setEntity(uefEntity);
            //3.执行Get请求
            response = client.execute(httpPost);
            //4.获取响应实体
            HttpEntity entity = response.getEntity();
            //获取返回状态码
            logger.info(String.valueOf(response.getStatusLine()));
            if (entity != null) {
                content = EntityUtils.toString(entity);
                logger.info(content);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return content;
    }
}
