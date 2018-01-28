package lite.sql.utility.poi.outputexcel;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import lite.sql.frontdoor.loginPage.loginList.Connect;
import lite.sql.backdoor.dao.DatabaseDao;
import lite.sql.backdoor.dao.OracleDaoImpl;
import lite.sql.backdoor.dao.QueryResultVo;



public class PoiInput
{
	public void poiInput(String fileName, String filePath)
	{
		DatabaseDao dao = Connect.dao;
		QueryResultVo vo = dao.checkQuery(inputQuery(fileName, filePath));
		if(vo.getError() != null){
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("확인");
			alert.setContentText(vo.getError());
			alert.showAndWait().get();
		}else{
			System.out.println("insert 성공!!~");
		}
		
	}
	
	private String inputQuery(String fileName, String filePath)
	{
		HSSFRow row;
		HSSFCell cell;
		ArrayList<String> query2 = new ArrayList<String>();
		String query = "";
		String query1 = "";
		String query3 = "";
		String a = "";
		List<String> columnValueList = new ArrayList<String>();
		
		try
		{
			FileInputStream fis = new FileInputStream(filePath+ "/"+fileName);
			HSSFWorkbook xlsxWb = new HSSFWorkbook(fis);

			// 시트 개수 획득
			int sheetCn = xlsxWb.getNumberOfSheets();
			System.out.println("sheet수 : " + sheetCn);

			for (int i = 0; i < sheetCn; i++)
			{
				// 0번쨰 시트 정보 취득
				HSSFSheet sheet = xlsxWb.getSheetAt(i);

				// 취득된 sheet에서 row수 획득

				int rows = sheet.getPhysicalNumberOfRows();
				System.out.println("sheet의 row 수: " + rows);

				// 취득된 row에서 cell수 획득
				int cells = sheet.getRow(i).getPhysicalNumberOfCells();
				System.out.println("sheet의 row에 취득대상 cell수: " + cells);

				System.out.println("cells수");
				System.out.println(cells);
				/*
				 * row = sheet.getRow(0); // row 가져오기 if(row != null) { for(int q =1; q<cells; q++) { cell = row.getCell(q); System.out.println(cell.toString()); columnName[q-1] = cell.toString(); } }
				 */
				for (int r = 1; r < rows; r++)
				{
					row = sheet.getRow(r);
					String value = "";

					for (int c = 0; c < cells; c++)
					{
						cell = row.getCell(c);
						
						if(cell.getStringCellValue()==null || cell.getStringCellValue().equals("null"))
						{
							if(c==cells-1) value += "null";
							else
							{
								value+= "null, ";
							}
						}
						else
						{
							value += "'" + cell.getStringCellValue() + "',  ";
							if (c == cells - 1)
							{
								value += "'" + cell.getStringCellValue() + "'";
							}
						}
					}
					System.out.println(value);
					columnValueList.add(value);

				}
				// System.out.println(columnValueList.get(0).toString());
				query1 = "insert all ";

				for (int c = 0; c < columnValueList.size(); c++) // .replace("[", "(").replace("]", ")")
				{
					a = "into "+sheet.getSheetName()+" values(" + columnValueList.get(c).toString() + ")";
					query2.add(a);
				}
				// System.out.println(query2);

				query3 = "select * from dual;";
				query = query1 + query2.toString().replace("[", "").replace("]", "").replace("),", ")").replace(" 00:00:00", "") + query3;
				System.out.println(query.toString());
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return query;
	}
}
