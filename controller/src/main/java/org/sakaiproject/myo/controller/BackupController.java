package org.sakaiproject.myo.controller;

import org.sakaiproject.myo.service.ObjService;
import org.sakaiproject.myo.service.OkrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BackupController extends BaseController {
	
	@Autowired
	ObjService objService;
	
	@Autowired
	OkrService okrSerivce;

    @RequestMapping("/backup")
    public String goBackup() {

        return "backup/index";
    }
}
