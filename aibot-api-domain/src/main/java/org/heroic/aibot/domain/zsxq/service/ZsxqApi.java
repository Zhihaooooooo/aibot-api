package org.heroic.aibot.domain.zsxq.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.serializer.SerializeConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.heroic.aibot.domain.zsxq.IZsxqApi;
import org.heroic.aibot.domain.zsxq.model.aggregate.UnAnsweredQuestionAggregates;
import org.heroic.aibot.domain.zsxq.model.req.AnswerReq;
import org.heroic.aibot.domain.zsxq.model.req.ReqData;
import org.heroic.aibot.domain.zsxq.model.res.AnswerRes;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Serializable;

/**
 * 知识星球API接口
 *
 * @author dingzhihao
 * @version 1.0
 */
@Slf4j
@Service
public class ZsxqApi implements IZsxqApi {
    /**
     * 根据groupId和cookie爬取待回答问题
     *
     * @param groupId
     * @param cookie
     * @return
     * @throws IOException
     */
    @Override
    public UnAnsweredQuestionAggregates queryUnAnsweredQuestionsTopicId(String groupId, String cookie) throws IOException {
        // 创建HttpClient对象
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        // 创建GET请求对象
        HttpGet httpGet = new HttpGet("https://api.zsxq.com/v2/groups/" + groupId + "/topics?scope=unanswered_questions&count=20");
        httpGet.addHeader("cookie", cookie);
        httpGet.addHeader("Content-Type", "application/json; charset=UTF-8");

        // 执行GET请求
        CloseableHttpResponse response = httpClient.execute(httpGet);

        // 返回结果
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String resJsonStr = EntityUtils.toString(response.getEntity());
            log.info("爬取知识星球的待回答问题 groupId:{} resJsonStr:{}", groupId, resJsonStr);
            return JSON.parseObject(resJsonStr, UnAnsweredQuestionAggregates.class); // 将待回答问题转为聚合对象并返回
        } else {
            throw new RuntimeException("queryUnAnsweredQuestionsTopicId Err Code is " + response.getStatusLine().getStatusCode());
        }
    }

    /**
     * @param groupId
     * @param cookie
     * @param topicId
     * @param text
     * @param silenced
     * @return
     * @throws IOException
     */
    @Override
    public boolean answer(String groupId, String cookie, String topicId, String text, boolean silenced) throws IOException {
        // 创建HttpClient对象
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        // POST请求对象
        HttpPost httpPost = new HttpPost("https://api.zsxq.com/v2/topics/" + topicId + "/answer");
        httpPost.addHeader("cookie", cookie);
        httpPost.addHeader("Content-Type", "application/json; charset=UTF-8");
        httpPost.addHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36");

        // 回答内容
        AnswerReq answerReq = new AnswerReq(new ReqData(text, new String[]{}, silenced)); // 将回答的信息封装在AnswerReq对象中
        // 将AnswerReq对象转为JSON字符串，并且Java对象转JSON时驼峰转下划线衔接
        SerializeConfig serializeConfig = new SerializeConfig();
        serializeConfig.propertyNamingStrategy = PropertyNamingStrategy.SnakeCase;
        String paramJson = JSONObject.toJSONString(answerReq, serializeConfig);
        StringEntity stringEntity = new StringEntity(paramJson, ContentType.create("text/json", "UTF-8"));
        httpPost.setEntity(stringEntity); // 将回答的信息设置到POST请求中

        // 执行POST请求
        CloseableHttpResponse response = httpClient.execute(httpPost);

        // 返回结果
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String resJsonStr = EntityUtils.toString(response.getEntity());
            log.info("回答知识星球的问题 groupId:{} topicId:{} resJsonStr:{}", groupId, topicId, resJsonStr);
            AnswerRes answerRes = JSON.parseObject(resJsonStr, AnswerRes.class);
            return answerRes.isSucceeded();
        } else {
            throw new RuntimeException("answer Err Code is " + response.getStatusLine().getStatusCode());
        }
    }
}
