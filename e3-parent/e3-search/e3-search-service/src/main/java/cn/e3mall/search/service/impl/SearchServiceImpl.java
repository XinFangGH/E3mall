package cn.e3mall.search.service.impl;

import cn.e3mall.search.dao.SearchDao;
import cn.e3mall.search.pojo.SearchItem;
import cn.e3mall.search.pojo.SearchResult;
import cn.e3mall.search.service.SearchService;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private SearchDao searchDao;

    @Override
    public SearchResult queryItem(String keyword, int page, Integer searchRows) throws Exception {
        SolrQuery query = new SolrQuery();
        query.setQuery(keyword);
        query.set("df", "item_title");
        query.setStart((page - 1) * searchRows);
        query.setRows(searchRows);

        query.setHighlight(true);
        query.setHighlightSimplePre("<em style='color:red'>");
        query.setHighlightSimplePost("</em>");
        query.addHighlightField("item_title");
        SearchResult result = searchDao.queryItem(query);
        result.setTotalPages((long) Math.ceil(result.getRecourdCount() / searchRows));

        return result;
    }
}
