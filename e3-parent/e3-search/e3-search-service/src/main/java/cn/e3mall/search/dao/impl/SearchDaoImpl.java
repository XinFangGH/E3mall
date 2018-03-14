package cn.e3mall.search.dao.impl;

import cn.e3mall.search.dao.SearchDao;
import cn.e3mall.search.pojo.SearchItem;
import cn.e3mall.search.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class SearchDaoImpl implements SearchDao {
    @Autowired
    private SolrServer solrServer;

    @Override
    public SearchResult queryItem(SolrQuery query) throws Exception {
        QueryResponse response = solrServer.query(query);
        SolrDocumentList solrDocumentList = response.getResults();
        long numFound = solrDocumentList.getNumFound();
        SearchResult searchResult = new SearchResult();
        List<SearchItem> list = new ArrayList<>();
        Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
        for (SolrDocument solrDocument : solrDocumentList) {
            SearchItem item = new SearchItem();
            item.setId((String) solrDocument.get("id"));
            item.setCategory_name((String) solrDocument.get("item_category_name"));
            item.setImage((String) solrDocument.get("item_image"));
            item.setPrice((long) solrDocument.get("item_price"));
            item.setSell_point((String) solrDocument.get("item_sell_point"));
            List<String> stringList = highlighting.get(solrDocument.get("id")).get("item_title");
            String title = "";
            if (stringList.size() > 0 && stringList != null) {
                title = stringList.get(0);
            } else {
                title = (String) solrDocument.get("item_title");
            }
            item.setTitle(title);
            list.add(item);
        }
        searchResult.setItemList(list);
        searchResult.setRecourdCount(numFound);
        return searchResult;
    }
}
