package latex.tools.single.excelReader;

import latex.tools.single.excelReader.xmlBeans.SQLResultBean;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Administrator on 2017/2/27.
 */
public class TestMain {
    public static Logger logger = LoggerFactory.getLogger(TestMain.class);
    private ArrayList<SQLResultBean> resultList = new ArrayList<SQLResultBean>();
    static HashMap<String,Integer> reslutmap1 = new HashMap<String,Integer>();
    static HashMap<String,Integer> resultmap2= new HashMap<String,Integer>();
    static HashMap<String,Integer> resultmap3 = new HashMap<String,Integer>();
    static HashMap<String,Integer> resultmap4 = new HashMap<String,Integer>();

    public static void main(String[] args) {
        TestMain test = new TestMain();
        Workbook wb =test.ExcelReader("/excel/source.xlsx");
        test.CIBwork(wb);
       // test.whatInMap(reslutmap1);
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

    public void CIBwork(Workbook wb){
        logger.trace("entry CIBwork Unit");
        Sheet sqlResult = wb.getSheet("SQLResults");
        reslutmap1 = new HashMap<String,Integer>();
        for (Iterator rowit = sqlResult.rowIterator();rowit.hasNext();){
            SQLResultBean sqlresult = new SQLResultBean();
            XSSFRow row = (XSSFRow) rowit.next();

            sqlresult.setJglb(row.getCell(2).toString());
            sqlresult.setJgmc(row.getCell(3).toString());
            sqlresult.setKhmc(row.getCell(5).toString());
            sqlresult.setNRJ(row.getCell(6).toString());
            sqlresult.setSyfd(row.getCell(7).toString());
            sqlresult.setYhmc(row.getCell(8).toString());

            if (!reslutmap1.containsKey(sqlresult.getJgmc())){
                reslutmap1.put(sqlresult.getJgmc(),0);
            }

            whatInMap(reslutmap1);
           // logger.trace("cell value is : {}",row.getCell(1));
            resultList.add(sqlresult);
        }

        resultmap2=(HashMap<String,Integer>)reslutmap1.clone();
        resultmap3=(HashMap<String,Integer>)reslutmap1.clone();
        resultmap4=(HashMap<String,Integer>)reslutmap1.clone();


        for (Iterator it = resultList.listIterator();it.hasNext();){
            SQLResultBean result = (SQLResultBean)it.next();
            switch(result.getSyfd()) {
                case "0":
                    EnterpriseCus(result);
                    //logger.trace("here");
                    break;
                case "05":
                    EnterpriseCus(result);
                    break;
                default :
                    GOVCus(result);
                    break;
            }
        }

    }

    public void EnterpriseCus(SQLResultBean bean){
        switch(bean.getJglb()){
            case "01":
                if (reslutmap1.containsKey(bean.getJgmc())){
                    reslutmap1.put(bean.getJgmc(),Integer.valueOf(reslutmap1.get(bean.getJgmc()))+Integer.valueOf(bean.getNRJ()));
                }
                break;
        }
    }

    public void GOVCus(SQLResultBean bean){
        switch(bean.getJglb()){
            case "0":
                if (resultmap3.containsKey(bean.getJgmc())){
                    resultmap3.put(bean.getJgmc(),Integer.valueOf(resultmap3.get(bean.getJgmc()))+Integer.valueOf(bean.getNRJ()));
                }
                break;
        }
    }

    public void whatInMap(HashMap<String,Integer> map){
        for(Map.Entry<String,Integer> entry:map.entrySet()){
            logger.info("-----------");
            logger.info("{} : {}",entry.getKey(),entry.getValue());
        }
    }
}
