package com.github.jgzl.infra.upms.domain.vo;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
/**
 * @author Levin
 */
@Data
public class UserVO {
    private Long id;
    /**
     * 姓名
     */
    private String nickName;
    /**
     * 姓名
     */
    private String realName;

    /**
     * 邮箱
     */
    private String email;
    /**
     * 手机
     */
    private String mobile;
	/**
	 * 用户名
	 */
    private String username;
    /**
     * 生日
     */
    private LocalDate birthday;
	
    /**
     * 擅长
     */
    private String goodAt;
    /**
     * 职业
     */
    private String occupation;

    /**
     * 创建时间（依托数据库功能）
     */
    protected LocalDateTime createdTime;

}
