package org.heroic.aibot.test;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.heroic.aibot.domain.ai.IErnieBot;
import org.heroic.aibot.domain.zsxq.IZsxqApi;
import org.heroic.aibot.domain.zsxq.model.aggregate.UnAnsweredQuestionAggregates;
import org.heroic.aibot.domain.zsxq.model.vo.Topics;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

/**
 * @author dingzhihao
 * @version 1.0
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootRunTest {
    @Value("${aibot-api.groupId}")
    private String groupId;
    @Value("${aibot-api.cookie}")
    private String cookie;
    @Autowired
    private IZsxqApi zsxqApi;
    @Autowired
    private IErnieBot ernieBot;

    @Test
    public void testZsxqApi() throws IOException {
        // 爬取待回答的问题信息
        UnAnsweredQuestionAggregates unAnsweredQuestionAggregates = zsxqApi.queryUnAnsweredQuestionsTopicId(groupId, cookie);
        log.info("测试结果:{}", JSONObject.toJSONString(unAnsweredQuestionAggregates));
        // 打印待回答的问题信息
        List<Topics> topics = unAnsweredQuestionAggregates.getRespData().getTopics();
        for (Topics topic : topics) {
            String topicId = topic.getTopicId();
            String text = topic.getQuestion().getText();
            log.info("topicId:{} text:{}", topicId, text);
            // 回答问题
            zsxqApi.answer(groupId, cookie, topicId, text, true);
        }
    }

    @Test
    public void testErnieBot() throws IOException {
        // 调用ERNIE-Bot 4.0接口回答问题
        String res = ernieBot.doIErnieBot("用C语言写一个冒泡排序");
        log.info(res);
    }
}
