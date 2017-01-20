package com.zyx.admin.service.system;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Maps;
import com.zyx.admin.common.utils.DateUtils;
import com.zyx.admin.common.utils.security.Digests;
import com.zyx.admin.common.utils.security.Encodes;
import com.zyx.admin.domain.User;
import com.zyx.admin.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by zhangyuanxin on 2017/1/16.
 */
@Service
public class UserService {
    /**加密方法*/
    public static final String HASH_ALGORITHM = "SHA-1";
    public static final int HASH_INTERATIONS = 1024;
    private static final int SALT_SIZE = 8;	//盐长度

    @Autowired
    private UserMapper userMapper;


    /**
     * 保存用户
     * @param user
     */
    @Transactional(readOnly=false)
    public void save(User user) {
        entryptPassword(user);
        user.setCreateDate(DateUtils.getSysTimestamp());
        userMapper.add(user);
    }


    public Page<User> findAllByPage(PageRequest pageRequest, Map<String, Object> map) {
        PageHelper.startPage(pageRequest.getPageNumber(), pageRequest.getPageSize());
        return userMapper.findAll(map);
    }

    public List<User> findAll(Map<String, Object> map) {
        return userMapper.findAll(map);
    }

    public int update(User user) {
        return userMapper.update(user);
    }

    /**
     * 修改密码
     * @param user
     */
    @Transactional(readOnly=false)
    public void updatePwd(User user) {
        entryptPassword(user);
        userMapper.update(user);
    }

    /**
     * 删除用户
     * @param id
     */
    @Transactional(readOnly=false)
    public void delete(Integer id){
        if(!isSupervisor(id)){
            Map<String, Object> map = Maps.newLinkedHashMap();
            userMapper.del(map);

        }

    }

    /**
     * 按登录名查询用户
     * @param loginName
     * @return 用户对象
     */
    public User getValidUser(String loginName) {
        //return userDao.findUnique(nameCriterion, statusCriterion);
        Map<String, Object> map = Maps.newLinkedHashMap();
        map.put("loginName", loginName);
        return userMapper.findAll(map).get(0);
    }


    public User getUser(String loginName) {
        Map<String, Object> map = Maps.newLinkedHashMap();
        map.put("loginName", loginName);
        return userMapper.findAll(map).get(0);
    }

    /**
     * 判断是否超级管理员
     * @param id
     * @return boolean
     */
    private boolean isSupervisor(Integer id) {
        return id == 1;
    }

    /**
     * 设定安全的密码，生成随机的salt并经过1024次 sha-1 hash
     */
    private void entryptPassword(User user) {
        byte[] salt = Digests.generateSalt(SALT_SIZE);
        user.setSalt(Encodes.encodeHex(salt));

        byte[] hashPassword = Digests.sha1(user.getPlainPassword().getBytes(),salt, HASH_INTERATIONS);
        user.setPassword(Encodes.encodeHex(hashPassword));
    }

    /**
     * 验证原密码是否正确
     * @param user
     * @return
     */
    public boolean checkPassword(User user,String oldPassword){
        byte[] salt =Encodes.decodeHex(user.getSalt()) ;
        byte[] hashPassword = Digests.sha1(oldPassword.getBytes(),salt, HASH_INTERATIONS);
        if(user.getPassword().equals(Encodes.encodeHex(hashPassword))){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 修改用户登录
     * @param user
     */
    public void updateUserLogin(User user){
        user.setLoginCount((user.getLoginCount()==null?0:user.getLoginCount())+1);
        user.setPreviousVisit(user.getLastVisit());
        user.setLastVisit(DateUtils.getSysTimestamp());
//        update(user);
    }

}
