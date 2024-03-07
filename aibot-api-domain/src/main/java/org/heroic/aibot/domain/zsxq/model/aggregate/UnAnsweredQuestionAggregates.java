package org.heroic.aibot.domain.zsxq.model.aggregate;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.heroic.aibot.domain.zsxq.model.res.RespData;

/**
 * 待回答问题的聚合信息
 *
 * @author dingzhihao
 * @version 1.0
 */
@Data
@AllArgsConstructor
public class UnAnsweredQuestionAggregates {
    private boolean succeeded;
    private RespData respData;
}
