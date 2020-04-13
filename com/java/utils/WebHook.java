package com.java.utils;


import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.List;

/**
 * @ClassName WebHook
 * @Description 在QQ群中发送消息
 * @Author LiYan
 * @Date 2020/4/11 17:42
 * @Version 1.0
 */
public class WebHook {
    public static boolean WebhookTools(String WEBHOOK_TOKEN, List<String> list) {
        try {
            HttpClient httpclient = HttpClients.createDefault();
            HttpPost httppost = new HttpPost(WEBHOOK_TOKEN);
            httppost.addHeader("Content-Type", "application/json; charset=utf-8");
            //构建一个json格式字符串textMsg，其内容是接收方需要的参数和消息内容
            String str = "";
            for (int i = 0; i < list.size(); i++) {
                str += "@" + list.get(i) + "    ";
            }
            str = str + "请尽快完成青年大学习截图";
            String textMsg = "{\"content\": [ {\"type\":0,\"data\":\"" + str + "\"}]}";
            StringEntity se = new StringEntity(textMsg, "utf-8");
            httppost.setEntity(se);
            HttpResponse response = httpclient.execute(httppost);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String result = EntityUtils.toString(response.getEntity(), "utf-8");
                System.out.println(result);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
