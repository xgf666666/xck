package com.example.xck.utils;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class TimeUtil {
    private static boolean isStopWebsiteDatetimeThread;

    /**
     *
     * @return 当前时间戳
     */
    public static long getCurrentTimeStamp(){
        return System.currentTimeMillis();
    }

    public static String getCurrentTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        return formatter.format(curDate);
    }

    public static String getNowTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());
        return formatter.format(curDate);
    }

    public static String getCurrentFileNameTime() {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    }

    public static String getCurrentFileNameTimeMillis() {
        Date date = new Date();
        return new SimpleDateFormat("yyyyMMddHHmmssSSS").format(date);
    }

    //获得时、分
    public static String getSysHMTime() {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(System.currentTimeMillis());
    }

    //获得时、分
    public static String getHMTimes(long time) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(time);
    }

    //获得分、秒
    public static String getMSTimes(long time) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        return format.format(time);
    }

    //获得时、分、秒
    public static String getSMSTimes(long times) {
//        long times=time/1000;
        long hours = times / (60 * 60);
        long minutes = (times - hours * ( 60 * 60)) / 60;
        long s=(times - hours * (60 * 60)-minutes*60);
        String diffTime = "";
        if (minutes < 10) {
            diffTime = hours + ":0" + minutes;
        } else {
            diffTime = hours + ":" + minutes;
        }
        if (s<10){
            diffTime= diffTime+":0" +s;
        }else {
            diffTime= diffTime+":" +s;
        }
        return diffTime;


    }

    //获得年
    public static int getYears() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        String str = df.format(date);
        return Integer.valueOf(str.substring(0, 4));
    }
	public static String getHMSTimes(long time){
		SimpleDateFormat format=new SimpleDateFormat("HH:mm:ss");
		return format.format(time);
	}

	/**
	 * strTime要转换的string类型的时间，formatType要转换的格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日
	 HH时mm分ss秒，
	 strTime的时间格式必须要与formatType的时间格式相同
	 */
	public static Date stringToDate(String strTime, String formatType)
			throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat(formatType);
		Date date = null;
		date = formatter.parse(strTime);
		return date;
	}
	/**
	 * formatType格式为yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
	 data Date类型的时间
	 */
	public static String dateToString(Date data, String formatType) {
		return new SimpleDateFormat(formatType).format(data);
	}
	// currentTime要转换的long类型的时间
	// formatType要转换的string类型的时间格式
	public static String longToString(long currentTime, String formatType) {
		Date date = null; // long类型转成Date类型
		try {
			date = longToDate(currentTime, formatType);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String strTime = dateToString(date, formatType); // date类型转成String
		return strTime;
	}
	// currentTime要转换的long类型的时间
	// formatType要转换的时间格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
	public static Date longToDate(long currentTime, String formatType)
			throws ParseException {
		Date dateOld = new Date(currentTime); // 根据long类型的毫秒数生命一个date类型的时间
		String sDateTime = dateToString(dateOld, formatType); // 把date类型的时间转换为string
		Date date = stringToDate(sDateTime, formatType); // 把String类型转换为Date类型
		return date;
	}

    //获得月
    public static int getMonth(long timeStr) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = df.format(timeStr);
        return Integer.valueOf(str.substring(5, 7));
    }

    //获得
    public static String getDate(long timeStr) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = df.format(timeStr);
        return str;
    }
    public static String formatConversationTime(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String time = sdf.format(new Date(timestamp));

        if (isToday(timestamp)) {
            return time;
        } else if (isYesterday(timestamp)) {
            return "昨天 " + time;
        } else if (isYear(timestamp)){
            sdf.applyPattern("MM-dd");
            return sdf.format(new Date(timestamp));
        }
        else {
            sdf.applyPattern("yyyy-MM-dd");
            return sdf.format(new Date(timestamp));
        }
    }

    public static boolean isToday(long timestamp) {
        Calendar today = Calendar.getInstance();
        Calendar otherDay = Calendar.getInstance();
        otherDay.setTimeInMillis(timestamp);
        return today.get(Calendar.YEAR) == otherDay.get(Calendar.YEAR)
                && today.get(Calendar.MONTH) == otherDay.get(Calendar.MONTH)
                && today.get(Calendar.DAY_OF_MONTH) == otherDay.get(Calendar.DAY_OF_MONTH);
    }
    public static boolean isYear(long timestamp) {
        Calendar now = Calendar.getInstance();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp);

        // 判断时间是否是当天
        return  (now.get(Calendar.YEAR) == calendar.get(Calendar.YEAR)
                &&( now.get(Calendar.MONTH) != calendar.get(Calendar.MONTH)
                || now.get(Calendar.DAY_OF_MONTH) != calendar.get(Calendar.DAY_OF_MONTH)));



    }

    public static boolean isYesterday(long timestamp) {
        Calendar yesterday = Calendar.getInstance();
        yesterday.add(Calendar.DAY_OF_MONTH, -1);
        Calendar otherDay = Calendar.getInstance();
        otherDay.setTimeInMillis(timestamp);
        return yesterday.get(Calendar.YEAR) == otherDay.get(Calendar.YEAR)
                && yesterday.get(Calendar.MONTH) == otherDay.get(Calendar.MONTH)
                && yesterday.get(Calendar.DAY_OF_MONTH) == otherDay.get(Calendar.DAY_OF_MONTH);
    }

    public static String formatDate(long timeInMillis) {
        Calendar now = Calendar.getInstance();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMillis);

        // 判断时间是否是当天
        if (now.get(Calendar.YEAR) == calendar.get(Calendar.YEAR)
                && now.get(Calendar.MONTH) == calendar.get(Calendar.MONTH)
                && now.get(Calendar.DAY_OF_MONTH) == calendar.get(Calendar.DAY_OF_MONTH)) {
            // 如果是当天时间，显示时分
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
            return sdf.format(new Date(timeInMillis));
        } else {
            // 如果不是当天时间，显示年月日时分
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
            return sdf.format(new Date(timeInMillis));
        }
    }

	private static String format;
	/**
	 * 获取指定网站的日期时间
	 */
	public static String getWebsiteDatetime(final String webUrl){
		new Thread(new Runnable() {
			@Override
			public void run() {
				if(isStopWebsiteDatetimeThread) return;
				try {
					URL url = new URL(webUrl);// 取得资源对象
					URLConnection uc = url.openConnection();// 生成连接对象
					uc.connect();// 发出连接
					long ld = uc.getDate();// 读取网站日期时间
					Date date = new Date(ld);// 转换为标准时间对象
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");// 输出北京时间
					format = sdf.format(date);
				}catch (IOException e){
					e.printStackTrace();
					format = null;
				}
			}
		}).start();
		return format;
	}

    public static void stopWebsiteDatetimeThread(boolean mIsStopWebsiteDatetimeThread) {
        isStopWebsiteDatetimeThread = mIsStopWebsiteDatetimeThread;
    }

    public static long getFormFormatTime(String date) {
        if (date==null||date.isEmpty()) return 0;
//		String date = "2001-03-15 15-37-05";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//24小时制
        long time = 0;
        try {
            time = simpleDateFormat.parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    /**
     * 显示差时间
     *
     * @param sdate
     * @return
     */
    public static String haveDoneTime(String sdate) {
        if (sdate == null) {
            return "Unknown";
        }
        long formFormatTime = getFormFormatTime(sdate);
        long formFormatTime1 = getFormFormatTime(getCurrentTime());

        long l = formFormatTime1 - formFormatTime;
        Date date = new Date(l);// 转换为标准时间对象
        int l1 = (int) (l / 3600000);
        SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
        return l1 + ":" + sdf.format(date);
    }

    /**
     * 把文件日期换乘unix时间戳
     *
     * @param strDate
     * @return
     */
    public static String getFileTimestamp(String strDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return Long.toString(date.getTime() / 1000);
    }

    /**
     * 比较两个时间大小
     * @param date
     * @param anotherDate
     * @return
     * @throws ParseException
     */
    public static int compareToWholeTime(String date, String anotherDate) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date sd = df.parse(date);
        Date anotherSd = df.parse(anotherDate);
        return sd.compareTo(anotherSd);
    }
}