package cn.e3mall.service.impl;

import cn.e3mall.common.pojo.TreeNode;
import cn.e3mall.mapper.TbItemCatMapper;
import cn.e3mall.pojo.TbItemCat;
import cn.e3mall.pojo.TbItemCatExample;
import cn.e3mall.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemCatServiceImpl implements ItemCatService {

    @Autowired
    private TbItemCatMapper mapper;

    @Override
    public List<TreeNode> findById(Long parentId) {
        TbItemCatExample example = new TbItemCatExample();
        TbItemCatExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbItemCat> list = mapper.selectByExample(example);
        List<TreeNode> result = new ArrayList<>();
        for (TbItemCat itemCat : list) {
            TreeNode treeNode = new TreeNode();
            treeNode.setId(itemCat.getId());
            treeNode.setState(itemCat.getIsParent() ? "closed" : "open");
            treeNode.setText(itemCat.getName());
            result.add(treeNode);
        }

        return result;
    }
}
