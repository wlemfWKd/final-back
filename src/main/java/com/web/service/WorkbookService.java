package com.web.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.domain.WbDTO;
import com.web.persistence.WorkbookRepository;

@Service
public class WorkbookService {

    @Autowired
    private WorkbookRepository workbookRepository;

    public void saveAll(List<WbDTO> list) {
        workbookRepository.saveAll(list);
    }

    public void saveList(List<WbDTO> list) {
        for (WbDTO dto : list) {
            workbookRepository.save(dto);
        }
    }

//    @PostConstruct
    public void getWbList() {
        final String wbList = "https://www.comcbt.com/xe/";
        Connection conn = Jsoup.connect(wbList);

        try {
            Document document = conn.get();
            List<WbDTO> lists = getWbHeader(document);
            saveAll(lists);
            System.out.println(lists);
        } catch (IOException ignored) {
            ignored.printStackTrace();
        }
    }

    private List<WbDTO> getWbHeader(Document document) throws IOException {
        Elements WbTableBody = document.select(".li3 > a");
        
        
        List<WbDTO> lists = new ArrayList<>();
        for (Element element : WbTableBody) {
        	if(shouldExcludeHref(element.attr("href"))) {
        		continue;
        	}
            WbDTO dto = new WbDTO();
            dto.setHref(element.attr("href"));
            dto.setText(element.select("span").text());
            lists.add(dto);
        }
        
        return lists;
    }

    private void filterAndSave(List<WbDTO> lists) {
        List<WbDTO> filteredList = new ArrayList<>();
        for (WbDTO dto : lists) {
            if (!shouldExcludeHref(dto.getHref())) {
                filteredList.add(dto);
            }
        }
        saveList(filteredList);
    }

    private boolean shouldExcludeHref(String href) {
        // 특정한 href 값을 제외하기 위한 조건을 구현

        String[] url = {
        		"https://www.comcbt.com/xe/npc",
        		"https://www.comcbt.com/xe/npcqna",
        		"https://www.comcbt.com/xe/donganne"
        		};
        
        if(href.equals(url[0]) || href.equals(url[1]) || href.equals(url[2])) {
        	return true;
        }
        return false;
    }

	public List<WbDTO> getAllWorkbooks() {
		return workbookRepository.findAll();
	}
}
