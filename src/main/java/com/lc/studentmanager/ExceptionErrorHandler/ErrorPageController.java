package com.lc.studentmanager.ExceptionErrorHandler;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @BelongsProject: studentmanager
 * @BelongsPackage: com.lc.studentmanager.ExceptionErrorHandler
 * @Author: lc
 * @CreateTime: 2019-12-14 13:46
 * @Description:
 */
@Controller
public class ErrorPageController implements ErrorController {

    @GetMapping("/error")
    public String errorPage(){
        return "404";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
