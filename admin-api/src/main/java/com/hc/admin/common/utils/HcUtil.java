package com.hc.admin.common.utils;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.hc.admin.authorization.JWTUtil;
import com.hc.admin.bean.User;
import com.hc.admin.common.function.CacheSelector;
import com.hc.admin.service.CacheService;
import com.hc.admin.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;

import java.util.function.Supplier;
import java.util.stream.IntStream;

/**
 * FEBS工具类
 */
@Slf4j
public class HcUtil {

    /**
     * 缓存查询摸板，先查缓存，如果缓存查询失败再从数据库查询
     *
     * @param cacheSelector    查询缓存的方法
     * @param databaseSelector 数据库查询方法
     * @return T
     */
    @SuppressWarnings("unchecked")
    public static <T> T selectCacheByTemplate(CacheSelector<?> cacheSelector, Supplier<?> databaseSelector) {
        try {
            // 先查 Redis缓存
            log.debug("query data from redis ······");
            return (T) cacheSelector.select();
        } catch (Exception e) {
            // 数据库查询
            log.error("redis error：", e);
            log.debug("query data from database ······");
            return (T) databaseSelector.get();
        }
    }

    /**
     * 获取当前操作用户
     *
     * @return 用户信息
     */
    public static User getCurrentUser() {
        String token = (String) SecurityUtils.getSubject().getPrincipal();
        String username = JWTUtil.getUsername(token);
        UserService userService = SpringContextUtil.getBean(UserService.class);
        CacheService cacheService = SpringContextUtil.getBean(CacheService.class);

        return selectCacheByTemplate(() -> cacheService.getUser(username), () -> userService.findByName(username));
    }

    /**
     * token 加密
     *
     * @param token token
     * @return 加密后的 token
     */
    public static String encryptToken(String token) {
        try {
            EncryptUtil encryptUtil = new EncryptUtil(HcEnum.TOKEN_CACHE_PREFIX.getValue());
            return encryptUtil.encrypt(token);
        } catch (Exception e) {
            log.info("token加密失败：", e);
            return null;
        }
    }

    /**
     * token 解密
     *
     * @param encryptToken 加密后的 token
     * @return 解密后的 token
     */
    public static String decryptToken(String encryptToken) {
        try {
            EncryptUtil encryptUtil = new EncryptUtil(HcEnum.TOKEN_CACHE_PREFIX.getValue());
            return encryptUtil.decrypt(encryptToken);
        } catch (Exception e) {
            log.info("token解密失败：", e);
            return null;
        }
    }

    /**
     * 驼峰转下划线
     *
     * @param value 待转换值
     * @return 结果
     */
    public static String camelToUnderscore(String value) {
        if (StringUtils.isBlank(value)) {
            return value;
        }
        String[] arr = StringUtils.splitByCharacterTypeCamelCase(value);
        if (arr.length == 0) {
            return value;
        }
        StringBuilder result = new StringBuilder();
        IntStream.range(0, arr.length).forEach(i -> {
            if (i != arr.length - 1) {
                result.append(arr[i]).append(StringPool.UNDERSCORE);
            } else {
                result.append(arr[i]);
            }
        });
        return StringUtils.lowerCase(result.toString());
    }

    /**
     * 字符串提取数字
     * @param str
     */

    public static long getNumber(String str) {
        str=str.trim();
        String outStr="";
        if(str != null && !"".equals(str)){
            for(int i=0;i<str.length();i++){
                if(str.charAt(i)>=48 && str.charAt(i)<=57){
                    outStr+=str.charAt(i);
                }
            }
        }
        Long res=Long.valueOf(outStr);
        return res;
    }

}
