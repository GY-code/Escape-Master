package com.example.applicationtest001;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class PermissUtil {
    public static final int PERMISSON_REQUESTCODE = 123;
    //app需要进行检测的权限数组
    public static String[] appNeedPermissions = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.CHANGE_WIFI_STATE,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.CHANGE_NETWORK_STATE,
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WAKE_LOCK,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    //app权限检测
    public static boolean checkPermissions(Activity activity, String... permissions) {
        List<String> needRequestPermissonList = findDeniedPermissions(activity, permissions);
        if (null != needRequestPermissonList
                && needRequestPermissonList.size() > 0) {
            ActivityCompat.requestPermissions(activity,
                    needRequestPermissonList.toArray(
                            new String[needRequestPermissonList.size()]),
                    PERMISSON_REQUESTCODE);
            return false;
        } else return true;
    }

    //获取权限集中需要申请权限的列表
    public static List<String> findDeniedPermissions(Activity contexts, String[] permissions) {
        List<String> needRequestPermissonList = new ArrayList<String>();
        for (String perm : permissions) {
            if (ContextCompat.checkSelfPermission(contexts,
                    perm) != PackageManager.PERMISSION_GRANTED) {
                needRequestPermissonList.add(perm);
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        contexts, perm)) {
                    needRequestPermissonList.add(perm);
                }
            }
        }
        return needRequestPermissonList;
    }

    //检测是否说有的权限都已经授权
    public static boolean verifyPermissions(int[] grantResults) {

        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }
}
