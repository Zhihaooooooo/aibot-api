package org.heroic.aibot.domain.zsxq.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author dingzhihao
 * @version 1.0
 */
@Data
@AllArgsConstructor
public class Topics {
    private String topicId;
    private Group group;
    private String type;
    private Question question;
    private boolean answered;
    private int likesCount;
    private int rewardsCount;
    private int commentsCount;
    private int readingCount;
    private int readersCount;
    private boolean digested;
    private boolean sticky;
    private String createTime;
    private UserSpecific userSpecific;
}
