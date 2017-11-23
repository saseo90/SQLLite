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
     * 메인 페이지로 이동한다.
     * cloud/main or cloud
     * 
     * @return
     * @throws Exception
     */
    @RequestMapping(value={"/main"})
    public String moveMainPage() throws Exception{
        return "index";
    }
    
    /**
     * 에러 페이지로 이동한다.
     * cloud/error
     * 
     * @return
     * @throws Exception
     */
    @RequestMapping(value={"/error"})
    public String moveErrorPage() throws Exception{
        return "error";
    }
    
    
//    public String addBBSUseInf(@ModelAttribute("searchVO") BoardUseInfVO bdUseVO, ModelMap model) throws Exception {
//
//        if (!checkAuthority(model)) return "cmm/uat/uia/EgovLoginUsr";  // server-side 권한 확인
//
//        return "cop/com/EgovBoardUseInfRegist";
//    }
    
}
