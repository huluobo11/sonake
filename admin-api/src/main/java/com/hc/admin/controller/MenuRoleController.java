package com.hc.admin.controller;

import java.util.Arrays;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.validation.BindingResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hc.admin.generator.entity.MenuRoleEntity;
import com.hc.admin.generator.service.MenuRoleService;
import cn.nis.ntc.api.controller.BaseController;
import com.common.utils.PageUtils;

import javax.validation.Valid;
import cn.nis.ntc.bean.vo.front.Rets;


/**
 * 
 *
 * @author xzyuan
 * @email yxzbby@aliyun.com
 * @date 2019-06-08 17:36:39
 */
@RestController
@RequestMapping("generator/menurole")
public class MenuRoleController extends BaseController{
    private Logger logger = LoggerFactory.getLogger(MenuRoleController.class);
    @Autowired
    private MenuRoleService menuRoleService;

    /**
     * 列表
     */
    @RequestMapping(method = RequestMethod.GET)
    @ApiResponses({@ApiResponse(code = 200, message = "请求成功")})
    @ApiOperation(value = "查询 MenuRole列表", notes = "查询MenuRole列表")
    public Object list(@Valid MenuRoleDto dto){
        PageUtils page = menuRoleService.queryPage(dto);

        return Rets.success(page);
    }

    /**
     * 保存
     */
    @ApiResponses({@ApiResponse(code = 200, message = "新增成功")})
    @ApiOperation(value = "新增 MenuRole", notes = "新增MenuRole")
    @BussinessLog(value = "save MenuRole",key = "Id")
    @RequestMapping(method = RequestMethod.POST)
    public Object save(@RequestBody MenuRoleEntity menuRole, BindingResult result){
        logger.info(JSON.toJSONString(menuRole));
		menuRoleService.save(menuRole);

        return Rets.success();
    }

    /**
     * 修改
     */
    @ApiResponses({@ApiResponse(code = 200, message = "修改成功")})
    @ApiOperation(value = "修改 MenuRole", notes = "新增MenuRole")
    @BussinessLog(value = "update MenuRole" ,key = "Id")
    @RequestMapping(method = RequestMethod.PUT)
    public Object update(@RequestBody MenuRoleEntity menuRole){
        logger.info(JSON.toJSONString(MenuRole));
		menuRoleService.updateById(menuRole);

        return Rets.success();
    }

    /**
     * 删除
     */
    @ApiResponses({@ApiResponse(code = 200, message = "删除成功")})
    @ApiOperation(value = "删除 MenuRole", notes = "删除 MenuRole")
    @BussinessLog(value = "delete MenuRole",key = "Id")
    public R delete(@RequestBody MenuRoleDto MenuRole){
        Long [] ids=MenuRole.getIds();
		menuRoleService.removeByIds(Arrays.asList(mrIds));

        return Rets.success();
    }

}
