package latex.tools.single.excelReader;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2017/2/27.
 */
public class TestMain {
    public static Logger logger = LoggerFactory.getLogger(TestMain.class);

    public static void main(String[] args) {
        TestMain test = new TestMain();
        Workbook wb =test.ExcelReader("/excel/source.xlsx");
        
    }

    public Workbook ExcelReader(String fileDir) {
        Workbook wb = null;
        try {
            wb = WorkbookFactory.create(new FileInputStream(this.getClass().getResource(fileDir).getPath()));
        }catch(IOException e){
            logger.error("can't find excel file");
            e.printStackTrace();
        }catch(InvalidFormatException e){
            logger.error("not currect xlsx file");
            e.printStackTrace();
        }
        return wb;
    }
}
