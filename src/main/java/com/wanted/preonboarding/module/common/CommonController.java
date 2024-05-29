package com.wanted.preonboarding.module.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/")
public class CommonController {

    @GetMapping("/docs")
    public String getRestDocs(){
        return "/docs/index.html";
    }

}
