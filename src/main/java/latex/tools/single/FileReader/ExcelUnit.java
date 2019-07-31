package latex.tools.single.excelReader;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;

public class ExcelUnit {
    public static Logger logger = LoggerFactory.getLogger(ExcelUnit.class);

    public Workbook ExcelReader(String fileDir) {
        logger.trace("filedir is {}",fileDir);
        Workbook wb = null;
        try {
            wb = WorkbookFactory.create(new FileInputStream(
                    this.getClass().getResource(fileDir).getPath()));
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
