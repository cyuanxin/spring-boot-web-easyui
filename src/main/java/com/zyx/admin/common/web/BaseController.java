package com.zyx.admin.common.web;

import com.google.common.base.Splitter;
import com.google.common.collect.Maps;
import com.zyx.admin.common.persistence.Page;
import com.zyx.admin.common.persistence.PropertyFilter;
import com.zyx.admin.common.utils.DateUtils;
import com.zyx.admin.common.utils.StringUtils;
import com.zyx.admin.domain.User;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.ibatis.io.Resources;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.servlet.http.HttpServletRequest;
import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 基础控制器
 * 其他控制器继承此控制器获得日期字段类型转换和防止XSS攻击的功能
 *
 * @author ty
 * @description
 * @date 2014年3月19日
 */
public class BaseController {
    private static final Logger log = LoggerFactory.getLogger(BaseController.class);
    protected static final String SUCCESS = "success";
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // String类型转换，将所有传递进来的String进行HTML编码，防止XSS攻击
        binder.registerCustomEditor(String.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(text == null ? null : StringEscapeUtils.escapeHtml4(text.trim()));
            }

            @Override
            public String getAsText() {
                Object value = getValue();
                return value != null ? value.toString() : "";
            }
        });

        // Date 类型转换
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(DateUtils.parseDate(text));
            }
        });

        // Timestamp 类型转换
        binder.registerCustomEditor(Timestamp.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                Date date = DateUtils.parseDate(text);
                setValue(date == null ? null : new Timestamp(date.getTime()));
            }
        });
    }



    public Map<String, Object> getMapByRequest(HttpServletRequest httpServletRequest) {
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(httpServletRequest);
        Map<String, Object> maps = getMapByPropertyFilter(filters);
        return maps;
    }
    public Map<String, Object> getMapByPropertyFilter(List<PropertyFilter> filters) {
        if (filters.isEmpty()) {
            return Maps.newLinkedHashMap();
        }
        Map<String, Object> maps = Maps.newLinkedHashMap();
        for (PropertyFilter filter : filters) {
            maps.put(filter.getPropertyName(), filter.getMatchValue());
//            maps.put(filter.getPropertyName(), );
        }
        return maps;
    }

    public void handleListInMap(String listKey, Map<String, Object> maps) {
        String raw = (String) maps.get(listKey);
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(raw)) {
            List<String> subStrategys = Splitter.on(",").omitEmptyStrings().splitToList(raw);
            maps.put(listKey, subStrategys);
        }
    }

    public Session getShiroSession() {
        return SecurityUtils.getSubject().getSession();
    }

    private static final String USER_KEY = "user";

    public User getLoginUser() {
        return (User) getShiroSession().getAttribute(USER_KEY);
    }

    /**
     * 获取page对象
     *
     * @param request
     * @return page对象
     */
    public <T> Page<T> getPage(HttpServletRequest request) {
        int pageNo = 1;    //当前页码
        int pageSize = 20;    //每页行数
        String orderBy = "id";    //排序字段
        String order = "asc";    //排序顺序
        if (StringUtils.isNotEmpty(request.getParameter("page")))
            pageNo = Integer.valueOf(request.getParameter("page"));
        if (StringUtils.isNotEmpty(request.getParameter("rows")))
            pageSize = Integer.valueOf(request.getParameter("rows"));
        if (StringUtils.isNotEmpty(request.getParameter("sort")))
            orderBy = request.getParameter("sort").toString();
        if (StringUtils.isNotEmpty(request.getParameter("order")))
            order = request.getParameter("order").toString();
        return new Page<T>(pageNo, pageSize, orderBy, order);
    }

    /**
     * 获取easyui分页数据
     *
     * @param page
     * @return map对象
     */
    public <T> Map<String, Object> getEasyUIData(Page<T> page) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("rows", page.getResult());
        map.put("total", page.getTotalCount());
        return map;
    }

    public <T> Map<String, Object> getEasyUIData(com.github.pagehelper.Page page) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("rows", page.getResult());
        map.put("total", page.getTotal());
        return map;
    }

    public final static String IMPORT_TEM_BASE_PATH = "/import/";
    public final static String IMPORT_XML_BASE_PATH = "/import/";


    public InputStream getImportConfigStream(String fileName) {
        try {
            return Resources.getResourceAsStream(IMPORT_XML_BASE_PATH.concat(fileName));
        } catch (IOException e) {
            log.error("getImportConfigStream" + e);
        }
        return null;
    }

}
