package cn.lyf.account.dto;


import lombok.Data;
import java.util.Date;

/**
 * 前台请求查询计划列表参数VO类
 */
@Data
public class QueryPlanListDTO {
    private Integer userId;
    private Integer pageSize;
    private Integer pageNumber;
    private String goal;
    /**
     * 计划优先级
     */
    private Integer planPriority;
    private Integer planStatus;
    private Date start1;
    private Date start2;
    private Date end1;
    private Date end2;
}
