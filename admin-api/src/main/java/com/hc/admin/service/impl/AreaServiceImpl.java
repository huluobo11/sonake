package com.hc.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hc.admin.bean.Area;
import com.hc.admin.common.PageUtils;
import com.hc.admin.dao.AreaDao;
import com.hc.admin.service.AreaService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
/**
 * 
 *
 * @author xzyuan
 * @email yxzbby@aliyun.com
 * @date 2019-06-08 17:36:41
 */
@Service("areaService")
public class AreaServiceImpl extends ServiceImpl<AreaDao, Area> implements AreaService {

    @Override
    public PageUtils queryPage(Area area) {
        //构造自定义查询条件(自定义添加条件)
        LambdaQueryWrapper<Area> queryWrapper=new LambdaQueryWrapper<>();
        IPage<Area> page = this.page(
                new Page<>(),
                queryWrapper
        );
        return new PageUtils(page);
    }

}