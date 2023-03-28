package com.graduation.seniorabilityassessment.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class AllAssess {
    //    private Assess assess;
//    private Daily daily;
//    private Spirit spirit;
//    private Sensory sensory;

    private Integer id;
    private String assessId;
    private Date assessDate;
    private String assessReason;
    private Integer assessedId;
    private String assessedName;
    private String assessedIdentityCard;
    private Integer dailyRank;
    private Integer spiritRank;
    private Integer sensoryRank;
    private Integer socialAttendRank;
    private Integer primaryRank;
    private String rankChangeReason;
    private Integer ultimateRank;
    private String assessSign;
    private String informationProviderSign;
    private Integer assessorId;

    //daily
    private String eat;
    private String bath;
    private String makeup;
    private String wear;
    private Integer shit;
    private String bee;
    private String goToilet;
    private String bedTransfer;
    private String walk;
    private String floor;
    private Integer dailyLife;

    //spirit
    private String recognition;
    private String attack;
    private String depress;
    private String spiritTotal;

    //sensory
    private String conscious;
    private String sight;
    private String listening;
    private String communicate;

    //socialAttend
    private String lifeAbility;
    private String workAbility;
    private String timeSpace;
    private Integer personOrientation;
    private String socialCommunication;
    private Integer socialAttendTotal;
}
