/*
 * Copyright 1999-2004 Alibaba.com All right reserved. This software is the confidential and proprietary information of
 * Alibaba.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package org.springweb.core;
import java.io.IOException;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
/**
 * 可以按照不同的运行模式启用相应的配置
 * 
 * @author guotao.zhaogt 2011-4-2 下午04:27:12
 */
public class MutilPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer implements InitializingBean {
	
    private static final String PRODUCTION_MODE = "production.mode";
    // 缓存所有的属性配置
    private Properties          properties;
    /**
     * @return the mode
     */
    public String getMode() {
        return properties.getProperty(PRODUCTION_MODE);
    }
    @Override
    protected Properties mergeProperties() throws IOException {
        Properties mergeProperties = super.mergeProperties();
        // 根据路由原则，提取最终生效的properties
        this.properties = new Properties();
        // 获取路由规则,系统属性设置mode优先
        String mode = System.getProperty(PRODUCTION_MODE);
        if (StringUtils.isEmpty(mode)) {
            String str = mergeProperties.getProperty(PRODUCTION_MODE);
            mode = str != null ? str : "ONLINE";
        }
        properties.put(PRODUCTION_MODE, mode);
        String[] modes = mode.split(",");
        Set<Entry<Object, Object>> es = mergeProperties.entrySet();
        for (Entry<Object, Object> entry : es) {
            String key = (String) entry.getKey();
            int idx = key.lastIndexOf('_');
            String realKey = idx == -1 ? key : key.substring(0, idx);
            if (!properties.containsKey(realKey)) {
                Object value = null;
                for (String md : modes) {
                    value = mergeProperties.get(realKey + "_" + md);
                    if (value != null) {
                        properties.put(realKey, value);
                        break;
                    }
                }
                if (value == null) {
                    value = mergeProperties.get(realKey);
                    if (value != null) {
                        properties.put(realKey, value);
                    } else {
                        throw new RuntimeException("impossible empty property for " + realKey);
                    }
                }
            }
        }
        return properties;
    }
    /**
     * 开放此方法给需要的业务
     * 
     * @param key
     * @return
     */
    public String getProperty(String key) {
        return resolvePlaceholder(key, properties);
    }
	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub	
	}
}
