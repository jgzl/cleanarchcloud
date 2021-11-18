package com.github.jgzl.infra.upms.domain.vo;
import lombok.Data;
/**
 * 构建 Vue路由
 *
 * @author lihaifeng
 * @since 2019-10-20 15:17
 */
@Data
public class VueRouter {
    private static final long serialVersionUID = -3327478146308500708L;
    private Long id;
    private Long parentId;
	/**
	 * 路径
	 */
	private String path;
	/**
	 * 按钮名称
	 */
	private String name;
	/**
	 * 菜单名称
	 */
	private String label;
	/**
	 * 图标
	 */
	private String icon;
	/**
	 * 组件
	 */
	private String component;
	/**
	 * 重定向
	 */
	private String redirect;
	/**
	 * 元数据
	 */
	private RouterMeta meta;
    private String model;
    private String permission;
    /**
     * 排序
     */
    private Integer sequence;
	/**
	 * 类型（1=菜单;2=按钮;3=路由;5=一键发布模板）
	 */
	private Integer type;
    private Boolean global;
    private Boolean status;
	/**
	 * 显示/隐藏
	 */
	private Boolean display;
}
