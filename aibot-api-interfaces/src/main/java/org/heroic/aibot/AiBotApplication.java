package org.heroic.aibot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 项目启动入口
 *
 * @author dingzhihao
 * @version 1.0
 */
@Slf4j
@SpringBootApplication
public class AiBotApplication {
    public static void main(String[] args) {
        SpringApplication.run(AiBotApplication.class, args);
        log.info("项目启动成功");
    }
}
