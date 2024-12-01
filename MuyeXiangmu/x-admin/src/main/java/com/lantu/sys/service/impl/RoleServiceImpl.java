package com.lantu.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lantu.sys.entity.Role;
import com.lantu.sys.entity.RoleMenu;
import com.lantu.sys.mapper.RoleMapper;
import com.lantu.sys.mapper.RoleMenuMapper;
import com.lantu.sys.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Muye
 * @since 2024-10-01
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Override
    @Transactional

    public void addRole(Role role) {

        this.baseMapper.insert(role);

        if(null != role.getMenuIdList()){
            for(Integer menuId : role.getMenuIdList()){
                roleMenuMapper.insert(new RoleMenu(null, role.getRoleId(),menuId));
            }
        }
    }
    @Override

    public Role getRoleById(Integer id){
        Role role = this.baseMapper.selectById(id);
        List<Integer> menuIdList = roleMenuMapper.getMenuIdListByRoleId(id);
        role.setMenuIdList(menuIdList);
        return role;
    }

    @Override
    @Transactional
    public void updateRole(Role role){
       this.baseMapper.updateById(role);
        LambdaQueryWrapper<RoleMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RoleMenu::getRoleId,role.getRoleId());
        roleMenuMapper.delete(wrapper);
        if(null != role.getMenuIdList()){
            for(Integer menuId : role.getMenuIdList()){
                roleMenuMapper.insert(new RoleMenu(null, role.getRoleId(),menuId));
            }
        }
    }

    @Override
    public void deleteRoleById(Integer id){
        this.baseMapper.deleteById(id);
        LambdaQueryWrapper<RoleMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RoleMenu::getRoleId,id);
        roleMenuMapper.delete(wrapper);
    }

}
