package com.github.jgzl.application.auth.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.github.jgzl.common.core.util.Result;
import com.github.jgzl.common.security.validate.Add;
import com.github.jgzl.common.security.vo.SysOauthClientDetailsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.jgzl.application.auth.service.client.SysOauthClientDetailsService;

/**
 * 客户端管理Controller
 *
 * @author lihaifeng
 * 2019/7/5 15:15
 */
@RestController
@RequestMapping("/clients")
public class OauthClientController {

    @Autowired
    private SysOauthClientDetailsService oauthClientService;

	/**
	 * 查询单个客户端
	 *
	 * @param clientId 客户端ID
	 * @return
	 */
	@GetMapping("/{clientId}")
	public ResponseEntity<Result> get(@PathVariable("clientId") String clientId) {

		return ResponseEntity.ok(Result.ok(oauthClientService.getVo(clientId)));
	}

	/**
	 * 修改单个客户端
	 *
	 * @param vo 客户端
	 * @return
	 */
	@PutMapping
	public ResponseEntity<Result> update(@Valid @RequestBody SysOauthClientDetailsVO vo) {

		return ResponseEntity.ok(Result.ok(oauthClientService.update(vo)));
	}

	/**
	 * 新增单个客户端
	 *
	 * @param vo 客户端
	 * @return
	 */
	@PostMapping
	public ResponseEntity<Result> add(@Validated(Add.class) @RequestBody SysOauthClientDetailsVO vo) {

		return ResponseEntity.ok(Result.ok(oauthClientService.add(vo)));
	}

	/**
	 * 删除单个客户端
	 *
	 * @param clientId 客户端ID
	 * @return
	 */
	@DeleteMapping("/{clientId}")
	public ResponseEntity<Result> delete(@PathVariable("clientId") String clientId) {

		return ResponseEntity.ok(Result.ok(oauthClientService.delete(clientId)));
	}

	/**
	 * 分页查询客户端
	 *
	 * @param request 请求参数
	 * @return ResponseEntity
	 */
	@GetMapping
	public ResponseEntity<Result> page(HttpServletRequest request) {
		final Integer current = Integer.valueOf(request.getParameter("page"));
		final Integer size = Integer.valueOf(request.getParameter("limit"));
		Page page = new Page(current, size);
		return ResponseEntity.ok(Result.ok(oauthClientService.selectPageVo(page)));
	}
}
