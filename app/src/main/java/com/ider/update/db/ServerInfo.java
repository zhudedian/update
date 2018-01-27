package com.ider.update.db;

/**
 * Created by Eric on 2018/1/27.
 */

public class ServerInfo {
    /**
     * code : 0
     * msg : 获取升级信息成功
     * mac : ca:1e:2f:a8:d8:a6
     * deviceModel : molly
     * newVersion : mos-7.1.2
     * size : 431564951
     * date : 2017-10-24
     * md5 : fc0458d3752fe61ee2479c98b6730b8d
     * url : http://www.trehere.com:8089/ydh/upload/48f40ca2-4959-4d4d-9e7d-7bb7e41c6b6c.zip
     */

    private String code;
    private String msg;
    private String mac;
    private String deviceModel;
    private String newVersion;
    private String size;
    private String date;
    private String md5;
    private String url;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public String getNewVersion() {
        return newVersion;
    }

    public void setNewVersion(String newVersion) {
        this.newVersion = newVersion;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
