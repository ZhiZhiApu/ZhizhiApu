package com.lantu.sys.service;

import com.lantu.sys.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Muye
 * @since 2024-10-01
 */
public interface IMenuService extends IService<Menu> {

    List<Menu> getAllMenu();

    //List<Menu> getMenuListByUserId(Integer userId);
}
