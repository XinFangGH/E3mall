package cn.e3mall.controller;

import cn.e3mall.common.pojo.DataGridResult;
import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.content.service.ContentService;
import cn.e3mall.pojo.TbContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ContentController {

    @Autowired
    private ContentService contentService;

    /**
     * 查询列表
     *
     * @param categoryId
     * @return
     */
    @RequestMapping("/content/query/list")
    @ResponseBody
    public DataGridResult getContentList(long categoryId, int page, int rows) {
        DataGridResult gridResult = contentService.getContentListDataGrid(categoryId, page, rows);
        return gridResult;
    }

    /**
     * 新增列表
     */
    @RequestMapping("/content/save")
    @ResponseBody
    public E3Result saveContent(TbContent tbContent) {
        E3Result result = contentService.saveCnotent(tbContent);

        return result;
    }

    /**
     * 修改列表
     */
    @RequestMapping("/rest/content/edit")
    @ResponseBody
    public E3Result updateContent(TbContent tbContent){
        E3Result result = contentService.updateContent(tbContent);

        return result;
    }

    /**
     * 删除列表
     */
    @RequestMapping("/content/delete")
    @ResponseBody
    public E3Result deleteContent(String ids){
        E3Result result = contentService.deleteContent(ids);
        return result;
    }

}
