package me.aidoc.client.entity.resp;

import java.util.List;

import me.aidoc.client.entity.BaseResp;

/**
 * Created by lijianqiang on 2018/1/19.
 */

public class NewsListResp extends BaseResp {

    /**
     * page : 0
     * page_size : 20
     * count : 1
     * pages : 1
     * list : [{"watch_num":0,"title":"新闻1","created_at":null,"content":"","cover":"http://a.hiphotos.baidu.com/image/pic/item/500fd9f9d72a6059f550a1832334349b023bbae3.jpg","id":1,"forward_num":0}]
     * extend : null
     */

    private int page;
    private int page_size;
    private int count;
    private int pages;
    private Object extend;
    private List<ListBean> list;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPage_size() {
        return page_size;
    }

    public void setPage_size(int page_size) {
        this.page_size = page_size;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public Object getExtend() {
        return extend;
    }

    public void setExtend(Object extend) {
        this.extend = extend;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * watch_num : 0
         * title : 新闻1
         * created_at : null
         * content :
         * cover : http://a.hiphotos.baidu.com/image/pic/item/500fd9f9d72a6059f550a1832334349b023bbae3.jpg
         * id : 1
         * forward_num : 0
         */

        private int watch_num;
        private String title;
        private String created_at;
        private String content;
        private String cover;
        private int id;
        private int forward_num;

        public int getWatch_num() {
            return watch_num;
        }

        public void setWatch_num(int watch_num) {
            this.watch_num = watch_num;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getForward_num() {
            return forward_num;
        }

        public void setForward_num(int forward_num) {
            this.forward_num = forward_num;
        }

        @Override
        public String toString() {
            return "ListBean{" +
                    "watch_num=" + watch_num +
                    ", title='" + title + '\'' +
                    ", created_at='" + created_at + '\'' +
                    ", content='" + content + '\'' +
                    ", cover='" + cover + '\'' +
                    ", id=" + id +
                    ", forward_num=" + forward_num +
                    '}';
        }
    }
}
