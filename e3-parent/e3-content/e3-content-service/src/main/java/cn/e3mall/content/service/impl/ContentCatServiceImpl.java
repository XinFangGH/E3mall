package cn.e3mall.content.service.impl;

import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.common.pojo.TreeNode;
import cn.e3mall.content.service.ContentCatService;
import cn.e3mall.mapper.TbContentCategoryMapper;
import cn.e3mall.pojo.TbContentCategory;
import cn.e3mall.pojo.TbContentCategoryExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ContentCatServiceImpl implements ContentCatService {

    @Autowired
    private TbContentCategoryMapper contentCategoryMapper;

    /**
     * 查询所有目录节点
     *
     * @param parentId
     * @return
     */
    @Override
    public List<TreeNode> findCateList(long parentId) {
        TbContentCategoryExample example = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
        List<TreeNode> treeNodes = new ArrayList<>();
        for (TbContentCategory category : list) {
            TreeNode treeNode = new TreeNode();
            treeNode.setId(category.getId());
            treeNode.setText(category.getName());
            treeNode.setState(category.getIsParent() ? "closed" : "open");
            treeNodes.add(treeNode);
        }

        return treeNodes;
    }

    /**
     * 创建新目录节点
     *
     * @param parentId
     * @param name
     * @return
     */
    @Override
    public E3Result creatTreeNode(long parentId, String name) {
        TbContentCategory tbContentCategory = new TbContentCategory();
        tbContentCategory.setParentId(parentId);
        tbContentCategory.setName(name);
        tbContentCategory.setStatus(1);
        tbContentCategory.setSortOrder(1);
        tbContentCategory.setCreated(new Date());
        tbContentCategory.setUpdated(new Date());
        //新增节点一定是子节点
        tbContentCategory.setIsParent(false);
        //插入到数据库
        contentCategoryMapper.insert(tbContentCategory);
        //查询当前父节点信息
        TbContentCategory category = contentCategoryMapper.selectByPrimaryKey(parentId);
        //判断它是不是父节点,如果不是 改为是
        if (!category.getIsParent()) {
            category.setIsParent(true);
            //保存修改
            contentCategoryMapper.updateByPrimaryKey(category);
        }

        return E3Result.ok(tbContentCategory);
    }

    /**
     * 修改节点
     *
     * @param id
     * @param name
     */
    @Override
    public void updateNode(long id, String name) {
        TbContentCategory category = contentCategoryMapper.selectByPrimaryKey(id);
        category.setName(name);
        contentCategoryMapper.updateByPrimaryKey(category);
    }

    /**
     * 删除节点
     *
     * @param id
     * @return
     */
    @Override
    public E3Result deleteNode(long id) {
            TbContentCategory category = contentCategoryMapper.selectByPrimaryKey(id);
            if (category.getIsParent()) {
                return E3Result.build(250, "只能删除子节点");
            }
            Long parentId = category.getParentId();
            if (parentId == 30) {
                contentCategoryMapper.deleteByPrimaryKey(id);
                return E3Result.ok();
            }
            TbContentCategory parent = contentCategoryMapper.selectByPrimaryKey(parentId);
            parent.setIsParent(false);
            contentCategoryMapper.updateByPrimaryKey(parent);

            return E3Result.ok();

    }
}
