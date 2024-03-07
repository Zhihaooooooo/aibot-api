package org.heroic.aibot.domain.zsxq.model.req;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 回答问题进行封装
 *
 * @author dingzhihao
 * @version 1.0
 */
@Data
@AllArgsConstructor
public class ReqData {
    private String text;
    private String[] imageIds;
    private boolean silenced;
}
