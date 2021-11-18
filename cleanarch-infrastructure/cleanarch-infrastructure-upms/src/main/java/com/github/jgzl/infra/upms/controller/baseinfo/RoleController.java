package com.github.jgzl.infra.upms.controller.baseinfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.jgzl.common.core.annotation.log.SysLog;
import com.github.jgzl.common.core.util.BeanUtilPlus;
import com.github.jgzl.common.core.util.Result;
import com.github.jgzl.common.data.TenantEnvironment;
import com.github.jgzl.common.data.mybatis.auth.DataScopeType;
import com.github.jgzl.common.data.mybatis.conditions.Wraps;
import com.github.jgzl.infra.upms.domain.dto.RoleDTO;
import com.github.jgzl.infra.upms.domain.dto.RoleResSaveDTO;
import com.github.jgzl.infra.upms.domain.dto.RoleUserDTO;
import com.github.jgzl.infra.upms.domain.entity.baseinfo.Role;
import com.github.jgzl.infra.upms.domain.entity.baseinfo.RoleOrg;
import com.github.jgzl.infra.upms.domain.entity.baseinfo.RoleRes;
import com.github.jgzl.infra.upms.domain.vo.RoleDetailVO;
import com.github.jgzl.infra.upms.domain.vo.RolePermissionResp;
import com.github.jgzl.infra.upms.domain.vo.RoleResVO;
import com.github.jgzl.infra.upms.domain.vo.UserRoleResp;
import com.github.jgzl.infra.upms.service.RoleOrgService;
import com.github.jgzl.infra.upms.service.RoleResService;
import com.github.jgzl.infra.upms.service.RoleService;
import com.github.jgzl.infra.upms.service.UserRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static com.github.jgzl.common.core.util.Result.success;
/**
 * 角色管理
 *
 * @author lihaifeng
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {
    private final TenantEnvironment tenantEnvironment;
    private final RoleService roleService;
    private final RoleResService roleResService;
    private final RoleOrgService roleOrgService;
    private final UserRoleService userRoleService;
	/**
	 * 角色列表
	 * @return
	 */
    @GetMapping("/query_all")
    public Result<List<Role>> query() {
        final List<Role> page = this.roleService.list();
        return success(page);
    }
	/**
	 * 角色列表
	 * @param current
	 * @param size
	 * @param name
	 * @param locked
	 * @param scopeType
	 * @return
	 */
    @GetMapping
    public Result<IPage<Role>> query(@RequestParam(required = false, defaultValue = "1") Integer current,
                                     @RequestParam(required = false, defaultValue = "20") Integer size,
                                     String name, Boolean locked, DataScopeType scopeType) {
        final Page<Role> page = this.roleService.page(new Page<>(current, size), Wraps.<Role>lbQ()
                .eq(Role::getLocked, locked).like(Role::getName, name).eq(Role::getScopeType, scopeType));
        return success(page);
    }
	/**
	 * 角色详情
	 * @param id
	 * @return
	 */
    @GetMapping("/{id}/detail")
    public Result<RoleDetailVO> details(@PathVariable Long id) {
        Role role = roleService.getById(id);
        RoleDetailVO detail = BeanUtilPlus.toBean(role, RoleDetailVO.class);
        final RoleResVO authority = this.roleResService.findAuthorityIdByRoleId(id);
        detail.setAuthority(authority);
        return success(detail);
    }
	/**
	 * 添加角色
	 * @param data
	 * @return
	 */
    @PostMapping
    @SysLog(value = "添加角色")
    public Result<ResponseEntity<Void>> add(@Validated @RequestBody RoleDTO data) {
        roleService.saveRole(tenantEnvironment.userId(), data);
        return success();
    }
	/**
	 * 编辑角色
	 * @param id
	 * @param data
	 * @return
	 */
    @PutMapping("/{id}")
    @SysLog(value = "编辑角色")
    public Result<ResponseEntity<Void>> edit(@PathVariable Long id, @Validated @RequestBody RoleDTO data) {
        roleService.updateRole(id, tenantEnvironment.userId(), data);
        return success();
    }
	/**
	 * 删除角色
	 * @param id
	 * @return
	 */
    @DeleteMapping("/{id}")
    @SysLog(value = "删除角色")
    public Result<ResponseEntity<Void>> del(@PathVariable Long id) {
        this.roleService.removeByRoleId(id);
        return success();
    }
	/**
	 * 角色关联的用户
	 * @param roleId
	 * @return
	 */
	@GetMapping("/{roleId}/users")
    public Result<UserRoleResp> userByRoleId(@PathVariable Long roleId) {
        return success(userRoleService.findUserByRoleId(roleId));
    }
	/**
	 * 只能看到自身权限
	 * @param roleId
	 * @return
	 */
    @GetMapping("/{role_id}/resources/permissions")
    public Result<RolePermissionResp> permission(@PathVariable("role_id") Long roleId) {
        return success(this.roleService.findRolePermissionById(roleId));
    }

	/**
	 * 角色关联的资源
	 * @param roleId
	 * @return
	 */
	@GetMapping("/{roleId}/role_res")
    public Result<List<RoleRes>> resByRoleId(@PathVariable Long roleId) {
        return success(roleResService.list(Wraps.<RoleRes>lbQ().eq(RoleRes::getRoleId, roleId)));
    }
	/**
	 * 角色关联的组织
	 * @param roleId
	 * @return
	 */
	@GetMapping("/{roleId}/role_rog")
    public Result<List<RoleOrg>> orgByRoleId(@PathVariable Long roleId) {
        return success(roleOrgService.list(Wraps.<RoleOrg>lbQ().eq(RoleOrg::getRoleId, roleId)));
    }
	/**
	 * 角色分配操作资源
	 * @param roleId
	 * @param dto
	 * @return
	 */
    @PostMapping("/{roleId}/authority")
    public Result<ResponseEntity<Void>> distributionAuthority(@PathVariable Long roleId, @RequestBody RoleResSaveDTO dto) {
        this.roleResService.saveRoleAuthority(dto);
        return success();
    }
	/**
	 * 角色分配用户
	 * @param roleId
	 * @param dto
	 * @return
	 */
    @PostMapping("/{roleId}/users")
    public Result<ResponseEntity<Void>> distributionUser(@PathVariable Long roleId, @RequestBody RoleUserDTO dto) {
        this.roleService.saveUserRole(roleId, dto.getUserIdList());
        return success();
    }

}
