package org.sqllite.manager.login.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.sqllite.manager.login.service.LoginService;

/**
 * 로그인 페이지 호출을 위한 컨트롤러 클래스
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
@Controller("/sqllite/login")
public class LoginController {
    
    /** 관리자 로그인 서비스 구현체  */
    @Resource(name = "adminLoginService")
    private LoginService adminLoinService;
    /** 사용자 로그인 서비스 구현체  */
    @Resource(name = "userLoginService")
    private LoginService userLoinService;
    
//    /** DefaultBeanValidator */
//    @Autowired
//    private DefaultBeanValidator beanValidator;
//    
//    /** EgovMessageSource */
//    @Resource(name="egovMessageSource")
//    EgovMessageSource egovMessageSource;
    
    /**
     * 로그인 페이지로 이동한다.
     * cloud/login
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value={"/login"})
    public String moveLoginPage() throws Exception{
        return "login";
    }
    
    /**
     * 로그아웃 .
     * cloud/logout
     * 
     * @return
     * @throws Exception
     */
    @RequestMapping(value={"/logout"})
    public String moveLogoutPage() throws Exception{
        return "login";
    }
    
}
