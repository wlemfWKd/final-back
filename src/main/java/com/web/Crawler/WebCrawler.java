package com.web.Crawler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

@RestController
@RequestMapping("/crawl")
public class WebCrawler {

    @GetMapping
    public Map<String, Object> crawl() {
        Map<String, Object> response = new HashMap<>();

        try {
            // 첫 번째 URL
            String url1 = "https://www.youthcenter.go.kr/youngPlcyUnif/youngPlcyUnifDtl.do?pageIndex=1&frameYn=&bizId=R2024010218403&dtlOpenYn=&plcyTpOpenTy=&plcyCmprInfo=&srchWord=&chargerOrgCdAll=&srchAge=&trgtJynEmp=&trgtJynEmp=&srchSortOrder=1&pageUnit=12";
            Document document1 = Jsoup.connect(url1).get();
            Elements titleElements1 = document1.select(".common_table01:first-child .list_tit");
            Elements contentElements1 = document1.select(".common_table01:first-child .list_cont");

            // 두 번째 URL
            String url2 = "https://www.youthcenter.go.kr/youngPlcyUnif/youngPlcyUnifDtl.do?pageIndex=1&frameYn=&bizId=R2024010818564&dtlOpenYn=&plcyTpOpenTy=&plcyCmprInfo=&srchWord=%EC%9D%91%EC%8B%9C%EB%A3%8C&chargerOrgCdAll=&srchAge=&trgtJynEmp=&trgtJynEmp=&srchSortOrder=2&pageUnit=12";
            Document document2 = Jsoup.connect(url2).get();
            Elements titleElements2 = document2.select(".common_table01:first-child .list_tit");
            Elements contentElements2 = document2.select(".common_table01:first-child .list_cont");

            // 크롤링 결과를 담을 Map
            Map<String, String> resultMap1 = new HashMap<>();
            Map<String, String> resultMap2 = new HashMap<>();

            // 첫 번째 URL에서 제목과 내용을 Map에 추가
            for (int i = 0; i < titleElements1.size(); i++) {
                String title = titleElements1.get(i).text();
                String content = contentElements1.get(i).text();
                resultMap1.put("li_title"+i, title);
                resultMap1.put("li_cont"+i, content);
            }

            // 두 번째 URL에서 제목과 내용을 Map에 추가
            for (int i = 0; i < titleElements2.size(); i++) {
                String title = titleElements2.get(i).text();
                String content = contentElements2.get(i).text();
                resultMap2.put("li_title"+i, title);
                resultMap2.put("li_cont"+i, content);
            }

            // 최종 결과를 응답에 담음
            response.put("section1", resultMap1);
            response.put("section2", resultMap2);

        } catch (IOException e) {
            e.printStackTrace();
            response.put("error", "An error occurred while crawling the website.");
        }

        return response;
    }
}
