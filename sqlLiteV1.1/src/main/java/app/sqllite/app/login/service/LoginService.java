package app.sqllite.app.login.service;

import java.util.List;

import com.cmm.utility.impl.DefaultLoginVo;
/**
 * 
 * 로그인화면 서비스 기능 인터페이스
 * @author Lee SEONG-HYUN
 * @since 2018.01.21
 * @version 1.0
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *     수정일                   수정자          수정내용
 *  ----------  -------  ---------------------------
 *  2018.01.21    이성현         최초작성
 *  </pre>
 *  
 */ 
public interface LoginService {
    /** 접속테스트 */
    public String connectTest(DefaultLoginVo objVO);
    /** 접속 */
    public String connection(DefaultLoginVo objVO);
    /** 빠른사용자정보 XML 파일 읽기 */
    public List<DefaultLoginVo> readUserALL();
    /** 빠른사용자정보 XML 파일 쓰기 */
    public String saveUser(DefaultLoginVo defaultLoginVo);
    /** 빠른사용자정보 XML 파일 중 한건 수정*/
    public String modifyUser(DefaultLoginVo defaultLoginVo);
    /** 빠른사용자정보 XML 파일 중 한건 삭제 */
    public String deleteUser(DefaultLoginVo defaultLoginVo);
}
