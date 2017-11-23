package org.sqllite.manager.login.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.sqllite.manager.login.dao.LoginDao;
import org.sqllite.manager.login.service.LoginService;
import org.sqllite.manager.login.vo.LoginAccessVo;

@Service("userLoginService")
public class UserLoginService implements LoginService{

    @Resource(name="userLoginDao")
    LoginDao userLoginDao;
    
    @Override
    public Boolean loginAdmin(LoginAccessVo vo) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void logoutAdmin() throws Exception {
        // TODO Auto-generated method stub
        
    }

}
