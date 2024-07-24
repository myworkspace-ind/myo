package org.sakaiproject.myo.backend;

import org.sakaiproject.myo.IOkrBackend;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
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

	@Value("${okr.username:admin@myworkspace.vn}")
	private String username;

	@Value("${okr.password:admin}")
	private String password;

	@Value("${okr.token:eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbkBteXdvcmtzcGFjZS52biIsInJvbGUiOiJBRE1JTiIsImlhdCI6MTcyMTI5NjYxOCwiZXhwIjoxNzIyMTYwNjE4fQ.y1RYJuQ4FzmdFbzgXzJDT9_XKF1AdrNjz5q9ibwfpLmaX6fVmCjCHr6UraR47eQRm5fE5gF3xEkzK6HlRCq4Yg}")
	private String okrAuthToken;

	private final RestTemplate restTemplate = new RestTemplate();
	
	private static String parentPeriodId;
	private static String childPeriodId;
	private static String organizationId;

	public static void main(String[] args) {
		OkrBackend okrBackend = new OkrBackend();
		okrBackend.loadData();
	}

	private String getAuthToken() {
		// Create Gson instance
		Gson gson = new Gson();

		// Create body request with username and password
		JsonObject bodyJson = new JsonObject();
		bodyJson.addProperty("username", username);
		bodyJson.addProperty("password", password);
		String body = gson.toJson(bodyJson);

		// Create HttpHeaders for the request
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");
		// Create HttpEntity with headers and body
		HttpEntity<String> entity = new HttpEntity<>(body, headers);
		// Send POST request to endpoint to get token
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<JsonResponse> response;
		try {
			response = restTemplate.exchange(okrBaseURL + loginEndpoint, HttpMethod.POST, entity, JsonResponse.class);
		} catch (HttpClientErrorException e) {
			System.err.println("HTTP error occurred: " + e.getStatusCode() + " - " + e.getStatusText());
			return null;
		} catch (Exception e) {
			// Handle other exceptions
			System.err.println("Error occurred: " + e.getMessage());
			return null;
		}

		if (response.getStatusCode().is2xxSuccessful()) {
			JsonResponse jsonResponse = response.getBody();
			if (jsonResponse != null && jsonResponse.getToken() != null) {
				String token = jsonResponse.getToken();
				System.out.println("Success get token: " + token);
				return token;
			} else {
				System.err.println("Response does not contain token field or 'token' is null");
				return null; 
			}
		} else {
			System.err.println("Request failed with status code: " + response.getStatusCode());
			return null; 
		}
	}

	public void loadData() {
		try {
			// Lấy token mới nếu chưa có hoặc đã hết hạn
			if (okrAuthToken == null || okrAuthToken.isEmpty()) {
				getAuthToken();
			}

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
			getAuthToken(); // Lấy token mới
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
			if (okrAuthToken == null || okrAuthToken.isEmpty()) {
				System.out.println("Loading token...");
				// okrAuthToken = getAuthToken();
				System.out.println("Loaded token..." + getAuthToken());
			}
			System.out.println(okrAuthToken);
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
                    System.out.println("Period ID (Parent): " + parentPeriodId);

                    // Check if the data object has a "childs" array
                    if (dataObject.has("childs")) {
                        JsonArray childs = dataObject.getAsJsonArray("childs");
                        for (JsonElement childElement : childs) {
                            JsonObject childObject = childElement.getAsJsonObject();
                            childPeriodId = childObject.get("periodId").getAsString();
                            System.out.println("Period ID (Child): " + childPeriodId);
                        }
                    }
                }
            }

			System.out.print(responseJson);
			return responseJson;
		} catch (Exception e) {
			System.out.println("An error occurred: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public String getOrganization() {
		try {
			if (okrAuthToken == null || okrAuthToken.isEmpty()) {
				System.out.println("Loading token...");
				// okrAuthToken = getAuthToken();
				System.out.println("Loaded token..." + getAuthToken());
			}
			System.out.println(okrAuthToken);
			String serverUrl = okrBaseURL + "/userprofile";
			HttpHeaders headers = new HttpHeaders();
			headers.set("Authorization", "Bearer " + okrAuthToken);
			HttpEntity<String> entity = new HttpEntity<>(headers);
			System.out.println("Fine1");
			ResponseEntity<String> response = restTemplate.exchange(serverUrl, HttpMethod.GET, entity, String.class);
			headers.set("Authorization", "Bearer " + okrAuthToken);
			entity = new HttpEntity<>(headers);
			response = restTemplate.exchange(serverUrl, HttpMethod.GET, entity, String.class);
			System.out.println("Fine2");
			String responseJson = response.getBody(); 
			System.out.println("Fine3");
            JsonParser jsonParser = new JsonParser();
            JsonElement jsonElement = jsonParser.parse(responseJson);
            JsonObject responseObject = jsonElement.getAsJsonObject();
//            JsonArray dataArray = responseObject.getAsJsonArray("data");
            System.out.println("Fine4");
            if (responseObject.has("data")) {
                JsonElement dataElement = responseObject.get("data");
                
                if (dataElement.isJsonObject()) {
                    // Handle case where "data" is an object
                    JsonObject dataObject = dataElement.getAsJsonObject();
                    
                    if (dataObject.has("defaultOrgId")) {
                        organizationId = dataObject.get("defaultOrgId").getAsString();
                        System.out.println("Organization ID: " + organizationId);
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

			System.out.println(responseJson);
			System.out.println("OrgID: " + organizationId);
			return organizationId;
		} catch (Exception e) {
			System.out.println("An error occurred: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	
	@Override
	public String getObjectives() {
		try {
			if (okrAuthToken == null || okrAuthToken.isEmpty()) {
				System.out.println("Loading token...");
				// okrAuthToken = getAuthToken();
				System.out.println("Loaded token..." + getAuthToken());
			}
			System.out.println(okrAuthToken);
			String serverUrl = okrBaseURL + "/okr/auth/all/" + getOrganization() + "/1074bb9e-f8e4-4e87-935b-a0f009ddbe4a";
			HttpHeaders headers = new HttpHeaders();
			headers.set("Authorization", "Bearer " + okrAuthToken);
			HttpEntity<String> entity = new HttpEntity<>(headers);
			System.out.println("Fined1");
			ResponseEntity<String> response = restTemplate.exchange(serverUrl, HttpMethod.GET, entity, String.class);
			headers.set("Authorization", "Bearer " + okrAuthToken);
			entity = new HttpEntity<>(headers);
			response = restTemplate.exchange(serverUrl, HttpMethod.GET, entity, String.class);
			System.out.println("Fined2");
			String responseJson = response.getBody(); 
			System.out.println("Fined3");
			
//			JsonParser jsonParser = new JsonParser();
//            JsonElement jsonElement = jsonParser.parse(responseJson);
//            JsonObject responseObject = jsonElement.getAsJsonObject();
//            JsonArray dataArray = responseObject.getAsJsonArray("data");
//
//            if (dataArray != null && dataArray.size() > 0) {
//                for (JsonElement dataElement : dataArray) {
//                    JsonObject dataObject = dataElement.getAsJsonObject();
//                    parentPeriodId = dataObject.get("periodId").getAsString();
//                    System.out.println("Period ID (Parent): " + parentPeriodId);
//
//                    // Check if the data object has a "childs" array
//                    if (dataObject.has("childs")) {
//                        JsonArray childs = dataObject.getAsJsonArray("childs");
//                        for (JsonElement childElement : childs) {
//                            JsonObject childObject = childElement.getAsJsonObject();
//                            childPeriodId = childObject.get("periodId").getAsString();
//                            System.out.println("Period ID (Child): " + childPeriodId);
//                        }
//                    }
//                }
//            }

			System.out.print(responseJson);
			return responseJson;
		} catch (Exception e) {
			System.out.println("An error occurred: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	
	class JsonResponse {
		private String token;
		private String messages;

		public String getToken() {
			return token;
		}

		public void setToken(String token) {
			this.token = token;
		}

		public String getMessages() {
			return messages;
		}

		public void setMessages(String messages) {
			this.messages = messages;
		}
	}
}