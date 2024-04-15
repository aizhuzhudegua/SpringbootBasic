package io.github.wzk.controller;

import io.github.wzk.entity.Alarmlist;
import io.github.wzk.service.AlarmlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/AlarmList")
@CrossOrigin
public class AlarmlistController {

    @Autowired
    AlarmlistService alarmlistservice;

    @GetMapping("/getLatest/{orderBy}")
    @ResponseBody
    public List<Alarmlist> getLatest(@PathVariable("orderBy") String orderBy) {
        List<Alarmlist> list =  alarmlistservice.getLatest(orderBy);
        //System.out.println(list.get(0));
        return list;
    }

}
