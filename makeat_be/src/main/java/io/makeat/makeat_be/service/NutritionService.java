package io.makeat.makeat_be.service;

import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


@Slf4j
@Service
public class NutritionService {

    public String getNutrient(String foodName) throws IOException, ParseException {
        String nutrinetInfo = "";

        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1471000/FoodNtrIrdntInfoService1/getFoodNtrItdntList1"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=zGlMo2YhDISGj2h%2B0JLPZyx2KZubBibtKxCdeMSXdQVbWSsv%2BXHmPbZs0uZOUulYXNn4V20sDH4V3RKoxxezwg%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("desc_kor","UTF-8") + "=" + URLEncoder.encode(foodName, "UTF-8")); /*식품이름*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("type","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*응답데이터 형식(xml/json) Default: xml*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");

        log.info("Response code: " + conn.getResponseCode());

        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        log.info(sb.toString());

        //json parsing
        String info = sb.toString();
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(info);
        JSONObject body = (JSONObject)jsonObject.get("body");

        JSONArray items = (JSONArray)body.get("items");

        for(int i = 0; i < items.size(); i++)
        {
            String items_name = "";
            String once = "";
            String tan = "";
            String dan = "";
            String zi = "";
            String na = "";
            String cal = "";

            JSONObject items_info = (JSONObject)items.get(i);
            items_name += items_info.get("DESC_KOR") + " ";
            once += items_info.get("SERVING_WT") + " ";
            tan += items_info.get("NUTR_CONT2") + " ";
            dan += items_info.get("NUTR_CONT3") + " ";
            zi += items_info.get("NUTR_CONT4") + " ";
            na += items_info.get("NUTR_CONT6") + " ";
            cal += items_info.get("NUTR_CONT1") + " ";

            nutrinetInfo = "식품 이름: " + items_name + "1회 제공량: " + once + "탄수화물: " + tan + "단백질: " + dan + "지방: " + zi + "나트륨: " + na + "칼로리: " + cal + "\n";
        }
        return nutrinetInfo;
    }
}
