package org.trandy.trandy_server.member.service;

import com.google.gson.JsonParser;
import com.google.gson.JsonElement;
import org.springframework.stereotype.Service;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class KakaoService {
    public String getKakaoAccessToken(String code) {
        String accessToken = "";
        String requsetUrl = "https://kauth.kakao.com/oauth/token";

        try {
            URL url = new URL(requsetUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            //post요청
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            //post요청에 필요한 파라미터 처리
            BufferedWriter bufWriter = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=219c411447f686a59391ba49ddb3505d");
            sb.append("&redirect_uri=http://localhost:8080/oauth/kakao");
            sb.append("&code=" + code);
            bufWriter.write(sb.toString());
            bufWriter.flush();

            // responseCode가 200을 반환하면 정상
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code : " + responseCode);

            //json타입의 response 읽어오기
            BufferedReader bufReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            String result = "";

            while ((inputLine = bufReader.readLine()) != null) {
                result += inputLine;
            }
            System.out.println("Response body : " + result);

            //gson으로 json파싱
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            accessToken = element.getAsJsonObject().get("access_token").getAsString();

            System.out.println("Access token : " + accessToken);

            bufReader.close();
            bufWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return accessToken;
    }

}
