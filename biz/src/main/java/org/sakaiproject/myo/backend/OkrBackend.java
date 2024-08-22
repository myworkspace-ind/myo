package org.sakaiproject.myo.backend;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.sakaiproject.myo.IOkrBackend;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Service
public class OkrBackend implements IOkrBackend {

	@Value("${okr.baseURL:http://localhost:8500}")
	private String okrBaseURL;

	@Value("${okr.authEndpoint:/auth")
	private String authEndpoint;

	@Value("${okr.authEndpoint:/login")
	private String loginEndpoint;

	private String okrAuthToken;

	private final RestTemplate restTemplate = new RestTemplate();

	private static String parentPeriodId;
	private static String childPeriodId;
	private static String organizationId;

	public static void main(String[] args) {
		OkrBackend okrBackend = new OkrBackend();
		okrBackend.loadData();
	}

	public String getAuthToken(String username, String password) throws Exception {
		String serverUrl = okrBaseURL + "/auth";
		Map<String, String> requestBody = new HashMap<>();
		requestBody.put("username", username);
		requestBody.put("password", password);

		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");

		HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

		ResponseEntity<Map> response;
		try {
			response = restTemplate.exchange(serverUrl, HttpMethod.POST, requestEntity, Map.class);
			if (response.getStatusCode() == HttpStatus.OK) {
				Map<String, String> responseBody = response.getBody();
				if (responseBody != null && responseBody.containsKey("token")) {
					okrAuthToken = responseBody.get("token");
					System.out.println("okrAuthToken: " + okrAuthToken);
					return okrAuthToken;
				}
			}
//		    } else {
//		    	System.out.println("Check error");
//		        Map<String, String> responseBody = response.getBody();
//		        if (responseBody != null && responseBody.containsKey("message")) {
//		            String errorMessage = responseBody.get("message");
//		            System.out.println("Error message: " + errorMessage);
//		            return "";
//		        }
//		    }
		} catch (HttpClientErrorException e) {
			// TODO: handle exception
			String errorMessage = e.getResponseBodyAsString();
			// System.out.println("Catch: " + errorMessage);

			JsonParser jsonParser = new JsonParser();
			JsonObject jsonObject = jsonParser.parse(errorMessage).getAsJsonObject();
			if (jsonObject.has("message")) {
				String message = jsonObject.get("message").getAsString();
				// System.out.println("error: " + message);
				throw new Exception(message);
			}
			throw new Exception(errorMessage);
		}
		return null;
	}

	public void loadData() {
		try {

			// Tạo HttpHeaders và thêm token xác thực
			HttpHeaders headers = new HttpHeaders();
			headers.set("Authorization", "Bearer " + okrAuthToken);

			// Tạo HttpEntity với headers
			HttpEntity<String> entity = new HttpEntity<>(headers);

			// Định nghĩa URL endpoint API
			String apiUrl = okrBaseURL + "/period"; // Thay thế bằng endpoint thực tế

			// Gửi yêu cầu GET với headers
			ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, String.class);

			// Kiểm tra mã trạng thái
			// if (response.getStatusCode() == HttpStatus.UNAUTHORIZED) {
			// System.out.println("Unauthorized: Refreshing token and retrying...");
			// getAuthToken(); // Lấy token mới
			headers.set("Authorization", "Bearer " + okrAuthToken); // Cập nhật headers với token mới
			entity = new HttpEntity<>(headers);
			response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, String.class); // Thử lại yêu cầu với token
																							// mới

			System.out.println("Response: " + response.getBody());
		} catch (Exception e) {
			System.out.println("An error occurred: " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public String getPeriod() {
		try {
			System.out.println("Load Period - OkrToken: " + okrAuthToken);
			String serverUrl = okrBaseURL + "/period";
			HttpHeaders headers = new HttpHeaders();
			headers.set("Authorization", "Bearer " + okrAuthToken);
			HttpEntity<String> entity = new HttpEntity<>(headers);

			ResponseEntity<String> response = restTemplate.exchange(serverUrl, HttpMethod.GET, entity, String.class);
			headers.set("Authorization", "Bearer " + okrAuthToken);
			entity = new HttpEntity<>(headers);
			response = restTemplate.exchange(serverUrl, HttpMethod.GET, entity, String.class);
			String responseJson = response.getBody();

			JsonParser jsonParser = new JsonParser();
			JsonElement jsonElement = jsonParser.parse(responseJson);
			JsonObject responseObject = jsonElement.getAsJsonObject();
			JsonArray dataArray = responseObject.getAsJsonArray("data");

			if (dataArray != null && dataArray.size() > 0) {
				for (JsonElement dataElement : dataArray) {
					JsonObject dataObject = dataElement.getAsJsonObject();
					parentPeriodId = dataObject.get("periodId").getAsString();
//					System.out.println("Period ID (Parent): " + parentPeriodId);

					// Check if the data object has a "childs" array
					if (dataObject.has("childs")) {
						JsonArray childs = dataObject.getAsJsonArray("childs");
						for (JsonElement childElement : childs) {
							JsonObject childObject = childElement.getAsJsonObject();
							childPeriodId = childObject.get("periodId").getAsString();
//							System.out.println("Period ID (Child): " + childPeriodId);
						}
					}
				}
			}
			return responseJson;
		} catch (Exception e) {
			System.out.println("An error occurred: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	public String getCurrentPeriodId() {
		try {
			System.out.println(okrAuthToken);
			String serverUrl = okrBaseURL + "/period";
			HttpHeaders headers = new HttpHeaders();
			headers.set("Authorization", "Bearer " + okrAuthToken);
			HttpEntity<String> entity = new HttpEntity<>(headers);

			ResponseEntity<String> response = restTemplate.exchange(serverUrl, HttpMethod.GET, entity, String.class);
			String responseJson = response.getBody();

			JsonParser jsonParser = new JsonParser();
			JsonElement jsonElement = jsonParser.parse(responseJson);
			JsonObject responseObject = jsonElement.getAsJsonObject();
			JsonArray dataArray = responseObject.getAsJsonArray("data");

			if (dataArray != null && dataArray.size() > 0) {
				for (JsonElement dataElement : dataArray) {
					JsonObject dataObject = dataElement.getAsJsonObject();

					// Check if the data object has a "childs" array
					if (dataObject.has("childs")) {
						JsonArray childs = dataObject.getAsJsonArray("childs");
						for (JsonElement childElement : childs) {
							JsonObject childObject = childElement.getAsJsonObject();
							if (childObject.get("currentPeriod").getAsBoolean()) {
								String childPeriodId = childObject.get("periodId").getAsString();
//								System.out.println("Current Period ID (Child): " + childPeriodId);
								return childPeriodId;
							}
						}
					} else {
						// Check if the current period is true in the parent period
						if (dataObject.get("currentPeriod").getAsBoolean()) {
							String periodId = dataObject.get("periodId").getAsString();
							System.out.println("Current Period ID: " + periodId);
							return periodId;
						}
					}
				}
			}

			System.out.print(responseJson);
			return null;
		} catch (Exception e) {
			System.out.println("An error occurred: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String getOrganization() {
		try {
			System.out.println(okrAuthToken);
			String serverUrl = okrBaseURL + "/userprofile";
			HttpHeaders headers = new HttpHeaders();
			headers.set("Authorization", "Bearer " + okrAuthToken);
			HttpEntity<String> entity = new HttpEntity<>(headers);
			ResponseEntity<String> response = restTemplate.exchange(serverUrl, HttpMethod.GET, entity, String.class);
			headers.set("Authorization", "Bearer " + okrAuthToken);
			entity = new HttpEntity<>(headers);
			response = restTemplate.exchange(serverUrl, HttpMethod.GET, entity, String.class);
			String responseJson = response.getBody();
			JsonParser jsonParser = new JsonParser();
			JsonElement jsonElement = jsonParser.parse(responseJson);
			JsonObject responseObject = jsonElement.getAsJsonObject();

			if (responseObject.has("data")) {
				JsonElement dataElement = responseObject.get("data");

				if (dataElement.isJsonObject()) {
					// Handle case where "data" is an object
					JsonObject dataObject = dataElement.getAsJsonObject();

					if (dataObject.has("defaultOrgId")) {
						organizationId = dataObject.get("defaultOrgId").getAsString();
//						System.out.println("Organization ID: " + organizationId);
					} else {
						System.out.println("Error: 'defaultOrgId' not found in 'data' object.");
					}
				} else if (dataElement.isJsonArray()) {
					// Handle case where "data" is an array (assuming first element)
					JsonArray dataArray = dataElement.getAsJsonArray();

					if (dataArray.size() > 0) {
						JsonObject firstOrganization = dataArray.get(0).getAsJsonObject();

						if (firstOrganization.has("defaultOrgId")) {
							organizationId = firstOrganization.get("defaultOrgId").getAsString();
							System.out.println("Organization ID: " + organizationId);
						} else {
							System.out.println("Error: 'defaultOrgId' not found in the first element of 'data' array.");
						}
					} else {
						System.out.println("Error: 'data' array is empty.");
					}
				} else {
					System.out.println("Error: Unexpected type for 'data'.");
				}
			} else {
				System.out.println("Error: 'data' field is missing.");
			}

//			System.out.println(responseJson);
//			System.out.println("OrgID: " + organizationId);
			return organizationId;
		} catch (Exception e) {
			System.out.println("An error occurred: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	
//	@Override
//	public String getObjectives(String periodId, String organizationId) {
//		try {
//			System.out.println(okrAuthToken);
//			if(organizationId == null || organizationId.isEmpty()) {
//				organizationId = getOrganization();
//			}
//			if(periodId == null || periodId.isEmpty()) {
//				periodId = getCurrentPeriodId();
//			}
//			
////			String serverUrl = okrBaseURL + "/okr/auth/all/" + getOrganization() + "/" + getCurrentPeriodId();
//			String serverUrl = okrBaseURL + "/okr/auth/all/" + organizationId + "/" + periodId;
//			HttpHeaders headers = new HttpHeaders();
//			headers.set("Authorization", "Bearer " + okrAuthToken);
//			HttpEntity<String> entity = new HttpEntity<>(headers);
//			System.out.println("Fined1");
//			ResponseEntity<String> response = restTemplate.exchange(serverUrl, HttpMethod.GET, entity, String.class);
//			headers.set("Authorization", "Bearer " + okrAuthToken);
//			entity = new HttpEntity<>(headers);
//			response = restTemplate.exchange(serverUrl, HttpMethod.GET, entity, String.class);
//			String responseJson = response.getBody(); 
//			System.out.print(responseJson);
//			return responseJson;
//		} catch (Exception e) {
//			System.out.println("An error occurred: " + e.getMessage());
//			e.printStackTrace();
//			return null;
//		}
//	}

	@Override
	public String getObjectives() {
	    try {
	        String serverUrl = okrBaseURL + "/okr/auth/all/" + getOrganization() + "/" + getCurrentPeriodId();

	        HttpHeaders headers = new HttpHeaders();
	        headers.set("Authorization", "Bearer " + okrAuthToken);

	        HttpEntity<String> entity = new HttpEntity<>(headers);

	        ResponseEntity<String> response = restTemplate.exchange(serverUrl, HttpMethod.GET, entity, String.class);

	        String responseJson = response.getBody();

	        System.out.println("Response JSON: " + responseJson);
	        return responseJson;
	    } catch (Exception e) {
	        System.out.println("An error occurred while fetching objectives: " + e.getMessage());
	        e.printStackTrace();
	        return null;
	    }
	}

	@Override
	public ResponseEntity<String> postOkr(String jsonData) {
		try {
			String serverUrl = okrBaseURL + "/personalOkr/advanced/";
			HttpHeaders headers = new HttpHeaders();
			System.out.println("post1");
			headers.set("Content-Type", "application/json");
			headers.set("Authorization", "Bearer " + okrAuthToken);

			HttpEntity<String> requestEntity = new HttpEntity<>(jsonData, headers);
			System.out.println("post2");
			ResponseEntity<String> response = restTemplate.exchange(serverUrl, HttpMethod.POST, requestEntity,
					String.class);
			System.out.println("post3");
			if (response.getStatusCode() == HttpStatus.OK) {
				// Handle successful response
				return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
			} else {
				// Handle non-200 response status
				return new ResponseEntity<>(response.getBody(), response.getStatusCode());
			}
		} catch (Exception e) {
			// Handle exception
			e.printStackTrace();
			return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	

	@Override
	public ResponseEntity<String> postPeriod(String jsonData) {
		try {
			String serverUrl = okrBaseURL + "/period";
			HttpHeaders headers = new HttpHeaders();
			System.out.println("post1");
			headers.set("Content-Type", "application/json");
			headers.set("Authorization", "Bearer " + okrAuthToken);

			HttpEntity<String> requestEntity = new HttpEntity<>(jsonData, headers);
			System.out.println("postperiod2");
			ResponseEntity<String> response = restTemplate.exchange(serverUrl, HttpMethod.POST, requestEntity,
					String.class);
			System.out.println("postperiod3");
			if (response.getStatusCode() == HttpStatus.OK) {
				// Handle successful response
				return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
			} else {
				// Handle non-200 response status
				return new ResponseEntity<>(response.getBody(), response.getStatusCode());
			}
		} catch (Exception e) {
			// Handle exception
			e.printStackTrace();
			return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public boolean deleteObjectives(String Id) {
		try {
			System.out.println("deleteObjectiveTest");
			String objectiveId = getObjectiveIdByName(Id);
			System.out.println("ObjID: " + objectiveId);
			String serverUrl = okrBaseURL + "/personalOkr/" + objectiveId;
			System.out.println("okok1");
			HttpHeaders headers = new HttpHeaders();
			headers.set("Authorization", "Bearer " + okrAuthToken);

			// Create the HTTP entity with headers
			HttpEntity<String> entity = new HttpEntity<>(headers);

			// Send DELETE request
			ResponseEntity<Void> response = restTemplate.exchange(serverUrl, HttpMethod.DELETE, entity, Void.class);

			// Check response status
			if (response.getStatusCode() == HttpStatus.OK || response.getStatusCode() == HttpStatus.NO_CONTENT) {
				System.out.println("Status: " + response.getStatusCode());
			    System.out.println("Objective deleted successfully");
			    return true;
			} else {
			    System.out.println("Failed to delete objective. Status code: " + response.getStatusCode());
			    return false;
			}
		} catch (Exception e) {
			System.out.println("An error occurred: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	public String getObjectiveIdByName(String name) {
	    try {
	        // Define the URL for the API endpoint to fetch all objectives
	        String serverUrl = okrBaseURL + "/okr/auth/all/" + getOrganization() + "/" + getCurrentPeriodId();
	        
	        // Set up the HTTP headers with authorization token
	        HttpHeaders headers = new HttpHeaders();
	        headers.set("Authorization", "Bearer " + okrAuthToken);
	        HttpEntity<String> entity = new HttpEntity<>(headers);
	        
	        // Make the API call to fetch all objectives
	        ResponseEntity<String> response = restTemplate.exchange(serverUrl, HttpMethod.GET, entity, String.class);
	        
	        // Get the JSON response body
	        String responseJson = response.getBody();
	        System.out.println("Objectives JSON: " + responseJson);
	        
	        // Parse the JSON response
	        JsonParser jsonParser = new JsonParser();
	        JsonElement jsonElement = jsonParser.parse(responseJson);
	        JsonObject responseObject = jsonElement.getAsJsonObject();
	        
	        // Extract the 'objectives' array from the nested 'data' object
	        JsonObject dataObject = responseObject.getAsJsonObject("data");
	        JsonArray objectivesArray = dataObject.getAsJsonArray("objectives");

	        // Iterate through the array to find the objective with the matching name
	        for (JsonElement element : objectivesArray) {
	            JsonObject objective = element.getAsJsonObject();
	            
	            // Log the JSON object being processed
	            System.out.println("Processing Objective: " + objective.toString());
	            
	            // Check if the "description" attribute matches the name
	            if (objective.has("description")) {
	                String objectiveName = objective.get("description").getAsString(); // Get objective description as String
	                
	                // Log the name being compared
	                System.out.println("Comparing Name: " + objectiveName + " with " + name);
	                
	                if (objectiveName.equals(name)) {
	                    // Return the "objectiveId" if the name matches
	                    System.out.println("Found Objective with Name: " + name);
	                    return objective.has("objectiveId") ? objective.get("objectiveId").getAsString() : null;
	                }
	            }
	        }
	        
	        System.out.println("No objective found with Name: " + name);
	        return null;
	    } catch (Exception e) {
	        System.out.println("An error occurred while fetching the objective_id by name: " + e.getMessage());
	        e.printStackTrace();
	        return null;
	    }
	}

	public ResponseEntity<String> updateKeyResultGrade(String keyResultId, String payload){
		try {
			String serverUrl = okrBaseURL + "/keyResult/grade/" + keyResultId;
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("Authorization", "Bearer " + okrAuthToken);
			
			HttpEntity<String> requestEntity = new HttpEntity<>(payload, headers);
            ResponseEntity<String> response = restTemplate.exchange(serverUrl, HttpMethod.PUT, requestEntity, String.class);
            
            if (response.getStatusCode() == HttpStatus.OK) {
                return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(response.getBody(), response.getStatusCode());
            }
            
		} catch (Exception e) {
			e.printStackTrace();
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<String> updateOkrDraftSave(String updatedOkr) {
		try {
			String serverUrl = okrBaseURL + "/personalOkr/advanced/update/";
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("Authorization", "Bearer " + okrAuthToken);
			

			HttpEntity<String> requestEntity = new HttpEntity<>(updatedOkr, headers);
            ResponseEntity<String> response = restTemplate.exchange(serverUrl, HttpMethod.POST, requestEntity, String.class);
			System.out.println("Checked");
			if (response.getStatusCode() == HttpStatus.OK) {
				// Handle successful response
				return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
			} else {
				// Handle non-200 response status
				return new ResponseEntity<>(response.getBody(), response.getStatusCode());
			}
		} catch (Exception e) {
			// Handle exception
			e.printStackTrace();
			return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	public String getOkrAuthToken() {
		return okrAuthToken;
	}

	public void setOkrAuthToken(String token) {
		this.okrAuthToken = token;
	}

}