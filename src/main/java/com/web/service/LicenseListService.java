package com.web.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.web.data.TestList;
import com.web.domain.LicenseList;
import com.web.domain.Yes24BookCrawl;
import com.web.persistence.LicenseRepository;

@Service
public class LicenseListService {

    @Autowired
    private LicenseRepository licenseListRepository;

    public LicenseListService(LicenseRepository licenseRepository) {
        this.licenseListRepository = licenseRepository;
    }
    
    public void saveLicense(LicenseList licenseList) {
    	licenseListRepository.save(licenseList);
    }
    
    public void saveLicenseList(List<LicenseList> licenseList) {
        licenseListRepository.saveAll(licenseList);
    }
    
     
    
    public static void main(String[] args) {
        

    }
    
    @PostConstruct
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

            List<LicenseList> Lists = new ArrayList<>();
            
            int count =0; //
            NodeList itemList = document.getElementsByTagName("item");
            for (int i = 0; i < itemList.getLength(); i++) {
                Node itemNode = itemList.item(i);
                if (itemNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element itemElement = (Element) itemNode;

                    count++; //                    
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

                   System.out.println(licenseList);
                    Lists.add(licenseList);
                    
                }
            }
            saveLicenseList(Lists);
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
