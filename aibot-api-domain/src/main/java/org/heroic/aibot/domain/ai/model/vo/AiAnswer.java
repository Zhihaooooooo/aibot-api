package org.heroic.aibot.domain.ai.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author dingzhihao
 * @version 1.0
 */
@Data
@AllArgsConstructor
public class AiAnswer {
    private String id;
    private String object;
    private int created;
    private String result;
    private boolean isTruncated;
    private boolean needClearHistory;
    private String finishReason;
}
