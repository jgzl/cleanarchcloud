package com.github.jgzl.infra.upms.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.jgzl.infra.upms.service.SysOauthClientDetailsService;
import com.github.jgzl.common.core.util.Result;
import com.github.jgzl.common.api.validate.Add;
import com.github.jgzl.common.api.vo.SysOauthClientDetailsVo;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 客户端管理Controller
 *
 * @author lihaifeng
 * 2019/7/5 15:15
 */
@Slf4j
@AllArgsConstructor
@Api(tags = "客户端控制器")
@RestController
public class OauthClientController {

    private SysOauthClientDetailsService oauthClientService;

	/**
	 * 查询单个客户端
	 *
	 * @param clientId 客户端ID
	 * @return
	 */
	@GetMapping("/client/{clientId}")
	public Result<SysOauthClientDetailsVo> get(@PathVariable("clientId") String clientId) {
		return Result.ok(oauthClientService.getVo(clientId));
	}

	/**
	 * 修改单个客户端
	 *
	 * @param vo 客户端
	 * @return
	 */
	@PutMapping("/client")
	public Result<Boolean> update(@Valid @RequestBody SysOauthClientDetailsVo vo) {

		return Result.ok(oauthClientService.update(vo));
	}

	/**
	 * 新增单个客户端
	 *
	 * @param vo 客户端
	 * @return
	 */
	@PostMapping("/client")
	public Result<Boolean> add(@Validated(Add.class) @RequestBody SysOauthClientDetailsVo vo) {
		return Result.ok(oauthClientService.add(vo));
	}

	/**
	 * 删除单个客户端
	 *
	 * @param clientId 客户端ID
	 * @return
	 */
	@DeleteMapping("/client/{clientId}")
	public Result<Boolean> delete(@PathVariable("clientId") String clientId) {
		return Result.ok(oauthClientService.removeById(clientId));
	}

	/**
	 * 分页查询客户端
	 *
	 * @return ResponseEntity
	 */
	@GetMapping("/clients")
	public Result<IPage<SysOauthClientDetailsVo>> pageList(@RequestParam Integer page, @RequestParam Integer rows) {
		Page<SysOauthClientDetailsVo> pageVo = new Page<>(page, rows);
		return Result.ok(oauthClientService.selectPageVo(pageVo));
	}

	/**
	 * 删除多个客户端(至多100个)
	 *
	 * @param clientId 客户端ID
	 * @return
	 */
	@DeleteMapping("/clients/{clientId}")
	public Result<Boolean> multiDelete(@PathVariable("clientId") String clientId) {
		List<String> ids = Arrays.stream(clientId.split(",")).collect(Collectors.toList());
		return Result.ok(oauthClientService.removeByIds(ids));
	}
}
