package cn.e3mall.portal.controller;

import cn.e3mall.content.service.ContentService;
import cn.e3mall.pojo.TbContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private ContentService contentService;

    @Value("${index.categoryId}")
    private long categoryId;

    @RequestMapping("/index")
    public String showIndex(Model model) {
        List<TbContent> list = contentService.findByCategoryId(categoryId);
        model.addAttribute("ad1List", list);

        return "index";
    }

}
