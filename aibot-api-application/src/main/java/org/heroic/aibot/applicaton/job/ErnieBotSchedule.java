package org.heroic.aibot.applicaton.job;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.heroic.aibot.domain.ai.IErnieBot;
import org.heroic.aibot.domain.zsxq.IZsxqApi;
import org.heroic.aibot.domain.zsxq.model.aggregate.UnAnsweredQuestionAggregates;
import org.heroic.aibot.domain.zsxq.model.vo.Topics;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

/**
 * 定时任务
 *
 * @author dingzhihao
 * @version 1.0
 */
@Slf4j
@EnableScheduling
@Configuration
public class ErnieBotSchedule {
    @Value("${aibot-api.groupId}")
    private String groupId;
    @Value("${aibot-api.cookie}")
    private String cookie;
    @Resource
    private IZsxqApi zsxqApi;
    @Resource
    private IErnieBot ernieBot;

    // 表达式：cron.qqe2.com
    @Scheduled(cron = "0/30 * * * * ?")
    public void run() {
        try {
            GregorianCalendar calendar = new GregorianCalendar();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            if (hour >= 23 || hour <= 5) { // 晚上23点之后到次日6点之前不回答
                log.info("防止风控，晚上不回答问题");
                return;
            }

            if (new Random().nextBoolean()) {
                log.info("防止风控，随机不回答问题");
                return;
            }

            // 1、爬取知识星球的问题
            UnAnsweredQuestionAggregates unAnsweredQuestionAggregates = zsxqApi.queryUnAnsweredQuestionsTopicId(groupId, cookie);
            log.info("爬取结果:{}", JSONObject.toJSONString(unAnsweredQuestionAggregates));
            List<Topics> topics = unAnsweredQuestionAggregates.getRespData().getTopics();
            if (null == topics || topics.isEmpty()) {
                log.info("本次检索未查询到待回答问题");
                return;
            }
            // 2、调用ERNIE-Bot 4.0接口回答问题
            Topics topic = topics.get(0);
            String answer = ernieBot.doIErnieBot(topic.getQuestion().getText().trim());
            // 3、回答知识星球的问题
            boolean status = zsxqApi.answer(groupId, cookie, topic.getTopicId(), answer, true);
            log.info("编号:{} 问题:{} 回答:{} 状态:{}", topic.getTopicId(), topic.getQuestion().getText(), answer, status);
        } catch (Exception e) {
            log.error("自动回复异常", e);
        }
    }
}
