package com.codelocker.model;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class ExceptionLogger {
	static Logger logger;// = Logger.getLogger(LoggableClass.class);
	
	public ExceptionLogger(Class loggableClass) {
		logger = Logger.getLogger(loggableClass);
		PropertyConfigurator.configure("./configs/base.configuration");
	}
	
	public void generateTraceReport(String message) {
		logger.trace(message);
	}
	
	public void generateDebugReport(String message) {
		logger.debug(message);
	}
	
	public void generateInfoReport(String message) {
		logger.info(message);
	}
	
	public void generateWarnReport(String message) {
		logger.warn(message);
	}
	
	public void generateErrorReport(String message) {
		logger.error(message);
	}
	
	public void generateFatalReport(String message) {
		logger.fatal(message);
	}
}
