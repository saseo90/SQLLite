package com.cmm.utility;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.cmm.utility.impl.DefaultLoginVo;
import com.cmm.utility.impl.SessionVO;

import app.sqllite.database.msssql.service.impl.MsSQLVO;
import app.sqllite.database.mysql.service.impl.MySQLVO;
import app.sqllite.database.oracle.service.impl.OracleVO;

/**
 * 
 * XML 생성 유틸 
 * - 빠른로그인 : XML 정보 읽기/쓰기/수정 기능 구현 클래스
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
public class EasyXMLUtil {
    private final String fileUrl = "c:/test/saved.xml";
    private final String fileName = "c:/test/saved.xml";
    private final String driName = "c:/test";
    
//    private void makeXml(List<LoginUser> list) throws Exception {
//        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
//        Node userlist = document.createElement("userList");
//        document.appendChild(userlist);
//    }
    /**
     * XML 작업을 위한 인스턴스 생성한다.
     * @return
     * @throws ParserConfigurationException
     */
    private Document getInstance() throws ParserConfigurationException{
        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        return document;
    }

    /**
     * 특정 사용자의 데이터베이스 정보를 추가한다.
     * @param usrInfo
     * @throws Exception 
     */
    public void addXmlDocDatabaseInfo(SessionVO usrInfo) {
//        if(chkIsFile()){//기존 파일에 덮어쓰기
//            Document xmlDoc = readXMLDoc();
//            String usrId;
//            String usrPw;
//            NodeList root = xmlDoc.getElementsByTagName("UserInfo");
//            for (int i = 0; i < root.getLength(); i++) {
//                usrId = root.item(i).getFirstChild().getTextContent();
////              logger.info(usrId);
//                if(usrId.equals(usrInfo.getUsrId())){
//                    Element dbList = (Element) root.item(i).getLastChild();
//                    Element dbInfo =  creatXMLContextDBInfo(usrInfo.getDbList().get(0), xmlDoc);
//                    dbList.appendChild(dbInfo);
//                    craetXmlDoc(xmlDoc);
////                    logger.info("성공 : XML 파일 에 사용자정보(DB계정정보)추가함 ");
//                    break;
//                }
//            }
//        }else{//신규 생성
//            craetXmlUsrInfo(usrInfo);
//        }
    }
    /**
     * XML 문서저장 빠른 사용자로그인 정보를 문서로 저장한다.
     * --메인(로그인화면)에서 추가된 정보 또는 신규정보를 저장할 떄 사용한다.
     * --기존파일이 없을 경우 createSaveBodyDefault() 메소드를 사용한다.
     * --문제는 기존파일이 있는 경우에 로직이 복잡하다. 추후 개선할 필요있다.
     * @param defaultLoginVo
     * @throws ParserConfigurationException
     * @throws TransformerConfigurationException
     * @throws TransformerException
     * @throws TransformerFactoryConfigurationError
     * @throws IOException
     * @throws SAXException
     */
    public void saveDefaultUser(DefaultLoginVo defaultLoginVo) throws ParserConfigurationException, TransformerConfigurationException, TransformerException, TransformerFactoryConfigurationError, IOException, SAXException {
//        if(!chkIsFile()){return;}
        if(!chkIsDirectory()){return;}
        List<DefaultLoginVo> beforeVO = readDefaultUserALL();
        if(0<beforeVO.size()){//복수
            beforeVO.add(defaultLoginVo);
//            System.out.println("전채:"+beforeVO.toString());
            Document doc = getInstance();
            String resultCode ="00";
            String resultMsg  ="메시지";
            Element response = doc.createElement("response");

            Element body = createSaveBodyDefault(doc, beforeVO);
            String totalCount =""+(body.getElementsByTagName("UserInfo").getLength());
            Element header = createHeader(doc, resultCode,resultMsg,totalCount);
            response.appendChild(header);
            response.appendChild(body);
            craetXmlDoc(response);
        }else{
            Document doc = getInstance();
            String resultCode ="00";
            String resultMsg  ="메시지";
            String totalCount ="1";
            Element response = doc.createElement("response");
            Element header = createHeader(doc, resultCode,resultMsg,totalCount);
            Element body = createSaveBodyDefault(doc, defaultLoginVo);
            response.appendChild(header);
            response.appendChild(body);
            craetXmlDoc(response);
        }
        }
    /**
     * 생성될 XML 파일의 해더부분 작성한다.
     * -- header > reaultCode :: 00
     * -- header > reaultMsg  :: 메시지
     * @param doc
     * @param resultCode
     * @param resultMsg
     * @param totalCount
     * @return
     */
    private Element createHeader(Document doc, String resultCode, String resultMsg, String totalCount) {
        doc.createElement("header");
        Element header = doc.createElement("header");
        Element reaultCodeEL = doc.createElement("reaultCode");
        Element reaultMsgEL = doc.createElement("reaultMsg");
        Element totalCountEL = doc.createElement("totalCount");
        reaultCodeEL.setTextContent(resultCode);
        reaultMsgEL.setTextContent(resultMsg);
        totalCountEL.setTextContent(totalCount);
        header.appendChild(reaultCodeEL);
        header.appendChild(reaultMsgEL);
        header.appendChild(totalCountEL);
        return header;
    }
    /**
     * 생성될 XML 파일의 본문부분 작성한다.
     * -- 신규생성
     * -- DefaultLoginVo 한건을 저장할 떄 사용한다.
     * @param doc
     * @param defaultLoginVo
     * @return
     */
    private Element createSaveBodyDefault(Document doc, DefaultLoginVo defaultLoginVo) {
        Element body = doc.createElement("body");
        Element items = doc.createElement("UserInfos");
        Element item = doc.createElement("UserInfo");
        Element rowNum = doc.createElement("rowNum");
        Element userId = doc.createElement("userId");
        Element userPw = doc.createElement("userPw");
        Element userType = doc.createElement("userType");
        item.setAttribute("Seq", "1");
        rowNum.setTextContent(defaultLoginVo.getRowNum());
        userId.setTextContent(defaultLoginVo.getUserId());
        userPw.setTextContent(defaultLoginVo.getUserPw());
        userType.setTextContent(defaultLoginVo.getUsrType());
        Element dbItems = doc.createElement("dbItems");
        Element dbItem = doc.createElement("dbItem");
        Element dbEnum = doc.createElement("dbEnum");
        Element dbType = doc.createElement("dbType");;
        Element accessId = doc.createElement("accessId");
        Element accessPw = doc.createElement("accessPw");
        Element accessIp = doc.createElement("accessIp");
        Element accessPort = doc.createElement("accessPort");
        Element accessSID = doc.createElement("accessSID");
        Element accessDBName = doc.createElement("accessDBName");
        Element dbGrant = doc.createElement("dbGrant");
        dbType.setTextContent(defaultLoginVo.getDbType());
        dbEnum.setTextContent(defaultLoginVo.getDbEnum());
        accessId.setTextContent(defaultLoginVo.getAccessId());
        accessPw.setTextContent(defaultLoginVo.getAccessPw());
        accessIp.setTextContent(defaultLoginVo.getAccessIp());
        accessPort.setTextContent(defaultLoginVo.getAccessPort());
        accessSID.setTextContent(defaultLoginVo.getAccessSID());
        accessDBName.setTextContent(defaultLoginVo.getAccessDBName());
        dbGrant.setTextContent(defaultLoginVo.getDbGrant());
        dbItem.appendChild(dbType);
        dbItem.appendChild(dbEnum);
        dbItem.appendChild(accessId);
        dbItem.appendChild(accessPw);
        dbItem.appendChild(accessIp);
        dbItem.appendChild(accessPort);
        dbItem.appendChild(accessSID);
        dbItem.appendChild(accessDBName);
        dbItem.appendChild(dbGrant);
        dbItems.appendChild(dbItem);
        item.appendChild(rowNum);
        item.appendChild(userId);
        item.appendChild(userPw);
        item.appendChild(userType);
        item.appendChild(dbItems);
        items.appendChild(item);
        body.appendChild(items);
        return body;
    }
    
    /**
     *  생성될 XML 파일의 본문부분 작성한다.
     * -- 신규 사용자추가
     * -- DefaultLoginVo 여러 건을 저장할 떄 사용한다.
     * @param doc
     * @param defaultLoginVo
     * @return
     */
    private Element createSaveBodyDefault(Document doc, List<DefaultLoginVo> defaultLoginVo){
        Element body = doc.createElement("body");
        Element items = doc.createElement("UserInfos");
        
        List<Element> itemList = new ArrayList<Element>();
        // itemVO itemList1
        int seqNum = 0;
        for (DefaultLoginVo itemVO : defaultLoginVo) {
            String rowNumTxt = itemVO.getRowNum();
//            String dbEnumTxt = itemVO.getDbEnum();
            boolean flag = true;
            for (Element itemEL : itemList) {
               String rowNumTemp = itemEL.getFirstChild().getTextContent();;
                if(rowNumTxt.equals(rowNumTemp)){//특정사용자의 DB정보 추가
                    Element dbItem = doc.createElement("dbItem");
                    Element dbEnum = doc.createElement("dbEnum");
                    Element dbType = doc.createElement("dbType");;
                    Element accessId = doc.createElement("accessId");
                    Element accessPw = doc.createElement("accessPw");
                    Element accessIp = doc.createElement("accessIp");
                    Element accessPort = doc.createElement("accessPort");
                    Element accessSID = doc.createElement("accessSID");
                    Element accessDBName = doc.createElement("accessDBName");
                    Element dbGrant = doc.createElement("dbGrant");
                    dbEnum.setTextContent(itemVO.getDbEnum());
                    dbType.setTextContent(itemVO.getDbType());
                    accessId.setTextContent(itemVO.getAccessId());
                    accessPw.setTextContent(itemVO.getAccessPw());
                    accessIp.setTextContent(itemVO.getAccessIp());
                    accessPort.setTextContent(itemVO.getAccessPort());
                    accessSID.setTextContent(itemVO.getAccessSID());
                    accessDBName.setTextContent(itemVO.getAccessDBName());
                    dbGrant.setTextContent(itemVO.getDbGrant());
                    dbItem.appendChild(dbEnum);
                    dbItem.appendChild(dbType);
                    dbItem.appendChild(accessId);
                    dbItem.appendChild(accessPw);
                    dbItem.appendChild(accessIp);
                    dbItem.appendChild(accessPort);
                    dbItem.appendChild(accessSID);
                    dbItem.appendChild(accessDBName);
                    dbItem.appendChild(dbGrant);
                    itemEL.getLastChild().appendChild(dbItem);
                    flag = false;    
                }
            }
            if(flag){//신규 사용자 노드 추가(새로작성)
                Element item = doc.createElement("UserInfo");
                Element rowNum = doc.createElement("rowNum");
                Element userId = doc.createElement("userId");
                Element userPw = doc.createElement("userPw");
                Element userType = doc.createElement("userType");
                ++seqNum;
                item.setAttribute("Seq", ""+seqNum);
                rowNum.setTextContent(itemVO.getRowNum());
                userId.setTextContent(itemVO.getUserId());
                userPw.setTextContent(itemVO.getUserPw());
                userType.setTextContent(itemVO.getUsrType());
                Element dbItems = doc.createElement("dbItems");
                Element dbItem = doc.createElement("dbItem");
                Element dbEnum = doc.createElement("dbEnum");
                Element dbType = doc.createElement("dbType");;
                Element accessId = doc.createElement("accessId");
                Element accessPw = doc.createElement("accessPw");
                Element accessIp = doc.createElement("accessIp");
                Element accessPort = doc.createElement("accessPort");
                Element accessSID = doc.createElement("accessSID");
                Element accessDBName = doc.createElement("accessDBName");
                Element dbGrant = doc.createElement("dbGrant");
                dbEnum.setTextContent(itemVO.getDbEnum());
                dbType.setTextContent(itemVO.getDbType());
                accessId.setTextContent(itemVO.getAccessId());
                accessPw.setTextContent(itemVO.getAccessPw());
                accessIp.setTextContent(itemVO.getAccessIp());
                accessPort.setTextContent(itemVO.getAccessPort());
                accessSID.setTextContent(itemVO.getAccessSID());
                accessDBName.setTextContent(itemVO.getAccessDBName());
                dbGrant.setTextContent(itemVO.getDbGrant());
                dbItem.appendChild(dbEnum);
                dbItem.appendChild(dbType);
                dbItem.appendChild(accessId);
                dbItem.appendChild(accessPw);
                dbItem.appendChild(accessIp);
                dbItem.appendChild(accessPort);
                dbItem.appendChild(accessSID);
                dbItem.appendChild(accessDBName);
                dbItem.appendChild(dbGrant);
                dbItems.appendChild(dbItem);
                item.appendChild(rowNum);
                item.appendChild(userId);
                item.appendChild(userPw);
                item.appendChild(userType);
                item.appendChild(dbItems);
                itemList.add(item);
            }
        }
        for (Element item : itemList) {
            items.appendChild(item);
        }
        body.appendChild(items);
        return body;
    }

    /**
     * XML 문서저장 빠른 사용자로그인 정보를 문서로 저장한다.
     * --메인(로그인화면)에서 신규정보 또는 수정된 정보를 저장할 떄 사용한다.
     * --기존파일이 없을 경우 createSaveBodyDefault() 메소드를 사용한다.
     * --문제는 기존파일이 있는 경우에 로직이 복잡하다. 추후 개선할 필요있다.
     * @param defaultLoginVo
     * @throws ParserConfigurationException
     * @throws TransformerConfigurationException
     * @throws TransformerException
     * @throws TransformerFactoryConfigurationError
     * @throws IOException
     * @throws SAXException
     */
    public int modifyDefaultUser(DefaultLoginVo defaultLoginVo) throws ParserConfigurationException, TransformerConfigurationException, TransformerException, TransformerFactoryConfigurationError, IOException, SAXException {
//        if(!chkIsFile()){return;}
        int modeCount = 0; 
        if(!chkIsDirectory()){return -1;}
        List<DefaultLoginVo> beforeVO = readDefaultUserALL();
        if(0<beforeVO.size()){//복수
            String dbEnum =defaultLoginVo.getDbEnum();
            String rowNum =defaultLoginVo.getRowNum();
            boolean flag = true;
            for (DefaultLoginVo beforeVOItem : beforeVO) {
                String dbEnumTemp = beforeVOItem.getDbEnum();
                String rowNumTemp = beforeVOItem.getRowNum();
                if(dbEnum.equals(dbEnumTemp)&&rowNum.equals(rowNumTemp)){
                    beforeVOItem.setAccessId(defaultLoginVo.getAccessId());
                    beforeVOItem.setAccessPw(defaultLoginVo.getAccessPw());
                    beforeVOItem.setAccessIp(defaultLoginVo.getAccessIp());
                    beforeVOItem.setAccessPort(defaultLoginVo.getAccessPort());
                    beforeVOItem.setAccessSID(defaultLoginVo.getAccessSID());
                    beforeVOItem.setAccessDBName(defaultLoginVo.getAccessDBName());
                    beforeVOItem.setDbGrant(defaultLoginVo.getDbGrant());
                    beforeVOItem.setDbType(defaultLoginVo.getDbType());
                    flag=false;
                    ++modeCount;
                }
            }
            if(flag){
                beforeVO.add(defaultLoginVo);
            }
//            System.out.println("전채:"+beforeVO.toString());
            Document doc = getInstance();
            String resultCode ="00";
            String resultMsg  ="메시지";
            String totalCount ="0";
            Element response = doc.createElement("response");
            Element result = createSaveBodyDefault(doc, beforeVO);
//            Map<String, Object> result = createSaveBodyDefault(doc, beforeVO);
//            int modeCount =  (int) result.get("modeCount");
//            Element body = (Element) result.get("body"); 
//            totalCount =""+(body.getElementsByTagName("UserInfo").getLength());
//            Element header = createHeader(doc, resultCode,resultMsg,totalCount);
//            response.appendChild(header);
//            response.appendChild(body);
//            craetXmlDoc(response);
//            return modeCount;
            totalCount=""+modeCount;
            Element header = createHeader(doc, resultCode,resultMsg,totalCount);
            response.appendChild(header);
            response.appendChild(result);
            craetXmlDoc(response);
            return modeCount;
        }else{
            Document doc = getInstance();
            String resultCode ="00";
            String resultMsg  ="메시지";
            String totalCount ="1";
            Element response = doc.createElement("response");
            Element header = createHeader(doc, resultCode,resultMsg,totalCount);
            Element body = createSaveBodyDefault(doc, defaultLoginVo);
            response.appendChild(header);
            response.appendChild(body);
            craetXmlDoc(response);
        }
        return modeCount=1;
    }
    /**
     * XML 문서저장 빠른 사용자로그인 정보를 문서로 저장한다.
     * --메인(로그인화면)에서 신규정보 또는 수정된 정보를 저장할 떄 사용한다.
     * --기존파일이 없을 경우 createSaveBodyDefault() 메소드를 사용한다.
     * --문제는 기존파일이 있는 경우에 로직이 복잡하다. 추후 개선할 필요있다.
     * @param defaultLoginVo
     * @throws IOException 
     * @throws TransformerFactoryConfigurationError 
     * @throws TransformerException 
     * @throws TransformerConfigurationException 
     * @throws ParserConfigurationException 
     * @throws SAXException 
     */
    public int deleteDefaultUser(DefaultLoginVo defaultLoginVo) throws TransformerConfigurationException, TransformerException, TransformerFactoryConfigurationError, IOException, ParserConfigurationException, SAXException{
        int modeCount = 0; 
        if(!chkIsDirectory()){return -1;}
        List<DefaultLoginVo> beforeVO = readDefaultUserALL();
        if(0<beforeVO.size()){//복수
            String dbEnum =defaultLoginVo.getDbEnum();
            String rowNum =defaultLoginVo.getRowNum();
            boolean flag = true;
            List<DefaultLoginVo> beforeVO2 = new ArrayList<DefaultLoginVo>();
            beforeVO2.addAll(beforeVO);
            for (DefaultLoginVo beforeVOItem : beforeVO2) {
                String dbEnumTemp = beforeVOItem.getDbEnum();
                String rowNumTemp = beforeVOItem.getRowNum();
                if(dbEnum.equals(dbEnumTemp)&&rowNum.equals(rowNumTemp)){
                    beforeVO.remove(beforeVOItem);
                    flag=false;
                    ++modeCount;
                }
            }
            if(flag){
                beforeVO.add(defaultLoginVo);
            }
            Document doc = getInstance();
            String resultCode ="00";
            String resultMsg  ="메시지";
            String totalCount ="0";
            Element response = doc.createElement("response");
            Element result = createSaveBodyDefault(doc, beforeVO);
            totalCount=""+modeCount;
            Element header = createHeader(doc, resultCode,resultMsg,totalCount);
            response.appendChild(header);
            response.appendChild(result);
            craetXmlDoc(response);
            return modeCount;
        }else{
            Document doc = getInstance();
            String resultCode ="00";
            String resultMsg  ="메시지";
            String totalCount ="1";
            Element response = doc.createElement("response");
            Element header = createHeader(doc, resultCode,resultMsg,totalCount);
            Element body = createSaveBodyDefault(doc, defaultLoginVo);
            response.appendChild(header);
            response.appendChild(body);
            craetXmlDoc(response);
        }
        return modeCount=1;    
    }
    
//    /**
//     *  생성될 XML 파일의 본문부분 작성한다.
//     * -- 사용자 의 DB 수정 
//     * -- DefaultLoginVo 여러 건을 저장할 떄 사용한다.
//     * @param doc
//     * @param defaultLoginVo
//     * @return
//     */
//    private HashMap<String, Object> createModyfyBodyDefault(Document doc, List<DefaultLoginVo> defaultLoginVo){
//        HashMap<String, Object> result = new HashMap<String, Object>(); 
//        int modeCount = 0;
//        Element body = doc.createElement("body");
//        Element items = doc.createElement("UserInfos");
//        
//        List<Element> itemList = new ArrayList<Element>();
//        // itemVO itemList1
//        int seqNum = 0;
//        for (DefaultLoginVo itemVO : defaultLoginVo) {
//            String rowNumTxt = itemVO.getRowNum();
//            String dbEnumTxt = itemVO.getDbEnum();
//            System.out.println("?"+dbEnumTxt);
//            boolean flag = true;
//            for (Element itemEL : itemList) {
//            String rowNumTemp = itemEL.getFirstChild().getTextContent();;
//            NodeList dbItemELs = itemEL.getLastChild().getChildNodes();
//            if(rowNumTxt.equals(rowNumTemp)){
//               for (int i = 0; i < dbItemELs.getLength(); ++i) {
//               Node dbItemEL = dbItemELs.item(i);
//               String dbEnumTemp = dbItemEL.getLastChild().getTextContent();
//               
//               System.out.println("?"+dbEnumTemp);
//               if(dbEnumTxt.equals(dbEnumTemp)){//특정사용자의 DB정보 수정
//                   NodeList dbItemAttrs = dbItemEL.getChildNodes();
//                   for (int j = 0; j < dbItemAttrs.getLength(); ++j) {
//                   Node dbAttr = dbItemAttrs.item(j);
//                   String attrName = dbAttr.getTextContent();
//                   if("dbType".equals(attrName)){
//                       dbAttr.setTextContent(itemVO.getDbType());
//                   }else if("accessId".equals(attrName)){
//                       dbAttr.setTextContent(itemVO.getAccessId());
//                   }else if("accessPw".equals(attrName)){
//                       dbAttr.setTextContent(itemVO.getAccessPw());
//                   }else if("accessIp".equals(attrName)){
//                       dbAttr.setTextContent(itemVO.getAccessIp());
//                   }else if("accessPort".equals(attrName)){
//                       dbAttr.setTextContent(itemVO.getAccessPort());
//                   }else if("accessSID".equals(attrName)){
//                       dbAttr.setTextContent(itemVO.getAccessSID());
//                   }else if("accessDBName".equals(attrName)){
//                       dbAttr.setTextContent(itemVO.getAccessDBName());
//                   }else if("dbGrant".equals(attrName)){
//                       dbAttr.setTextContent(itemVO.getDbGrant());
//                   }
//                   }//for
//               ++modeCount;
//               flag = false;
//               }//if
//               if(flag&&dbEnumTxt.equals(dbEnumTemp)){//특정사용자의 DB정보 추가
//                Element dbItem = doc.createElement("dbItem");
//                Element dbEnum = doc.createElement("dbEnum");
//                Element dbType = doc.createElement("dbType");;
//                Element accessId = doc.createElement("accessId");
//                Element accessPw = doc.createElement("accessPw");
//                Element accessIp = doc.createElement("accessIp");
//                Element accessPort = doc.createElement("accessPort");
//                Element accessSID = doc.createElement("accessSID");
//                Element accessDBName = doc.createElement("accessDBName");
//                Element dbGrant = doc.createElement("dbGrant");
//                dbEnum.setTextContent(itemVO.getDbEnum());
//                dbType.setTextContent(itemVO.getDbType());
//                accessId.setTextContent(itemVO.getAccessId());
//                accessPw.setTextContent(itemVO.getAccessPw());
//                accessIp.setTextContent(itemVO.getAccessIp());
//                accessPort.setTextContent(itemVO.getAccessPort());
//                accessSID.setTextContent(itemVO.getAccessSID());
//                accessDBName.setTextContent(itemVO.getAccessDBName());
//                dbGrant.setTextContent(itemVO.getDbGrant());
//                dbItem.appendChild(dbEnum);
//                dbItem.appendChild(dbType);
//                dbItem.appendChild(accessId);
//                dbItem.appendChild(accessPw);
//                dbItem.appendChild(accessIp);
//                dbItem.appendChild(accessPort);
//                dbItem.appendChild(accessSID);
//                dbItem.appendChild(accessDBName);
//                dbItem.appendChild(dbGrant);
//                itemEL.getLastChild().appendChild(dbItem);
//                flag = false;    
//            }//if
//            }//for
//        }//if
//        }//for
//            if(flag){//신규 사용자 노드 추가(새로작성)
//                Element item = doc.createElement("UserInfo");
//                Element rowNum = doc.createElement("rowNum");
//                Element userId = doc.createElement("userId");
//                Element userPw = doc.createElement("userPw");
//                Element userType = doc.createElement("userType");
//                ++seqNum;
//                item.setAttribute("Seq", ""+seqNum);
//                rowNum.setTextContent(itemVO.getRowNum());
//                userId.setTextContent(itemVO.getUserId());
//                userPw.setTextContent(itemVO.getUserPw());
//                userType.setTextContent(itemVO.getUsrType());
//                Element dbItems = doc.createElement("dbItems");
//                Element dbItem = doc.createElement("dbItem");
//                Element dbEnum = doc.createElement("dbEnum");
//                Element dbType = doc.createElement("dbType");;
//                Element accessId = doc.createElement("accessId");
//                Element accessPw = doc.createElement("accessPw");
//                Element accessIp = doc.createElement("accessIp");
//                Element accessPort = doc.createElement("accessPort");
//                Element accessSID = doc.createElement("accessSID");
//                Element accessDBName = doc.createElement("accessDBName");
//                Element dbGrant = doc.createElement("dbGrant");
//                dbEnum.setTextContent(itemVO.getDbEnum());
//                dbType.setTextContent(itemVO.getDbType());
//                accessId.setTextContent(itemVO.getAccessId());
//                accessPw.setTextContent(itemVO.getAccessPw());
//                accessIp.setTextContent(itemVO.getAccessIp());
//                accessPort.setTextContent(itemVO.getAccessPort());
//                accessSID.setTextContent(itemVO.getAccessSID());
//                accessDBName.setTextContent(itemVO.getAccessDBName());
//                dbGrant.setTextContent(itemVO.getDbGrant());
//                dbItem.appendChild(dbEnum);
//                dbItem.appendChild(dbType);
//                dbItem.appendChild(accessId);
//                dbItem.appendChild(accessPw);
//                dbItem.appendChild(accessIp);
//                dbItem.appendChild(accessPort);
//                dbItem.appendChild(accessSID);
//                dbItem.appendChild(accessDBName);
//                dbItem.appendChild(dbGrant);
//                dbItems.appendChild(dbItem);
//                item.appendChild(rowNum);
//                item.appendChild(userId);
//                item.appendChild(userPw);
//                item.appendChild(userType);
//                item.appendChild(dbItems);
//                itemList.add(item);
//            }//if
//        }//for
//        for (Element item : itemList) {
//            items.appendChild(item);
//        }
//        body.appendChild(items);
//        result.put("body", body);
//        result.put("modeCount", modeCount);
//        return result;
//    }
    
    /**
     * xml 읽기 
     * --모든 사용자 정보 불러오기
     * --기본경로
     * @return
     * @throws ParserConfigurationException 
     * @throws IOException 
     * @throws SAXException 
     * @throws Exception
     */
    public  List<DefaultLoginVo> readDefaultUserALL() throws SAXException, IOException, ParserConfigurationException {
        return readDefaultUserALL(this.fileUrl);
    }
    /**
     * xml 읽기 
     * --모든 사용자 정보 불러오기
     * --임의경로
     * @return
     * @throws ParserConfigurationException 
     * @throws IOException 
     * @throws SAXException 
     */
    public  List<DefaultLoginVo> readDefaultUserALL(String fileUrl) throws SAXException, IOException, ParserConfigurationException {
        ArrayList<DefaultLoginVo> result = new ArrayList<DefaultLoginVo>();
        if(chkIsFile(fileUrl)&&chkIsDirectory(driName)){
            Document xmlDoc = readXMLDoc(fileUrl);
//             루트 엘리먼트 접근
//            Element root = xmlDoc.getDocumentElement();
            NodeList items = xmlDoc.getElementsByTagName("UserInfo");
            for (int i = 0; i < items.getLength(); i++) {
                List<DefaultLoginVo> item = readDefaultUserALL(items.item(i));
                result.addAll(item);
//                System.out.println("전체");
            }
        }
        return result;
    }
    /**
     * xml파일에서 읽은 사용자정보 한개를 파싱한다.
     * --사용자화면에서 사용할 DefaultLoginVo 객체로 반환한다.
     * @param item
     * @return
     */
    private List<DefaultLoginVo> readDefaultUserALL(Node userInfoEL) {
        List<DefaultLoginVo> result = new ArrayList<DefaultLoginVo>();
        NodeList itemEL = userInfoEL.getChildNodes();
        String rowNum = null;
        String userId = null;
        String userPw = null;
        String userType = null;

        for (int i = 0; i < itemEL.getLength(); i++) {
            Node tempNode = itemEL.item(i);
            String nodeName = tempNode.getNodeName();
            if(nodeName.equals("rowNum")){
                rowNum = tempNode.getTextContent();
            }else if(nodeName.equals("userId")){
                userId = tempNode.getTextContent();
            }else if(nodeName.equals("userPw")){
                userPw = tempNode.getTextContent();
            }else if(nodeName.equals("userType")){
                userType = tempNode.getTextContent();
            }else if(nodeName.equals("dbItems")){
                NodeList temp = tempNode.getChildNodes();
                int iniTemp = temp.getLength();
                DefaultLoginVo resultVO = null;
                //                if(0<iniTemp){
                for (int j = 0; j < iniTemp; j++) {
                    resultVO = new DefaultLoginVo();
                    resultVO.setRowNum(rowNum);
                    resultVO.setUserId(userId);
                    resultVO.setUserPw(userPw);
                    resultVO.setUsrType(userType);
                    Node dbItem = temp.item(j);
                    NodeList dbItemAttrs = dbItem.getChildNodes();
                    for (int k = 0; k < dbItemAttrs.getLength(); k++) {
                        Node dbItemAttr = dbItemAttrs.item(k);
                        String dbItemAttrName = dbItemAttr.getNodeName();
                        if(dbItemAttrName.equals("dbType")){
                            resultVO.setDbType(dbItemAttr.getTextContent());
                        }else if(dbItemAttrName.equals("dbEnum")){
                            resultVO.setDbEnum(dbItemAttr.getTextContent());
                        }else if(dbItemAttrName.equals("accessId")){
                            resultVO.setAccessId(dbItemAttr.getTextContent());
                        }else if(dbItemAttrName.equals("accessPw")){
                            resultVO.setAccessPw(dbItemAttr.getTextContent());
                        }else if(dbItemAttrName.equals("accessIp")){
                            resultVO.setAccessIp(dbItemAttr.getTextContent());
                        }else if(dbItemAttrName.equals("accessPort")){
                            resultVO.setAccessPort(dbItemAttr.getTextContent());
                        }else if(dbItemAttrName.equals("accessSID")){
                            resultVO.setAccessSID(dbItemAttr.getTextContent());
                        }else if(dbItemAttrName.equals("accessDBName")){
                            resultVO.setAccessDBName(dbItemAttr.getTextContent());
                        }else if(dbItemAttrName.equals("dbGrant")){
                            resultVO.setDbGrant(dbItemAttr.getTextContent());
                        }
                    }
                    result.add(resultVO);
                }
            }
        }
//        System.out.println(result.toString());
        return result;
    }
    

    /**
     * xml 읽기 
     * --모든 사용자 정보 불러오기
     * --기본경로
     * @return
     * @throws ParserConfigurationException 
     * @throws IOException 
     * @throws SAXException 
     */
    public  List<SessionVO> readUserALL() throws SAXException, IOException, ParserConfigurationException  {
        return readUserALL(this.fileUrl);
    }
    /**
     * xml 읽기 
     * --모든 사용자 정보 불러오기
     * --임의경로
     * @return
     * @throws ParserConfigurationException 
     * @throws IOException 
     * @throws SAXException 
     */
    public  List<SessionVO> readUserALL(String fileUrl) throws SAXException, IOException, ParserConfigurationException {
        ArrayList<SessionVO> result = new ArrayList<SessionVO>();
        if(chkIsFile(fileUrl)){
            Document xmlDoc = readXMLDoc(fileUrl);
//             루트 엘리먼트 접근
//            Element root = xmlDoc.getDocumentElement();
            NodeList items = xmlDoc.getElementsByTagName("UserInfo");
           
            for (int i = 0; i < items.getLength(); ++i) {
                SessionVO item = readUserALL(items.item(i));
                result.add(item);
            }
//            System.out.println(result.toString());
        }
        return result;
    }
    
  
    /**
     * xml파일에서 읽은 사용자정보 한개를 파싱한다.
     * @param item
     * @return
     */
    private  SessionVO readUserALL(Node item) {
        SessionVO result = new SessionVO();
        String rowNum = item.getFirstChild().getTextContent();
        String userId = item.getNextSibling().getTextContent();
        String userPw = item.getNextSibling().getTextContent();
        String userType = item.getNextSibling().getTextContent();
        NodeList dbItemALL = item.getLastChild().getChildNodes();
        ArrayList<Object> dbItems = readUserDBItem(dbItemALL);
        result.setRowNum(rowNum);
        result.setUserId(userId);
        result.setUserPw(userPw);
        result.setUsrType(userType);
        result.setDbObjList(dbItems);
        return result;
    }
    /**
     * xml파일에서 읽은 사용자정보의 DB정보 한개를 파싱한다.
     * @param dbItem DB노드
     * @return
     */
    private  ArrayList<Object> readUserDBItem(NodeList dbItemALL) {
        ArrayList<Object> result = new ArrayList<Object>();
        for (int i = 0; i < dbItemALL.getLength(); ++i) {
            Node dbItem = dbItemALL.item(i);

            String dbType = dbItem.getFirstChild().getTextContent();
            String dbEnum = dbItem.getNextSibling().getTextContent();
            String accessId = dbItem.getNextSibling().getTextContent();
            String accessPw = dbItem.getNextSibling().getTextContent();
            String accessIp = dbItem.getNextSibling().getTextContent();
            String accessPort = dbItem.getNextSibling().getTextContent();
            String accessSID = dbItem.getLastChild().getTextContent();
            String accessDBName = dbItem.getLastChild().getTextContent();
            if(dbType.toUpperCase().equals("ORACLE")){
                OracleVO oraVO = new OracleVO();
                oraVO.setDbEnum(dbEnum);
                oraVO.setDbType("ORACLE");
                oraVO.setAccessId(accessId);
                oraVO.setAccessPw(accessPw);
                oraVO.setAccessIp(accessIp);
                oraVO.setAccessPort(accessPort);
                oraVO.setAccessSID(accessSID);
            }else if(dbType.toUpperCase().equals("MSSQL")){
                MsSQLVO msVO = new MsSQLVO();
                msVO.setDbEnum(dbEnum);
                msVO.setDbType("MSSQL");
                msVO.setAccessId(accessId);
                msVO.setAccessPw(accessPw);
                msVO.setAccessIp(accessIp);
                msVO.setAccessPort(accessPort);
                msVO.setAccessDBName(accessDBName);     
            }else if(dbType.toUpperCase().equals("MYSQL")){
                MySQLVO myVO = new MySQLVO();
                myVO.setDbEnum(dbEnum);
                myVO.setDbType("MYSQL");
                myVO.setAccessId(accessId);
                myVO.setAccessPw(accessPw);
                myVO.setAccessIp(accessIp);
                myVO.setAccessPort(accessPort);
                myVO.setAccessDBName(accessDBName);    
            }
        }
        return result;
    }
    /**
     * 특정 사용자의 모든 사용자정보를 신규 XML 문서로 작성한다.
     * --기본경로에 생성한다.
     * @param ursInfo
     * @throws ParserConfigurationException 
     * @throws TransformerFactoryConfigurationError 
     * @throws TransformerException 
     * @throws TransformerConfigurationException 
     * @throws IOException 
     */
    public  void craetXmlUsrInfo(List<SessionVO> usrInfoList) throws ParserConfigurationException, TransformerConfigurationException, TransformerException, TransformerFactoryConfigurationError, IOException {
        craetXmlUsrInfo(usrInfoList, this.fileName);
    }
    /**
     * 특정 사용자의 모든 사용자정보를 신규 XML 문서로 작성한다.
     * --임의경로에 생성한다.
     * @param ursInfo
     * @throws ParserConfigurationException 
     * @throws TransformerFactoryConfigurationError 
     * @throws TransformerException 
     * @throws TransformerConfigurationException 
     * @throws IOException 
     */
    public boolean craetXmlUsrInfo(List<SessionVO> usrInfoList, String fileName) throws ParserConfigurationException, TransformerConfigurationException, TransformerException, TransformerFactoryConfigurationError, IOException {
        if(!chkIsFile(fileUrl)){return false;}
        if(!chkIsDirectory(fileUrl)){return false;}
        if(usrInfoList==null){}
        Document document = getInstance();
        Element response = document.createElement("response");
        Node userInfos = document.createElement("UserInfos");
//        1.사용자 정보
        for (SessionVO vo : usrInfoList) {
            Node userInfo = craetXmlUsrInfo(vo,document);
//        4.
            userInfos.appendChild(userInfo);
        }
        response.appendChild(userInfos);
//        5.문서생성
        craetXmlDoc(response,fileName);
        return true;
    }
    /**
     * 특정 사용자의 모든 사용자정보를 신규 XML 문서로 작성한다.
     * --기본경로에 생성한다.
     * @param ursInfo
     * @throws ParserConfigurationException 
     * @throws TransformerFactoryConfigurationError 
     * @throws TransformerException 
     * @throws TransformerConfigurationException 
     * @throws IOException 
     */
    public void craetXmlUsrInfo(SessionVO usrInfo) throws TransformerConfigurationException, TransformerException, TransformerFactoryConfigurationError, ParserConfigurationException, IOException {
        craetXmlUsrInfo(usrInfo, this.fileName); 
    }
    /**
     * 특정 사용자의 모든 사용자정보를 신규 XML 문서로 작성한다.
     * --기본경로에 생성한다.
     * @param ursInfo
     * @throws TransformerFactoryConfigurationError 
     * @throws TransformerException 
     * @throws TransformerConfigurationException 
     * @throws ParserConfigurationException 
     * @throws IOException 
     */
    public boolean craetXmlUsrInfo(SessionVO usrInfo, String fileName) throws TransformerConfigurationException, TransformerException, TransformerFactoryConfigurationError, ParserConfigurationException, IOException  {
        if(!chkIsFile(fileUrl)){return false;}
        if(!chkIsDirectory(fileUrl)){return false;}
        if(usrInfo==null){}
        Document document = getInstance();
        Element response = document.createElement("response");
        Node userInfos = document.createElement("UserInfos");
//        1.사용자 정보
        Node userInfo = craetXmlUsrInfo(usrInfo,document);
//        4.
        userInfos.appendChild(userInfo);
        response.appendChild(userInfos);
//        5.문서생성
        craetXmlDoc(response,fileName);
        return true;
    }
    /**
     * 사용자정보 XML DOC 생성한다.
     * 특정 사용자의 사용자 정보를 Node로 만든다.
     * @param usrInfo
     * @param document
     * @return
     */
    private Node craetXmlUsrInfo(SessionVO usrInfo,Document document) {
//      1.사용자 정보
      Node userInfo = document.createElement("UserInfo");
      Element rowNum = document.createElement("rowNum");
      Element userId = document.createElement("userId");
      Element userPw = document.createElement("userPw");
      Element userType = document.createElement("userType");
//      2.
      rowNum.setTextContent(usrInfo.getRowNum());
      userId.setTextContent(usrInfo.getUserId());
      userPw.setTextContent(usrInfo.getUserPw());
      Element dbItems = creatXMLContextDBInfo(usrInfo.getDbObjList(),document);
//      3.
      userInfo.appendChild(rowNum);
      userInfo.appendChild(userId);
      userInfo.appendChild(userPw);
      userInfo.appendChild(userType);
      userInfo.appendChild(dbItems);
       return userInfo;
    }

    /**
     * XML 문서 내용 부분 생성 : dbItems
     * @param dbObjList : DB 접속 정보
     * @param document 
     * @return Element : dbItems
     */
    private Element creatXMLContextDBInfo(ArrayList<Object> dbObjList,  Document document ) {
        Element dbIteams = document.createElement("dbItems");
        for (Object dbObj : dbObjList) {
            if(dbObj instanceof OracleVO){
                OracleVO oraDB = (OracleVO) dbObj;
                dbIteams.appendChild(createXMLOracleDBInfo(oraDB, document));
            }else if(dbObj instanceof MsSQLVO){
                MsSQLVO msSQLDB = (MsSQLVO) dbObj;
                dbIteams.appendChild(createXMLMsSQLDBInfo(msSQLDB, document));
            }else if(dbObj instanceof MySQLVO){
                MySQLVO mySQLDB = (MySQLVO) dbObj;
                dbIteams.appendChild(createXMLMySQLDBInfo(mySQLDB, document));
            }
        }
        return dbIteams;
    }
    /**
     * XML 문서 내용 부분 생성 : dbItems 의  Oracle DB Iteam
     * @param oraDB : OracleVO
     * @param document 
     * @return
     */
    private Element createXMLOracleDBInfo(OracleVO oraDB, Document document) {
        Element dbItem = document.createElement("dbItem");
        Element dbType = document.createElement("dbType");
        Element dbEnum = document.createElement("dbEnum");
        Element dbId = document.createElement("accessId");
        Element dbPw = document.createElement("accessPw");
        Element dbIP= document.createElement("accessIp");
        Element dbPort = document.createElement("accessPort");
        Element dbSid = document.createElement("accessSID");
        
        dbType.setTextContent(oraDB.getDbType());
        dbEnum.setTextContent(oraDB.getDbEnum());
        dbId.setTextContent(oraDB.getAccessId());
        dbPw.setTextContent(oraDB.getAccessPw());
        dbIP.setTextContent(oraDB.getAccessIp());
        dbPort.setTextContent(oraDB.getAccessPort());
        dbSid.setTextContent(oraDB.getAccessSID());
        
        dbItem.appendChild(dbType);
        dbItem.appendChild(dbEnum);
        dbItem.appendChild(dbId);
        dbItem.appendChild(dbPw);
        dbItem.appendChild(dbIP);
        dbItem.appendChild(dbPort);
        dbItem.appendChild(dbSid);
        return dbItem;
    }
    /**
     * XML 문서 내용 부분 생성 : dbItems 의  MsSQL DB Iteam
     * @param msSQLDB : MsSQLVO
     * @param document
     * @return
     */
    private Element createXMLMsSQLDBInfo(MsSQLVO msSQLDB, Document document) {
        Element dbItem = document.createElement("dbItem");
        Element dbType = document.createElement("dbType");
        Element dbEnum = document.createElement("dbEnum");
        Element dbId = document.createElement("accessId");
        Element dbPw = document.createElement("accessPw");
        Element dbIP= document.createElement("accessIp");
        Element dbPort = document.createElement("accessPort");
        Element dbDBName = document.createElement("accessDBName");

        dbType.setTextContent(msSQLDB.getDbType());
        dbEnum.setTextContent(msSQLDB.getDbEnum());
        dbId.setTextContent(msSQLDB.getAccessId());
        dbPw.setTextContent(msSQLDB.getAccessPw());
        dbIP.setTextContent(msSQLDB.getAccessIp());
        dbPort.setTextContent(msSQLDB.getAccessPort());
        dbDBName.setTextContent(msSQLDB.getAccessDBName());
        
        dbItem.appendChild(dbType);
        dbItem.appendChild(dbEnum);
        dbItem.appendChild(dbId);
        dbItem.appendChild(dbPw);
        dbItem.appendChild(dbIP);
        dbItem.appendChild(dbPort);
        dbItem.appendChild(dbDBName);
        return dbItem;
    }
    /**
     * XML 문서 내용 부분 생성 : dbItems 의  MySQL DB Iteam
     * @param mySQLDB : MySQLVO
     * @param document
     * @return
     */
    private Element createXMLMySQLDBInfo(MySQLVO mySQLDB, Document document) {
        Element dbItem = document.createElement("dbItem");
        Element dbType = document.createElement("dbType");
        Element dbEnum = document.createElement("dbEnum");
        Element dbId = document.createElement("accessId");
        Element dbPw = document.createElement("accessPw");
        Element dbIP= document.createElement("accessIp");
        Element dbPort = document.createElement("accessPort");
        Element dbDBName = document.createElement("accessDBName");

        dbType.setTextContent(mySQLDB.getDbType());
        dbEnum.setTextContent(mySQLDB.getDbEnum());
        dbId.setTextContent(mySQLDB.getAccessId());
        dbPw.setTextContent(mySQLDB.getAccessPw());
        dbIP.setTextContent(mySQLDB.getAccessIp());
        dbPort.setTextContent(mySQLDB.getAccessPort());
        dbDBName.setTextContent(mySQLDB.getAccessDBName());
        
        dbItem.appendChild(dbType);
        dbItem.appendChild(dbEnum);
        dbItem.appendChild(dbId);
        dbItem.appendChild(dbPw);
        dbItem.appendChild(dbIP);
        dbItem.appendChild(dbPort);
        dbItem.appendChild(dbDBName);
        return dbItem;
    }
    /**
     * xml 읽기 
     * --기본경로
     * @return Document
     * @throws SAXException
     * @throws IOException
     * @throws ParserConfigurationException
     */
    private Document readXMLDoc() throws SAXException, IOException, ParserConfigurationException {
        return readXMLDoc(this.fileUrl);
    }
    /**
     * xml 읽기 
     * --임의경로
     * @param fileUrl 임의경로
     * @return Document
     * @throws SAXException
     * @throws IOException
     * @throws ParserConfigurationException
     */
    private Document readXMLDoc(String fileUrl) throws SAXException, IOException, ParserConfigurationException {
        File f = new File(fileUrl);
        InputStream is = new FileInputStream(f);
        // DOM Document 객체 생성하기 위한 메서드
        DocumentBuilderFactory bf = DocumentBuilderFactory.newInstance();
        // DOM 파서로부터 입력받은 파일을 파싱하도록 요청
        DocumentBuilder parser = bf.newDocumentBuilder();
//        Document xmlDoc = null;
        // DOM 파서로부터 입력받은 파일을 파싱하도록 요청
        Document result = parser.parse(is);
//        if(is!=null)is.close();
        return result;
    }
    
    /**
     * xml 문서 생성 : 기본경로에 생성한다.
     * @param document 문서 내용
     * @throws TransformerConfigurationException
     * @throws TransformerException
     * @throws TransformerFactoryConfigurationError
     * @throws IOException 
     */
    private void craetXmlDoc(Element document) throws TransformerConfigurationException, TransformerException, TransformerFactoryConfigurationError, IOException{
        craetXmlDoc(document, this.fileName);
    }
    /**
     * xml 문서 생성  : 임의경로에 생성한다.
     * @param document 문서 내용
     * @param fileName 임의경로
     * @throws TransformerConfigurationException
     * @throws TransformerException
     * @throws TransformerFactoryConfigurationError
     * @throws IOException 
     */
    private void craetXmlDoc(Element document, String fileName) throws TransformerConfigurationException, TransformerException, TransformerFactoryConfigurationError, IOException{
        File f = new File(fileName);
        OutputStream os = new FileOutputStream(f); 
        StreamResult xmlFile = new StreamResult(os);
        DOMSource xmlDOM = new DOMSource(document);
        TransformerFactory tff = TransformerFactory.newInstance();
        Transformer tf = tff.newTransformer();
        tf.transform(xmlDOM, xmlFile);
        if(os!=null){
            os.flush();
            os.close();
        }
    }
    /** 
     * 파일 존재 확인 : 기본경로
     * @param fileName : 기본경로
     * @return 
     */
    private boolean chkIsFile(){
        return chkIsFile(this.fileName);
    }
    /** 
     * 파일 존재 확인 
     * @param fileName : 임의경로
     * @return
     */
    private  boolean chkIsFile(String fileName){
        File f = new File(fileName);
        return f.isFile();//파일 존재
    }
    /** 
     * 디렉토리 존재 확인 : 기본경로
     * @param driName 기본경로
     * @return
     */
    private  boolean chkIsDirectory(){
        return chkIsDirectory(this.driName);
    }
    /** 
     * 디렉토리 존재 확인 : 임의경로
     * @param driName 임의 경로
     * @return
     */
    private  boolean chkIsDirectory(String driName){
        File d = new File(driName);
        return d.isDirectory();//파일의 디렉토리 존재여부 확인 
    }
    
    private void chkObjVO(Object obj){
        if(obj instanceof OracleVO){
            OracleVO oraVO = (OracleVO) obj;
        }else if(obj instanceof MySQLVO){
            MySQLVO MysVO = (MySQLVO) obj;
        }else if(obj instanceof MsSQLVO){
            MsSQLVO MssVO = (MsSQLVO) obj;
        }
    }
}