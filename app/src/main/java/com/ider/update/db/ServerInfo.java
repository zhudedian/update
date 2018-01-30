package com.ider.update.db;

import java.util.List;

/**
 * Created by Eric on 2018/1/27.
 */

public class ServerInfo {

    /**
     * code : 0
     * msg : 获取升级信息成功
     * data : [{"packageType":"1","newVersion":"mos-7.1.1","newSize":"416717914","url":"http://www.trehere.com:8089/ydh/upload/f2fc3018-dd23-449f-b016-b6e295a72cca.zip","md5":"7b907de84a16da3161ca8f2d29ade592","deviceModel":"ID_AI81","vendorID":"0","upgradeInfo":"","peripheral":"rf315","peripheralKey":"rf315","upgradeWay":"1"}]
     */

    private String code;
    private String msg;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * packageType : 1
         * newVersion : mos-7.1.1
         * newSize : 416717914
         * url : http://www.trehere.com:8089/ydh/upload/f2fc3018-dd23-449f-b016-b6e295a72cca.zip
         * md5 : 7b907de84a16da3161ca8f2d29ade592
         * deviceModel : ID_AI81
         * vendorID : 0
         * upgradeInfo :
         * peripheral : rf315
         * peripheralKey : rf315
         * upgradeWay : 1
         */

        private String packageType;
        private String newVersion;
        private String newSize;
        private String url;
        private String md5;
        private String deviceModel;
        private String vendorID;
        private String upgradeInfo;
        private String peripheral;
        private String peripheralKey;
        private String upgradeWay;

        public String getPackageType() {
            return packageType;
        }

        public void setPackageType(String packageType) {
            this.packageType = packageType;
        }

        public String getNewVersion() {
            return newVersion;
        }

        public void setNewVersion(String newVersion) {
            this.newVersion = newVersion;
        }

        public String getNewSize() {
            return newSize;
        }

        public void setNewSize(String newSize) {
            this.newSize = newSize;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getMd5() {
            return md5;
        }

        public void setMd5(String md5) {
            this.md5 = md5;
        }

        public String getDeviceModel() {
            return deviceModel;
        }

        public void setDeviceModel(String deviceModel) {
            this.deviceModel = deviceModel;
        }

        public String getVendorID() {
            return vendorID;
        }

        public void setVendorID(String vendorID) {
            this.vendorID = vendorID;
        }

        public String getUpgradeInfo() {
            return upgradeInfo;
        }

        public void setUpgradeInfo(String upgradeInfo) {
            this.upgradeInfo = upgradeInfo;
        }

        public String getPeripheral() {
            return peripheral;
        }

        public void setPeripheral(String peripheral) {
            this.peripheral = peripheral;
        }

        public String getPeripheralKey() {
            return peripheralKey;
        }

        public void setPeripheralKey(String peripheralKey) {
            this.peripheralKey = peripheralKey;
        }

        public String getUpgradeWay() {
            return upgradeWay;
        }

        public void setUpgradeWay(String upgradeWay) {
            this.upgradeWay = upgradeWay;
        }
    }
}
