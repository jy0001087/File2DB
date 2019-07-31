package latex.tools.single.FileReader.Mapper;

import latex.tools.single.FileReader.xmlBeans.SQLResultBean;

/**
 * Created by Bud on 2017/2/28.
 */
public interface ISQLResultBean {
    public SQLResultBean selectOne();
    public void addSQLResult(SQLResultBean bean);
}
