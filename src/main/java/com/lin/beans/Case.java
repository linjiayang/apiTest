package com.lin.beans;

public class Case {
    private String id;
    private String desc;
    private String method;
    private String url;
    private String param;
    private String verify;
    private String run;
    private String save;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getVerify() {
        return verify;
    }

    public void setVerify(String verify) {
        this.verify = verify;
    }

    public String getRun() {
        return run;
    }

    public void setRun(String run) {
        this.run = run;
    }

    public String getSave() {
        return save;
    }

    public void setSave(String save) {
        this.save = save;
    }

    @Override
    public String toString() {
        return "Case{" +
                "id='" + id + '\'' +
                ", desc='" + desc + '\'' +
                ", method='" + method + '\'' +
                ", url='" + url + '\'' +
                ", param='" + param + '\'' +
                ", verify='" + verify + '\'' +
                ", run='" + run + '\'' +
                ", save='" + save + '\'' +
                '}';
    }
}
