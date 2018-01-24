package com.ider.update.util;

/**
 * Created by Eric on 2018/1/18.
 */

public class PostUtil {

    private String getPostInfo(){
        return "{\n" +
                "    \"deviceModel\":\"ID_AI91\",\n" +
                "    \"vendorID\":\"0X0041\",\n" +
                "    \"firmwareVersion\":\"mos7.1.1\",\n" +
                "    \"serialNumber\":\"\",\n" +
                "    \"launcherPackage\":\"com.ider.overlauncher\",\n" +
                "    \"launcherVersion\":\"3.1.5\",\n" +
                "    \"mac\":\""+NetUtil.getEthMac()+"\",\n" +
                "    \"ip\":\"204.81.69.141\",\n" +
                "    \"peripheral\":{\n" +
                "      \"wifi\":{\n" +
                "            \"wifiRemote\":\"0\",\n" +
                "            \"wifiSocket\":\"0\",\n" +
                "            \"wifiRelay\":\"0\",\n" +
                "            \"wifiPowerstrip\":\"0\",\n" +
                "            \"wifiLight\":\"0\",\n" +
                "            \"wifiSoundbox\":\"0\",\n" +
                "            \"wifiMicrophone\":\"0\",\n" +
                "            \"wifiGateway\":\"0\"\n" +
                "        },\n"+
                "        \"bluetooth\":{\n" +
                "            \"bluetoothRemote\":\"1\",\n" +
                "            \"bluetoothSocket\":\"0\",\n" +
                "            \"bluetoothRelay\":\"0\",\n" +
                "            \"bluetoothPowerstrip\":\"0\",\n" +
                "            \"bluetoothLight\":\"0\",\n" +
                "            \"bluetoothSoundbox\":\"0\",\n" +
                "            \"bluetoothMicrophone\":\"0\",\n" +
                "            \"bluetoothGateway\":\"0\",\n" +
                "            \"bluetoothWristband\":\"0\"\n" +
                "        },\n" +
                "        \"zigbee\":{\n" +
                "            \"zigbeeRemote\":\"0\",\n" +
                "            \"zigbeeSocket\":\"0\",\n" +
                "            \"zigbeeRelay\":\"0\",\n" +
                "            \"zigbeePowerstrip\":\"0\",\n" +
                "            \"zigbeeLight\":\"0\",\n" +
                "            \"zigbeeSoundbox\":\"0\",\n" +
                "            \"zigbeeMicrophone\":\"0\",\n" +
                "            \"zigbeeGateway\":\"0\",\n" +
                "            \"zigbeeWristband\":\"0\"\n" +
                "        },\n"+
                "        \"upart\":{\n" +
                "            \"upart\":\"0\",\n" +
                "            \"vpart\":\"0\"\n" +
                "        },\n" +
                "        \"rf\":{\n" +
                "            \"315\":\"0\",\n" +
                "            \"433\":\"0\"\n" +
                "        }\n" +
                "    },\n" +
                "    \"remote\":\"0\",\n" +
                "    \"reportTime\":\""+TimeUtil.getStrTime()+"\",\n" +
                "    \"sign\":\""+MD5Util.getMD5(NetUtil.getEthMac()+TimeUtil.getStrTime())+"\"\n"+
                "}";
    }
}
