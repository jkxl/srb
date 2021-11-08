package com.atguigu.srb.base.util;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
  * @description 获取唯一单号（基于redis），如果redis 集群,需设置自增歩长,使用命令设置
  *
  * @author jack
  * @date2020/9/8 10:07
  * @param
  * @return
  */
@Configuration
public class RedisFormNoGenerate {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
      * @description 获取唯一Id（202009080001）12位
      *
      * @author jack
      * @date2020/9/8 10:04
      * @param[key, delta]
      * @return java.lang.String
      */
    public String getFormNo(String key, Long delta) {
        try {
            // delta为空默认值1
            if (null == delta) {
                delta = 1L;
            }
            // 生成8位的时间戳（当天的日期）
            String timeStamp = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            // 获得redis-key
            String newKey = key + ":" + timeStamp;
            // 获取自增值（时间戳+自定义key）
            Long increment = redisTemplate.opsForValue().increment(newKey, delta);
            // 设置时间戳生成的key的有效期为2秒
            redisTemplate.expire(newKey, 1, TimeUnit.DAYS);
            // 获取订单号，时间戳 + 唯一自增Id( 4位数,不够补0)
            return timeStamp + String.format("%04d", increment);
        } catch (Exception e) {
            // redis 宕机时采用时间戳加随机数
            String timeStamp = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            Random random = new Random();
            //8位日期 + 4位随机数
            timeStamp += (random.nextInt(10) + "") + (random.nextInt(10) + "") + (random.nextInt(10) + "") + (random.nextInt(10) + "");
            return timeStamp;
        }
    }
}
