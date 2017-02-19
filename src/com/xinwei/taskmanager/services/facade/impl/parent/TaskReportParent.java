package com.xinwei.taskmanager.services.facade.impl.parent;

import java.util.Map;
import java.util.TimerTask;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.xinwei.taskmanager.model.TaskRecord;
import com.xinwei.taskmanager.model.dto.CICache;
import com.xinwei.taskmanager.model.dto.TaskReportFromSlave;
import com.xinwei.taskmanager.model.sub.ResourceSnapshot;
import com.xinwei.taskmanager.services.facade.impl.AbstractFacadeTaskServicerImpl;
import com.xinwei.uem.util.Convert;

public class TaskReportParent extends AbstractFacadeTaskServicerImpl {

	public void checkSlaveIsDead(int key) {
		if (scheduledThreadPoolExecutor == null) {
			scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(3);
		}
		TimerTask timerTask = new TimerTask() {
			@Override
			public void run() {
				if (taskReportMap.size() > 0) {
					for (Map.Entry<Integer, TaskReportFromSlave> entry : taskReportMap.entrySet()) {
						if (entry.getValue().getCount() < 0 || taskReportMap.size() >= 2) {
							logger.info("Start taskReportMap.size() : " + taskReportMap.size());
							logger.info("report task end");
							if (taskReportMap.size() >= 2) {
								if (reportReqModel.getId() == entry.getKey()) {
									continue;
								}
							}
							TaskRecord taskRecord = taskRecordService.getTaskById(entry.getKey());
							if (!taskRecord.getStatus().equals("close")) {
								taskRecord.setResult("fail");
								taskRecord.setStatus("close");
								if (taskRecord.getLogs() == null) {
									ResourceSnapshot resourceSnapshot = Convert
											.parserJson(taskRecord.getResource_snapshot(), ResourceSnapshot.class);
									String key = resourceSnapshot.getName() + "_" + entry.getKey();
									CICache ciCache = cacheService.getCacheLog(key, taskRecord.getTest_group());
									cacheService.delCITaskCache(key);
									cacheService.delStepLog(key + ".step");
									taskRecord.setLogs(ciCache.getLogs());
								}
								taskRecordService.updateTaskRecord(taskRecord);
							}
							taskReportMap.remove(entry.getKey());
							logger.info("End taskReportMap.size() : " + taskReportMap.size() + " ½áÊøreportµÄentry.getKey() : " + entry.getKey());
							timerTaskMap.get(entry.getKey()).cancel();
							scheduledThreadPoolExecutor.shutdownNow();
							scheduledThreadPoolExecutor = null;
							logger.info("End Started timerTaskMap.size() : " + timerTaskMap.size());
							timerTaskMap.remove(entry.getKey());
							logger.info("End timerTaskMap.size() : " + timerTaskMap.size());

							break;
						} else {
							int c = entry.getValue().getCount();
							taskReportMap.get(entry.getKey()).setCount(--c);
						}
					}
				}
			}
		};
		timerTaskMap.put(key, timerTask);
		logger.info("Start timerTaskMap.size() : " + timerTaskMap.size());
		scheduledThreadPoolExecutor.scheduleAtFixedRate(timerTask, 2000, 2000, TimeUnit.MILLISECONDS);
	}
}
