package com.xinwei.taskmanager.dispatch;

import org.slf4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.task.TaskExecutor;

import com.xinwei.taskmanager.dispatch.impl.InnerBusinessControl;
import com.xinwei.uem.util.RPCServer;
import com.xinwei.uem.util.workqueue.AsyncRpcMessageProcessBean;
import com.xinwei.uem.util.workqueue.WorkQueuesRpcServer;

public class Dispatch extends ApplicationObjectSupport implements AsyncRpcMessageProcessBean {
	private TaskExecutor threadPool = null;
	private WorkQueuesRpcServer workQueuesRpcServer = null;
	private RPCServer rpcServerQueue01ForReal = null;
	private RPCServer rpcServerQueue02ForSim = null;
	private ApplicationContext context = null;
	private InnerBusinessControl innerBusinessControl = null;
	private static Logger logger = null;

	public Dispatch(TaskExecutor threadPool) throws Throwable {
		super();
		this.threadPool = threadPool;
	}

	public void innerCommunicateForReal() {
		try {
			this.rpcServerQueue01ForReal.RunRpcCall(innerBusinessControl);
		} catch (Throwable e) {
			logger.error("dispatch message error", e);
		}
	}

	public void innerCommunicateForSim() {
		try {
			this.rpcServerQueue02ForSim.RunRpcCall(innerBusinessControl);
		} catch (Throwable e) {
			logger.error("dispatch message error", e);
		}
	}

	@Override
	public TaskExecutor getTaskExecutor() {
		return threadPool;
	}

	@Override
	public BusinessBean getBusinessBean() throws Throwable {
		// context = new
		// FileSystemXmlApplicationContext("classpath:resource/context/platform.xml");
		ApplicationContext context = getApplicationContext();
		BusinessControl bc = (BusinessControl) context.getBean("outerBusinessControl");

		return bc;
	}

	@Override
	public void exceptionProcessMessage(Throwable e) {
		logger.error("process error ", e);
	}

	@Override
	public void completeProcessMessage(BusinessBean bb) {
		//logger.debug("completeProcessMessage " + "Close Current business" + context);
		context = null;
		try {
		} catch (Exception e) {
			logger.error("return BusinessBean fail", e);
		}
	}

	public WorkQueuesRpcServer getWorkQueuesRpcServer() {
		return workQueuesRpcServer;
	}

	public void setWorkQueuesRpcServer(WorkQueuesRpcServer workQueuesRpcServer) {

		this.workQueuesRpcServer = workQueuesRpcServer;
		this.workQueuesRpcServer.setRecvProcessBean(this);
	}

	public RPCServer getRpcServerQueue02ForSim() {
		return rpcServerQueue02ForSim;
	}

	public void setRpcServerQueue02ForSim(RPCServer rpcServerQueue02ForSim) {
		this.rpcServerQueue02ForSim = rpcServerQueue02ForSim;
	}

	public RPCServer getRpcServerQueue01ForReal() {
		return rpcServerQueue01ForReal;
	}

	public void setRpcServerQueue01ForReal(RPCServer rpcServerQueue01ForReal) {
		this.rpcServerQueue01ForReal = rpcServerQueue01ForReal;
	}

	public InnerBusinessControl getInnerBusinessControl() {
		return innerBusinessControl;
	}

	public void setInnerBusinessControl(InnerBusinessControl innerBusinessControl) {
		this.innerBusinessControl = innerBusinessControl;
	}
}
