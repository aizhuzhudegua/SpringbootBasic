package io.github.wzk.service;

import io.github.wzk.entity.Alarmlist;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 王周凯
* @description 针对表【alarmlist】的数据库操作Service
* @createDate 2023-12-06 10:09:56
*/
public interface AlarmlistService extends IService<Alarmlist> {
    List<Alarmlist> getLatest(String orderBy);
}
