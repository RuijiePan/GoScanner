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
        private List<?> title;
        private List<?> tel;
        private List<?> mobile;
        private List<?> fax;
        private List<?> email;
        private List<?> comp;
        private List<?> dept;
        private List<?> degree;
        private List<?> addr;
        private List<?> post;
        private List<?> mbox;
        private List<?> htel;
        private List<?> web;
        private List<?> im;
        private List<?> numOther;
        private List<?> other;
        private List<?> extTel;

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

        public List<?> getTitle() {
            return title;
        }

        public void setTitle(List<?> title) {
            this.title = title;
        }

        public List<?> getTel() {
            return tel;
        }

        public void setTel(List<?> tel) {
            this.tel = tel;
        }

        public List<?> getMobile() {
            return mobile;
        }

        public void setMobile(List<?> mobile) {
            this.mobile = mobile;
        }

        public List<?> getFax() {
            return fax;
        }

        public void setFax(List<?> fax) {
            this.fax = fax;
        }

        public List<?> getEmail() {
            return email;
        }

        public void setEmail(List<?> email) {
            this.email = email;
        }

        public List<?> getComp() {
            return comp;
        }

        public void setComp(List<?> comp) {
            this.comp = comp;
        }

        public List<?> getDept() {
            return dept;
        }

        public void setDept(List<?> dept) {
            this.dept = dept;
        }

        public List<?> getDegree() {
            return degree;
        }

        public void setDegree(List<?> degree) {
            this.degree = degree;
        }

        public List<?> getAddr() {
            return addr;
        }

        public void setAddr(List<?> addr) {
            this.addr = addr;
        }

        public List<?> getPost() {
            return post;
        }

        public void setPost(List<?> post) {
            this.post = post;
        }

        public List<?> getMbox() {
            return mbox;
        }

        public void setMbox(List<?> mbox) {
            this.mbox = mbox;
        }

        public List<?> getHtel() {
            return htel;
        }

        public void setHtel(List<?> htel) {
            this.htel = htel;
        }

        public List<?> getWeb() {
            return web;
        }

        public void setWeb(List<?> web) {
            this.web = web;
        }

        public List<?> getIm() {
            return im;
        }

        public void setIm(List<?> im) {
            this.im = im;
        }

        public List<?> getNumOther() {
            return numOther;
        }

        public void setNumOther(List<?> numOther) {
            this.numOther = numOther;
        }

        public List<?> getOther() {
            return other;
        }

        public void setOther(List<?> other) {
            this.other = other;
        }

        public List<?> getExtTel() {
            return extTel;
        }

        public void setExtTel(List<?> extTel) {
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
