package com.hframework.myna.config.controller;

import com.hframework.beans.controller.Pagination;
import com.hframework.beans.controller.ResultCode;
import com.hframework.beans.controller.ResultData;
import com.hframework.common.util.ExampleUtils;
import com.hframework.beans.exceptions.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;
import javax.annotation.Resource;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.ServletRequestDataBinder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import com.hframework.web.ControllerHelper;
import com.hframework.myna.config.domain.model.Task;
import com.hframework.myna.config.domain.model.Task_Example;
import com.hframework.myna.config.service.interfaces.ITaskSV;

@Controller
@RequestMapping(value = "/config/task")
public class TaskController   {
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

	@Resource
	private ITaskSV iTaskSV;
  





    @InitBinder
    protected void initBinder(HttpServletRequest request,
        ServletRequestDataBinder binder) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CustomDateEditor editor = new CustomDateEditor(df, false);
        binder.registerCustomEditor(Date.class, editor);
    }

    /**
     * 查询展示任务列表
     * @param task
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryListByAjax.json")
    @ResponseBody
    public ResultData list(@ModelAttribute("task") Task task,
                                      @ModelAttribute("example") Task_Example example, Pagination pagination){
        logger.debug("request : {},{},{}", task, example, pagination);
        try{
            ExampleUtils.parseExample(task, example);
            //设置分页信息
            example.setLimitStart(pagination.getStartIndex());
            example.setLimitEnd(pagination.getEndIndex());

            final List< Task> list = iTaskSV.getTaskListByExample(example);
            pagination.setTotalCount(iTaskSV.getTaskCountByExample(example));

            return ResultData.success().add("list",list).add("pagination",pagination);
        }catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }
    }



    /**
     * 查询展示任务明细
     * @param task
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryOneByAjax.json")
    @ResponseBody
    public ResultData detail(@ModelAttribute("task") Task task){
        logger.debug("request : {},{}", task.getId(), task);
        try{
            Task result = null;
            if(task.getId() != null) {
                result = iTaskSV.getTaskByPK(task.getId());
            }else {
                Task_Example example = ExampleUtils.parseExample(task, Task_Example.class);
                List<Task> list = iTaskSV.getTaskListByExample(example);
                if(list != null && list.size() == 1) {
                    result = list.get(0);
                }
            }

            if(result != null) {
                return ResultData.success(result);
            }else {
                return ResultData.error(ResultCode.RECODE_IS_NOT_EXISTS);
            }
        }catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }
    }

    /**
    * 搜索一个任务
    * @param  task
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/searchOneByAjax.json")
    @ResponseBody
    public ResultData search(@ModelAttribute(" task") Task  task){
        logger.debug("request : {}",  task);
        try{
            Task result = null;
            if(task.getId() != null) {
                result =  iTaskSV.getTaskByPK(task.getId());
            }else {
                Task_Example example = ExampleUtils.parseExample( task, Task_Example.class);

                example.setLimitStart(0);
                example.setLimitEnd(1);

                List<Task> list =  iTaskSV.getTaskListByExample(example);
                if(list != null && list.size() > 0) {
                    result = list.get(0);
                }
            }

            if(result != null) {
                return ResultData.success(result);
            }else {
                return ResultData.error(ResultCode.RECODE_IS_NOT_EXISTS);
            }
        }catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }
    }

    /**
    * 创建任务
    * @param task
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createByAjax.json")
    @ResponseBody
    public ResultData create(@ModelAttribute("task") Task task) {
        logger.debug("request : {}", task);
        try {
            ControllerHelper.setDefaultValue(task, "id");
            int result = iTaskSV.create(task);
            if(result > 0) {
                return ResultData.success(task);
            }
        } catch (BusinessException e ){
            return e.result();
        } catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }
        return ResultData.error(ResultCode.UNKNOW);
    }

    /**
    * 批量维护任务
    * @param tasks
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createsByAjax.json")
    @ResponseBody
    public ResultData batchCreate(@RequestBody Task[] tasks) {
        logger.debug("request : {}", tasks);

        try {
            ControllerHelper.setDefaultValue(tasks, "id");
            ControllerHelper.reorderProperty(tasks);

            int result = iTaskSV.batchOperate(tasks);
            if(result > 0) {
                return ResultData.success(tasks);
            }
        } catch (BusinessException e ){
            return e.result();
        } catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }
        return ResultData.error(ResultCode.UNKNOW);
    }

    /**
    * 更新任务
    * @param task
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/updateByAjax.json")
    @ResponseBody
    public ResultData update(@ModelAttribute("task") Task task) {
        logger.debug("request : {}", task);
        try {
            ControllerHelper.setDefaultValue(task, "id");
            int result = iTaskSV.update(task);
            if(result > 0) {
                return ResultData.success(task);
            }
        } catch (BusinessException e ){
            return e.result();
        } catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }
        return ResultData.error(ResultCode.UNKNOW);
    }

    /**
    * 创建或更新任务
    * @param task
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/saveOrUpdateByAjax.json")
    @ResponseBody
    public ResultData saveOrUpdate(@ModelAttribute("task") Task task) {
        logger.debug("request : {}", task);
        try {
            ControllerHelper.setDefaultValue(task, "id");
            int result = iTaskSV.batchOperate(new Task[]{task});
            if(result > 0) {
                return ResultData.success(task);
            }
        } catch (BusinessException e ){
            return e.result();
        } catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }
        return ResultData.error(ResultCode.UNKNOW);
    }

    /**
    * 删除任务
    * @param task
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/deleteByAjax.json")
    @ResponseBody
    public ResultData delete(@ModelAttribute("task") Task task) {
        logger.debug("request : {}", task);

        try {
            ControllerHelper.setDefaultValue(task, "id");
            int result = iTaskSV.delete(task);
            if(result > 0) {
                return ResultData.success(task);
            }else {
                return ResultData.error(ResultCode.RECODE_IS_NOT_EXISTS);
            }
        } catch (BusinessException e ){
            return e.result();
        } catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }
    }
  	//getter
 	
	public ITaskSV getITaskSV(){
		return iTaskSV;
	}
	//setter
	public void setITaskSV(ITaskSV iTaskSV){
    	this.iTaskSV = iTaskSV;
    }
}
