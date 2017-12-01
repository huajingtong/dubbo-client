package com.paulzhangcc.democlient.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.paulzhangcc.demo.rpc.api.DemoFacadeService;
import com.paulzhangcc.demo.rpc.dto.DemoDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserService{
    @Reference
    DemoFacadeService demoFacadeService;

    public List<DemoDTO> top(){
        return demoFacadeService.top();
    }
}
