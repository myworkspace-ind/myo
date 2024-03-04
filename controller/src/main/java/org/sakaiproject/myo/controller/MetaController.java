package org.sakaiproject.myo.controller;

import org.sakaiproject.myo.model.TableStructure;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MetaController extends BaseController {
	
	//final static String[] OBJ_HEADER = {"id", "orgId", "userId", "periodId", "name", "reviewDate", "createBy", "lastModified", "lastModifiedBy", "isCustomProgress", "customProgress", "linkedtoid", "createdAt", "srcObjId", "comment", "okrId", "status", "weight"};
	final static String[] OBJ_HEADER = {"Kỳ", "BP", "Họ tên", "Email", "Objective", "Keyresult", "Type", "duedate", "target", "kr_weight", "progress", "completed_date", "self_grade", "grade"};
	
	final static int[] OBJ_COLWIDTHS = {150, 100, 120, 150, 250, 400, 80, 100, 50, 50, 50, 50, 50, 50};
	
	
	@RequestMapping(value = "/meta-table/{table}")
	public TableStructure getTableStructure(@PathVariable("table") String table) {
//		final static List<Object[]> OBJ_INIT_DATA = {"", "", "", "", "", "", "", "", "", "", "", "", "", ""};
		
		return new TableStructure(OBJ_COLWIDTHS, OBJ_HEADER, null);
	}
}
