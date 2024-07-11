package org.sakaiproject.myo.controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.sakaiproject.myo.model.TableStructure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import lombok.extern.slf4j.Slf4j;

/**
 * Handles requests for the application home page.
 */
@Controller
@Slf4j
public class CrudController extends BaseController {
	@Value("${productList.colHeaders:description, dueDate, itype, weight, numberResult, numberTarget, yesNoResult,yesNoTarget, percentageResult, percentageTarget,standard,startvalue }")
	private String[] OkrListColHeaders;

	@Value("${productList.colWidths:150, 50, 150, 100, 75, 75, 75,75,75,75,75,75}")
	private int[] OkrListColWidths;

	@Value("classpath:okr.json")
	private Resource resourceModelJson;

	@GetMapping(value = "crud")
	public ModelAndView displayCrudOkr(HttpServletRequest request, HttpSession httpSession) {
		ModelAndView mav = new ModelAndView("crud");
		return mav;
	}

	@GetMapping(value = "/crud/loaddata", produces = "application/json")
	@ResponseBody
	public ResponseEntity<TableStructure> getProductTableModelData() {
		List<Object[]> lstModelData = getDemoModelData();
		TableStructure okrTable = new TableStructure(OkrListColWidths, OkrListColHeaders, lstModelData);
		return ResponseEntity.ok(okrTable);
	}

	private List<Object[]> getDemoModelData() {
		List<Object[]> data = new ArrayList<>();

		try {
			String jsonContent = IOUtils.toString(resourceModelJson.getInputStream(), StandardCharsets.UTF_8);
			JSONArray jsonArray = new JSONArray(jsonContent);

			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				data.add(new Object[] { jsonObject.getString("description"), jsonObject.getString("dueDate"),
						jsonObject.getString("itype"), jsonObject.getString("weight"),
						jsonObject.getString("numberResult"), jsonObject.getString("numberTarget"),
						jsonObject.getString("yesNoResult"), jsonObject.getString("yesNoTarget"),
						jsonObject.getString("percentageResult"), jsonObject.getString("percentageTarget"),
						jsonObject.getString("standard"), jsonObject.getString("startvalue") });
			}
		} catch (IOException e) {
			log.error("Error reading model data", e);
		}

		return data;
	}
}