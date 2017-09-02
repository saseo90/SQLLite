package lite.sql.frontdoor.loginPage.loginList;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class LoginList {
	public static void main(String[] args) {
		LoginList ll = new LoginList();
		Scanner scanner = new Scanner(System.in);
		List<LoginUser> lu = ll.userList();
		for (int i = 0; i < lu.size(); i++) {
			System.out.println(lu.get(i));
		}
		try {
			ll.makeXml(lu);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public void makeXml(List<LoginUser> list) throws Exception {
		Document document = DocumentBuilderFactory.newInstance()
				.newDocumentBuilder().newDocument();
		Node userlist = document.createElement("userList");
		document.appendChild(userlist);

		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
			Element user = document.createElement("selectUser");
			userlist.appendChild(user);
			Element ip = document.createElement("ip");
			Element id = document.createElement("id");
			Element pwd = document.createElement("pwd");
			Element sid = document.createElement("sid");
			Element port = document.createElement("port");
			Element dataType = document.createElement("dataType");
			ip.setTextContent(list.get(i).getIp());
			id.setTextContent(list.get(i).getId());
			sid.setTextContent(list.get(i).getSid());
			pwd.setTextContent(list.get(i).getPw());
			port.setTextContent(list.get(i).getPort());
			dataType.setTextContent(list.get(i).getDataType());
			user.appendChild(sid);
			user.appendChild(pwd);
			user.appendChild(id);
			user.appendChild(ip);
			user.appendChild(port);
			user.appendChild(dataType);

		}

		// Document 저장
		DOMSource xmlDOM = new DOMSource(document);
		StreamResult xmlFile = new StreamResult(new File("saved.xml"));
		TransformerFactory.newInstance().newTransformer()
				.transform(xmlDOM, xmlFile);

		// makerj.tistory.com
	}

	public static List<LoginUser> userList() {
		List<LoginUser> list = new ArrayList<LoginUser>();
		try {
			// DOM Document 객체 생성하기 위한 메서드
			DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
			// DOM 파서로부터 입력받은 파일을 파싱하도록 요청
			DocumentBuilder parser = f.newDocumentBuilder();

			// XML 파일 지정
			String url = "saved.xml";

			Document xmlDoc = null;
			// DOM 파서로부터 입력받은 파일을 파싱하도록 요청
			xmlDoc = parser.parse(url);

			// 루트 엘리먼트 접근
			Element root = xmlDoc.getDocumentElement();

			// 하위 엘리먼트 접근
			NodeList n1 = root.getElementsByTagName("selectUser");
			Node bookNode = n1.item(0);
			Element bookElement = (Element) bookNode;

			// 텍스트 (노드) 접근
			NodeList bookN1 = bookNode.getChildNodes();
			// 인덱스 사용시 주의할 것.
			// 엔터키에 해당하느 부분이 읽힐 수 있다.
			// (위 방식 보다는, getElementsByTagName() 을 이용해 접근하는게 좋다.)
			Node titleNode = bookN1.item(1);
			//System.out.println("------------------------------------------------------------------");
			for (int i = 0; i < n1.getLength(); i++) {
				Node bNode = n1.item(i);
				Element bElement = (Element) bNode;
				// String is = bElement.getAttribute("isbn");
				// String ki = bElement.getAttribute("kind");
				String sid = bElement.getElementsByTagName("sid").item(0)
						.getTextContent();
				String pw = bElement.getElementsByTagName("pwd").item(0)
						.getTextContent();
				String id = bElement.getElementsByTagName("id").item(0)
						.getTextContent();
				String ip = bElement.getElementsByTagName("ip").item(0)
						.getTextContent();
				String port = bElement.getElementsByTagName("port").item(0)
						.getTextContent();
				String dataType = bElement.getElementsByTagName("dataType")
						.item(0).getTextContent();
				list.add(new LoginUser(ip, id, pw, sid, port, dataType));
			}

		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return list;
	}

	private static String getTagValue(String sTag, Element eElement) {
		NodeList nlList = eElement.getElementsByTagName(sTag).item(0)
				.getChildNodes();

		Node nValue = (Node) nlList.item(0);

		return nValue.getNodeValue();
	}

}