package cn.e3mall.search.service;

import cn.e3mall.search.pojo.SearchItem;
import cn.e3mall.search.pojo.SearchResult;

import java.util.List;

public interface SearchService {
    SearchResult  queryItem(String keyword, int page, Integer searchRows) throws Exception;
}
