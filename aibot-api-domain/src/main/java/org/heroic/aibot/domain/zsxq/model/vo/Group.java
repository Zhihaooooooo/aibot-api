package org.heroic.aibot.domain.zsxq.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * VO：Value Object值对象，主要体现在视图的对象，对于一个Web页面将整个页面的属性封装成一个对象，然后用一个VO对象在控制层与视图层进行传输交换
 *
 * @author dingzhihao
 * @version 1.0
 */
@Data
@AllArgsConstructor
public class Group {
    private String groupId;
    private String name;
    private String type;
}