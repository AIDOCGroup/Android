package me.aidoc.client.entity.resp;

import me.aidoc.client.entity.BaseResp;

import java.util.List;

public class StepListResp extends BaseResp {

    private String page_size;//
    private String count;//
    private String pages;//
    private String page;//
    private List<Data> list;

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public List<Data> getList() {
        return list;
    }

    public void setList(List<Data> list) {
        this.list = list;
    }

    public String getPage_size() {
        return page_size;
    }

    public void setPage_size(String page_size) {
        this.page_size = page_size;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public class Data{
        private String yearMonth;
        private int steps;
        private String aidoc;
        public String getYearMonth() {
            return yearMonth;
        }

        public void setYearMonth(String yearMonth) {
            this.yearMonth = yearMonth;
        }

        public int getSteps() {
            return steps;
        }

        public String getAidoc() {
            return aidoc;
        }

        public void setAidoc(String aidoc) {
            this.aidoc = aidoc;
        }

        public void setSteps(int steps) {
            this.steps = steps;
        }
    }

}
