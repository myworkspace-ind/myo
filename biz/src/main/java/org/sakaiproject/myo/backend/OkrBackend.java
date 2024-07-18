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
import com.google.gson.JsonObject;

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
        System.out.println("Debug1" + okrAuthToken);
        // Create HttpEntity with headers and body
        HttpEntity<String> entity = new HttpEntity<>(body, headers);
        System.out.println("Debug2");
        // Send POST request to endpoint to get token
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<JsonResponse> response;
        try {
            // Define a custom response type that matches your JSON structure
        	System.out.println("Debug3");
            response = restTemplate.exchange(okrBaseURL + loginEndpoint, HttpMethod.POST, entity, JsonResponse.class);
            System.out.println("Debug4");
        } catch (HttpClientErrorException e) {
            // Handle HTTP errors (e.g., 4xx, 5xx)
            System.err.println("HTTP error occurred: " + e.getStatusCode() + " - " + e.getStatusText());
            return null; // Or handle the error as appropriate for your application
        } catch (Exception e) {
            // Handle other exceptions
            System.err.println("Error occurred: " + e.getMessage());
            return null; // Or handle the error as appropriate for your application
        }

        // Check the response status
        if (response.getStatusCode().is2xxSuccessful()) {
            JsonResponse jsonResponse = response.getBody();
            System.out.println("Debug5");
            // Check if the token is present
            if (jsonResponse != null && jsonResponse.getToken() != null) {
                String token = jsonResponse.getToken();
                System.out.println("Success get token: " + token);
                return token;
            } else {
                System.err.println("Response does not contain token field or 'token' is null");
                return null; // Or handle the missing token as appropriate
            }
        } else {
            System.err.println("Request failed with status code: " + response.getStatusCode());
            return null; // Or handle the HTTP status code as appropriate
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
			//System.out.println("Unauthorized: Refreshing token and retrying...");
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
			// Lấy token mới nếu chưa có hoặc đã hết hạn
			if (okrAuthToken == null || okrAuthToken.isEmpty()) {
				System.out.println("Loading token...");
				//okrAuthToken = getAuthToken();
				System.out.println("Loaded token..." + getAuthToken());
			}
			System.out.println(okrAuthToken);
			String serverUrl = okrBaseURL + "/period";
			HttpHeaders headers = new HttpHeaders();
			System.out.println("Token1" + okrAuthToken);
			headers.set("Authorization", "Bearer " + okrAuthToken);
			System.out.println("Token2");
			HttpEntity<String> entity = new HttpEntity<>(headers);
			
			ResponseEntity<String> response = restTemplate.exchange(serverUrl, HttpMethod.GET, entity, String.class);
			System.out.println("Token3");
			// if (response.getStatusCode() == HttpStatus.UNAUTHORIZED) {
			System.out.println("Unauthorized: Refreshing token and retrying...");
			getAuthToken(); // Lấy token mới
			headers.set("Authorization", "Bearer " + okrAuthToken); // Cập nhật headers với token mới
			entity = new HttpEntity<>(headers);
			response = restTemplate.exchange(serverUrl, HttpMethod.GET, entity, String.class); // Thử lại yêu cầu với
																								// token mới
			// }

			// Get and process response
			String responseJson = response.getBody();
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