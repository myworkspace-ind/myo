package org.sakaiproject.myo.controller;

import java.util.List;

import org.sakaiproject.myo.model.SearchObj;
import org.sakaiproject.myo.service.OkrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class RestfulController extends BaseController {
	
//	final static String[] OBJ_HEADER = {"id", "orgId", "userId", "periodId", "name", "reviewDate", "createBy", "lastModified", "lastModifiedBy", "isCustomProgress", "customProgress", "linkedtoid", "createdAt", "srcObjId", "comment", "okrId", "status", "weight"};
	@Autowired
	OkrService okrService;

    @RequestMapping("/obj/byemail/{email}")
    public List<Object[]> getObjByEmail(@PathVariable("email") String email) {
    	
    	List<Object[]> lstObj = okrService.getRepo().findObjByEmail(new String[] {email});
    	
    	int len = (lstObj != null) ? lstObj.size() : 0;
    	log.debug("len=" + len);

        return lstObj;
    }
    
    // @RequestMapping(value = "/search-obj", method = RequestMethod.POST, produces="application/json")
    // If no produces="application/json": return json with tags such as: List><item>Quý 3 - 2022 (10,11,12/2022)</item>
    @PostMapping(value = "/search-obj", produces="application/json")
    public List<Object[]>  processSearch(@ModelAttribute("model") SearchObj model, BindingResult bindingResult) {
    	List<Object[]> lstObj = okrService.getRepo().findObjByEmail(new String[] {model.getEmails()});
    	
    	int len = (lstObj != null) ? lstObj.size() : 0;
    	log.debug("len=" + len);

        return lstObj;
    }
}
