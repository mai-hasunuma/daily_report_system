package listener;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Application Lifecycle Listener implementation class PropertiesListener
 *
 */
@WebListener
public class PropertiesListener implements ServletContextListener {

    /**
     * Default constructor.
     */
    public PropertiesListener() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0)  {
         // TODO Auto-generated method stub
    }

    /**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0)  {
        // アプリケーションスコープの取得
         ServletContext context = arg0.getServletContext();
         // .getRealPath 絶対パスの取得
         String path = context.getRealPath("/META-INF/application.properties");
         try {
             /*propertiesから変数と価を取得*/
             // InputStreamはread oly
             InputStream is = new FileInputStream(path);
             Properties properties = new Properties();
             properties.load(is);
             is.close();

             // 変数名のリストのイテレータを取得
             /*properties.stringPropertyNames() ではPropertiesインスタンスが保持しているキーの名前である
             String1,String2,String3,String4が返される
             .iterator()とすることでこのように[String1,String2,String3,String4]配列で保存されpitに代入*/
             Iterator<String> pit = properties.stringPropertyNames().iterator();
             while(pit.hasNext()) {
                 String pname = pit.next();
                 /*まずpnameにキーであるString1が代入され、properties.getProperty(String1)= 値となる
                         　　　　　　これがキーの数文だけ実行されアプリケーションスコープに代入される*/
                 context.setAttribute(pname, properties.getProperty(pname));
             }
         } catch(FileNotFoundException e) {
         } catch(IOException e) {
         }
    }

}
