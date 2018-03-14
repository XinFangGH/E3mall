package cn.e3mall.search.controller;

import cn.e3mall.search.pojo.SearchItem;
import cn.e3mall.search.pojo.SearchResult;
import cn.e3mall.search.service.SearchItemService;
import cn.e3mall.search.service.SearchService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class SearchItemController {
    @Autowired
    private SearchService searchService;
    @Value("${search.result.rows}")
    private Integer searchRows;

    @RequestMapping("/search")
    public String queryItem(String keyword, @RequestParam(defaultValue = "1") int page, Model model) throws Exception {
        if (StringUtils.isNotBlank(keyword)) {
            String utf8 = new String(keyword.getBytes("iso8859-1"), "utf8");
            keyword = utf8;
        }
        SearchResult searchResult = searchService.queryItem(keyword, page, searchRows);
        model.addAttribute("query", keyword);
        model.addAttribute("page", page);

        model.addAttribute("itemList", searchResult.getItemList());
        model.addAttribute("totalPages", searchResult.getTotalPages());
        model.addAttribute("recourdCount", searchResult.getRecourdCount());

        return "search";

    }

}
