package org.sakaiproject.myo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class StorageController extends BaseController {

    @RequestMapping("/storage")
    public String goStorage() {
        return "storage/index";
    }
}
