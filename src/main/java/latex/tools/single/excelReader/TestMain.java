package latex.tools.single.excelReader;

import com.sun.xml.internal.ws.api.pipe.FiberContextSwitchInterceptor;
import latex.tools.single.excelReader.Mapper.ISQLResultBean;
import latex.tools.single.excelReader.xmlBeans.SQLResultBean;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/2/27.
 */
public class TestMain {
    public static Logger logger = LoggerFactory.getLogger(TestMain.class);
    private static ArrayList<SQLResultBean> resultList = new ArrayList<SQLResultBean>();

    public static void main(String[] args) {
        ArrayList<String> fileList = new ArrayList<String>();
        Workbook wb = null;

        fileList.add("/excel/TZ_1.xlsx");
        fileList.add("/excel/TZ_12.xlsx");
        fileList.add("/excel/DQ_1.xlsx");
        fileList.add("/excel/DQ_12.xlsx");
        fileList.add("/excel/HQ_1.xlsx");
        fileList.add("/excel/HQ_12.xlsx");
        TestMain test = new TestMain();
        for(int i=0;i<fileList.size();i++) {
            wb = test.ExcelReader(fileList.get(i));
            test.ConvertToBeanMap(wb,fileList.get(i));
            test.InsertCibCustDep(resultList);
        }
    }

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

    public void ConvertToBeanMap(Workbook wb,String filedir) {
        logger.trace("entry ConvertToBean Unit");
        String depositType="";
        String depositMonth="";
        Pattern pattern = Pattern.compile("[A-Z]{2}");
        Matcher matcher = pattern.matcher(filedir);
        while(matcher.find()){
           depositType = matcher.group();
        }
        Pattern patternNum = Pattern.compile("[0-9]{1,2}");
        Matcher matcherNum = patternNum.matcher(filedir);
        while (matcherNum.find()){
            depositMonth = matcherNum.group();
        }

        Sheet sqlResult = wb.getSheet("SQL Results");
        for (Iterator rowit = sqlResult.rowIterator(); rowit.hasNext(); ) {
            SQLResultBean sqlresult = new SQLResultBean();
            XSSFRow row = (XSSFRow) rowit.next();

            sqlresult.setJglb(row.getCell(8).toString());
            sqlresult.setJgmc(row.getCell(3).toString());
            sqlresult.setKhmc(row.getCell(5).toString());
            sqlresult.setNrj(row.getCell(6).toString());
            sqlresult.setSyfd(row.getCell(7).toString());
            sqlresult.setYhmc(row.getCell(2).toString());
            sqlresult.setDepositType(depositType);
            sqlresult.setDepositMonth(depositMonth);
            resultList.add(sqlresult);
        }
        logger.info("totoal number of resultList is {}",resultList.size());
    }

    public void InsertCibCustDep(ArrayList<SQLResultBean> bean){
        SqlSessionFactory factory = new DataBaseTest().getSession();
        SqlSession session = factory.openSession();
        ISQLResultBean oper = session.getMapper(ISQLResultBean.class);
        for (int i =0;i<bean.size();i++){
            oper.addSQLResult(bean.get(i));
        }
        session.commit();
    }

    public void whatInList(ArrayList<SQLResultBean> list){
        for(int i =0;i<list.size();i++){
            SQLResultBean bean = list.get(i);
            logger.trace("{}-{}-{}-{}",i,bean.getJgmc(),bean.getNrj(),bean.getJglb());
        }
    }
}
