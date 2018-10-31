package cn.lyf.account.po;
import lombok.Data;
/**
 * @author lyf
 * 用于保存在session中的字段
 */
@Data
public class SessionData {
    private String userName;
    private String account;
}
