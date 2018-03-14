import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;

public class SolrJTest {
    @org.junit.Test
    public void fun1() throws Exception {
        SolrServer solrServer = new HttpSolrServer("http://192.168.25.128:8080/solr");
        SolrInputDocument document = new SolrInputDocument();
        document.addField("id", "test001");
        document.addField("item_title", "测试商品");
        document.addField("item_price", "199");
        solrServer.add(document);
        solrServer.commit();
    }
}
