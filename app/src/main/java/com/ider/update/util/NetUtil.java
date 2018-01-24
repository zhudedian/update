package com.ider.update.util;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Created by Eric on 2018/1/18.
 */

public class NetUtil {
    public static String getEthMac() {
        String ethernetMac = null;
        try {
            Enumeration localEnumeration = NetworkInterface.getNetworkInterfaces();

            while (localEnumeration.hasMoreElements()) {
                NetworkInterface localNetworkInterface = (NetworkInterface) localEnumeration.nextElement();
                String interfaceName = localNetworkInterface.getDisplayName();

                if (interfaceName == null) {
                    continue;
                }

                if (interfaceName.equals("eth0")) {
                    // MACAddr = convertMac(localNetworkInterface
                    // .getHardwareAddress());
                    ethernetMac = convertToMac(localNetworkInterface.getHardwareAddress());
                    if (ethernetMac != null && ethernetMac.startsWith("0:")) {
                        ethernetMac = "0" + ethernetMac;
                    }
                    break;
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return ethernetMac;
    }
    private static String convertToMac(byte[] mac) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mac.length; i++) {
            byte b = mac[i];
            int value = 0;
            if (b >= 0 && b <= 16) {
                value = b;
                sb.append("0" + Integer.toHexString(value));
            } else if (b > 16) {
                value = b;
                sb.append(Integer.toHexString(value));
            } else {
                value = 256 + b;
                sb.append(Integer.toHexString(value));
            }
            if (i != mac.length - 1) {
                sb.append(":");
            }
        }
        return sb.toString();
        //return "30:01:02:03:32:b0";
    }
}
