package com.xinwei.taskmanager.services.util.impl;

import org.dom4j.Element;

import com.xinwei.taskmanager.model.sub.SubResource;
import com.xinwei.taskmanager.services.util.AtomOperationService;

public class PDNIPOperationServiceImpl extends AtomOperation implements AtomOperationService {

	@Override
	public void atomAction(Object...objects) {
		Element atomActionXML = null;
		SubResource subResource = null;
		for (Object object : objects) {
			if (object instanceof Element) {
				atomActionXML = (Element) object;
			} else if (object instanceof SubResource) {
				subResource = (SubResource) object;
			}
		}
		Element atom = atomActionXML.addElement("Property");
		atom.addAttribute("name", "PDNIP");
		atom.addAttribute("value", String.valueOf(subResource.getPdnip()));
		
	}

}
