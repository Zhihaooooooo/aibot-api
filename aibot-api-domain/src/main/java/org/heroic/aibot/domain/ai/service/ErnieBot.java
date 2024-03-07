package org.heroic.aibot.domain.ai.service;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.heroic.aibot.domain.ai.IErnieBot;
import org.heroic.aibot.domain.ai.model.vo.AiAnswer;
import org.heroic.aibot.domain.ai.utils.Utils;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * ERNIE-Bot 4.0大模型接口
 *
 * @author dingzhihao
 * @version 1.0
 */
@Service
public class ErnieBot implements IErnieBot {
    /**
     * 调用ERNIE-Bot 4.0接口回答问题
     *
     * @param question
     * @return
     * @throws IOException
     */
    @Override
    public String doIErnieBot(String question) throws IOException {
        // 创建HttpClient对象
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        // 创建POST请求对象
        HttpPost httpPost = new HttpPost("https://aip.baidubce.com/rpc/2.0/ai_custom/v1/wenxinworkshop/chat/completions_pro?access_token=" + Utils.getAccessToken());
        httpPost.addHeader("Content-Type", "application/json");

        String paramJson = "{\n" +
                "\t\"messages\": [{\n" +
                "\t\t\"role\": \"user\",\n" +
                "\t\t\"content\": \"" + question + "\"\n" +
                "\t}]\n" +
                "}";

        StringEntity stringEntity = new StringEntity(paramJson, ContentType.create("text/json", "UTF-8"));
        httpPost.setEntity(stringEntity); // 将问题的信息设置到POST请求中

        // 执行POST请求
        CloseableHttpResponse response = httpClient.execute(httpPost);

        // 返回结果
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String resJsonStr = EntityUtils.toString(response.getEntity());
            AiAnswer aiAnswer = JSON.parseObject(resJsonStr, AiAnswer.class); // 将返回结果的JSON字符串转为AiAnswer对象
            return aiAnswer.getResult();
        } else {
            throw new RuntimeException("doIErnieBot Err Code is " + response.getStatusLine().getStatusCode());
        }
    }
}
