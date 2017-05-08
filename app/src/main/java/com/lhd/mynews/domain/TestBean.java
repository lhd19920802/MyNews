package com.lhd.mynews.domain;

import java.util.List;

/**
 * Created by lihuaidong on 2017/5/8 8:49.
 * 微信：lhd520ssp
 * QQ:414320737
 * 作用：
 */
public class TestBean
{
    private int code;
    private List<ListBean> list;

    public int getCode()
    {
        return code;
    }

    public void setCode(int code)
    {
        this.code = code;
    }

    public List<ListBean> getList()
    {
        return list;
    }

    public void setList(List<ListBean> list)
    {
        this.list = list;
    }

    public static class ListBean
    {
        private String aid;
        private String author;
        private String create;
        private String description;
        private String duration;
        private int mid;
        private String pic;
        private String title;
        private String typename;

        public String getAid()
        {
            return aid;
        }

        public void setAid(String aid)
        {
            this.aid = aid;
        }

        public String getAuthor()
        {
            return author;
        }

        public void setAuthor(String author)
        {
            this.author = author;
        }

        public String getCreate()
        {
            return create;
        }

        public void setCreate(String create)
        {
            this.create = create;
        }

        public String getDescription()
        {
            return description;
        }

        public void setDescription(String description)
        {
            this.description = description;
        }

        public String getDuration()
        {
            return duration;
        }

        public void setDuration(String duration)
        {
            this.duration = duration;
        }

        public int getMid()
        {
            return mid;
        }

        public void setMid(int mid)
        {
            this.mid = mid;
        }

        public String getPic()
        {
            return pic;
        }

        public void setPic(String pic)
        {
            this.pic = pic;
        }

        public String getTitle()
        {
            return title;
        }

        public void setTitle(String title)
        {
            this.title = title;
        }

        public String getTypename()
        {
            return typename;
        }

        public void setTypename(String typename)
        {
            this.typename = typename;
        }

        @Override
        public String toString()
        {
            return "ListBean{" +
                   "aid='" + aid + '\'' +
                   ", author='" + author + '\'' +
                   ", create='" + create + '\'' +
                   ", description='" + description + '\'' +
                   ", duration='" + duration + '\'' +
                   ", mid=" + mid +
                   ", pic='" + pic + '\'' +
                   ", title='" + title + '\'' +
                   ", typename='" + typename + '\'' +
                   '}';
        }
    }


}
