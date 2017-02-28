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
import java.util.*;

/**
 * Created by Administrator on 2017/2/27.
 */
public class TestMain {
    public static Logger logger = LoggerFactory.getLogger(TestMain.class);
    private static ArrayList<SQLResultBean> resultList = new ArrayList<SQLResultBean>();

    private ArrayList<SQLResultBean> EntLoanList = new ArrayList<SQLResultBean>();
    private ArrayList<SQLResultBean> EntNoLoanList = new ArrayList<SQLResultBean>();
    private ArrayList<SQLResultBean> GovLoanList = new ArrayList<SQLResultBean>();
    private ArrayList<SQLResultBean> GovNoLoanList = new ArrayList<SQLResultBean>();

    public static void main(String[] args) {
        TestMain test = new TestMain();
        Workbook wb =test.ExcelReader("/excel/source.xlsx");
        test.ConvertToBeanMap(wb);
        test.DataSliper(resultList);
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

    public void ConvertToBeanMap(Workbook wb) {
        logger.trace("entry ConvertToBean Unit");
        Sheet sqlResult = wb.getSheet("SQLResults");
        for (Iterator rowit = sqlResult.rowIterator(); rowit.hasNext(); ) {
            SQLResultBean sqlresult = new SQLResultBean();
            XSSFRow row = (XSSFRow) rowit.next();

            sqlresult.setJglb(row.getCell(8).toString());
            sqlresult.setJgmc(row.getCell(3).toString());
            sqlresult.setKhmc(row.getCell(5).toString());
            sqlresult.setNrj(row.getCell(6).toString());
            sqlresult.setSyfd(row.getCell(7).toString());
            sqlresult.setYhmc(row.getCell(2).toString());

            resultList.add(sqlresult);
        }
        logger.info("totoal number of resultList is {}",resultList.size());
    }
    public void DataSliper(ArrayList<SQLResultBean> resultList){
        for (Iterator it = resultList.listIterator();it.hasNext();){
            SQLResultBean result = (SQLResultBean)it.next();
            //logger.trace("{}",result.getSyfd().equals("0"));
            switch(result.getSyfd()) {
                case "0":
                    //无贷户
                    NoLoan(result);
                    // logger.trace("NoLoan - {}",result.getJglb());
                    break;
                default :
                    //有贷户
                    Loan(result);
                    break;
            }
        }
        whatInList(EntNoLoanList);
    }

    public void NoLoan(SQLResultBean bean){
        if (bean.getJglb().equals("01")||bean.getJglb().equals("02")
                ||bean.getJglb().equals("13")||bean.getJglb().equals("14")){
            EntNoLoanList.add(bean);
           // logger.trace("NoLoan");
        }else
        {
            GovNoLoanList.add(bean);
        }
    }

    public void Loan(SQLResultBean bean){
        if (bean.getJglb().equals("01")||bean.getJglb().equals("02")
                ||bean.getJglb().equals("13")||bean.getJglb().equals("14")){
            EntLoanList.add(bean);
        }else
        {
            GovLoanList.add(bean);
        }
    }

    public void whatInList(ArrayList<SQLResultBean> list){
        for(int i =0;i<list.size();i++){
            SQLResultBean bean = list.get(i);
            logger.trace("{}-{}-{}-{}",i,bean.getJgmc(),bean.getNrj(),bean.getJglb());
        }
    }
}
