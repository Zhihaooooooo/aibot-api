package org.heroic.aibot.domain.zsxq.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author dingzhihao
 * @version 1.0
 */
@Data
@AllArgsConstructor
public class Question {
    private Owner owner;
    private Questionee questionee;
    private String text;
    private boolean expired;
    private boolean anonymous;
    private OwnerDetail ownerDetail;
    private String ownerLocation;
}
