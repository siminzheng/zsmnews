package Bean;

import java.util.List;

public class Wangyipic_Bean {

    private PicDataEntity result;

    public PicDataEntity getPicDataEntity(){return result;}


    public static class PicDataEntity {
        private String postid;
        private String desc;
        private String pvnum;
        private String createdate;
        private String scover;
        private String setname;
        private String cover;
        private List<?> pics;
        private String clientcover1;
        private String replynum;
        private String topicname;
        private String setid;
        private String seturl;
        private String datetime;
        private String clientcover;
        private String imgsum;
        private String tcover;

        public String getPostid() {
            return postid;
        }

        public void setPostid(String postid) {
            this.postid = postid;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getPvnum() {
            return pvnum;
        }

        public void setPvnum(String pvnum) {
            this.pvnum = pvnum;
        }

        public String getCreatedate() {
            return createdate;
        }

        public void setCreatedate(String createdate) {
            this.createdate = createdate;
        }

        public String getScover() {
            return scover;
        }

        public void setScover(String scover) {
            this.scover = scover;
        }

        public String getSetname() {
            return setname;
        }

        public void setSetname(String setname) {
            this.setname = setname;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public List<?> getPics() {
            return pics;
        }

        public void setPics(List<?> pics) {
            this.pics = pics;
        }

        public String getClientcover1() {
            return clientcover1;
        }

        public void setClientcover1(String clientcover1) {
            this.clientcover1 = clientcover1;
        }

        public String getReplynum() {
            return replynum;
        }

        public void setReplynum(String replynum) {
            this.replynum = replynum;
        }

        public String getTopicname() {
            return topicname;
        }

        public void setTopicname(String topicname) {
            this.topicname = topicname;
        }

        public String getSetid() {
            return setid;
        }

        public void setSetid(String setid) {
            this.setid = setid;
        }

        public String getSeturl() {
            return seturl;
        }

        public void setSeturl(String seturl) {
            this.seturl = seturl;
        }

        public String getDatetime() {
            return datetime;
        }

        public void setDatetime(String datetime) {
            this.datetime = datetime;
        }

        public String getClientcover() {
            return clientcover;
        }

        public void setClientcover(String clientcover) {
            this.clientcover = clientcover;
        }

        public String getImgsum() {
            return imgsum;
        }

        public void setImgsum(String imgsum) {
            this.imgsum = imgsum;
        }

        public String getTcover() {
            return tcover;
        }

        public void setTcover(String tcover) {
            this.tcover = tcover;
        }
    }
}
