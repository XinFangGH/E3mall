package cn.e3mall.search.dao;

import cn.e3mall.search.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;

public interface SearchDao {
    SearchResult queryItem(SolrQuery query) throws Exception;
}
