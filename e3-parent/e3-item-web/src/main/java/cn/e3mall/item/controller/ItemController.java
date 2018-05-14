package cn.e3mall.item.controller;

import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.item.pojo.Item;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;
import cn.e3mall.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping("/item/{itemId}")
    public String showItem(@PathVariable long itemId, Model model) {

        TbItem tbItem = itemService.findById(itemId);
        Item item = new Item(tbItem);
        TbItemDesc tbItemDesc = (TbItemDesc) itemService.findItemDescById(itemId).getData();

        model.addAttribute("item", item);
        model.addAttribute("itemDesc", tbItemDesc);
        return "item";
    }

}
