package com.allen.imsystem.common;

import com.allen.imsystem.common.Const.GlobalConst;
import com.allen.imsystem.common.exception.BusinessException;
import com.allen.imsystem.common.exception.ExceptionType;
import com.allen.imsystem.common.utils.JWTUtil;
import com.allen.imsystem.common.utils.RedisUtil;
import com.allen.imsystem.service.impl.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component("defaultCacheHolder")
public class CacheHolder implements ICacheHolder {

    @Autowired
    private RedisService redisService;

    private static final Integer USER_LOGIN_EXPRIED = 60*60*3;

    @Override
    public String getUid(Object source) {
        HttpServletRequest request = (HttpServletRequest)source;
        String jwtToken = request.getHeader("token");
        if(jwtToken==null){
            throw new BusinessException(ExceptionType.NO_LOGIN_ERROR, "没有token信息，请登录");
        }
        String uid = JWTUtil.getMsgFromToken(jwtToken,"uid",String.class);
        return uid;
    }

    @Override
    public Integer getUserId(Object source) {
        HttpServletRequest request = (HttpServletRequest)source;
        String jwtToken = request.getHeader("token");
        if(jwtToken==null){
            throw new BusinessException(ExceptionType.NO_LOGIN_ERROR, "没有token信息，请登录");
        }
        Integer userId = JWTUtil.getMsgFromToken(jwtToken,"userId",Integer.class);
        return userId;
    }

    @Override
    public String getImageCode(String key) {
        if(key != null){
            return (String) redisService.get(GlobalConst.Redis.KEY_IMAGE_CODE+key);
        }
        return null;
    }

    @Override
    public boolean setImageCode(String imageCode, String key) {
        if(imageCode != null && key != null){
            return redisService.set(GlobalConst.Redis.KEY_IMAGE_CODE+key, imageCode, 5*60L);
        }
        return false;
    }

    @Override
    public void removeImageCode(String key) {
        if(key != null){
            redisService.del(GlobalConst.Redis.KEY_IMAGE_CODE+key);
        }
    }

    @Override
    public boolean setEmailCode(String emailCode, String key) {
        if(emailCode != null && key != null){
            return redisService.set(GlobalConst.Redis.KEY_EMAIL_CODE+key, emailCode, 20*60);
        }
        return false;
    }

    @Override
    public void removeEmailCode(String key) {
        if(key != null){
            redisService.del(GlobalConst.Redis.KEY_EMAIL_CODE+key);
        }
    }

    @Override
    public String getEmailCode(String key) {
        if(key != null){
            return (String) redisService.get(GlobalConst.Redis.KEY_EMAIL_CODE+key);
        }
        return null;
    }
}
