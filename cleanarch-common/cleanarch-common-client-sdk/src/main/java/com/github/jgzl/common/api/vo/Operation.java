package com.github.jgzl.common.api.vo;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.core.GrantedAuthority;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 操作权限
 *
 * @author lihaifeng
 * 2019/7/16 15:15
 * @see SecurityExpressionRoot
 */
@Data
@AllArgsConstructor
public class Operation implements GrantedAuthority {

    private static final long serialVersionUID = 1L;

    private static final String PREFIX = "OP_";

    private String op;

    @Override
    public String getAuthority() {
        return PREFIX + op.toUpperCase();
    }
}
