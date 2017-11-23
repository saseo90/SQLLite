package org.sqllite.manager.login.vo;
/**
 * 로그인 처리를 위한 View Object 클래스
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
public class LoginAccessVo {
   private String adminNo="";//PK_NO
   private String adminId="";//아이디
   private String adminPw="";//비밀번호
   private String adminName="";//이름
public String getAdminNo() {
    return adminNo;
}
public void setAdminNo(String adminNo) {
    this.adminNo = adminNo;
}
public String getAdminId() {
    return adminId;
}
public void setAdminId(String adminId) {
    this.adminId = adminId;
}
public String getAdminPw() {
    return adminPw;
}
public void setAdminPw(String adminPw) {
    this.adminPw = adminPw;
}
public String getAdminName() {
    return adminName;
}
public void setAdminName(String adminName) {
    this.adminName = adminName;
}
@Override
public String toString() {
    return "LoginAccessVo [adminNo=" + adminNo + ", adminId=" + adminId + ", adminPw=" + adminPw + ", adminName="
            + adminName + "]";
}
}