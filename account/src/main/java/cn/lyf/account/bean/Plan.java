package cn.lyf.account.bean;

import lombok.Data;

import java.util.Date;

/**
 * 计划实体类
 */
@Data
public class Plan {
    private Integer id;
    private Integer userId;
    private String goal;
    private Date planBegainTime;
    private Date planEndTime;
    /**
     * 计划优先级：1.非紧急 2.普通 3.紧急
     */
    private Integer planPriority;
    private String planDescription;
    /**
     * 计划提前完成时间
     */
    private Integer planAheadTime;
    /**
     * 计划延迟完成时间
     */
    private Integer planDelayTime;
    /**
     * 计划状态：1.已完成 2.进行中 3.未完成 4.废弃 5.延期
     */
    private Integer planStatus;
    private Date creatTime;
    private Date updateTime;

}
