package com.jb.goscanner.function.bean;

import java.util.List;

/**
 * Created by panruijie on 2017/9/3.
 * Email : zquprj@gmail.com
 */

public class CardInfo {


    /**
     * reason : successed
     * result : {"rotatedAngle":null,"name":["郝明遍"],"title":[],"tel":[],"mobile":[],"fax":[],"email":[],"comp":[],"dept":[],"degree":[],"addr":[],"post":[],"mbox":[],"htel":[],"web":[],"im":[],"numOther":[],"other":[],"extTel":[]}
     * error_code : 0
     */

    private String reason;
    private ResultBean result;
    private int error_code;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public static class ResultBean {
        /**
         * rotatedAngle : null
         * name : ["郝明遍"]
         * title : []
         * tel : []
         * mobile : []
         * fax : []
         * email : []
         * comp : []
         * dept : []
         * degree : []
         * addr : []
         * post : []
         * mbox : []
         * htel : []
         * web : []
         * im : []
         * numOther : []
         * other : []
         * extTel : []
         */

        private Object rotatedAngle;
        private List<String> name;
        private List<String> title;
        private List<String> tel;
        private List<String> mobile;
        private List<String> fax;
        private List<String> email;
        private List<String> comp;
        private List<String> dept;
        private List<String> degree;
        private List<String> addr;
        private List<String> post;
        private List<String> mbox;
        private List<String> htel;
        private List<String> web;
        private List<String> im;
        private List<String> numOther;
        private List<String> other;
        private List<String> extTel;

        public Object getRotatedAngle() {
            return rotatedAngle;
        }

        public void setRotatedAngle(Object rotatedAngle) {
            this.rotatedAngle = rotatedAngle;
        }

        public List<String> getName() {
            return name;
        }

        public void setName(List<String> name) {
            this.name = name;
        }

        public List<String> getTitle() {
            return title;
        }

        public void setTitle(List<String> title) {
            this.title = title;
        }

        public List<String> getTel() {
            return tel;
        }

        public void setTel(List<String> tel) {
            this.tel = tel;
        }

        public List<String> getMobile() {
            return mobile;
        }

        public void setMobile(List<String> mobile) {
            this.mobile = mobile;
        }

        public List<String> getFax() {
            return fax;
        }

        public void setFax(List<String> fax) {
            this.fax = fax;
        }

        public List<String> getEmail() {
            return email;
        }

        public void setEmail(List<String> email) {
            this.email = email;
        }

        public List<String> getComp() {
            return comp;
        }

        public void setComp(List<String> comp) {
            this.comp = comp;
        }

        public List<String> getDept() {
            return dept;
        }

        public void setDept(List<String> dept) {
            this.dept = dept;
        }

        public List<String> getDegree() {
            return degree;
        }

        public void setDegree(List<String> degree) {
            this.degree = degree;
        }

        public List<String> getAddr() {
            return addr;
        }

        public void setAddr(List<String> addr) {
            this.addr = addr;
        }

        public List<String> getPost() {
            return post;
        }

        public void setPost(List<String> post) {
            this.post = post;
        }

        public List<String> getMbox() {
            return mbox;
        }

        public void setMbox(List<String> mbox) {
            this.mbox = mbox;
        }

        public List<String> getHtel() {
            return htel;
        }

        public void setHtel(List<String> htel) {
            this.htel = htel;
        }

        public List<String> getWeb() {
            return web;
        }

        public void setWeb(List<String> web) {
            this.web = web;
        }

        public List<String> getIm() {
            return im;
        }

        public void setIm(List<String> im) {
            this.im = im;
        }

        public List<String> getNumOther() {
            return numOther;
        }

        public void setNumOther(List<String> numOther) {
            this.numOther = numOther;
        }

        public List<String> getOther() {
            return other;
        }

        public void setOther(List<String> other) {
            this.other = other;
        }

        public List<String> getExtTel() {
            return extTel;
        }

        public void setExtTel(List<String> extTel) {
            this.extTel = extTel;
        }

        @Override
        public String toString() {
            return "ResultBean{" +
                    "rotatedAngle=" + rotatedAngle +
                    ", name=" + name +
                    ", title=" + title +
                    ", tel=" + tel +
                    ", mobile=" + mobile +
                    ", fax=" + fax +
                    ", email=" + email +
                    ", comp=" + comp +
                    ", dept=" + dept +
                    ", degree=" + degree +
                    ", addr=" + addr +
                    ", post=" + post +
                    ", mbox=" + mbox +
                    ", htel=" + htel +
                    ", web=" + web +
                    ", im=" + im +
                    ", numOther=" + numOther +
                    ", other=" + other +
                    ", extTel=" + extTel +
                    '}';
        }
    }
}
