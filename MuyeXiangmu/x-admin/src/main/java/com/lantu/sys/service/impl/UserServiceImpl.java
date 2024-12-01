package com.lantu.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lantu.common.utils.JwtUtil;
import com.lantu.sys.entity.Menu;
import com.lantu.sys.entity.User;
import com.lantu.sys.entity.UserRole;
import com.lantu.sys.mapper.UserMapper;
import com.lantu.sys.mapper.UserRoleMapper;
import com.lantu.sys.service.IMenuService;
import com.lantu.sys.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Muye
 * @since 2024-10-01
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Resource
    private UserRoleMapper userRoleMapper;


    @Override
    public Map<String, Object> login(User user) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername,user.getUsername());
        User loginUser = this.baseMapper.selectOne(wrapper);
        if(loginUser != null && passwordEncoder.matches(user.getPassword(),loginUser.getPassword())){
//            String key = "user::" + UUID.randomUUID();
            loginUser.setPassword(null);

            String token = jwtUtil.createToken(loginUser);

//            redisTemplate.opsForValue().set(key,loginUser,30, TimeUnit.MINUTES);
            Map<String,Object> data = new HashMap<>();
            data.put("token",token);
            return data;
        }
        return null;
    }

//    @Override
//    public Map<String, Object> login(User user) {
//        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
//        wrapper.eq(User::getUsername,user.getUsername());
//        wrapper.eq(User::getPassword,user.getPassword());
//        User loginUser = this.baseMapper.selectOne(wrapper);
//        if(loginUser != null){
//            String key = "user::" + UUID.randomUUID();
//            loginUser.setPassword(null);
//            redisTemplate.opsForValue().set(key,loginUser,30, TimeUnit.MINUTES);
//            Map<String,Object> data = new HashMap<>();
//            data.put("token",key);
//            return data;
//        }
//        return null;
//    }

    @Override
    public Map<String, Object> getUserInfo(String token) {
//      Object obj = redisTemplate.opsForValue().get(token);
        User loginUser = null;
        try {
            loginUser = jwtUtil.parseToken(token, User.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(loginUser != null){
            //User user = JSON.parseObject(JSON.toJSONString(obj),User.class);
            Map<String, Object> data =  new HashMap<>();
            data.put("name",loginUser.getUsername());
            data.put("avatar",loginUser.getAvatar());

            //角色
            List<String> roleList = this.getBaseMapper().getRoleNamesByUserId(loginUser.getId());
            data.put("roles", roleList);

            //权限列表
            //List<Menu> menuList = menuService.getMenuListByUserId(loginUser.getId());
            //data.put("menuList", menuList);


            return data;
        }
        return null;
    }

    @Override
    public void logout(String token) {
        //redisTemplate.delete(token);
    }

    @Override
    @Transactional
    public void addUser(User user) {
        this.baseMapper.insert(user);
        List<Integer> roleIdList = user.getRoleIdList();
        if(roleIdList != null){
            for(Integer roleId : roleIdList){
                userRoleMapper.insert(new UserRole(null, user.getId(),roleId));
            }
        }

    }

    @Override
    public User getUserById(Integer id) {
        User user = this.baseMapper.selectById(id);
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRole::getUserId,id);
        List<UserRole> userRoleList = userRoleMapper.selectList(wrapper);

        List<Integer> roleIdList = userRoleList.stream()
                .map(userRole -> {
                    return userRole.getRoleId();
                })
                .collect(Collectors.toList());
        user.setRoleIdList(roleIdList);

        return user;
    }

    @Override
    @Transactional
    public void updateUser(User user){
        this.baseMapper.updateById(user);
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRole::getUserId,user.getId());
        userRoleMapper.delete(wrapper);
        List<Integer> roleIdList = user.getRoleIdList();
        if(roleIdList != null){
            for(Integer roleId : roleIdList){
                userRoleMapper.insert(new UserRole(null, user.getId(),roleId));
            }
        }
    }

    @Override
    public void deleteUserById(Integer id){
        this.baseMapper.deleteById(id);
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRole::getUserId,id);
        userRoleMapper.delete(wrapper);
    }
}
