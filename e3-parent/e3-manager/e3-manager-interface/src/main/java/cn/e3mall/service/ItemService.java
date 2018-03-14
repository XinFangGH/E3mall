package cn.e3mall.service;

import cn.e3mall.common.pojo.DataGridResult;
import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.pojo.TbItem;

public interface ItemService {
    TbItem findById(long id);

    DataGridResult findPageList(Integer page,Integer rows);

    E3Result saveItem(TbItem tbItem, String desc);

    E3Result findItemDescById(long id);

    E3Result updateItem(TbItem item, String desc);

    E3Result updateInstock(String ids);

    E3Result updateReshelf(String ids);

    E3Result delete(String ids);
}
