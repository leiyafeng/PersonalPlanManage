package cn.lyf.account.dto;

import lombok.Data;

@Data
public class AjaxResponseDTO<T> {
    /**
     * 响应码：1.成功 2.失败
     */
    private Integer code;
    /**
     * 响应信息
     */
    private String msg;
    /**
     * 响应数据
     */
    private T data;

    public AjaxResponseDTO buildSucess(T sucData,String msg){
        this.setCode(1);
        this.setMsg(msg);
        this.setData(sucData);
        return this;
    }

    public AjaxResponseDTO buildError(String msg , T data){
        this.setMsg(msg);
        this.setCode(2);
        this.setData(data);
        return this;
    }
}
