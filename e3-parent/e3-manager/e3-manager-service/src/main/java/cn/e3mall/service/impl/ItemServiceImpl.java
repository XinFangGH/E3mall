package cn.e3mall.service.impl;


import cn.e3mall.common.jedis.JedisClient;
import cn.e3mall.common.pojo.DataGridResult;
import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.common.utils.IDUtils;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.mapper.TbItemDescMapper;
import cn.e3mall.mapper.TbItemMapper;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;
import cn.e3mall.pojo.TbItemExample;
import cn.e3mall.service.ItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.Date;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private TbItemMapper itemMapper;
    @Autowired
    private TbItemDescMapper descMapper;
    @Autowired
    private JmsTemplate jmsTemplate;
    @Resource
    private Destination topicDestination;
    @Autowired
    private JedisClient jedisClient;
    @Value("${item.cache.expire}")
    private Integer itemCacheExpire;

    @Override
    public TbItem findById(long id) {

        try {
            String json = jedisClient.get("item_info:" + id + ":base");
            if (StringUtils.isNotBlank(json)) {
                TbItem item = JsonUtils.jsonToPojo(json, TbItem.class);
                return item;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        TbItemExample example = new TbItemExample();
        TbItemExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        List<TbItem> tbItems = itemMapper.selectByExample(example);
        try {
            if (tbItems != null && tbItems.size() > 0) {
                TbItem item = tbItems.get(0);
                jedisClient.set("item_info:" + id + ":base", JsonUtils.objectToJson(item));
                jedisClient.expire("item_info:" + id + ":base", itemCacheExpire);
                return item;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public DataGridResult findPageList(Integer page, Integer rows) {
        TbItemExample example = new TbItemExample();
        PageHelper.startPage(page, rows);
        List<TbItem> items = itemMapper.selectByExample(example);
        PageInfo<TbItem> pageInfo = new PageInfo<>(items);
        DataGridResult result = new DataGridResult();
        result.setTotal((int) pageInfo.getTotal());
        result.setRows(items);
        return result;
    }

    @Override
    public E3Result saveItem(final TbItem tbItem, String desc) {
        final long itemId = IDUtils.genItemId();
        tbItem.setId(itemId);
        tbItem.setCreated(new Date());
        tbItem.setUpdated(new Date());
        tbItem.setStatus((byte) 1);
        itemMapper.insert(tbItem);

        TbItemDesc itemDesc = new TbItemDesc();
        itemDesc.setItemId(itemId);
        itemDesc.setCreated(new Date());
        itemDesc.setUpdated(new Date());
        itemDesc.setItemDesc(desc);
        descMapper.insert(itemDesc);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    Thread.sleep(1000);
                    jmsTemplate.send(topicDestination, new MessageCreator() {
                        @Override
                        public Message createMessage(Session session) throws JMSException {

                            return session.createTextMessage(itemId + "");
                        }
                    });

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();

        return E3Result.ok();
    }

    @Override
    public E3Result findItemDescById(long id) {
        try {
            String json = jedisClient.get("item_info:" + id + ":desc");
            if (StringUtils.isNotBlank(json)) {
                TbItemDesc itemDesc = JsonUtils.jsonToPojo(json, TbItemDesc.class);
                return E3Result.ok(itemDesc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        TbItemDesc tbItemDesc = null;
        try {
            tbItemDesc = descMapper.selectByPrimaryKey(id);
            jedisClient.set("item_info:" + id + ":desc", JsonUtils.objectToJson(tbItemDesc));
            jedisClient.expire("item_info:" + id + ":desc", itemCacheExpire);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return E3Result.ok(tbItemDesc);
    }

    @Override
    public E3Result updateItem(TbItem item, String desc) {
        Long itemId = item.getId();
        TbItem item1 = itemMapper.selectByPrimaryKey(itemId);
        item.setStatus(item1.getStatus());
        item.setCreated(item1.getCreated());
        item.setUpdated(new Date());
        itemMapper.updateByPrimaryKey(item);

        TbItemDesc tbItemDesc = descMapper.selectByPrimaryKey(itemId);
        tbItemDesc.setUpdated(new Date());
        tbItemDesc.setItemDesc(desc);
        descMapper.updateByPrimaryKey(tbItemDesc);

        return E3Result.ok();
    }

    @Override
    public E3Result updateInstock(String ids) {
        String[] itemIds = ids.split(",");
        for (String itemId : itemIds) {
            long id = Long.parseLong(itemId);
            TbItem item = new TbItem();
            item.setStatus((byte) 2);
            TbItemExample example = new TbItemExample();
            TbItemExample.Criteria criteria = example.createCriteria();
            criteria.andIdEqualTo(id);
            itemMapper.updateByExampleSelective(item, example);
        }

        return E3Result.ok();
    }

    @Override
    public E3Result updateReshelf(String ids) {
        String[] itemIds = ids.split(",");
        for (String itemId : itemIds) {
            long id = Long.parseLong(itemId);
            TbItem item = new TbItem();
            item.setStatus((byte) 1);
            TbItemExample example = new TbItemExample();
            TbItemExample.Criteria criteria = example.createCriteria();
            criteria.andIdEqualTo(id);
            itemMapper.updateByExampleSelective(item, example);
        }

        return E3Result.ok();
    }

    @Override
    public E3Result delete(String ids) {
        String[] itemIds = ids.split(",");
        for (String itemId : itemIds) {
            long id = Long.parseLong(itemId);
            itemMapper.deleteByPrimaryKey(id);
        }

        return E3Result.ok();
    }

}
