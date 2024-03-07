package org.heroic.aibot.domain.ai;

import java.io.IOException;

/**
 * ERNIE-Bot 4.0大模型接口
 *
 * @author dingzhihao
 * @version 1.0
 */
public interface IErnieBot {
    String doIErnieBot(String question) throws IOException;
}
