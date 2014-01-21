package au.net.asoftware.metamovie.utility;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

	private final static Logger logger = LoggerFactory.getLogger(Main.class.getName());

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException {

		// Base paths:
		File dataBasePath = null;

		if (getHostname().equals("zion")) {

			dataBasePath = new File("D:/sde/data/movies");

		} else if (getHostname().equals("romulas")) {

			dataBasePath = new File("b:/movies");

		} else if (getHostname().startsWith("RAVX")) {

			dataBasePath = new File("C:/sde/data/movies");

		} else {
			logger.error("What box are you working on? not one I know about");
			System.exit(1);
		}

		logger.info("metaMovieCli starting up, whirr click...\n");

		Directory parentDirectory = new Directory(dataBasePath);

		// parentDirectory.fullReport(dataBasePath);

		parentDirectory.summaryReport(dataBasePath);

		logger.info("metaMovieCli shutting down, grind clunk...");
	}

	/**
	 * 
	 * @return String hostName
	 */
	private static String getHostname() {
		InetAddress addr = null;
		try {
			addr = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			String msg = "Unable to determine the systems hostname";
			System.err.println(msg);
			System.exit(1);
		}
		// byte[] ipAddr = addr.getAddress();
		String hostName = addr.getHostName();
		return hostName;
	}
}
