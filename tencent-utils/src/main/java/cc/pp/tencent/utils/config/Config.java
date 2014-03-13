package cc.pp.tencent.utils.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * User: chenwei@pp.cc
 * Date: 14-1-11
 * Time: 下午10:20.
 */
public class Config {

	private static final Logger logger = LoggerFactory.getLogger(Config.class);
	static Properties config = new Properties() {

		private static final long serialVersionUID = -5435827645420544689L;

		{
			try (InputStream in = Config.class.getClassLoader().getResourceAsStream("conf.properties")) {
				this.load(in);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	};

	public static String get(String key) {
		return config.getProperty(key);
	}

	public static Properties getProps(String confFileName) {
		Properties result = new Properties();
		logger.info("Load resource: " + confFileName);
		try (InputStream in = Config.class.getClassLoader().getResourceAsStream(confFileName)) {
			result.load(in);
			return result;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}


}
