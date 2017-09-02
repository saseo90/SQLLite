package lite.sql.utility.text;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

public class FileTxtOut {
	public void filetxt(List<String> tableList, String path, String fileName) {
		try {
			System.out.println("?????");
			String srcText = new String();
			File file = new File(path + "/" + fileName);
			file.createNewFile();
			FileOutputStream  fos = new FileOutputStream(file);
			OutputStreamWriter osw = new OutputStreamWriter(fos);			
			BufferedWriter output =new BufferedWriter(osw);
			
			for (int i = 0; i < tableList.size(); i++) {
				StringBuilder  sb = new StringBuilder();
				TextOut to  =new TextOut();
				sb = to.table(tableList.get(i));
				output.write(sb.toString());
				output.newLine();
			}
			
			output.close();			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
