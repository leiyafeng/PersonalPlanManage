package cn.lyf.account.po;

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
     * 计划延迟完成时间/天
     */
    private Integer planDelayDays;
    /**
     * 计划状态：1.已完成 2.进行中 3.未完成 4.废弃 5.延期 6.未开始
     */
    private Integer planStatus;
    private Date creatTime;
    private Date updateTime;
    /**
     * 延期请求次数，最多允许申请3次
     */
    private Integer planDelayCount;

}
