package lite.sql.utility.poi.inputexcel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFooter;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import lite.sql.frontdoor.loginPage.loginList.Connect;
import lite.sql.frontdoor.main.MainPanController;
import lite.sql.backdoor.dao.DatabaseDao;
import lite.sql.backdoor.dao.OracleDaoImpl;

public class PoiOut {
	
	public void poiOutput(List<String> tableList, String path, String title) {

		try {

			FileOutputStream fos = setFile(path, title);
			System.out.println("setFile");
			HSSFWorkbook exWorkbook = mekeExcel(tableList);
			System.out.println("setExcel");
			exWorkbook.write(fos);
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private FileOutputStream setFile(String filePath, String fileName)
			throws FileNotFoundException {

		File file = new File(filePath);
		System.out.println(filePath);
		System.out.println(fileName);
		if (!file.exists()) {
			file.mkdirs();
		}

		FileOutputStream fos = new FileOutputStream(filePath+"/" + fileName);

		return fos;
	}

	private HSSFWorkbook mekeExcel(List<String> tableList) {
		// TODO Auto-generated method stub
		
		DatabaseDao dao = Connect.dao;
		HSSFWorkbook WorkBook = new HSSFWorkbook();
		System.out.println("????");
		for (int i = 0; i < tableList.size(); i++) {
			HSSFSheet sheet = null;
			HSSFRow row = null;
			HSSFCell cell = null;

			System.out.println(tableList.get(i));
			//WorkBook.setSheetName(i+1, tableList.get(i).toString());
			
			List<List<String>> list = dao.getData(tableList.get(i));
			
			sheet = WorkBook.createSheet(tableList.get(i));
			for (int j = 0; j < list.size(); j++) {
				
				row = sheet.createRow(j);
				for (int k = 0; k < list.get(j).size()-1; k++) {
					cell = row.createCell(k);
					cell.setCellValue(new HSSFRichTextString(list.get(j).get(k+1)));

				}
			}
			
			HSSFPrintSetup hps = sheet.getPrintSetup();
			hps.setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);
			hps.setLandscape(false);
			hps.setScale((short) 100);

			HSSFFooter footer = sheet.getFooter();
			footer.setCenter(HSSFFooter.page() + "/" + HSSFFooter.numPages());

			sheet.setMargin(HSSFSheet.TopMargin, 0.6);
			sheet.setMargin(HSSFSheet.BottomMargin, 0.6);
			sheet.setMargin(HSSFSheet.LeftMargin, 0.6);
			sheet.setMargin(HSSFSheet.RightMargin, 0.6);
		}
		
		
		return WorkBook;
	}
}
