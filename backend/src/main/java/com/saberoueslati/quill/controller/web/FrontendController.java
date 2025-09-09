package com.saberoueslati.quill.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FrontendController {

    @RequestMapping(value = "/{path:[^\\.]*}")  // matches any path without a dot (excludes static files)
    public String redirect() {
        // Forward to Angular's index.html
        return "forward:/index.html";
    }
}
