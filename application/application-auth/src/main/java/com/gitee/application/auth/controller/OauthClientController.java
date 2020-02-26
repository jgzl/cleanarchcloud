package com.gitee.application.auth.controller;

import javax.validation.Valid;

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
import com.gitee.application.auth.service.client.SsoOauthClientDetailsService;
import com.gitee.common.core.util.Response;
import com.gitee.common.security.validate.Add;
import com.gitee.common.security.vo.SsoOauthClientDetailsVO;

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
    private SsoOauthClientDetailsService oauthClientService;

    /**
     * 查询单个客户端
     *
     * @param clientId 客户端ID
     * @return
     */
    @GetMapping("/{clientId}")
    public ResponseEntity<Response> get(@PathVariable("clientId") String clientId) {

        return ResponseEntity.ok(Response.success(oauthClientService.getVo(clientId)));
    }

    /**
     * 修改单个客户端
     *
     * @param vo 客户端
     * @return
     */
    @PutMapping
    public ResponseEntity<Response> update(@Valid @RequestBody SsoOauthClientDetailsVO vo) {

        return ResponseEntity.ok(Response.success(oauthClientService.update(vo)));
    }

    /**
     * 新增单个客户端
     *
     * @param vo 客户端
     * @return
     */
    @PostMapping
    public ResponseEntity<Response> add(@Validated(Add.class) @RequestBody SsoOauthClientDetailsVO vo) {

        return ResponseEntity.ok(Response.success(oauthClientService.add(vo)));
    }

    /**
     * 删除单个客户端
     *
     * @param clientId 客户端ID
     * @return
     */
    @DeleteMapping("/{clientId}")
    public ResponseEntity<Response> delete(@PathVariable("clientId") String clientId) {

        return ResponseEntity.ok(Response.success(oauthClientService.delete(clientId)));
    }

    /**
     * 分页查询客户端
     *
     * @param page 分页参数
     * @return
     */
    @GetMapping
    public ResponseEntity<Response> page(Page page) {

        return ResponseEntity.ok(Response.success(oauthClientService.selectPageVo(page)));
    }
}
