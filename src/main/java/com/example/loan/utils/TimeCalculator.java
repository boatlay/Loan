package com.example.loan.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;

public class TimeCalculator {

    /**
     * 计算指定LocalDateTime加一个月后的到期时间
     * @param startDateTime 起始日期时间（如2026-01-31 14:30:25）
     * @return 到期日期时间（日期按规则调整，时分秒不变）
     */
    public static LocalDateTime toOneMonthNextDateTime(LocalDateTime startDateTime) {
        if (startDateTime == null) {
            throw new IllegalArgumentException("起始日期时间不能为空");
        }

        //提取日期部分单独处理
        LocalDate startDate = startDateTime.toLocalDate();
        //计算加一个月后的目标日期
        LocalDate candidateDate = startDate.plusMonths(1);
        //校验：若对日不存在则取次月最后一天
        if (candidateDate.getDayOfMonth() != startDate.getDayOfMonth()) {
            candidateDate = candidateDate.with(TemporalAdjusters.lastDayOfMonth());
        }

        //把处理后的日期和原有的时分秒合并，得到最终的LocalDateTime
        return LocalDateTime.of(
                candidateDate.getYear(),
                candidateDate.getMonth(),
                candidateDate.getDayOfMonth(),
                startDateTime.getHour(),
                startDateTime.getMinute(),
                startDateTime.getSecond(),
                startDateTime.getNano()
        );
    }

    /**
     * 现在距离计划规定是否逾期
     * @param endTime 上一次已支付的计划中的截止时间
     * @return 逾期返回true，反之返回false
     */
    public static boolean isOverdue(LocalDateTime endTime){
        if (endTime == null) {
            throw new IllegalArgumentException("截止时间endTime不能为空");
        }

        //若当前时间比上一次已支付的计划中的截止时间还要大一个月还多，则现在支付属于逾期
        return LocalDateTime.now().isAfter(toOneMonthNextDateTime(endTime));
    }

    /**
     * 计算targetTime到当前时间的天数，小数部分无论多少都进1
     * @param targetTime 目标时间
     * @return 向上取整后的天数
     */
    public static long calNumToTargetDay(LocalDateTime targetTime) {
        // 1. 获取当前时间（系统默认时区）
        LocalDateTime now = LocalDateTime.now();

        // 2. 转换为毫秒数（需绑定时区，LocalDateTime本身无时区）
        ZoneId zoneId = ZoneId.systemDefault();
        long targetMillis = targetTime.atZone(zoneId).toInstant().toEpochMilli();
        long nowMillis = now.atZone(zoneId).toInstant().toEpochMilli();

        // 3. 计算时间差（毫秒），处理目标时间晚于当前时间的情况（返回0）
        long diffMillis = Math.max(0, nowMillis - targetMillis);

        // 4. 转换为天数（1天=86400000毫秒），得到带小数的天数
        double daysWithDecimal = (double) diffMillis / 86400000D;

        // 5. 向上取整（核心：3.1→4，3.0→3，0.5→1）
        return (long) Math.ceil(daysWithDecimal);
    }
}
