package com.lhd.mynews.domain;

import java.util.List;

/**
 * Created by lihuaidong on 2017/5/5 19:15.
 * 微信：lhd520ssp
 * QQ:414320737
 * 作用：
 */
public class TabDetailBean
{


    private DataBean data;
    private int retcode;

    public DataBean getData()
    {
        return data;
    }

    public void setData(DataBean data)
    {
        this.data = data;
    }

    public int getRetcode()
    {
        return retcode;
    }

    public void setRetcode(int retcode)
    {
        this.retcode = retcode;
    }

    public static class DataBean
    {
        private String countcommenturl;
        private String more;
        private String title;
        private List<NewsBean> news;
        private List<TopicBean> topic;
        private List<TopnewsBean> topnews;

        public String getCountcommenturl()
        {
            return countcommenturl;
        }

        public void setCountcommenturl(String countcommenturl)
        {
            this.countcommenturl = countcommenturl;
        }

        public String getMore()
        {
            return more;
        }

        public void setMore(String more)
        {
            this.more = more;
        }

        public String getTitle()
        {
            return title;
        }

        public void setTitle(String title)
        {
            this.title = title;
        }

        public List<NewsBean> getNews()
        {
            return news;
        }

        public void setNews(List<NewsBean> news)
        {
            this.news = news;
        }

        public List<TopicBean> getTopic()
        {
            return topic;
        }

        public void setTopic(List<TopicBean> topic)
        {
            this.topic = topic;
        }

        public List<TopnewsBean> getTopnews()
        {
            return topnews;
        }

        public void setTopnews(List<TopnewsBean> topnews)
        {
            this.topnews = topnews;
        }

        public static class NewsBean
        {
            private boolean comment;
            private String commentlist;
            private String commenturl;
            private int id;
            private String listimage;
            private String pubdate;
            private String title;
            private String type;
            private String url;

            public boolean isComment()
            {
                return comment;
            }

            public void setComment(boolean comment)
            {
                this.comment = comment;
            }

            public String getCommentlist()
            {
                return commentlist;
            }

            public void setCommentlist(String commentlist)
            {
                this.commentlist = commentlist;
            }

            public String getCommenturl()
            {
                return commenturl;
            }

            public void setCommenturl(String commenturl)
            {
                this.commenturl = commenturl;
            }

            public int getId()
            {
                return id;
            }

            public void setId(int id)
            {
                this.id = id;
            }

            public String getListimage()
            {
                return listimage;
            }

            public void setListimage(String listimage)
            {
                this.listimage = listimage;
            }

            public String getPubdate()
            {
                return pubdate;
            }

            public void setPubdate(String pubdate)
            {
                this.pubdate = pubdate;
            }

            public String getTitle()
            {
                return title;
            }

            public void setTitle(String title)
            {
                this.title = title;
            }

            public String getType()
            {
                return type;
            }

            public void setType(String type)
            {
                this.type = type;
            }

            public String getUrl()
            {
                return url;
            }

            public void setUrl(String url)
            {
                this.url = url;
            }
        }

        public static class TopicBean
        {
            private String description;
            private int id;
            private String listimage;
            private int sort;
            private String title;
            private String url;

            public String getDescription()
            {
                return description;
            }

            public void setDescription(String description)
            {
                this.description = description;
            }

            public int getId()
            {
                return id;
            }

            public void setId(int id)
            {
                this.id = id;
            }

            public String getListimage()
            {
                return listimage;
            }

            public void setListimage(String listimage)
            {
                this.listimage = listimage;
            }

            public int getSort()
            {
                return sort;
            }

            public void setSort(int sort)
            {
                this.sort = sort;
            }

            public String getTitle()
            {
                return title;
            }

            public void setTitle(String title)
            {
                this.title = title;
            }

            public String getUrl()
            {
                return url;
            }

            public void setUrl(String url)
            {
                this.url = url;
            }
        }

        public static class TopnewsBean
        {
            private boolean comment;
            private String commentlist;
            private String commenturl;
            private int id;
            private String pubdate;
            private String title;
            private String topimage;
            private String type;
            private String url;

            public boolean isComment()
            {
                return comment;
            }

            public void setComment(boolean comment)
            {
                this.comment = comment;
            }

            public String getCommentlist()
            {
                return commentlist;
            }

            public void setCommentlist(String commentlist)
            {
                this.commentlist = commentlist;
            }

            public String getCommenturl()
            {
                return commenturl;
            }

            public void setCommenturl(String commenturl)
            {
                this.commenturl = commenturl;
            }

            public int getId()
            {
                return id;
            }

            public void setId(int id)
            {
                this.id = id;
            }

            public String getPubdate()
            {
                return pubdate;
            }

            public void setPubdate(String pubdate)
            {
                this.pubdate = pubdate;
            }

            public String getTitle()
            {
                return title;
            }

            public void setTitle(String title)
            {
                this.title = title;
            }

            public String getTopimage()
            {
                return topimage;
            }

            public void setTopimage(String topimage)
            {
                this.topimage = topimage;
            }

            public String getType()
            {
                return type;
            }

            public void setType(String type)
            {
                this.type = type;
            }

            public String getUrl()
            {
                return url;
            }

            public void setUrl(String url)
            {
                this.url = url;
            }
        }
    }
}
