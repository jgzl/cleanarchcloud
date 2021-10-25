package com.github.jgzl.common.api.vo;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

/**
 * 树形节点
 * @author lihaifeng
 * @date 2017年11月9日23:33:45
 */
@Data
public class TreeNode {
	/**
	 * 当前节点id
	 */
	protected int id;

	/**
	 * 父节点id
	 */
	protected int parentId;

	/**
	 * 子节点列表
	 */
	protected List<TreeNode> children = new ArrayList<TreeNode>();

	public void add(TreeNode node) {
		children.add(node);
	}
}
