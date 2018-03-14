package cn.e3mall.controller;

import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.common.pojo.TreeNode;
import cn.e3mall.content.service.ContentCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ContentCatController {

    @Autowired
    private ContentCatService contentCatService;

    /**
     * 查询所有内容
     *
     * @return
     */
    @RequestMapping("/content/category/list")
    @ResponseBody
    public List<TreeNode> findCateList(@RequestParam(name = "id", defaultValue = "0") long parentId) {
        List<TreeNode> list = contentCatService.findCateList(parentId);
        return list;
    }

    /**
     * 添加新节点
     */

    @RequestMapping("/content/category/create")
    @ResponseBody
    public E3Result creatTreeNode(long parentId, String name) {

        E3Result result = contentCatService.creatTreeNode(parentId, name);

        return result;
    }

    /**
     * 修改节点
     */
    @RequestMapping("/content/category/update")
    @ResponseBody
    public void updateNode(long id, String name) {
        contentCatService.updateNode(id, name);

    }

    /**
     * 删除子节点
     */
    @RequestMapping("/content/category/delete/")
    @ResponseBody
    public E3Result deleteNode(long id) {
        E3Result result = contentCatService.deleteNode(id);
        return result;

    }

}
