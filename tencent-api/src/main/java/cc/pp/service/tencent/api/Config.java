package cc.pp.service.tencent.api;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by chenwei on 14-2-20.
 */
public class Config {

	private static Logger logger = LoggerFactory.getLogger(Config.class);

	static Properties config = new Properties() {

		private static final long serialVersionUID = 1L;

		{
			try (InputStream in = Config.class.getClassLoader().getResourceAsStream("conf.properties")) {
				if (in != null) {
					this.load(in);
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	};

	public static String get(String key) {
		return config.getProperty(key);
	}

	public static String get(String key, String defaultValue) {
		return config.getProperty(key, defaultValue);
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
