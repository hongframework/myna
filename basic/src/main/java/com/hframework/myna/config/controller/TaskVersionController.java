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
import com.hframework.myna.config.domain.model.TaskVersion;
import com.hframework.myna.config.domain.model.TaskVersion_Example;
import com.hframework.myna.config.service.interfaces.ITaskVersionSV;

@Controller
@RequestMapping(value = "/config/taskVersion")
public class TaskVersionController   {
    private static final Logger logger = LoggerFactory.getLogger(TaskVersionController.class);

	@Resource
	private ITaskVersionSV iTaskVersionSV;
  





    @InitBinder
    protected void initBinder(HttpServletRequest request,
        ServletRequestDataBinder binder) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CustomDateEditor editor = new CustomDateEditor(df, false);
        binder.registerCustomEditor(Date.class, editor);
    }

    /**
     * 查询展示任务版本信息列表
     * @param taskVersion
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryListByAjax.json")
    @ResponseBody
    public ResultData list(@ModelAttribute("taskVersion") TaskVersion taskVersion,
                                      @ModelAttribute("example") TaskVersion_Example example, Pagination pagination){
        logger.debug("request : {},{},{}", taskVersion, example, pagination);
        try{
            ExampleUtils.parseExample(taskVersion, example);
            //设置分页信息
            example.setLimitStart(pagination.getStartIndex());
            example.setLimitEnd(pagination.getEndIndex());

            final List< TaskVersion> list = iTaskVersionSV.getTaskVersionListByExample(example);
            pagination.setTotalCount(iTaskVersionSV.getTaskVersionCountByExample(example));

            return ResultData.success().add("list",list).add("pagination",pagination);
        }catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }
    }



    /**
     * 查询展示任务版本信息明细
     * @param taskVersion
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryOneByAjax.json")
    @ResponseBody
    public ResultData detail(@ModelAttribute("taskVersion") TaskVersion taskVersion){
        logger.debug("request : {},{}", taskVersion.getId(), taskVersion);
        try{
            TaskVersion result = null;
            if(taskVersion.getId() != null) {
                result = iTaskVersionSV.getTaskVersionByPK(taskVersion.getId());
            }else {
                TaskVersion_Example example = ExampleUtils.parseExample(taskVersion, TaskVersion_Example.class);
                List<TaskVersion> list = iTaskVersionSV.getTaskVersionListByExample(example);
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
    * 搜索一个任务版本信息
    * @param  taskVersion
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/searchOneByAjax.json")
    @ResponseBody
    public ResultData search(@ModelAttribute(" taskVersion") TaskVersion  taskVersion){
        logger.debug("request : {}",  taskVersion);
        try{
            TaskVersion result = null;
            if(taskVersion.getId() != null) {
                result =  iTaskVersionSV.getTaskVersionByPK(taskVersion.getId());
            }else {
                TaskVersion_Example example = ExampleUtils.parseExample( taskVersion, TaskVersion_Example.class);

                example.setLimitStart(0);
                example.setLimitEnd(1);

                List<TaskVersion> list =  iTaskVersionSV.getTaskVersionListByExample(example);
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
    * 创建任务版本信息
    * @param taskVersion
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createByAjax.json")
    @ResponseBody
    public ResultData create(@ModelAttribute("taskVersion") TaskVersion taskVersion) {
        logger.debug("request : {}", taskVersion);
        try {
            ControllerHelper.setDefaultValue(taskVersion, "id");
            int result = iTaskVersionSV.create(taskVersion);
            if(result > 0) {
                return ResultData.success(taskVersion);
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
    * 批量维护任务版本信息
    * @param taskVersions
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createsByAjax.json")
    @ResponseBody
    public ResultData batchCreate(@RequestBody TaskVersion[] taskVersions) {
        logger.debug("request : {}", taskVersions);

        try {
            ControllerHelper.setDefaultValue(taskVersions, "id");
            ControllerHelper.reorderProperty(taskVersions);

            int result = iTaskVersionSV.batchOperate(taskVersions);
            if(result > 0) {
                return ResultData.success(taskVersions);
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
    * 更新任务版本信息
    * @param taskVersion
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/updateByAjax.json")
    @ResponseBody
    public ResultData update(@ModelAttribute("taskVersion") TaskVersion taskVersion) {
        logger.debug("request : {}", taskVersion);
        try {
            ControllerHelper.setDefaultValue(taskVersion, "id");
            int result = iTaskVersionSV.update(taskVersion);
            if(result > 0) {
                return ResultData.success(taskVersion);
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
    * 创建或更新任务版本信息
    * @param taskVersion
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/saveOrUpdateByAjax.json")
    @ResponseBody
    public ResultData saveOrUpdate(@ModelAttribute("taskVersion") TaskVersion taskVersion) {
        logger.debug("request : {}", taskVersion);
        try {
            ControllerHelper.setDefaultValue(taskVersion, "id");
            int result = iTaskVersionSV.batchOperate(new TaskVersion[]{taskVersion});
            if(result > 0) {
                return ResultData.success(taskVersion);
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
    * 删除任务版本信息
    * @param taskVersion
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/deleteByAjax.json")
    @ResponseBody
    public ResultData delete(@ModelAttribute("taskVersion") TaskVersion taskVersion) {
        logger.debug("request : {}", taskVersion);

        try {
            ControllerHelper.setDefaultValue(taskVersion, "id");
            int result = iTaskVersionSV.delete(taskVersion);
            if(result > 0) {
                return ResultData.success(taskVersion);
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
 	
	public ITaskVersionSV getITaskVersionSV(){
		return iTaskVersionSV;
	}
	//setter
	public void setITaskVersionSV(ITaskVersionSV iTaskVersionSV){
    	this.iTaskVersionSV = iTaskVersionSV;
    }
}
