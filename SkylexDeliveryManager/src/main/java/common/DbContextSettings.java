package common;

import java.util.HashMap;
import java.util.Map;

public class DbContextSettings {
    public static Map<String,String> contextSettings()
    {
        var contextSettings = new HashMap<String,String>();
        contextSettings.put("hibernate.connection.driver_class", "org.h2.Driver");
        contextSettings.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        contextSettings.put("hibernate.connection.url", "jdbc:h2:file:./projectDb");
        contextSettings.put("hibernate.connection.username", "sa");
        contextSettings.put("hibernate.connection.password", "");
        contextSettings.put("hibernate.show_sql", "true");
        contextSettings.put("hibernate.hbm2ddl.auto", "create");
        return contextSettings;
    }
}
