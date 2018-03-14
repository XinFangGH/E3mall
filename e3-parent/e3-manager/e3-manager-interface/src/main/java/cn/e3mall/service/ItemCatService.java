package cn.e3mall.service;

import cn.e3mall.common.pojo.TreeNode;

import java.util.List;

public interface ItemCatService {
    List<TreeNode> findById(Long parentId);
}
