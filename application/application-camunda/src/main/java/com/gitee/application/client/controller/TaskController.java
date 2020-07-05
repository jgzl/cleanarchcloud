package com.gitee.application.client.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.ManagementService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.task.TaskQuery;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/task")
public class TaskController {

	private final HistoryService historyService;
	private final IdentityService identityService;
	private final ManagementService managementService;
	private final RepositoryService repositoryService;
	private final RuntimeService runtimeService;
	private final TaskService taskService;

	private String processDefinitionKey="holiday";

	public TaskController(HistoryService historyService,
			IdentityService identityService, ManagementService managementService, RepositoryService repositoryService,
			RuntimeService runtimeService, TaskService taskService) {
		this.historyService = historyService;
		this.identityService = identityService;
		this.managementService = managementService;
		this.repositoryService = repositoryService;
		this.runtimeService = runtimeService;
		this.taskService = taskService;
	}

	@RequestMapping("/start")
	public String startProcess(){
		Map<String,Object> values=new HashMap<>();
		String[] assigneeList = {"a","b","c","d"};
		values.put("supervisor","lgd");
		values.put("assigneeList", Arrays.asList(assigneeList));
		values.put("personnel","lzw");
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey,values);
		System.out.println("当前流程实例id为"+processInstance.getId());
		System.out.println("当前流程定义的id为"+processInstance.getProcessDefinitionId());
		System.out.println("当前ProcessInstance的id为"+processInstance.getProcessInstanceId());
		System.out.println("当前RootProcessInstance的id为"+processInstance.getRootProcessInstanceId());
		return "success";
	}

	@RequestMapping("/delete")
	public String deleteDeployment(){
		repositoryService.deleteDeployment("ebc7586c-be81-11ea-8282-aeb3837f64d0",true);
		return "success";
	}

	@RequestMapping("/addUser/{processInstanceId}/{username}")
	public String addUser(@PathVariable String processInstanceId,@PathVariable String username){
		runtimeService.createProcessInstanceModification(processInstanceId)
				.startBeforeActivity("seniorManagerApproval")//会签节点的activityId
				.setVariable("assignee",username)
				.execute();
		return "success";
	}

	@RequestMapping("/list/{username}")
	public String listTask(@PathVariable String username){
		TaskQuery taskQuery = taskService.createTaskQuery();
		List<Task> taskList = taskQuery
				.processDefinitionKey("holiday")
				.taskAssignee(username).list();

		taskList.forEach(task -> {
			log.info("xxx的流程实例ID[{}]",task.getProcessInstanceId());
			log.info("xxx的流程任务ID[{}]",task.getId());
			log.info("xxx的流程任务名称[{}]",task.getName());
			log.info("xxx的流程定义id[{}]",task.getProcessDefinitionId());
			log.info("xxx的流程任务定义key[{}]",task.getTaskDefinitionKey());
			log.info("xxx的流程审批人[{}]",task.getAssignee());
		});
		return "success";
	}

	@RequestMapping("/complete/{taskId}")
	public String complete(@PathVariable String taskId){
		log.info("审批开始");
		taskService.complete(taskId);
		log.info("审批完成");
		return "success";
	}
}
