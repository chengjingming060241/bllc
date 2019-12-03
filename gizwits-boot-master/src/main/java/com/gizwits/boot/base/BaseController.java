package com.gizwits.boot.base;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gizwits.boot.exceptions.SysExceptionEnum;
import com.gizwits.boot.exceptions.SystemException;
import org.springframework.core.MethodParameter;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by rongmc on 16/3/22.
 */
@Controller
@ControllerAdvice
public class BaseController {

    protected <T> ResponseObject<T> success(T dataObject) {
        return new ResponseObject<>(dataObject);
    }

    protected  ResponseObject success(){
        return  new ResponseObject();
    }

    @ExceptionHandler
    public @ResponseBody
    ResponseObject exceptionHandler(HttpServletRequest request, HttpServletResponse httpResponse, Exception ex) {
        ResponseObject responseObject = new ResponseObject() ;
        ex.printStackTrace();
        try {

            if(ex instanceof SystemException) {//自定义异常提示
                SystemException  systemException = (SystemException) ex;
                responseObject.setMessage(systemException.getMessage());
                responseObject.setCode(systemException.getCode());
                return responseObject;
            }else if(ex instanceof MethodArgumentNotValidException){//数据验证错误提示
                responseObject.setCode(SysExceptionEnum.ILLEGAL_PARAM.getCode());
                String message = ex.getMessage() ;
                int start = message.lastIndexOf("default message");
                message = message.substring(start,message.length())//截取部分中文提示信息
                        .replace("default message","").replace("]]","]");//结尾处理

                MethodParameter methodParameter=((MethodArgumentNotValidException) ex).getParameter();
//                String className ="," +methodParameter.getParameterType().getTypeName()+",";
                responseObject.setMessage(SysExceptionEnum.ILLEGAL_PARAM.getMessage()
                        +message);
                return responseObject;
            }else if( ex instanceof DataAccessException){//数据库操作异常
                responseObject.setMessage(SysExceptionEnum.SQL_ERROR.getMessage());
                responseObject.setCode(SysExceptionEnum.SQL_ERROR.getCode());
                return responseObject;
            }else{
                responseObject.setMessage(SysExceptionEnum.INTERNAL_ERROR.getMessage());
                responseObject.setCode(SysExceptionEnum.INTERNAL_ERROR.getCode());
                return responseObject;
            }
        } catch (Exception e) {//其他异常
            responseObject.setMessage(SysExceptionEnum.INTERNAL_ERROR.getCode());
            responseObject.setCode(SysExceptionEnum.INTERNAL_ERROR.getMessage());
            return responseObject;
        }

    }

}
