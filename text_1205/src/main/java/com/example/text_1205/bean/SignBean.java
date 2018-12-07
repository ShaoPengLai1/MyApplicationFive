package com.example.text_1205.bean;

public class SignBean {

    /**
     * msg : 登录成功
     * code : 100
     * data : {"id":1,"name":"admin","mobile":13800138000}
     */

    private String msg;
    private int code;
    private DataBean data;
    private final int SUCCESS_CODE=100;
    public boolean isSuccess(){
        return code==SUCCESS_CODE;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * name : admin
         * mobile : 13800138000
         */

        private int id;
        private String name;
        private long mobile;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public long getMobile() {
            return mobile;
        }

        public void setMobile(long mobile) {
            this.mobile = mobile;
        }
    }
}
