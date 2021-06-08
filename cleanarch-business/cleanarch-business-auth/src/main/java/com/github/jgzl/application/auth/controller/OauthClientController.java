package com.github.jgzl.application.auth.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.jgzl.application.auth.service.client.SysOauthClientDetailsService;
import com.github.jgzl.common.core.util.Result;
import com.github.jgzl.common.security.validate.Add;
import com.github.jgzl.common.security.vo.SysOauthClientDetailsVO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 客户端管理Controller
 *
 * @author lihaifeng
 * 2019/7/5 15:15
 */
@Api(tags = "客户端控制器")
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
	public Result get(@PathVariable("clientId") String clientId) {

		return Result.ok(oauthClientService.getVo(clientId));
	}

	/**
	 * 修改单个客户端
	 *
	 * @param vo 客户端
	 * @return
	 */
	@PutMapping
	public Result update(@Valid @RequestBody SysOauthClientDetailsVO vo) {

		return Result.ok(oauthClientService.update(vo));
	}

	/**
	 * 新增单个客户端
	 *
	 * @param vo 客户端
	 * @return
	 */
	@PostMapping
	public Result add(@Validated(Add.class) @RequestBody SysOauthClientDetailsVO vo) {

		return Result.ok(oauthClientService.add(vo));
	}

	/**
	 * 删除单个客户端
	 *
	 * @param clientId 客户端ID
	 * @return
	 */
	@DeleteMapping("/{clientId}")
	public Result delete(@PathVariable("clientId") String clientId) {

		return Result.ok(oauthClientService.delete(clientId));
	}

	/**
	 * 分页查询客户端
	 *
	 * @param request 请求参数
	 * @return ResponseEntity
	 */
	@GetMapping
	public Result page(@RequestParam Integer page,@RequestParam Integer rows) {
		Page pageVo = new Page(page, rows);
		return Result.ok(oauthClientService.selectPageVo(pageVo));
	}
}
