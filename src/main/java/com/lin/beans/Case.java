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
    private String preCheck;
    private String preResult;
    private String afterCheck;
    private String afterResult;

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

    public String getPreCheck() {
        return preCheck;
    }

    public void setPreCheck(String preCheck) {
        this.preCheck = preCheck;
    }

    public String getPreResult() {
        return preResult;
    }

    public void setPreResult(String preResult) {
        this.preResult = preResult;
    }

    public String getAfterCheck() {
        return afterCheck;
    }

    public void setAfterCheck(String afterCheck) {
        this.afterCheck = afterCheck;
    }

    public String getAfterResult() {
        return afterResult;
    }

    public void setAfterResult(String afterResult) {
        this.afterResult = afterResult;
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
                ", preCheck='" + preCheck + '\'' +
                ", preResult='" + preResult + '\'' +
                ", afterCheck='" + afterCheck + '\'' +
                ", afterResult='" + afterResult + '\'' +
                '}';
    }
}
