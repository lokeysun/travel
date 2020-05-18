package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

public interface UserDao {

    /**
     * 根据用户名查询用户信息
     * @param username
     * @return
     */
    User findByUsername(String username);

    /**
     * 对于注册的新用户保存用户信息
     * @param user
     */
    void save(User user);

    /**
     * 根据激活码查询用户对象
     * @param code
     * @return
     */
    User findByCode(String code);

    /**
     * 根据查询到的用户对象修改激活状态
     * @param user
     */
    void updateStatus(User user);

    /**
     * 根据用户名和密码查询用户对象
     * @param username
     * @param password
     * @return
     */
    User findByUsernameAndPassword(String username,String password);
}
