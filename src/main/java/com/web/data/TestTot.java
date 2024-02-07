//국가기술자격의 등급별(기능사, 산업기사, 기사, 기술사, 기능장, 전문사무, 기능사보) 필기, 실기시험의 기준년도 최근 5년간 기준으로 연도별 응시자 및 합격자 수(명)를 조회할 수 있다.

package com.web.data;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class TestTot {
    public static void main(String[] args) throws IOException {
    	 StringBuilder urlBuilder = new StringBuilder("http://openapi.q-net.or.kr/api/service/rest/InquiryStatSVC/getTotExamList"); /*URL*/
         urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=8RQmmNMbqQKZO06m6d44ZNTJv55aWC7ld4cj5de9n14a6o3tbFOrn%2FF3Aa5cVQzRVlpUr2nt2J9sjnqrnD2KLA%3D%3D"); /*Service Key*/
         urlBuilder.append("&" + URLEncoder.encode("baseYY","UTF-8") + "=" + URLEncoder.encode("2022", "UTF-8")); /*기준년도*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/xml");

        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;

        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder xmlResponse = new StringBuilder();
            String line;

            while ((line = rd.readLine()) != null) {
                xmlResponse.append(line);
            }
            rd.close();

            // DOM을 사용하여 XML 파싱
            parseXmlResponse(xmlResponse.toString());
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            // 에러 스트림 처리가 필요한 경우 처리
        }

        conn.disconnect();
    }

    private static void parseXmlResponse(String xmlResponse) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new org.xml.sax.InputSource(new java.io.StringReader(xmlResponse)));

            // 루트 엘리먼트가 <response>인 것으로 가정하고 있습니다. 실제 XML 구조에 따라 코드를 수정하세요.
            Element rootElement = document.getDocumentElement();
            displayElement(rootElement, 0);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void displayElement(Node node, int depth) {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            for (int i = 0; i < depth; i++) {
                System.out.print("  "); // 중첩된 엘리먼트에 대한 들여쓰기
            }

            System.out.println(node.getNodeName() + ": " + node.getTextContent());

            NodeList children = node.getChildNodes();
            for (int i = 0; i < children.getLength(); i++) {
                displayElement(children.item(i), depth + 1);
            }
        }
    }
}