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
import com.hframework.myna.config.domain.model.TaskActions;
import com.hframework.myna.config.domain.model.TaskActions_Example;
import com.hframework.myna.config.service.interfaces.ITaskActionsSV;

@Controller
@RequestMapping(value = "/config/taskActions")
public class TaskActionsController   {
    private static final Logger logger = LoggerFactory.getLogger(TaskActionsController.class);

	@Resource
	private ITaskActionsSV iTaskActionsSV;
  





    @InitBinder
    protected void initBinder(HttpServletRequest request,
        ServletRequestDataBinder binder) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CustomDateEditor editor = new CustomDateEditor(df, false);
        binder.registerCustomEditor(Date.class, editor);
    }

    /**
     * 查询展示任务行为列表
     * @param taskActions
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryListByAjax.json")
    @ResponseBody
    public ResultData list(@ModelAttribute("taskActions") TaskActions taskActions,
                                      @ModelAttribute("example") TaskActions_Example example, Pagination pagination){
        logger.debug("request : {},{},{}", taskActions, example, pagination);
        try{
            ExampleUtils.parseExample(taskActions, example);
            //设置分页信息
            example.setLimitStart(pagination.getStartIndex());
            example.setLimitEnd(pagination.getEndIndex());

            final List< TaskActions> list = iTaskActionsSV.getTaskActionsListByExample(example);
            pagination.setTotalCount(iTaskActionsSV.getTaskActionsCountByExample(example));

            return ResultData.success().add("list",list).add("pagination",pagination);
        }catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }
    }



    /**
     * 查询展示任务行为明细
     * @param taskActions
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryOneByAjax.json")
    @ResponseBody
    public ResultData detail(@ModelAttribute("taskActions") TaskActions taskActions){
        logger.debug("request : {},{}", taskActions.getId(), taskActions);
        try{
            TaskActions result = null;
            if(taskActions.getId() != null) {
                result = iTaskActionsSV.getTaskActionsByPK(taskActions.getId());
            }else {
                TaskActions_Example example = ExampleUtils.parseExample(taskActions, TaskActions_Example.class);
                List<TaskActions> list = iTaskActionsSV.getTaskActionsListByExample(example);
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
    * 搜索一个任务行为
    * @param  taskActions
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/searchOneByAjax.json")
    @ResponseBody
    public ResultData search(@ModelAttribute(" taskActions") TaskActions  taskActions){
        logger.debug("request : {}",  taskActions);
        try{
            TaskActions result = null;
            if(taskActions.getId() != null) {
                result =  iTaskActionsSV.getTaskActionsByPK(taskActions.getId());
            }else {
                TaskActions_Example example = ExampleUtils.parseExample( taskActions, TaskActions_Example.class);

                example.setLimitStart(0);
                example.setLimitEnd(1);

                List<TaskActions> list =  iTaskActionsSV.getTaskActionsListByExample(example);
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
    * 创建任务行为
    * @param taskActions
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createByAjax.json")
    @ResponseBody
    public ResultData create(@ModelAttribute("taskActions") TaskActions taskActions) {
        logger.debug("request : {}", taskActions);
        try {
            ControllerHelper.setDefaultValue(taskActions, "id");
            int result = iTaskActionsSV.create(taskActions);
            if(result > 0) {
                return ResultData.success(taskActions);
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
    * 批量维护任务行为
    * @param taskActionss
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createsByAjax.json")
    @ResponseBody
    public ResultData batchCreate(@RequestBody TaskActions[] taskActionss) {
        logger.debug("request : {}", taskActionss);

        try {
            ControllerHelper.setDefaultValue(taskActionss, "id");
            ControllerHelper.reorderProperty(taskActionss);

            int result = iTaskActionsSV.batchOperate(taskActionss);
            if(result > 0) {
                return ResultData.success(taskActionss);
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
    * 更新任务行为
    * @param taskActions
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/updateByAjax.json")
    @ResponseBody
    public ResultData update(@ModelAttribute("taskActions") TaskActions taskActions) {
        logger.debug("request : {}", taskActions);
        try {
            ControllerHelper.setDefaultValue(taskActions, "id");
            int result = iTaskActionsSV.update(taskActions);
            if(result > 0) {
                return ResultData.success(taskActions);
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
    * 创建或更新任务行为
    * @param taskActions
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/saveOrUpdateByAjax.json")
    @ResponseBody
    public ResultData saveOrUpdate(@ModelAttribute("taskActions") TaskActions taskActions) {
        logger.debug("request : {}", taskActions);
        try {
            ControllerHelper.setDefaultValue(taskActions, "id");
            int result = iTaskActionsSV.batchOperate(new TaskActions[]{taskActions});
            if(result > 0) {
                return ResultData.success(taskActions);
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
    * 删除任务行为
    * @param taskActions
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/deleteByAjax.json")
    @ResponseBody
    public ResultData delete(@ModelAttribute("taskActions") TaskActions taskActions) {
        logger.debug("request : {}", taskActions);

        try {
            ControllerHelper.setDefaultValue(taskActions, "id");
            int result = iTaskActionsSV.delete(taskActions);
            if(result > 0) {
                return ResultData.success(taskActions);
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
 	
	public ITaskActionsSV getITaskActionsSV(){
		return iTaskActionsSV;
	}
	//setter
	public void setITaskActionsSV(ITaskActionsSV iTaskActionsSV){
    	this.iTaskActionsSV = iTaskActionsSV;
    }
}
