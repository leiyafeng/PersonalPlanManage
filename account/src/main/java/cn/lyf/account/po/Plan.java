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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public Date getPlanBegainTime() {
        return planBegainTime;
    }

    public void setPlanBegainTime(Date planBegainTime) {
        this.planBegainTime = planBegainTime;
    }

    public Date getPlanEndTime() {
        return planEndTime;
    }

    public void setPlanEndTime(Date planEndTime) {
        this.planEndTime = planEndTime;
    }

    public Integer getPlanPriority() {
        return planPriority;
    }

    public void setPlanPriority(Integer planPriority) {
        this.planPriority = planPriority;
    }

    public String getPlanDescription() {
        return planDescription;
    }

    public void setPlanDescription(String planDescription) {
        this.planDescription = planDescription;
    }

    public Integer getPlanDelayDays() {
        return planDelayDays;
    }

    public void setPlanDelayDays(Integer planDelayDays) {
        this.planDelayDays = planDelayDays;
    }

    public Integer getPlanStatus() {
        return planStatus;
    }

    public void setPlanStatus(Integer planStatus) {
        this.planStatus = planStatus;
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getPlanDelayCount() {
        return planDelayCount;
    }

    public void setPlanDelayCount(Integer planDelayCount) {
        this.planDelayCount = planDelayCount;
    }
}
