package org.heroic.aibot.domain.zsxq;

import org.heroic.aibot.domain.zsxq.model.aggregate.UnAnsweredQuestionAggregates;

import java.io.IOException;

/**
 * 知识星球API接口
 *
 * @author dingzhihao
 * @version 1.0
 */
public interface IZsxqApi {
    UnAnsweredQuestionAggregates queryUnAnsweredQuestionsTopicId(String groupId, String cookie) throws IOException;

    boolean answer(String groupId, String cookie, String topicId, String text, boolean silenced) throws IOException;
}
