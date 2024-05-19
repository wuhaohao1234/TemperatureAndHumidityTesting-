package com.example.temperatureandhumiditytesting.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.example.temperatureandhumiditytesting.base.App;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class LocationManagerUtil {

        private volatile static LocationManagerUtil locationManagerUtil = null;
        private final String TAG = "LocationManagerUtil";
        private final Context context = App.context;
        private final LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        private LocationManagerListener listener;
        private final Handler handler = new Handler(Looper.myLooper());

        /**
         * 超时时间10秒
         */
        private final long TIME_OUT = 10000;

        /**
         * 超时结束监听标记
         */
        private boolean endlistenerFlag = false;

        public static LocationManagerUtil getInstance() {
            if (null == locationManagerUtil) {
                synchronized (LocationManagerUtil.class) {
                    if (null == locationManagerUtil) {
                        locationManagerUtil = new LocationManagerUtil();
                    }
                }
            }
            return locationManagerUtil;
        }

        /**
         * 开始定位 单次定位
         *
         * @param listener
         */
        public void startSingleLocation(LocationManagerListener listener) {
            this.listener = listener;
            endlistenerFlag = false;

            if (null == locationManager) {
                return;
            }



            //检查权限
            boolean permission = checkPermission();
            if (!permission) {
                return;
            }

            String provider = LocationManager.NETWORK_PROVIDER;

            //判断provider是否可用
            boolean providerEnabled = locationManager.isProviderEnabled(provider);
            if (!providerEnabled) {
                return;
            }

            //获取缓存中的位置信息getLastKnownLocation
            Location location = locationManager.getLastKnownLocation(provider);
            if (null != location) {
                String locationAddr = getLocationAddr(location.getLongitude(), location.getLatitude());
                //清除定位信息
                location.reset();
            }

//        getWifi();
//
//        getTelephonyManager();

            //        Criteria crite = new Criteria();
//        crite.setAccuracy(Criteria.ACCURACY_FINE); //精度
//        crite.setPowerRequirement(Criteria.POWER_LOW); //功耗类型选择
//        String provider = locationManager.getBestProvider(crite, true);

//        String networkProvider = LocationManager.NETWORK_PROVIDER;
//        String gpsProvider = LocationManager.GPS_PROVIDER;
//        String passiveProvider = LocationManager.PASSIVE_PROVIDER;

            //添加地理围栏
//        locationManager.addProximityAlert(38.234, 114.234, 5, -1, PendingIntent.getBroadcast(this, 1, new Intent(), 3));
//        可以设置一个区域，当进入或离开这个区域的时候会收到通知，前两个参数指定一个点，第三个参数是半径，第四个参数是超时时间，设置为-1表示不存在超时，最后一个是广播接收器。
//        触发的Intent将使用键KEY_PROXIMITY_ENTERING，如果值为true，则设备进入邻近区域，如果是false，说明设备离开该区域。

            //获取一次定位结果requestSingleUpdate  单次定位
            locationManager.requestSingleUpdate(provider, locationListener, handler.getLooper());

            //超时结束定位
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    locationManager.removeUpdates(locationListener);
                    handler.removeCallbacks(this);
                    if (!endlistenerFlag) {
                        onFailureListener();
                    }
                }
            }, TIME_OUT);
        }

        /**
         * 开始定位 持续定位
         *
         * @param listener
         */
        public void startLocationUpdates(LocationManagerListener listener) {
            this.listener = listener;
            endlistenerFlag = false;

            if (null == locationManager) {
                return;
            }

            //检查权限
            boolean permission = checkPermission();
            if (!permission) {
                return;
            }

            String provider = LocationManager.NETWORK_PROVIDER;

            //判断provider是否可用
            boolean providerEnabled = locationManager.isProviderEnabled(provider);
            if (!providerEnabled) {
                return;
            }

            //获取缓存中的位置信息getLastKnownLocation
            Location location = locationManager.getLastKnownLocation(provider);
            if (null != location) {
                String locationAddr = getLocationAddr(location.getLongitude(), location.getLatitude());
                //清除定位信息
                location.reset();
            }

//        Criteria crite = new Criteria();
//        crite.setAccuracy(Criteria.ACCURACY_FINE); //精度
//        crite.setPowerRequirement(Criteria.POWER_LOW); //功耗类型选择
//        String provider = locationManager.getBestProvider(crite, true);

//        String networkProvider = LocationManager.NETWORK_PROVIDER;
//        String gpsProvider = LocationManager.GPS_PROVIDER;
//        String passiveProvider = LocationManager.PASSIVE_PROVIDER;

            //添加地理围栏
//        locationManager.addProximityAlert(38.234, 114.234, 5, -1, PendingIntent.getBroadcast(this, 1, new Intent(), 3));
//        可以设置一个区域，当进入或离开这个区域的时候会收到通知，前两个参数指定一个点，第三个参数是半径，第四个参数是超时时间，设置为-1表示不存在超时，最后一个是广播接收器。
//        触发的Intent将使用键KEY_PROXIMITY_ENTERING，如果值为true，则设备进入邻近区域，如果是false，说明设备离开该区域。

            //持续定位：
            //绑定监听，有4个参数
            //参数1，设备：有GPS_PROVIDER、NETWORK_PROVIDER、PASSIVE_PROVIDER  三种
            //参数2，位置信息更新周期，单位毫秒
            //参数3，位置变化最小距离：当位置距离变化超过此值时，将更新位置信息
            //参数4，监听
            //备注：参数2和3，如果参数3不为0，则以参数3为准；参数3为0，则通过时间来定时更新；两者为0，则随时刷新
            // 1分钟更新一次，或最小位移变化超过1米更新一次；
            locationManager.requestLocationUpdates(provider, 60 * 1000, 0, locationListener, handler.getLooper());

            //超时结束定位
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    locationManager.removeUpdates(locationListener);
                    handler.removeCallbacks(this);
                    if (!endlistenerFlag) {
                        onFailureListener();
                    }
                }
            }, TIME_OUT);
        }

        /**
         * 定位监听
         */
        private final LocationListener locationListener = new LocationListener() {
            /**
             * 位置信息变化时触发
             * 当位置发生改变后就会回调该方法，经纬度相关信息存在Location里面；
             */
            @Override
            public void onLocationChanged(Location location) {
                //赋值为true
                endlistenerFlag = true;
                if (null != locationManager) {
                    //解除注册监听 结束定位
                    locationManager.removeUpdates(this);
                }
                String time = DateUtils.formatDateTime(App.context,location.getTime(), 0);
                final double longitude = location.getLongitude();
                final double latitude = location.getLatitude();
                double altitude = location.getAltitude();
                double longitudeGd = location.getLongitude() + 0.0052719116;//经度 系统定位相对于高德定位坐标相差0.0052719116
                double latitudeGd = location.getLatitude() + 0.0010604858;//纬度 系统定位相对于高德定位坐标相差0.0010604858

                //获取地理位置
                String locationAddr = getLocationAddr(longitude, latitude);
                //获取高德经纬度地址
                String locationAddrGd = getLocationAddr(longitudeGd, latitudeGd);



                if (TextUtils.isEmpty(locationAddr)) {
                    //失败回调
                    onFailureListener();
                } else {
                    //成功回调
                    onSuccessListener(locationManager, location, longitudeGd, latitudeGd, locationAddr);
                }
            }

            /**
             * GPS状态变化时触发
             * 我们所采用的provider状态改变时会回调
             */
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                switch (status) {
                    //GPS状态为可见时
                    case LocationProvider.AVAILABLE:
                        Log.d(TAG, "当前GPS状态为可见状态 provider可用");
                        break;
                    //GPS状态为服务区外时
                    case LocationProvider.OUT_OF_SERVICE:
                        Log.d(TAG, "当前GPS状态为服务区外状态 无服务");
                        break;
                    //GPS状态为暂停服务时
                    case LocationProvider.TEMPORARILY_UNAVAILABLE:
                        Log.d(TAG, "当前GPS状态为暂停服务状态 provider不可用");
                        break;
                    default:
                        break;
                }
            }

            /**
             * GPS开启时触发
             * 当provider可用时被触发，比如定位模式切换到了使用精确位置时GPSProvider就会回调该方法；
             */
            @Override
            public void onProviderEnabled(String provider) {

            }

            /**
             * GPS禁用时触发
             * 当provider不可用时被触发，比如定位模式切换到了使用使用网络定位时GPSProvider就会回调该方法；
             */
            @Override
            public void onProviderDisabled(String proider) {

            }
        };

        /**
         * 检查权限
         *
         * @return
         */
        private boolean checkPermission() {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return false;
            }
            return true;
        }


        /**
         * 手机基站信息定位 不准确
         */

        /**
         * 获取网络位置
         *
         * @param longitude
         * @param latitude
         */

        /**
         * 获取地理位置
         *
         * @param longitude
         * @param latitude
         * @return
         */
        private String getLocationAddr(double longitude, double latitude) {
            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
            boolean flag = Geocoder.isPresent();
            if (!flag) {
                return "";
            }

//        locality(地址位置) 属性
//        并且有featureName(地址要素)
//        比如国家(countryName)
//        邮编(postalCode)
//        国家编码(countryCode)
//        省份(adminArea)
//        二级省份(subAdminArea)
//        二级城市(subLocality)
//        道路(thoroughfare) 等；

            //  getFromLocationName()：返回描述地理位置信息的集合，maxResults是返回地址的数目，建议使用1-5；
//        List<Address> addresses = geocoder.getFromLocationName("西二旗", 1);
//        if (addresses.size() > 0) {
//            //返回当前位置，精度可调
//            Address address = addresses.get(0);
//            if (address != null) {
//                Log.e("gzq", "sAddress：" + address.getLatitude());
//                Log.e("gzq", "sAddress：" + address.getLongitude());
//            }
//        }

            try {
                //根据经纬度返回对应的地理位置信息，参数maxResults表示返回地址的数目，建议使用1-5；
                List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                if (addresses.size() == 0) {
                    return "";
                }
                Address address = addresses.get(0);
                if (null == address) {
                    return "";
                }

                //获取最大地址行索引
                int maxAddressLineIndex = address.getMaxAddressLineIndex();
                //循环打印周围位置地址
                for (int i = 0; i < maxAddressLineIndex; i++) {
                    String addressStr = address.getAddressLine(i);
                }

                StringBuilder addressBuilder = new StringBuilder();
                String addressLine = address.getLocality();
                String addressLine1 = address.getAddressLine(1);
                if (null != addressLine) {
                    addressBuilder.append(addressLine);
                }
                if (null != addressLine1) {
                    addressBuilder.append("靠近").append(addressLine1);
                }
                return addressBuilder.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "";
        }

        public interface LocationManagerListener {
            /**
             * 定位成功数据回调
             */
            void onSuccessLocationListener(LocationManager manager, Location location, double longitude, double latitude, String locationAddr);

            /**
             * 定位失败回调
             */
            void onFailureLocationListener();
        }

        private void onSuccessListener(LocationManager manager, Location location, double longitude, double latitude, String locationAddr) {
            if (null != listener) {
                listener.onSuccessLocationListener(manager, location, longitude, latitude, locationAddr);
            }
        }

        private void onFailureListener() {
            if (null != listener) {
                listener.onFailureLocationListener();
            }
        }

    }


