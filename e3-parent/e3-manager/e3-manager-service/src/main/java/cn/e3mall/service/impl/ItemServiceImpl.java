package cn.e3mall.service.impl;

import cn.e3mall.common.pojo.DataGridResult;
import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.common.utils.IDUtils;
import cn.e3mall.mapper.TbItemDescMapper;
import cn.e3mall.mapper.TbItemMapper;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;
import cn.e3mall.pojo.TbItemExample;
import cn.e3mall.service.ItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private TbItemMapper itemMapper;
    @Autowired
    private TbItemDescMapper descMapper;

    @Override
    public TbItem findById(long id) {
        return itemMapper.selectByPrimaryKey(id);
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
    public E3Result saveItem(TbItem tbItem, String desc) {
        long itemId = IDUtils.genItemId();
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
        return E3Result.ok();
    }

    @Override
    public E3Result findItemDescById(long id) {

        TbItemDesc tbItemDesc = descMapper.selectByPrimaryKey(id);

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
