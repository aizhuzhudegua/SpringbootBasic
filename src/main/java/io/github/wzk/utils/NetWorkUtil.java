package io.github.wzk.utils;


import java.net.*;
import java.util.Enumeration;
import java.util.HashMap;

public class NetWorkUtil {



    public static HashMap<String, Inet4Address> getLocalIp4AddressFromNetworkInterface() throws SocketException, UnknownHostException {
        HashMap<String, Inet4Address> addresses = new HashMap<>();
        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        if (networkInterfaces == null) {
            return addresses;
        }
        while (networkInterfaces.hasMoreElements()) {
            NetworkInterface networkInterface = networkInterfaces.nextElement();
            if (networkInterface.isUp() && !networkInterface.isVirtual()) {
                Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    InetAddress inetAddress = inetAddresses.nextElement();
                    if (inetAddress instanceof Inet4Address && !inetAddress.getHostAddress().equals("127.0.0.1")) {
                        addresses.put(networkInterface.getName(), (Inet4Address) inetAddress);
                    }
                }
            }
        }
        return addresses;
    }


}