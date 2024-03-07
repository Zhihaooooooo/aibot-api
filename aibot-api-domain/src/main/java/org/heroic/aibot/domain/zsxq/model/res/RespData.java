package org.heroic.aibot.domain.zsxq.model.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.heroic.aibot.domain.zsxq.model.vo.Topics;

import java.util.List;

/**
 * 待回答问题进行封装，多个topic
 *
 * @author dingzhihao
 * @version 1.0
 */
@Data
@AllArgsConstructor
public class RespData {
    private List<Topics> topics;
}
