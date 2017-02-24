package com.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

/**
 * 网络工具
 * Created by Travis1022 on 2017/2/24.
 */
public final class NetworkUtils {
    public static final String NETWORK_TYPE_WIFI = "wifi";
    public static final String NETWORK_TYPE_FASTMOBILENETWORK = "fast_mobile_network";
    public static final String NETWORK_TYPE_2G = "2g";
    public static final String NETWORK_TYPE_WAP = "wap";
    public static final String NETWORK_TYPE_UNKNOWN = "unknown";
    public static final String NETWORK_TYPE_DISCONNECT = "disconnect";

    /**
     * 判断网络是否可用
     *
     * @param context context
     * @return 网络是否可用
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        if (mNetworkInfo == null) return false;
        return mNetworkInfo.isAvailable();
    }

    /**
     * 获取网络状态
     *
     * @param context context
     * @return 网络状态
     */
    public static int getNetworkType(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager == null ? null : connectivityManager.getActiveNetworkInfo();
        return networkInfo == null ? -1 : networkInfo.getType();
    }

    /**
     * Get network type name
     *
     * @param context context
     * @return NetworkTypeName
     */
    public static String getNetworkTypeName(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo;
        String type = NETWORK_TYPE_DISCONNECT;
        if (manager == null || (networkInfo = manager.getActiveNetworkInfo()) == null) return type;
        if (networkInfo.isConnected()) {
            String typeName = networkInfo.getTypeName();
            if ("WIFI".equalsIgnoreCase(typeName))
                type = NETWORK_TYPE_WIFI;
            else if ("MOBILE".equalsIgnoreCase(typeName))
                //获取默认代理主机后判断网络类型
                type = StringUtils.isEmpty(android.net.Proxy.getDefaultHost())
                        ? (isFastMobileNetwork(context) ? NETWORK_TYPE_FASTMOBILENETWORK : NETWORK_TYPE_2G)
                        : NETWORK_TYPE_WAP;
            else
                type = NETWORK_TYPE_UNKNOWN;
        }
        return type;
    }

    /**
     * 是否快速移动网络：3G、4G即为快速移动网络
     *
     * @param context context
     * @return 是否快速移动网络
     */
    private static boolean isFastMobileNetwork(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (telephonyManager == null) return false;
        switch (telephonyManager.getNetworkType()) {
            case TelephonyManager.NETWORK_TYPE_1xRTT:
                return false;
            case TelephonyManager.NETWORK_TYPE_CDMA:
                return false;
            case TelephonyManager.NETWORK_TYPE_EDGE:
                return false;
            case TelephonyManager.NETWORK_TYPE_IDEN:     //2G
                return false;
            case TelephonyManager.NETWORK_TYPE_GPRS:     //2G
                return false;
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
                return true;
            case TelephonyManager.NETWORK_TYPE_EVDO_0:   //3G
                return true;
            case TelephonyManager.NETWORK_TYPE_HSDPA:    //3.5G
                return true;
            case TelephonyManager.NETWORK_TYPE_HSPA:     //3G
                return true;
            case TelephonyManager.NETWORK_TYPE_HSUPA:    //3.5G
                return true;
            case TelephonyManager.NETWORK_TYPE_UMTS:     //3G  联通3G
                return true;
            case TelephonyManager.NETWORK_TYPE_EHRPD:    //3G
                return true;
            case TelephonyManager.NETWORK_TYPE_EVDO_B:   //3G
                return true;
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return true;
            case TelephonyManager.NETWORK_TYPE_LTE:      //4G
                return true;
            case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                return false;
            default:
                return false;
        }
    }
}
