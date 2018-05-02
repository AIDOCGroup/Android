package me.aidoc.client.entity.resp;

import java.util.List;

public class MasterResp {

    private List<DataBean> day;
    private List<DataBean> total;

    public List<DataBean> getDay() {
        return day;
    }

    public void setDay(List<DataBean> day) {
        this.day = day;
    }

    public List<DataBean> getTotal() {
        return total;
    }

    public void setTotal(List<DataBean> total) {
        this.total = total;
    }


    public static class DataBean {
        /**
         * userId : 5
         * dataNumber : 192935
         * mobile : 15624972206
         * nickname : Polaris
         * signature : Anything is possible!!!
         * avatar : http://tianyiaidoc.oss-cn-hangzhou.aliyuncs.com/27c53ce7-9c9b-46fb-b03c-b77b0756920b.png
         */

        private int userId;
        private int dataNumber;
        private String mobile;
        private String nickname;
        private String signature;
        private String avatar;

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getDataNumber() {
            return dataNumber;
        }

        public void setDataNumber(int dataNumber) {
            this.dataNumber = dataNumber;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getSignature() {
            return signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }
    }
}
