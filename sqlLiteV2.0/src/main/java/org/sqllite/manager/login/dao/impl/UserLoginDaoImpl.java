package org.sqllite.manager.login.dao.impl;

import org.springframework.stereotype.Repository;
import org.sqllite.manager.login.dao.LoginDao;
import org.sqllite.manager.login.vo.LoginAccessVo;

import egovframework.rte.psl.dataaccess.EgovAbstractDAO;

@Repository("userLoginDao")
public class UserLoginDaoImpl extends EgovAbstractDAO implements LoginDao{

    @Override
    public LoginAccessVo loginAdmin(LoginAccessVo LoginAccessVo) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

}
