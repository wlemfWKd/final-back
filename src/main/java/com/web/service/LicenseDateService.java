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
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.web.domain.LicenseDate;
import com.web.domain.LicenseInfo;
import com.web.persistence.LicenseDateRepository;


@Service
public class LicenseDateService {

	@Autowired
    private LicenseDateRepository DateRepository;
	
	public LicenseDateService(LicenseDateRepository dateRepository) {
        this.DateRepository = dateRepository;
    }
    
    public void savedate(LicenseDate dateRepository) {
    	DateRepository.save(dateRepository);
    }
    
    public void saveLicenseDate(List<LicenseDate> dateRepository) {
    	DateRepository.saveAll(dateRepository);
    }
	
    public static void main(String[] args) {

    }
	
    //@PostConstruct
	public void getTestDate() {
	    HttpURLConnection conn = null;
	    BufferedReader rd = null;

	    try {
	    	
	    	StringBuilder urlBuilder;
	    	
	    		for (int i = 1; i <= 4; i++) {
	    	    urlBuilder = new StringBuilder();
	    	    urlBuilder.append("http://openapi.q-net.or.kr/api/service/rest/InquiryTestInformationNTQSVC/");
	    	    
	    	    if(i == 1) {
	    	        urlBuilder.append("getPEList");
	    	    } else if (i == 2) {
	    	        urlBuilder.append("getMCList");
	    	    } else if (i == 3) {
	    	        urlBuilder.append("getEList");
	    	    } else {
	    	        urlBuilder.append("getCList");
	    	    }
	    	    
	    	    urlBuilder.append("?ServiceKey=8RQmmNMbqQKZO06m6d44ZNTJv55aWC7ld4cj5de9n14a6o3tbFOrn%2FF3Aa5cVQzRVlpUr2nt2J9sjnqrnD2KLA%3D%3D");

	    	
	    	    
	    	    URL url = new URL(urlBuilder.toString());
	    	    conn = (HttpURLConnection) url.openConnection();
	    	    conn.setRequestMethod("GET");
	    	    conn.setRequestProperty("Content-type", "application/xml");

	    	    if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
	    	        throw new RuntimeException("HTTP error code : " + conn.getResponseCode());
	    	    }

	    	    rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	    	    StringBuilder xmlResponse2 = new StringBuilder();
	    	    String line;

	    	    while ((line = rd.readLine()) != null) {
	    	        xmlResponse2.append(line);
	    	    }

	    	    // XML 파싱 및 DB에 저장
	    	    parseXmlResponse2(xmlResponse2.toString());
	    	}
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
	
	
	private void parseXmlResponse2(String xmlResponse2) {
		try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new org.xml.sax.InputSource(new java.io.StringReader(xmlResponse2)));

            List<LicenseDate> Dates = new ArrayList<>();
            List<String> existingDescriptions = new ArrayList<>(); // 이미 존재하는 description 값 저장
            
            int count =0; //
            NodeList itemList = document.getElementsByTagName("item");
            for (int i = 0; i < itemList.getLength(); i++) {
                Node itemNode = itemList.item(i);
                if (itemNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element itemElement = (Element) itemNode;

                    LicenseDate LD = new LicenseDate();
                    String description = getTagValue("description", itemElement);
                    
                    count++; //                    
                    if (!existingDescriptions.contains(description)) {
                        existingDescriptions.add(description); // 현재 description 값을 추가

                        LD.setDescription(description);
                        LD.setDocregstartdt(getTagValue2("docregstartdt", itemElement));
                        LD.setDocregenddt(getTagValue2("docregenddt", itemElement));
                        LD.setDocexamdt(getTagValue2("docexamdt", itemElement));
                        LD.setDocpassdt(getTagValue2("docpassdt", itemElement));
                        LD.setDocsubmitstartdt(getTagValue2("docsubmitstartdt", itemElement));
                        LD.setDocsubmitentdt(getTagValue2("docsubmitentdt", itemElement));
                        LD.setPracregstartdt(getTagValue2("pracregstartdt", itemElement));
                        LD.setPracregenddt(getTagValue2("pracregenddt", itemElement));
                        LD.setPracexamstartdt(getTagValue2("pracexamstartdt", itemElement));
                        LD.setPracexamenddt(getTagValue2("pracexamenddt", itemElement));
                        LD.setPracpassdt(getTagValue2("pracpassdt", itemElement));

                        System.out.println(LD);
                        Dates.add(LD);
                    }
                }
            }
            saveLicenseDate(Dates);
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
	
	private String getFormattedDate(String rawDate) {
	    if (rawDate != null && rawDate.length() == 8) {
	        return rawDate.substring(0, 4) + "-" + rawDate.substring(4, 6) + "-" + rawDate.substring(6, 8);
	    }
	    return null;
	}
	
	private String getTagValue2(String tagName, Element element) {
	    NodeList nodeList = element.getElementsByTagName(tagName);
	    if (nodeList.getLength() > 0) {
	        Node itemNode = nodeList.item(0);
	        if (itemNode != null && itemNode.getNodeType() == Node.ELEMENT_NODE) {
	            String rawDate = itemNode.getTextContent().trim();
	            return getFormattedDate(rawDate);
	        }
	    }
	    return null; // XML 요소의 텍스트 값이 존재하지 않을 때 null 반환
	}
	
	public List<LicenseDate> getAllDate() {
		return DateRepository.findAll();
	}
	
}
