package cn.e3mall.controller;

import cn.e3mall.common.pojo.DataGridResult;
import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping("/item/{itemId}")
    @ResponseBody
    public TbItem findAll(@PathVariable long itemId) {

        TbItem item = itemService.findById(itemId);

        return item;
    }

    /**
     * 分页查询
     *
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/item/list")
    @ResponseBody
    public DataGridResult findPageList(Integer page, Integer rows) {
        DataGridResult result = itemService.findPageList(page, rows);

        return result;
    }

    /**
     * 增加
     *
     * @param tbItem
     * @param desc
     * @return
     */
    @RequestMapping("/item/save")
    @ResponseBody
    public E3Result itemSave(TbItem tbItem, String desc) {
        E3Result result = itemService.saveItem(tbItem, desc);

        return result;
    }

    /**
     * 回显描述
     *
     * @param id
     * @return
     */
    @RequestMapping("item/desc")
    @ResponseBody
    public E3Result findItemDesc(long id) {
        E3Result result = itemService.findItemDescById(id);

        return result;
    }

    /**
     * 修改
     *
     * @param item
     * @param desc
     * @return
     */
    @RequestMapping("/item/update")
    @ResponseBody
    public E3Result updateItem(TbItem item, String desc) {
        E3Result result = itemService.updateItem(item, desc);

        return result;
    }

    /**
     * 下架
     *
     * @return
     */
    @RequestMapping("/item/instock")
    @ResponseBody
    public E3Result instock(String ids) {

        E3Result result = itemService.updateInstock(ids);

        return result;
    }

    /**
     * 上架
     *
     * @return
     */
    @RequestMapping("/item/reshelf")
    @ResponseBody
    public E3Result reshelf(String ids) {

        E3Result result = itemService.updateReshelf(ids);

        return result;
    }
   /**
     * 删除
     *
     * @return
     */
    @RequestMapping("/item/delete")
    @ResponseBody
    public E3Result delete(String ids) {

        E3Result result = itemService.delete(ids);

        return result;
    }

}
