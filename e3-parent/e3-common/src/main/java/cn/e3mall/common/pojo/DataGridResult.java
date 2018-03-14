package cn.e3mall.common.pojo;

import java.io.Serializable;
import java.util.List;

public class DataGridResult implements Serializable {

    private Integer total;
    private List<?> rows;

    public Integer getTotal() {
        return this.total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<?> getRows() {
        return this.rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }
}
