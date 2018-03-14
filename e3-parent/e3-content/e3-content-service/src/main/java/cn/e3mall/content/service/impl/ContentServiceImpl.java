package cn.e3mall.content.service.impl;

import cn.e3mall.common.pojo.DataGridResult;
import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.content.service.ContentService;
import cn.e3mall.mapper.TbContentMapper;
import cn.e3mall.pojo.TbContent;
import cn.e3mall.pojo.TbContentExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.e3mall.common.jedis.JedisClient;

import java.util.Date;
import java.util.List;

@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private TbContentMapper contentMapper;
    @Autowired
    private JedisClient jedisClient;

    @Override
    public E3Result saveCnotent(TbContent tbContent) {
        tbContent.setCreated(new Date());
        tbContent.setUpdated(new Date());
        contentMapper.insert(tbContent);
        //缓存同步
        jedisClient.hdel("e3mall-index", tbContent.getCategoryId().toString());

        return E3Result.ok();
    }

    @Override
    public E3Result updateContent(TbContent tbContent) {
        tbContent.setUpdated(new Date());
        contentMapper.updateByPrimaryKeySelective(tbContent);
        //缓存同步
        jedisClient.hdel("e3mall-index", tbContent.getCategoryId().toString());

        return E3Result.ok();
    }

    @Override
    public E3Result deleteContent(String ids) {
        String[] strIds = ids.split(",");
        for (String strId : strIds) {
            long id = Long.parseLong(strId);
            //缓存同步
            TbContent tbContent = contentMapper.selectByPrimaryKey(id);
            jedisClient.hdel("e3mall-index", tbContent.getCategoryId().toString());
            contentMapper.deleteByPrimaryKey(id);
        }

        return E3Result.ok();
    }

    @Override
    public List<TbContent> findByCategoryId(long categoryId) {
        try {
            String jedisName = jedisClient.hget("e3mall-index", categoryId + "");
            if (StringUtils.isNotBlank(jedisName)) {
                List<TbContent> list = JsonUtils.jsonToList(jedisName, TbContent.class);
                return list;
            }
        } catch (Exception e) {
        }

        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(categoryId);

        List<TbContent> list = contentMapper.selectByExample(example);
        try {
            jedisClient.hset("e3mall-index", categoryId + "", JsonUtils.objectToJson(list));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public DataGridResult getContentListDataGrid(long categoryId, int page, int rows) {
        // 1）创建一个查询条件，设置查询条件，根据内容分类id查询
        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(categoryId);
        // 2）设置分页条件，使用PageHelper
        PageHelper.startPage(page, rows);
        // 3）执行查询
        List<TbContent> list = contentMapper.selectByExample(example);
        // 4）从查询结果中取分页信息
        PageInfo<TbContent> pageInfo = new PageInfo<>(list);
        long total = pageInfo.getTotal();
        // 5）创建一个DataGridResult对象
        DataGridResult result = new DataGridResult();
        // 6）设置属性
        result.setTotal((int) total);
        result.setRows(list);
        // 7）返回结果
        return result;
    }
}
