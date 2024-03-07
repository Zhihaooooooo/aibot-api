package org.heroic.aibot.domain.zsxq.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author dingzhihao
 * @version 1.0
 */
@Data
@AllArgsConstructor
public class Questionee {
    private String userId;
    private String name;
    private String avatarUrl;
    private String description;
    private String location;
}
