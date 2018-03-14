package cn.e3mall.search.pojo;

import java.io.Serializable;
import java.util.List;

public class SearchResult implements Serializable{
    private long totalPages;
    private long recourdCount;
    private List<SearchItem> itemList;

    public long getTotalPages() {
        return this.totalPages;
    }

    public void setTotalPages(long totalPages) {
        this.totalPages = totalPages;
    }

    public long getRecourdCount() {
        return this.recourdCount;
    }

    public void setRecourdCount(long recourdCount) {
        this.recourdCount = recourdCount;
    }

    public List<SearchItem> getItemList() {
        return this.itemList;
    }

    public void setItemList(List<SearchItem> itemList) {
        this.itemList = itemList;
    }
}
