package com.example.message.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Author: ZZJ
 * @Description:
 * @Date: 2021/12/22
 */
public class DateUtils {

    static final int TOKEN_VALID_HOUR = 240;
    static final int CAPTCHA_VALID_MINUTE = 5;
    static final int INVITATION_VALID_MINUTE = 30;

    /**
     * 获取当前日期年月日时分秒yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String getDateTime() {
        SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        return simpleFormat.format(now);

    }

    //Date时间转指定时间格式字符串
    public static String currtimeToString14() {
        return dateToString(new Date(), "yyyyMMddHHmmss");
    }

    //Date时间转指定时间格式字符串
    public static String dateToString(Date date, String format) {
        SimpleDateFormat sf = new SimpleDateFormat(format);
        return sf.format(date);
    }

    /**
     * @Author: ZZJ
     * @Description: 生成数据库的token有效期时间
     * @Date: 2021/12/29
     */
    public static Date createTokenPeriod(){
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(new Date());
        rightNow.add(Calendar.HOUR_OF_DAY, TOKEN_VALID_HOUR);
        return rightNow.getTime();
    }

    /**
     * 生成邀请绑定链接的有效期时间
     * @return
     */
    public static Date createInvitationPeriod(){
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(new Date());
        rightNow.add(Calendar.MINUTE,INVITATION_VALID_MINUTE);
        return rightNow.getTime();
    }


    /**
     * @Author: ZZJ
     * @Description: 检查token时间检查
     * @Date: 2021/12/29
     * @return
     */
    public static boolean tokenIsValid(Date periodTime) throws ParseException {
        Calendar period = Calendar.getInstance();
        period.setTime(periodTime);
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(new Date());
        return period.compareTo(rightNow) > 0;
    }

    /**
     * @Author: ZZJ
     * @Description: 验证码时间检查
     * @Date: 2021/12/31
     * @return
     */
    public static boolean captchaIsValid(Date sendTime){
        Calendar period = Calendar.getInstance();
        period.setTime(sendTime);
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(new Date());
        //减去有效期和数据库内容作比较
        rightNow.add(Calendar.MINUTE,-CAPTCHA_VALID_MINUTE);
        return period.compareTo(rightNow) > 0;
    }

    public static boolean captchaIsValid(Date createTime, Integer validity) {
        Calendar period = Calendar.getInstance();
        period.setTime(createTime);
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(new Date());
        //减去有效期和数据库内容作比较
        rightNow.add(Calendar.HOUR_OF_DAY, -validity * 24);
        return period.compareTo(rightNow) > 0;
    }

    public static boolean InvitationIsValid(Date validTime){
        Calendar valid=Calendar.getInstance();
        valid.setTime(validTime);
        Calendar rightNow=Calendar.getInstance();
        rightNow.setTime(new Date());
        return valid.compareTo(rightNow)>0;
    }

    public static boolean UpgradeStrategyIsPush(Date pushTime){
        Calendar push = Calendar.getInstance();
        push.setTime(pushTime);
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(new Date());
        return rightNow.compareTo(push) > 0;
    }

    /*
    * 计算过了多少天
    * */
    public static double getDays(Date date){
        Calendar cal=Calendar.getInstance();
        cal.setTime(date);
        long oldTime=cal.getTimeInMillis();
        long nowTime=System.currentTimeMillis();
        double days=(double) (nowTime-oldTime)/(1000*60*60*24);
        return  days;
    }

    /*
     * 判断当前时间是否在范围时间内
     * */
    public static boolean isEffectiveDate(Date nowTime, Date startTime, Date endTime) {
        if (nowTime.getTime() == startTime.getTime()
                || nowTime.getTime() == endTime.getTime()) {
            return true;
        }

        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(startTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }

}
