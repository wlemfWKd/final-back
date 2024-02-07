package com.web.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.web.domain.LicenseList;
import com.web.service.LicenseListService;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Component
public class TestList {

    @Autowired
    private LicenseListService licenseListService;

//    public static void main(String[] args) {
//        TestList testList = new TestList();
//        testList.getDataAndSaveToDB();
//    }

    public void getDataAndSaveToDB() {
        HttpURLConnection conn = null;
        BufferedReader rd = null;

        try {
            StringBuilder urlBuilder = new StringBuilder("http://openapi.q-net.or.kr/api/service/rest/InquiryListNationalQualifcationSVC/getList"); /*URL*/
            urlBuilder.append("?serviceKey=8RQmmNMbqQKZO06m6d44ZNTJv55aWC7ld4cj5de9n14a6o3tbFOrn%2FF3Aa5cVQzRVlpUr2nt2J9sjnqrnD2KLA%3D%3D"); /*Service Key*/

            URL url = new URL(urlBuilder.toString());
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/xml");

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("HTTP error code : " + conn.getResponseCode());
            }

            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder xmlResponse = new StringBuilder();
            String line;

            while ((line = rd.readLine()) != null) {
                xmlResponse.append(line);
            }

            // XML 파싱 및 DB에 저장
            parseXmlResponse(xmlResponse.toString());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rd != null) {
                    rd.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    private void parseXmlResponse(String xmlResponse) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new org.xml.sax.InputSource(new java.io.StringReader(xmlResponse)));

            int count =0; //
            NodeList itemList = document.getElementsByTagName("item");
            for (int i = 0; i < itemList.getLength(); i++) {
                Node itemNode = itemList.item(i);
                if (itemNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element itemElement = (Element) itemNode;

                    count++; //
//                    LicenseList licenseList = new LicenseList();
//                    licenseList.setJmcd(getTagValue("jmcd", itemElement)); 
//                    licenseList.setJmfldnm(getTagValue("jmfldnm", itemElement));
//                    licenseList.setMdobligfldcd(getTagValue("mdobligfldcd", itemElement)); 
//                    licenseList.setMdobligfldnm(getTagValue("mdobligfldnm", itemElement));
//                    licenseList.setObligfldcd(getTagValue("obligfldcd", itemElement)); 
//                    licenseList.setObligfldnm(getTagValue("obligfldnm", itemElement));
//                    licenseList.setQualgbcd(getTagValue("qualgbcd", itemElement));
//                    licenseList.setQualgbnm(getTagValue("qualgbnm", itemElement));
//                    licenseList.setSeriescd(getTagValue("seriescd", itemElement));
//                    licenseList.setSeriesnm(getTagValue("seriesnm", itemElement));
                    
                    LicenseList licenseList = LicenseList.builder()
                    	    .jmcd(getTagValue("jmcd", itemElement))
                    	    .jmfldnm(getTagValue("jmfldnm", itemElement))
                    	    .mdobligfldcd(getTagValue("mdobligfldcd", itemElement))
                    	    .mdobligfldnm(getTagValue("mdobligfldnm", itemElement))
                    	    .obligfldcd(getTagValue("obligfldcd", itemElement))
                    	    .obligfldnm(getTagValue("obligfldnm", itemElement))
                    	    .qualgbcd(getTagValue("qualgbcd", itemElement))
                    	    .qualgbnm(getTagValue("qualgbnm", itemElement))
                    	    .seriescd(getTagValue("seriescd", itemElement))
                    	    .seriesnm(getTagValue("seriesnm", itemElement))
                    	    .build();

//                    	System.out.println(licenseList.toString()); 
                    
//                    System.out.println(licenseList.getJmcd()); // 콘솔 출력
//                    System.out.println(licenseList.getSeriesnm());
                    //System.out.println(licenseList);
                    //licenseListService.saveLicense(licenseList); // DB에 저장
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    	
    	
    private String getTagValue(String tagName, Element element) {
        NodeList nodeList = element.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0) {
            Node itemNode = nodeList.item(0);
            if (itemNode != null && itemNode.getNodeType() == Node.ELEMENT_NODE) {
                return itemNode.getTextContent().trim();
            }
        }
        return ""; // XML 요소의 텍스트 값이 존재하지 않을 때 빈 문자열 반환
    }

   
}



//licenseList.setJmcd(parseIntValue(getTagValue("jmcd", itemElement))); 
//licenseList.setJmfldnm(getTagValue("jmfldnm", itemElement));
//licenseList.setMdobligfldcd(parseIntValue(getTagValue("mdobligfldcd", itemElement))); 
//licenseList.setMdobligfldnm(getTagValue("mdobligfldnm", itemElement));
//licenseList.setObligfldcd(parseIntValue(getTagValue("obligfldcd", itemElement))); 
//licenseList.setObligfldnm(getTagValue("obligfldnm", itemElement));
//licenseList.setQualgbcd(getTagValue("qualgbcd", itemElement));
//licenseList.setQualgbnm(getTagValue("qualgbnm", itemElement));
//licenseList.setSeriescd(parseIntValue(getTagValue("seriescd", itemElement)));
//licenseList.setSeriesnm(getTagValue("seriesnm", itemElement));