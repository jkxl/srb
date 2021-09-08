package com.atguigu.common.exception;

import com.atguigu.common.result.R;
import com.atguigu.common.result.ResponseEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Component //Spring容易自动管理
 * @RestControllerAdvice //在controller层添加通知。如果使用@ControllerAdvice，则方法上需要添加@ResponseBody
 * @program: srb
 * @description: 统一异常处理器
 * @author: jiankai
 * @create: 2021-09-08 15:34
 **/
@Slf4j
@Component
@RestControllerAdvice
public class UnifiedExceptionHandler {
    /**
     * 未定义异常：当controller中抛出Exception，则捕获
     */
    @ExceptionHandler(value = Exception.class)
    public R handleException(Exception e) {
        log.error(e.getMessage(), e);
        return R.error();
    }

    /**
     * 特定异常: sql语法错误
     */
    @ExceptionHandler(BadSqlGrammarException.class)
    public R handleBadSqlGrammarException(BadSqlGrammarException e){
        log.error(e.getMessage(), e);
        return R.setResult(ResponseEnum.BAD_SQL_GRAMMAR_ERROR);
    }

    /**
     * 自定义异常
     */
    @ExceptionHandler(BusinessException.class)
    public R handleBusinessException(BusinessException e){
        log.error(e.getMessage(), e);
        return R.error().message(e.getMessage()).code(e.getCode());
    }
}
