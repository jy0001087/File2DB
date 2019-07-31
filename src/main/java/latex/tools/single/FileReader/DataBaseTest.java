package latex.tools.single.FileReader;

import latex.tools.single.FileReader.Mapper.ISQLResultBean;
import latex.tools.single.FileReader.xmlBeans.SQLResultBean;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Bud on 2017/2/28.
 * 数据库连接性测试
 */
public class DataBaseTest {
    public static Logger logger = LoggerFactory.getLogger(DataBaseTest.class);

    public static void main(String[] args) {
        SqlSession session = (new DataBaseTest().getSession()).openSession();
        //SQLResultBean bean = (SQLResultBean)session.selectOne("latex.tools.single.FileReader.selectOne");
        ISQLResultBean oper=session.getMapper(ISQLResultBean.class);
        SQLResultBean bean = oper.selectOne();
/**
        SQLResultBean addbean = new SQLResultBean();
        addbean.setJglb("2");
        addbean.setJgmc("2");
        addbean.setKhmc("2");
        addbean.setNrj("2");
        addbean.setSyfd("2");
        addbean.setYhmc("2");
        oper.addSQLResult(addbean);
        session.commit();
 **/
        logger.trace(bean.getYhmc());
    }

    public SqlSessionFactory getSession() {
        SqlSessionFactory sessionFactory = null;
        try {
            InputStream in = Resources.getResourceAsStream("mybatis.xml");
             sessionFactory =  new SqlSessionFactoryBuilder().build(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sessionFactory;
    }
}
