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
import com.hframework.myna.config.domain.model.Action;
import com.hframework.myna.config.domain.model.Action_Example;
import com.hframework.myna.config.service.interfaces.IActionSV;

@Controller
@RequestMapping(value = "/config/action")
public class ActionController   {
    private static final Logger logger = LoggerFactory.getLogger(ActionController.class);

	@Resource
	private IActionSV iActionSV;
  





    @InitBinder
    protected void initBinder(HttpServletRequest request,
        ServletRequestDataBinder binder) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CustomDateEditor editor = new CustomDateEditor(df, false);
        binder.registerCustomEditor(Date.class, editor);
    }

    /**
     * 查询展示行为列表
     * @param action
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryListByAjax.json")
    @ResponseBody
    public ResultData list(@ModelAttribute("action") Action action,
                                      @ModelAttribute("example") Action_Example example, Pagination pagination){
        logger.debug("request : {},{},{}", action, example, pagination);
        try{
            ExampleUtils.parseExample(action, example);
            //设置分页信息
            example.setLimitStart(pagination.getStartIndex());
            example.setLimitEnd(pagination.getEndIndex());

            final List< Action> list = iActionSV.getActionListByExample(example);
            pagination.setTotalCount(iActionSV.getActionCountByExample(example));

            return ResultData.success().add("list",list).add("pagination",pagination);
        }catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }
    }



    /**
     * 查询展示行为明细
     * @param action
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryOneByAjax.json")
    @ResponseBody
    public ResultData detail(@ModelAttribute("action") Action action){
        logger.debug("request : {},{}", action.getId(), action);
        try{
            Action result = null;
            if(action.getId() != null) {
                result = iActionSV.getActionByPK(action.getId());
            }else {
                Action_Example example = ExampleUtils.parseExample(action, Action_Example.class);
                List<Action> list = iActionSV.getActionListByExample(example);
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
    * 搜索一个行为
    * @param  action
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/searchOneByAjax.json")
    @ResponseBody
    public ResultData search(@ModelAttribute(" action") Action  action){
        logger.debug("request : {}",  action);
        try{
            Action result = null;
            if(action.getId() != null) {
                result =  iActionSV.getActionByPK(action.getId());
            }else {
                Action_Example example = ExampleUtils.parseExample( action, Action_Example.class);

                example.setLimitStart(0);
                example.setLimitEnd(1);

                List<Action> list =  iActionSV.getActionListByExample(example);
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
    * 创建行为
    * @param action
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createByAjax.json")
    @ResponseBody
    public ResultData create(@ModelAttribute("action") Action action) {
        logger.debug("request : {}", action);
        try {
            ControllerHelper.setDefaultValue(action, "id");
            int result = iActionSV.create(action);
            if(result > 0) {
                return ResultData.success(action);
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
    * 批量维护行为
    * @param actions
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createsByAjax.json")
    @ResponseBody
    public ResultData batchCreate(@RequestBody Action[] actions) {
        logger.debug("request : {}", actions);

        try {
            ControllerHelper.setDefaultValue(actions, "id");
            ControllerHelper.reorderProperty(actions);

            int result = iActionSV.batchOperate(actions);
            if(result > 0) {
                return ResultData.success(actions);
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
    * 更新行为
    * @param action
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/updateByAjax.json")
    @ResponseBody
    public ResultData update(@ModelAttribute("action") Action action) {
        logger.debug("request : {}", action);
        try {
            ControllerHelper.setDefaultValue(action, "id");
            int result = iActionSV.update(action);
            if(result > 0) {
                return ResultData.success(action);
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
    * 创建或更新行为
    * @param action
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/saveOrUpdateByAjax.json")
    @ResponseBody
    public ResultData saveOrUpdate(@ModelAttribute("action") Action action) {
        logger.debug("request : {}", action);
        try {
            ControllerHelper.setDefaultValue(action, "id");
            int result = iActionSV.batchOperate(new Action[]{action});
            if(result > 0) {
                return ResultData.success(action);
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
    * 删除行为
    * @param action
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/deleteByAjax.json")
    @ResponseBody
    public ResultData delete(@ModelAttribute("action") Action action) {
        logger.debug("request : {}", action);

        try {
            ControllerHelper.setDefaultValue(action, "id");
            int result = iActionSV.delete(action);
            if(result > 0) {
                return ResultData.success(action);
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
 	
	public IActionSV getIActionSV(){
		return iActionSV;
	}
	//setter
	public void setIActionSV(IActionSV iActionSV){
    	this.iActionSV = iActionSV;
    }
}
