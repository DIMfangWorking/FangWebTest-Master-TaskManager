package com.xinwei.taskmanager.services.util.impl;

import com.xinwei.taskmanager.services.facade.impl.AbstractFacadeTaskServicerImpl;
import com.xinwei.taskmanager.services.util.AtomOperationService;

public class LoadVersionOperationServiceImpl extends AtomOperation implements AtomOperationService {

	@Override
	public void atomAction(Object...objects) {
		AbstractFacadeTaskServicerImpl.loadedVersionFalg = true;
	}

}
