package org.sqllite.manager.login.service;

import org.sqllite.manager.login.vo.LoginAccessVo;

/**
 * 공통 로그인 페이지 처리를 위한 서비스 인터페이스
 * @author 이성현
 * @since 2017.06.26
 * @version 1.0
 * @see
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *     수정일             수정자       수정내용
 *  ------------ ------ ----------------------------
 *   2017.06.26   이성현      최초 생성
 *  
 * </pre>
 */
public interface LoginService {

    /** 로그인 */
    public Boolean loginAdmin(LoginAccessVo vo) throws Exception;
    /** 로그아웃 */
    public void logoutAdmin() throws Exception;
}
