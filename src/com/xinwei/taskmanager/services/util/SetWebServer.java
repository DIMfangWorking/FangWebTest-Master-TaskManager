package com.xinwei.taskmanager.services.util;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.ibatis.jdbc.Null;
import org.slf4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.xinwei.taskmanager.model.conf.WebServer;


public class SetWebServer {
	private static Logger logger = null;
	public static WebServer setWebServer(WebServer server) {
		server = new WebServer();
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.parse("taskmanager-config.xml");
			NodeList nodeList = document.getElementsByTagName("web_server");
			Node node = nodeList.item(0);
			Element element = (Element) node;
			server.setIp(element.getAttribute("ip"));
			server.setPort(Integer.parseInt(element.getAttribute("port")));
		} catch (Throwable e) {
			logger.info(e.getMessage());
		}
		return server;
	}
}
