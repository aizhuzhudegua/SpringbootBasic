package io.github.wzk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.wzk.entity.Alarmlist;
import io.github.wzk.service.AlarmlistService;
import io.github.wzk.mapper.AlarmlistMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 王周凯
* @description 针对表【alarmlist】的数据库操作Service实现
* @createDate 2023-12-06 10:09:56
*/
@Service
public class AlarmlistServiceImpl extends ServiceImpl<AlarmlistMapper, Alarmlist>
    implements AlarmlistService{

    @Autowired
    AlarmlistMapper alarmlistMapper;
    @Override
    public List<Alarmlist> getLatest(String orderBy) {
        //sql参数
        QueryWrapper<Alarmlist> queryWrapper = new QueryWrapper();
        //降序
        queryWrapper.orderByDesc(orderBy);
        //设置分页参数
        Page<Alarmlist> page = new Page<>(1,4);
        alarmlistMapper.selectPage(page,queryWrapper);

        //获取分页数据
        List<Alarmlist> list = page.getRecords();


        return list;
    }
}




