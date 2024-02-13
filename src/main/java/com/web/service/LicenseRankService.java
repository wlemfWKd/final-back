package com.web.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.web.domain.LicensRank;
import com.web.domain.LicenseList;
import com.web.persistence.RankRepository;


@Service
public class LicenseRankService {

	@Autowired
    private RankRepository rkRepository;

    public LicenseRankService(RankRepository rRepository) {
        this.rkRepository = rRepository;
    }
    
    public void saveLicense(LicensRank rRepository) {
    	rkRepository.save(rRepository);
    }
    
    public void saveRankList(List<LicensRank> rRepository) {
    	rkRepository.saveAll(rRepository);
    }
	
    //@PostConstruct
    public void getNoData() {
		HttpURLConnection conn = null;
		BufferedReader rd = null;

		try {
			for (int i = 1; i <= 6; i++) {
				StringBuilder urlBuilder = new StringBuilder(
						"http://openapi.q-net.or.kr/api/service/rest/InquiryGrdPtcondSVC/getRecpUppeJmList"); /* URL */
				urlBuilder.append(
						"?serviceKey=8RQmmNMbqQKZO06m6d44ZNTJv55aWC7ld4cj5de9n14a6o3tbFOrn%2FF3Aa5cVQzRVlpUr2nt2J9sjnqrnD2KLA%3D%3D"); /*
																																		 * Service
																																		 * Key
																																		 */
				urlBuilder.append("&baseYY=2024");
				urlBuilder.append("&pageNo=" + i);
				urlBuilder.append("&numOfRows=100");

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

	private void parseXmlResponse(String xmlResponse) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(new org.xml.sax.InputSource(new java.io.StringReader(xmlResponse)));

			List<LicensRank> Lists = new ArrayList<>();
			
			int count = 0;
			NodeList itemList = document.getElementsByTagName("item");
			for (int i = 0; i < itemList.getLength(); i++) {
				Node itemNode = itemList.item(i);
				if (itemNode.getNodeType() == Node.ELEMENT_NODE) {
					Element itemElement = (Element) itemNode;

					String sumYy = getTagValue("sumYy", itemElement);
					if ("2023".equals(sumYy)) {
						count++;

						LicensRank LR = new LicensRank();
						LR.setExamrecptcnt(Integer.parseInt(getTagValue("examRecptRank", itemElement)));
						LR.setJmcd(getTagValue("jmCd", itemElement));
						LR.setJmfldnm(getTagValue("jmFldNm", itemElement));
						LR.setSumyy(getTagValue("sumYy", itemElement));
						LR.setExamrecptcnt(Integer.parseInt(getTagValue("examRecptCnt", itemElement)));
						LR.setGrdnm(getTagValue("grdNm", itemElement));

						Lists.add(LR);
					}
				}
			}
			saveRankList(Lists);
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

	public List<LicensRank> getAllRanks() {
		return rkRepository.findAll();
	}
	
	
}
