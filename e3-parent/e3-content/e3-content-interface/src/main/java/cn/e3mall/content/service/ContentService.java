package cn.e3mall.content.service;

import cn.e3mall.common.pojo.DataGridResult;
import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.pojo.TbContent;

import java.util.List;

public interface ContentService {

    E3Result saveCnotent(TbContent tbContent);

    E3Result updateContent(TbContent tbContent);

    E3Result deleteContent(String ids);

    List<TbContent> findByCategoryId(long categoryId);

    DataGridResult getContentListDataGrid(long categoryId, int page, int rows);
}
