package app.sqllite.app.login.service.impl;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.xml.sax.SAXException;

import com.cmm.utility.EasyXMLUtil;
import com.cmm.utility.SessionUtil;
import com.cmm.utility.impl.DefaultLoginVo;
import com.cmm.utility.impl.SessionVO;

import app.sqllite.app.login.service.LoginService;
import app.sqllite.database.oracle.service.OracleDAO;
import app.sqllite.database.oracle.service.impl.OracleDAOimpl;
import app.sqllite.database.oracle.service.impl.OracleVO;
/**
 * 
 * 로그인 서비스 기능구현 클래스  
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
public class LoginServiceImpl implements LoginService{
//    private static Logger log = LoggerFactory.getLogger(LoginServiceImpl.class);
    private OracleDAO oraDAO=null;
    
    public String connectTest(DefaultLoginVo objVO) {
        String resultMSG="[접속성공]";
        String dbType = objVO.getDbType().toUpperCase();

        if(dbType.equals("ORACLE")){
            oraDAO = new OracleDAOimpl();
            OracleVO oraVO = new OracleVO();
            oraVO.setAccessId(objVO.getAccessId());
            oraVO.setAccessPw(objVO.getAccessPw());
            oraVO.setAccessIp(objVO.getAccessIp());
            oraVO.setAccessPort(objVO.getAccessPort());
            oraVO.setAccessSID(objVO.getAccessSID());
            try {
                oraDAO.connectTest(oraVO.getDriverUrl(),objVO.getAccessId(), objVO.getAccessPw());
            } catch (Exception e) {
                 e.printStackTrace();
                 resultMSG="[접속실패]"+e.getMessage();
            }
        }else if(dbType.equals("MSSQL")){
            resultMSG="[접속실패]";
        }else if(dbType.equals("MYSQL")){resultMSG="[접속실패]";
        }
        return resultMSG;
    }
    
    public String connection(DefaultLoginVo objVO) {
       String resultMSG="[접속성공]";
       String dbType = objVO.getDbType().toUpperCase();

       if(dbType.equals("ORACLE")){
           oraDAO = new OracleDAOimpl();
           OracleVO oraVO = new OracleVO();
           oraVO.setAccessIp(objVO.getAccessIp());
           oraVO.setAccessPw(objVO.getAccessPw());
           oraVO.setAccessPort(objVO.getAccessPort());
           oraVO.setAccessSID(objVO.getAccessSID());
           oraVO.setDbType("ORACLE");
           try {
                Connection connection = oraDAO.connect(oraVO.getDriverUrl(),objVO.getAccessId(), objVO.getAccessPw());
                SessionVO sessionVO = new SessionVO();
                sessionVO.setRowNum(SessionUtil.getUserRowNum());
                sessionVO.setUserId(objVO.getUserId());
                sessionVO.setUserPw(objVO.getUserPw());
                sessionVO.setUsrType(objVO.getUsrType());//관리자,담당자,사용자
                oraVO.setDbEnum(SessionUtil.getUserDBEnum());
                oraVO.setConnection(connection);
                sessionVO.setDbObjListAdd(oraVO);
                
                SessionUtil.connectionAdd(sessionVO);
           } catch (Exception e) {
                e.printStackTrace();
                resultMSG="[접속실패]"+e.getMessage();
           }
       }else if(dbType.equals("MSSQL")){
       }else if(dbType.equals("MYSQL")){
       }
       return resultMSG;
    }
    public List<DefaultLoginVo> readUserALL() {
        EasyXMLUtil eXMLUtil = new EasyXMLUtil();
        try {
            return eXMLUtil.readDefaultUserALL();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<DefaultLoginVo>();
    }
    public String saveUser(DefaultLoginVo defaultLoginVo) {
        if(defaultLoginVo ==null)return "[저장실패]선택된 정보가 없습니다.";
        EasyXMLUtil eXMLUtil = new EasyXMLUtil();
        defaultLoginVo.setRowNum(SessionUtil.getUserRowNum());
        defaultLoginVo.setDbEnum(SessionUtil.getUserDBEnum());
            try {
                eXMLUtil.saveDefaultUser(defaultLoginVo);
            } catch (TransformerConfigurationException e) {
                e.printStackTrace();
                return "[저장실패]"+e.getMessage();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
                return "[저장실패]"+e.getMessage();
            } catch (TransformerException e) {
                e.printStackTrace();
                return "[저장실패]"+e.getMessage();
            } catch (TransformerFactoryConfigurationError e) {
                e.printStackTrace();
                return "[저장실패]"+e.getMessage();
            } catch (IOException e) {
                e.printStackTrace();
                return "[저장실패]"+e.getMessage();
            } catch (SAXException e) {
                e.printStackTrace();
                return "[저장실패]"+e.getMessage();
            }
        return "[저장성공]";
    }
    public String modifyUser(DefaultLoginVo defaultLoginVo) {
        if(defaultLoginVo ==null)return "[수정실패]선택된 정보가 없습니다.";
        EasyXMLUtil eXMLUtil = new EasyXMLUtil();
        int modCount = 0;
        try {
            modCount = eXMLUtil.modifyDefaultUser(defaultLoginVo);
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
            return "[수정실패]"+e.getMessage();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            return "[수정실패]"+e.getMessage();
        } catch (TransformerException e) {
            e.printStackTrace();
            return "[수정실패]"+e.getMessage();
        } catch (TransformerFactoryConfigurationError e) {
            e.printStackTrace();
            return "[수정실패]"+e.getMessage();
        } catch (IOException e) {
            e.printStackTrace();
            return "[수정실패]"+e.getMessage();
        } catch (SAXException e) {
            e.printStackTrace();
            return "[수정실패]"+e.getMessage();
        }
        if(modCount<0){
            return "[수정실패]파일저장 기본경로에 문제가 있습니다.";
        }
        return "[수정성공]"+modCount+" 건 수정.";
    }
    public String deleteUser(DefaultLoginVo defaultLoginVo) {
        int delCount = 0;
        if(defaultLoginVo ==null)return "[수정실패]선택된 정보가 없습니다.";
        EasyXMLUtil eXMLUtil = new EasyXMLUtil();
        try {
            delCount = eXMLUtil.deleteDefaultUser(defaultLoginVo);
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
            return "[삭제실패]"+e.getMessage();
        } catch (TransformerException e) {
            e.printStackTrace();
            return "[삭제실패]"+e.getMessage();
        } catch (TransformerFactoryConfigurationError e) {
            e.printStackTrace();
            return "[삭제실패]"+e.getMessage();
        } catch (IOException e) {
            e.printStackTrace();
            return "[삭제실패]"+e.getMessage();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            return "[삭제실패]"+e.getMessage();
        } catch (SAXException e) {
            e.printStackTrace();
            return "[삭제실패]"+e.getMessage();
        }
        if(delCount<0){
            return "[삭제실패]파일저장 기본경로에 문제가 있습니다.";
        }
        return "[삭제성공]"+delCount+" 건 삭제.";
    }
}
