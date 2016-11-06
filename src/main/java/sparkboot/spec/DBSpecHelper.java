package sparkboot.spec;

import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.Configuration;
import org.javalite.activejdbc.Registry;
import org.javalite.test.jspec.JSpecSupport;
import org.junit.After;
import org.junit.Before;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by misha on 12.05.2016.
 */
public class DBSpecHelper extends JSpecSupport {

    protected static Properties db;
    protected static String env;

    static {
        Configuration config = Registry.INSTANCE.getConfiguration();
        env = config.getEnvironment();
        if (env.equals("development")) {
            env += ".test";
        }
        db = new Properties();
        try {
            db.load(DBSpecHelper.class.getResourceAsStream("/database.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Before
    public void setUpDB() {
        openDbConnection();
    }

    @After
    public void closeDB() {
        Base.rollbackTransaction();
        if (Base.hasConnection()) {
            Base.close();
        }
    }

    protected static void openDbConnection() {
        Properties props = new Properties();
        props.put("user", db.getProperty(env + ".username"));
        props.put("password", db.getProperty(env + ".password"));
        props.put("useUnicode", "true");
        props.put("characterEncoding", "UTF-8");
        props.put("characterSetResults", "UTF-8");
        if (!Base.hasConnection()) {
            Base.open(db.getProperty(env + ".driver"), db.getProperty(env + ".url"), props);
            Base.openTransaction();
        }
    }
}
