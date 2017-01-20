/*
 * Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zyx.admin;

import com.alibaba.druid.support.http.StatViewServlet;
import com.google.code.kaptcha.servlet.KaptchaServlet;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.Filter;
import javax.servlet.Servlet;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {HibernateJpaAutoConfiguration.class})
public class AdminWebApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(AdminWebApplication.class);
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(AdminWebApplication.class, args);
	}


	@Bean(name="characterFilter")
	public Filter characterEncodingFilter() {

		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");
		characterEncodingFilter.setForceEncoding(true);

		return characterEncodingFilter;
	}



//    @Bean
//    public FilterRegistrationBean openSessionFilter() {
//        OpenSessionInViewFilter openSessionInViewFilter = new OpenSessionInViewFilter();
//        openSessionInViewFilter.setSessionFactoryBeanName("sessionFactory");
//        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
//        filterRegistrationBean.setFilter(openSessionInViewFilter);
//        filterRegistrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE -1);
//        return filterRegistrationBean;
//
//    }


//    @Bean(name="ajaxFilter")
//    public FilterRegistrationBean ajaxFilter() {
//        AjaxFilter ajaxFilter = new AjaxFilter();
//        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
//        filterRegistrationBean.setFilter(ajaxFilter);
//        filterRegistrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE -2);
//        return filterRegistrationBean;
//    }

	@Bean
	public FilterRegistrationBean delegatingFilterProxy(){
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		DelegatingFilterProxy proxy = new DelegatingFilterProxy();
		proxy.setTargetFilterLifecycle(true);
		proxy.setTargetBeanName("shiroFilter");
		filterRegistrationBean.setFilter(proxy);
		return filterRegistrationBean;
	}


	@Bean(name="druidServlet")
	public Servlet druidServlet() {
		return new StatViewServlet();
	}

	@Bean
	public ServletRegistrationBean druidServletResistrationBean(@Qualifier("druidServlet") Servlet servlet) {
		ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean();
		servletRegistrationBean.setServlet(servlet);
		List<String> list = new ArrayList<>();
		list.add("/druid/*");
		servletRegistrationBean.setUrlMappings(list);
		return servletRegistrationBean;
	}


	@Bean(name="kaptchaServlet")
	public Servlet kaptchaServlet() {
		return new KaptchaServlet();
	}

	@Bean
	public ServletRegistrationBean kaptchaServletResistrationBean(@Qualifier("kaptchaServlet") Servlet servlet) {
		ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean();
		servletRegistrationBean.setServlet(servlet);
		List<String> list = new ArrayList<>();
		list.add("/static/images/kaptcha.jpg");
		servletRegistrationBean.setUrlMappings(list);
		return servletRegistrationBean;
	}



}
