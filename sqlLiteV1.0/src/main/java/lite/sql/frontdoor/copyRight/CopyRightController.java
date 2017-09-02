package lite.sql.frontdoor.copyRight;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

import lite.sql.backdoor.session.SessionThread;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class CopyRightController implements Initializable{
	


    @FXML
    private ScrollPane imageScroll;

    @FXML
    private VBox loginPane;

    @FXML
    private ScrollPane textScroll;


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		CopyRightThread.cc = this;
		
		CopyRightThread crt = new CopyRightThread();
		crt.setDaemon(true);
		crt.start();
		
		settingImage();
		settingText();

	}


	private void settingImage() {
		ImageView iv1 = new ImageView();
		ImageView iv2 = new ImageView();
		ImageView iv3 = new ImageView();
		ImageView iv4 = new ImageView();
		ImageView iv5 = new ImageView();
		ImageView iv6 = new ImageView();
		ImageView iv7 = new ImageView();
		ImageView iv8 = new ImageView();
		ImageView iv9 = new ImageView();
		ImageView iv10 = new ImageView();
		ImageView iv11 = new ImageView();
		ImageView iv12 = new ImageView();
		try {
			iv1.setImage(new Image(new FileInputStream("Pictures/1.jpg")));
			iv2.setImage(new Image(new FileInputStream("Pictures/2.jpg")));
			iv3.setImage(new Image(new FileInputStream("Pictures/3.jpg")));
			iv4.setImage(new Image(new FileInputStream("Pictures/4.jpg")));
			iv5.setImage(new Image(new FileInputStream("Pictures/5.jpg")));
			iv6.setImage(new Image(new FileInputStream("Pictures/6.jpg")));
			iv7.setImage(new Image(new FileInputStream("Pictures/7.jpg")));
			iv8.setImage(new Image(new FileInputStream("Pictures/8.jpg")));
			iv9.setImage(new Image(new FileInputStream("Pictures/9.jpg")));
			iv10.setImage(new Image(new FileInputStream("Pictures/10.jpg")));
			iv11.setImage(new Image(new FileInputStream("Pictures/11.jpg")));
			iv12.setImage(new Image(new FileInputStream("Pictures/12.jpg")));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		iv1.setFitHeight(350);
		iv2.setFitHeight(350);
		iv3.setFitHeight(350);
		iv4.setFitHeight(350);
		iv5.setFitHeight(350);
		iv6.setFitHeight(350);
		iv7.setFitHeight(350);
		iv8.setFitHeight(350);
		iv9.setFitHeight(350);
		iv10.setFitHeight(350);
		iv11.setFitHeight(350);
		iv12.setFitHeight(350);
		
		iv1.setFitWidth(350);
		iv2.setFitWidth(350);
		iv3.setFitWidth(350);
		iv4.setFitWidth(350);
		iv5.setFitWidth(350);
		iv6.setFitWidth(350);
		iv7.setFitWidth(350);
		iv8.setFitWidth(350);
		iv9.setFitWidth(350);
		iv10.setFitWidth(350);
		iv11.setFitWidth(350);
		iv12.setFitWidth(350);		
		
		VBox root = new VBox();
		root.getChildren().addAll(iv1);
		root.getChildren().addAll(iv2);
		root.getChildren().addAll(iv3);
		root.getChildren().addAll(iv4);
		root.getChildren().addAll(iv5);
		root.getChildren().addAll(iv6);
		root.getChildren().addAll(iv7);
		root.getChildren().addAll(iv8);
		root.getChildren().addAll(iv9);
		root.getChildren().addAll(iv10);
		root.getChildren().addAll(iv11);
		root.getChildren().addAll(iv12);
		imageScroll.setContent(root);
		
	}
	
	
	private void settingText(){
		TextArea tx1 = new TextArea();
		TextArea tx2 = new TextArea();
		TextArea tx3 = new TextArea();
		TextArea tx4 = new TextArea();
		TextArea tx5 = new TextArea();
		TextArea tx6 = new TextArea();
		TextArea tx7 = new TextArea();
		TextArea tx8 = new TextArea();
		TextArea tx9 = new TextArea();
		TextArea tx10 = new TextArea();
		TextArea tx11 = new TextArea();
		TextArea tx12 = new TextArea();
		
		tx1.setPrefHeight(350);
		tx2.setPrefHeight(350);
		tx3.setPrefHeight(350);
		tx4.setPrefHeight(350);
		tx5.setPrefHeight(350);
		tx6.setPrefHeight(350);
		tx7.setPrefHeight(350);
		tx8.setPrefHeight(350);
		tx9.setPrefHeight(350);
		tx10.setPrefHeight(350);
		tx11.setPrefHeight(350);
		tx12.setPrefHeight(350);
		
		tx1.setPrefWidth(600);
		tx2.setPrefWidth(600);
		tx3.setPrefWidth(600);
		tx4.setPrefWidth(600);
		tx5.setPrefWidth(600);
		tx6.setPrefWidth(600);
		tx7.setPrefWidth(600);
		tx8.setPrefWidth(600);
		tx9.setPrefWidth(600);
		tx10.setPrefWidth(600);
		tx11.setPrefWidth(600);
		tx12.setPrefWidth(600);
		
		tx1.setFont(Font.font("Serif", 20));
		tx2.setFont(Font.font("Serif", 20));
		tx3.setFont(Font.font("Serif", 20));
		tx4.setFont(Font.font("Serif", 20));
		tx5.setFont(Font.font("Serif", 20));
		tx6.setFont(Font.font("Serif", 20));
		tx7.setFont(Font.font("Serif", 20));
		tx8.setFont(Font.font("Serif", 20));
		tx9.setFont(Font.font("Serif", 20));
		tx10.setFont(Font.font("Serif", 20));
		tx11.setFont(Font.font("Serif", 20));
		tx12.setFont(Font.font("Serif", 20));
		
		tx1.setWrapText(true);
		tx2.setWrapText(true);
		tx3.setWrapText(true);
		tx4.setWrapText(true);
		tx5.setWrapText(true);
		tx6.setWrapText(true);
		tx7.setWrapText(true);
		tx8.setWrapText(true);
		tx9.setWrapText(true);
		tx10.setWrapText(true);
		tx11.setWrapText(true);
		tx12.setWrapText(true);
		
		tx1.setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY ) ) );
		
		
		tx1.setText("프로젝트를 수행하면서 수업에서 배울 수 있었던 내용을 활용해보기도 하고, 그 외적인 내용도 찾아볼 수 있어 좋은 경험이었습니다."
				+ "조원과의 화합과 협동도 중요했는데, 조장으로써 모자란 점이 많았으나 조원분들의 협조로 무사히 프로젝트를 완료할 수 있었습니다."
				+ "이번 경험을 단순한 수업의 일환보다도 실무와 연관지어 복습해본다면 좀 더 좋은 경험이 되지 않을까 합니다."
				+"\n\n\n-장정훈");
		tx2.setText("개인적인 욕심으로 인해 많은 애들에게 빠르게 "
				+ "끝나도록 독촉하여 미안한점도 많이 들었습니다. "
				+ "동기들의 능력을 믿고 열심히 하는 모습을 보면서 나도 "
				+ "열심히 해야지 하는 생각이 들면서 많은 배울점이 생각이 "
				+ "들었습니다. 모두들 끝나는 시간까지 열심히 하고 주어진 일을 "
				+ "최선을 다하는 모습에 저도 화이팅을 하며 프로젝트를 끝낼수 있어 "
				+ "다행이라고 생각이 듭니다"
				+ "\n\n\n-염규봉");
		tx3.setText("인원이 많아서 소통과 역할에 문제가 있지 않을까 걱정됬었는데 회의로 잘 풀어가며 진행되어서 좋은 결과가 나온것 같습니다."
				+ "한달 가까이 진행하면서 제가 부족한 점을 파악할 수 있었고, 그런 점들을 잘 풀수 있게 이끌어주신 팀장님들에게 감사합니당 짝짝짝"
				+ "\n\n\n-이다정");
		tx4.setText("개발을 진행하며 느낀점을 말하고자 합니다. "
				+ "지난 참관 때 선배동료들이 이구동성으로 말한 소통의 중요함을 "
				+ "다시 느꼈습니다. 단위개발이 진행되자 끝없는 고민과 공부, 그리고 "
				+ "휴식에 대한 갈증을 경험했습니다. 단위개발 후 통합되는 과정에서 소통"
				+ "부족으로 인해 해프닝을 겪었습니다. 이 경험은 나에게 좋은 공부였고 앞으"
				+ "로 개발자로서 금과옥조로 삼겠습니다. 마지막으로 두개조를 한 팀으로 잘 이끌"
				+ "어 준 정훈형님, 부족한 우리를 받혀주고 독려해준 규봉형님께 감사드립니다. "
				+ "이하 동료개발팀원들에게 고맙고 앞으로도 잘부탁드립니다.^&^"
				+ "\n\n\n-이성현");
		tx5.setText("프젝시작할때는 이게 될까 의구심이 많이 들었는데 이렇게 완성된걸보니 대바아아아악인거같아요 팀원들에게 도움이 많이 되지못했지만 많이 배우는 좋은 시간이였습니다 !!!\n\n\n\n-구윤지");
		tx6.setText("혼자였으면 절대 못했을 것 같은데, 팀원들이랑 같이 으쌰으쌰 하니까 프로젝트를 무사히 마칠 수 있었던 것 같습니다. 모르는건 서로 물어보고 하면서 많은것을 배웠습니다. 감사합니다.\n\n\n-임소현");
		tx7.setText("이번 프로젝트를 하면서 팀원 간의 의사소통이 중요하다고 느꼈고"
				+ "프로젝트를 통하여 잘 알지 못했던 부분을 배울수 있어서 좋았으며"
				+ "다음에 프로젝트를 할때에는 팀에 좀더 도움이 될수 있도록 실력을 키워야 겠다는 생각을 했습니다."
				+ "\n\n\n-정성");
		tx8.setText("조별 프로젝트를 하면서 많은 것을 배웠고, 좋은 경험을 한것 같습니다.\n\n\n-이종훈");
		tx9.setText("음...처음에는 막막하고 어떻게해야될지 잘모르는 부분도 많았습니다."
				+ "하지만 팀원들과 소통하고 도움을 받아 하나하나 해나가다보니 재미도 있고"
				+ "흥미도 생겼습니다. 혼자하면 더 힘들고 외로웠을 프로젝트를 팀으로써 더 쉽고"
				+ "재미있게 해나간것같습니다. 그렇습니다. 이것은 팀이기에 더 쉽게 가능했던 일입니다."
				+ "조원들에게 감사합니다\n\n\n-박지현");
		tx10.setText("프로젝트를 처음하면서 막막하기도 하고 어려워 했던 점도 많았지만 팀원들과 소통하면서 이해하기 어려웠던 부분이나 구현하기 힘들었던 부분을 해결할 수 있었습니다."
				+ "\n\n\n-유현덕");
		tx11.setText("프로젝트 시작 할때는 막막하고 이걸 과연 할 수 있을까? 생각으로 시작을 하였으나, 조원사람들의 도움을 많이받아 마무리를 짓게되었습니다."
				+ "이번 프로젝트로 인해 공부가 아주 많이되었습니다. 조원님들 고생 많이 하셨습니다!"
				+ "\n\n\n-서환기");
		tx12.setText("프로젝트를 진행하면서 팀원들과 팀장님덕분에 많은것을 공부했고 또 배웠습니다."
				+ "공부하고 배운만큼 팀에 도움이되고 싶었지만 그러지 못해 많이 아쉬웠던거같습니다."
				+ "그러나 프로젝트를 진행했던 그 시간자체는 참 즐거웠고 재미있었습니다 정말 소중한 경험이였습니다."
				+ "\n\n\n-한흥규");

		VBox root = new VBox();
		root.getChildren().addAll(tx1);
		root.getChildren().addAll(tx2);
		root.getChildren().addAll(tx3);
		root.getChildren().addAll(tx4);
		root.getChildren().addAll(tx5);
		root.getChildren().addAll(tx6);
		root.getChildren().addAll(tx7);
		root.getChildren().addAll(tx8);
		root.getChildren().addAll(tx9);
		root.getChildren().addAll(tx10);
		root.getChildren().addAll(tx11);
		root.getChildren().addAll(tx12);
		textScroll.setContent(root);
	}
	
	public void settingScroll(){
		textScroll.setVvalue(textScroll.getVvalue()+0.000005);
		imageScroll.setVvalue(imageScroll.getVvalue()+0.000005);
	}
	
	

}
