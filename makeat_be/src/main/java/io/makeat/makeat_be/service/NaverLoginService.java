package io.makeat.makeat_be.service;

// 네이버 API 예제 - 회원프로필 조회
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class NaverLoginService {

    @Value("${naver.client_id}")
    private String client_id;

    @Value("${naver.secret_id}")
    private String secret_id;


    /**
     * 인가코드로 토큰 반환
     * @param code
     * @return
     * @throws IOException
     */
    public String getToken(String code) throws IOException {

        String host = "https://nid.naver.com/oauth2.0/token";
        URL url = new URL(host);
        String token = "";
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true); // 데이터 기록 알려주기

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code&client_id=" + client_id + "&client_secret=" + secret_id);
            sb.append("&code=" + code);
            sb.append("&state=9kgsGTfH4j7IyAkg");

            bw.write(sb.toString());
            bw.flush();

            int responseCode = urlConnection.getResponseCode();
            log.info("responseCode = " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line = "";
            String result = "";
            while ((line = br.readLine()) != null) {
                result += line;
            }
            log.info("result = " + result);

            // json parsing
            JSONParser parser = new JSONParser();
            JSONObject elem = (JSONObject) parser.parse(result);

            token = elem.get("token").toString();
            log.info("access_token = " + token);

            br.close();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return token;
    }

    /**
     * 토큰으로 유저정보 파싱해서 반환
     * @param token
     * @return
     * @throws ParseException
     */
    public Map<String, Object> getUserInfo(String token) throws ParseException {

        Map<String, Object> result = new HashMap<>();

        String header = "Bearer " + token; // Bearer 다음에 공백 추가
        String apiURL = "https://openapi.naver.com/v1/nid/me";

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("Authorization", header);

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObj = (JSONObject) jsonParser.parse(getApi(apiURL,requestHeaders));
        JSONObject response = (JSONObject) jsonObj.get("response");

        String id = response.get("id").toString();
        String name = response.get("name").toString();
        String gender = response.get("gender").toString();

        result.put("id", id);
        result.put("nickname", name);
        result.put("gender", gender);

        return result;
    }

    /**
     * Api 접근해서 Body정보 반환
     * @param apiUrl
     * @param requestHeaders
     * @return
     */
    public static String getApi(String apiUrl, Map<String, String> requestHeaders){

        HttpURLConnection con = connect(apiUrl);
        try {
            con.setRequestMethod("GET");
            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                return readBody(con.getInputStream());
            } else { // 에러 발생
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }

    /**
     * API 연결
     * @param apiUrl
     * @return
     */
    private static HttpURLConnection connect(String apiUrl){
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }

    /**
     * body 정보 반환
     * @param body
     * @return
     */
    private static String readBody(InputStream body){
        InputStreamReader streamReader = new InputStreamReader(body);

        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();

            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }

            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
        }
    }
}


