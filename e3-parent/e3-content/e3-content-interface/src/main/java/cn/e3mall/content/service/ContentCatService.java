package cn.e3mall.content.service;

import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.common.pojo.TreeNode;

import java.util.List;

public interface ContentCatService {

    List<TreeNode> findCateList(long parentId);

    E3Result creatTreeNode(long parentId, String name);

    void updateNode(long id, String name);

    E3Result deleteNode(long id);
}
