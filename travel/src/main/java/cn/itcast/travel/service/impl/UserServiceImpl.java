package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;

public class UserServiceImpl implements UserService {

    private UserDao userDao = new UserDaoImpl();
    @Override
    public boolean regist(User user) {
        //1.根据用户名查询用户信息
        User u = userDao.findByUsername(user.getUsername());
        //2.根据查询出的用户信息和传入的注册信息进行判断此用户是否存在
        if(u!=null){
            return false;
        }
        //3.不存在时，就对注册的用户信息进行保存
        user.setCode(UuidUtil.getUuid());//设置激活码，通过UUID生成唯一字符串
        user.setStatus("N");            //设置激活状态
        userDao.save(user);             //调用save方法保存用户信息


        //4.激活邮件，发送邮件正文
        String content = "<a href='http://localhost/travel/user/active?code="+user.getCode()+"'>点击激活【黑马旅游网】</a>";
        MailUtils.sendMail(user.getEmail(),content,"激活邮件");
        return true;
    }

    /**
     * 激活用户
     * @param code
     * @return
     */
    @Override
    public boolean active(String code) {
        //1.根据激活码查询用户对象
        User user = userDao.findByCode(code);
        if(user != null){   //存在此用户，激活成功
            //2.调用dao中的方法修改激活状态
            userDao.updateStatus(user);
            return true;
        }else{
            return  false;
        }

    }

    /**
     * 登录方法
     * @param user
     * @return
     */
    @Override
    public User login(User user) {
        return userDao.findByUsernameAndPassword(user.getUsername(),user.getPassword());
    }
}
