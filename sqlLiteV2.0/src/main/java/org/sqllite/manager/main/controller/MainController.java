package org.sqllite.manager.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 매인 페이지 호출을 위한 컨트롤러 클래스
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
@Controller("/sqllite/main")
public class MainController {
    
    
//    /** EgovBBSUseInfoManageService */
//    @Resource(name = "EgovBBSUseInfoManageService")
//    private EgovBBSUseInfoManageService bbsUseService;
//    
//    /** DefaultBeanValidator */
//    @Autowired
//    private DefaultBeanValidator beanValidator;
//    
//    /** EgovMessageSource */
//    @Resource(name="egovMessageSource")
//    EgovMessageSource egovMessageSource;
    
    /**
     * 홈페이지로 이동한다.
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value={"/","/sqllite",""})
    public String moveMainPage() throws Exception{
        return "redirect:/sqllite/main";
    }
    
    /**
     * 신규 회원가입 페이지로 이동한다.
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value={"main"})
    public String moveMainPage(String main) throws Exception{
        
//        reqService.searchSplyPrdctBizno("1058634153");
        return "index";
    }
    
    
    /**
     * 손님 가입 화면으로 이동한다.
     * 
     * @return
     * @throws Exception
     */
    @RequestMapping(value={"register"})
    public String moveRegisterPage() throws Exception{
        return "register";
    }
    
    /**
     * 잠금화면으로 이동한다.
     * 
     * @return
     * @throws Exception
     */
    @RequestMapping(value={"lockscreen"})
    public String movelockscreenPage() throws Exception{
        return "lockscreen";
    }
    
    /**
     * 에러페이지로 이동한다.
     * 기본 페이지 404
     * 
     * @return
     * @throws Exception
     */
    @RequestMapping(value={"error"})
    public String moveErrorPage() throws Exception{
        return "error-index";
    }
    
    /**
     * 404 에러페이지로 이동한다.
     * 
     * @return
     * @throws Exception
     */
    @RequestMapping(value={"error_404"})
    public String moveError404Page() throws Exception{
        return "error-404";
    }
    
    /**
     * 500 에러페이지로 이동한다.
     * 
     * @return
     * @throws Exception
     */
    @RequestMapping(value={"error_500"})
    public String moveError500Page() throws Exception{
        return "error-500";
    }
    
    
//    public String addBBSUseInf(@ModelAttribute("searchVO") BoardUseInfVO bdUseVO, ModelMap model) throws Exception {
//
//        if (!checkAuthority(model)) return "cmm/uat/uia/EgovLoginUsr";  // server-side 권한 확인
//
//        return "cop/com/EgovBoardUseInfRegist";
//    }
    
}
