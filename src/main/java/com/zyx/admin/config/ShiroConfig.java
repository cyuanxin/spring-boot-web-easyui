package com.zyx.admin.config;

import com.zyx.admin.service.system.UserRealm;
import com.zyx.admin.system.utils.FormAuthenticationCaptchaFilter;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by layne.luo on 2016/8/24.
 */
@Configuration
public class ShiroConfig {


    @Bean(name="shiroEhcacheManager")
    public EhCacheManager ehCacheManager() {
        EhCacheManager ehCacheManager = new EhCacheManager();
        ehCacheManager.setCacheManagerConfigFile("classpath:cache/ehcache-shiro.xml");
        return ehCacheManager;
    }


    @Bean(name="securityManager")
    public DefaultWebSecurityManager securityManager(@Qualifier("userRealm")UserRealm realm, @Qualifier("shiroEhcacheManager") EhCacheManager ehCacheManager) {

        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(realm);
        securityManager.setCacheManager(ehCacheManager);
        return securityManager;
    }

    @Bean(name="myCaptchaFilter")
    public FormAuthenticationCaptchaFilter captchaFilter() {
        return new FormAuthenticationCaptchaFilter();
    }


    @Bean(name="shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(@Qualifier("securityManager") DefaultWebSecurityManager securityManager,
                                              @Qualifier("myCaptchaFilter") Filter myCaptchaFilter) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setLoginUrl("/a/login");
        shiroFilterFactoryBean.setSuccessUrl("/a");

        Map<String, Filter> maps = new HashMap<>();
        maps.put("authc", myCaptchaFilter);
        shiroFilterFactoryBean.setFilters(maps);


        Map<String, String> chainDefinitions = new HashMap<>();
        chainDefinitions.put("/static/**","anon");
        chainDefinitions.put("a/login","authc");
        chainDefinitions.put("a/**","user");
        chainDefinitions.put("/rest/**","authcBasic");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(chainDefinitions);

        return shiroFilterFactoryBean;

    }


    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    @ConditionalOnMissingBean
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator();
        daap.setProxyTargetClass(true);
        return daap;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor aasa = new AuthorizationAttributeSourceAdvisor();
        aasa.setSecurityManager(securityManager);
        return new AuthorizationAttributeSourceAdvisor();
    }



}


