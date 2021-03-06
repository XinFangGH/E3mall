package cn.e3mall.controller;

import cn.e3mall.common.pojo.TreeNode;
import cn.e3mall.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ItemCatController {

    @Autowired
    private ItemCatService itemCatService;

    @RequestMapping("item/cat/list")
    @ResponseBody
    public List<TreeNode> getItemCatList(@RequestParam(value = "id", defaultValue = "0") Long parentId) {

        List<TreeNode> list = itemCatService.findById(parentId);

        return list;
    }

}
