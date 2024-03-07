package org.heroic.aibot.domain.ai.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 工具类
 *
 * @author dingzhihao
 * @version 1.0
 */
@Component
public class Utils {
    private static String APIKey;
    private static String SecretKey;

    // 静态变量不能直接使用@Value注解
    @Value("${aibot-api.APIKey}")
    public void setAPIKey(String APIKey) {
        Utils.APIKey = APIKey;
    }

    @Value("${aibot-api.SecretKey}")
    public void setSecretKey(String SecretKey) {
        Utils.SecretKey = SecretKey;
    }

    /**
     * 根据APIKey和SecretKey获取access_token
     *
     * @return
     * @throws IOException
     */
    public static String getAccessToken() throws IOException {
        // 创建HttpClient对象
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        // 创建POST请求对象
        HttpPost httpPost = new HttpPost("https://aip.baidubce.com/oauth/2.0/token?grant_type=client_credentials&client_id=" + APIKey + "&client_secret=" + SecretKey);
        httpPost.addHeader("Content-Type", "application/json");
        httpPost.addHeader("Accept", "application/json");

        // 执行POST请求
        CloseableHttpResponse response = httpClient.execute(httpPost);

        // 返回结果
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String resJsonStr = EntityUtils.toString(response.getEntity());
            JSONObject jsonObject = JSONObject.parseObject(resJsonStr);
            String accessToken = (String) jsonObject.get("access_token"); // 取出返回结果中的access_token
            return accessToken;
        } else {
            throw new RuntimeException("getAccessToken Err Code is " + response.getStatusLine().getStatusCode());
        }
    }
}
