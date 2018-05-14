package cn.e3mall.search.listener;

import cn.e3mall.mapper.TbItemMapper;
import cn.e3mall.search.mapper.SearchItemMapper;
import cn.e3mall.search.pojo.SearchItem;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class ItemAddMessageListener implements MessageListener {
    @Autowired
    private SolrServer solrServer;
    @Autowired
    private SearchItemMapper mapper;

    @Override
    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;
        try {
            System.out.println("执行了吗");
            String textId = textMessage.getText();
            long id = Long.parseLong(textId);
            SearchItem item = mapper.getItemById(id);
            SolrInputDocument document = new SolrInputDocument();
            document.addField("id", item.getId());
            document.addField("item_title", item.getTitle());
            document.addField("item_sell_point", item.getSell_point());
            document.addField("item_price", item.getPrice());
            document.addField("item_image", item.getImage());
            document.addField("item_category_name", item.getCategory_name());
            solrServer.add(document);
            solrServer.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
