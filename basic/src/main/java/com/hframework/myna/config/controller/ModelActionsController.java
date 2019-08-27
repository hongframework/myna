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
import com.hframework.myna.config.domain.model.ModelActions;
import com.hframework.myna.config.domain.model.ModelActions_Example;
import com.hframework.myna.config.service.interfaces.IModelActionsSV;

@Controller
@RequestMapping(value = "/config/modelActions")
public class ModelActionsController   {
    private static final Logger logger = LoggerFactory.getLogger(ModelActionsController.class);

	@Resource
	private IModelActionsSV iModelActionsSV;
  





    @InitBinder
    protected void initBinder(HttpServletRequest request,
        ServletRequestDataBinder binder) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CustomDateEditor editor = new CustomDateEditor(df, false);
        binder.registerCustomEditor(Date.class, editor);
    }

    /**
     * 查询展示模式关联行为列表
     * @param modelActions
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryListByAjax.json")
    @ResponseBody
    public ResultData list(@ModelAttribute("modelActions") ModelActions modelActions,
                                      @ModelAttribute("example") ModelActions_Example example, Pagination pagination){
        logger.debug("request : {},{},{}", modelActions, example, pagination);
        try{
            ExampleUtils.parseExample(modelActions, example);
            //设置分页信息
            example.setLimitStart(pagination.getStartIndex());
            example.setLimitEnd(pagination.getEndIndex());

            final List< ModelActions> list = iModelActionsSV.getModelActionsListByExample(example);
            pagination.setTotalCount(iModelActionsSV.getModelActionsCountByExample(example));

            return ResultData.success().add("list",list).add("pagination",pagination);
        }catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }
    }



    /**
     * 查询展示模式关联行为明细
     * @param modelActions
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryOneByAjax.json")
    @ResponseBody
    public ResultData detail(@ModelAttribute("modelActions") ModelActions modelActions){
        logger.debug("request : {},{}", modelActions.getId(), modelActions);
        try{
            ModelActions result = null;
            if(modelActions.getId() != null) {
                result = iModelActionsSV.getModelActionsByPK(modelActions.getId());
            }else {
                ModelActions_Example example = ExampleUtils.parseExample(modelActions, ModelActions_Example.class);
                List<ModelActions> list = iModelActionsSV.getModelActionsListByExample(example);
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
    * 搜索一个模式关联行为
    * @param  modelActions
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/searchOneByAjax.json")
    @ResponseBody
    public ResultData search(@ModelAttribute(" modelActions") ModelActions  modelActions){
        logger.debug("request : {}",  modelActions);
        try{
            ModelActions result = null;
            if(modelActions.getId() != null) {
                result =  iModelActionsSV.getModelActionsByPK(modelActions.getId());
            }else {
                ModelActions_Example example = ExampleUtils.parseExample( modelActions, ModelActions_Example.class);

                example.setLimitStart(0);
                example.setLimitEnd(1);

                List<ModelActions> list =  iModelActionsSV.getModelActionsListByExample(example);
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
    * 创建模式关联行为
    * @param modelActions
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createByAjax.json")
    @ResponseBody
    public ResultData create(@ModelAttribute("modelActions") ModelActions modelActions) {
        logger.debug("request : {}", modelActions);
        try {
            ControllerHelper.setDefaultValue(modelActions, "id");
            int result = iModelActionsSV.create(modelActions);
            if(result > 0) {
                return ResultData.success(modelActions);
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
    * 批量维护模式关联行为
    * @param modelActionss
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createsByAjax.json")
    @ResponseBody
    public ResultData batchCreate(@RequestBody ModelActions[] modelActionss) {
        logger.debug("request : {}", modelActionss);

        try {
            ControllerHelper.setDefaultValue(modelActionss, "id");
            ControllerHelper.reorderProperty(modelActionss);

            int result = iModelActionsSV.batchOperate(modelActionss);
            if(result > 0) {
                return ResultData.success(modelActionss);
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
    * 更新模式关联行为
    * @param modelActions
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/updateByAjax.json")
    @ResponseBody
    public ResultData update(@ModelAttribute("modelActions") ModelActions modelActions) {
        logger.debug("request : {}", modelActions);
        try {
            ControllerHelper.setDefaultValue(modelActions, "id");
            int result = iModelActionsSV.update(modelActions);
            if(result > 0) {
                return ResultData.success(modelActions);
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
    * 创建或更新模式关联行为
    * @param modelActions
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/saveOrUpdateByAjax.json")
    @ResponseBody
    public ResultData saveOrUpdate(@ModelAttribute("modelActions") ModelActions modelActions) {
        logger.debug("request : {}", modelActions);
        try {
            ControllerHelper.setDefaultValue(modelActions, "id");
            int result = iModelActionsSV.batchOperate(new ModelActions[]{modelActions});
            if(result > 0) {
                return ResultData.success(modelActions);
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
    * 删除模式关联行为
    * @param modelActions
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/deleteByAjax.json")
    @ResponseBody
    public ResultData delete(@ModelAttribute("modelActions") ModelActions modelActions) {
        logger.debug("request : {}", modelActions);

        try {
            ControllerHelper.setDefaultValue(modelActions, "id");
            int result = iModelActionsSV.delete(modelActions);
            if(result > 0) {
                return ResultData.success(modelActions);
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
 	
	public IModelActionsSV getIModelActionsSV(){
		return iModelActionsSV;
	}
	//setter
	public void setIModelActionsSV(IModelActionsSV iModelActionsSV){
    	this.iModelActionsSV = iModelActionsSV;
    }
}
