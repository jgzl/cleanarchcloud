package com.github.jgzl.infra.upms.controller.baseinfo;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.jgzl.common.core.annotation.log.SysLog;
import com.github.jgzl.common.core.util.BeanUtilPlus;
import com.github.jgzl.common.core.util.Result;
import com.github.jgzl.infra.upms.domain.dto.StationPageDTO;
import com.github.jgzl.infra.upms.domain.dto.StationSaveDTO;
import com.github.jgzl.infra.upms.domain.entity.baseinfo.Station;
import com.github.jgzl.infra.upms.service.StationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.github.jgzl.common.core.util.Result.success;

/**
 * 岗位管理
 *
 * @author lihaifeng
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/stations")
public class StationController {

    private final StationService stationService;

	/**
	 * 岗位列表
	 * @param params
	 * @return
	 */
    @GetMapping
    public Result<IPage<Station>> query(StationPageDTO params) {
        return success(stationService.findStationPage(params, params));
    }

	/**
	 * 添加岗位
	 * @param dto
	 * @return
	 */
    @PostMapping
    @SysLog(value = "添加岗位")
    public Result<ResponseEntity<Void>> add(@Validated @RequestBody StationSaveDTO dto) {
        stationService.save(BeanUtil.toBean(dto, Station.class));
        return success();
    }

	/**
	 * 编辑岗位
	 * @param id
	 * @param dto
	 * @return
	 */
    @PutMapping("/{id}")
    @SysLog(value = "编辑岗位")
    public Result<ResponseEntity<Void>> edit(@PathVariable Long id, @Validated @RequestBody StationSaveDTO dto) {
        stationService.updateById(BeanUtilPlus.toBean(id, dto, Station.class));
        return success();
    }

	/**
	 * 删除岗位
	 * @param id
	 * @return
	 */
    @DeleteMapping("/{id}")
    @SysLog(value = "删除岗位")
    public Result<ResponseEntity<Void>> del(@PathVariable Long id) {
        stationService.removeById(id);
        return success();
    }

}
