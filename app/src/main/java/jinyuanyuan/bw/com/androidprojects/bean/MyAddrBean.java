package jinyuanyuan.bw.com.androidprojects.bean;

import java.util.List;

/**
 * author:author${朱佳华}
 * data:2019/1/7
 */
public class MyAddrBean {

    /**
     * result : [{"address":"安徽省-安庆市-枞阳县安徽省安庆市枞阳县1","createTime":1546895826000,"id":353,"phone":"15101552311","realName":"1","userId":791,"whetherDefault":1,"zipCode":"246000"}]
     * message : 查询成功
     * status : 0000
     */

    private String message;
    private String status;
    private List<ResultBean> result;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * address : 安徽省-安庆市-枞阳县安徽省安庆市枞阳县1
         * createTime : 1546895826000
         * id : 353
         * phone : 15101552311
         * realName : 1
         * userId : 791
         * whetherDefault : 1
         * zipCode : 246000
         */

        private String address;
        private long createTime;
        private int id;
        private String phone;
        private String realName;
        private int userId;
        private int whetherDefault;
        private String zipCode;



        private boolean isChecked;
        public boolean isChecked() {
            return isChecked;
        }
        public void setChecked(boolean checked) {
            isChecked = checked;
        }



        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getWhetherDefault() {
            return whetherDefault;
        }

        public void setWhetherDefault(int whetherDefault) {
            this.whetherDefault = whetherDefault;
        }

        public String getZipCode() {
            return zipCode;
        }

        public void setZipCode(String zipCode) {
            this.zipCode = zipCode;
        }


        @Override
        public String toString() {
            return "ResultBean{" +
                    "address='" + address + '\'' +
                    ", createTime=" + createTime +
                    ", id=" + id +
                    ", phone='" + phone + '\'' +
                    ", realName='" + realName + '\'' +
                    ", userId=" + userId +
                    ", whetherDefault=" + whetherDefault +
                    ", zipCode='" + zipCode + '\'' +
                    ", isChecked=" + isChecked +
                    '}';
        }
    }
}
