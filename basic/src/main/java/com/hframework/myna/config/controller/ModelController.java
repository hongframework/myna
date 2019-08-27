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
import com.hframework.myna.config.domain.model.Model;
import com.hframework.myna.config.domain.model.Model_Example;
import com.hframework.myna.config.service.interfaces.IModelSV;

@Controller
@RequestMapping(value = "/config/model")
public class ModelController   {
    private static final Logger logger = LoggerFactory.getLogger(ModelController.class);

	@Resource
	private IModelSV iModelSV;
  





    @InitBinder
    protected void initBinder(HttpServletRequest request,
        ServletRequestDataBinder binder) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CustomDateEditor editor = new CustomDateEditor(df, false);
        binder.registerCustomEditor(Date.class, editor);
    }

    /**
     * 查询展示工作模式列表
     * @param model
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryListByAjax.json")
    @ResponseBody
    public ResultData list(@ModelAttribute("model") Model model,
                                      @ModelAttribute("example") Model_Example example, Pagination pagination){
        logger.debug("request : {},{},{}", model, example, pagination);
        try{
            ExampleUtils.parseExample(model, example);
            //设置分页信息
            example.setLimitStart(pagination.getStartIndex());
            example.setLimitEnd(pagination.getEndIndex());

            final List< Model> list = iModelSV.getModelListByExample(example);
            pagination.setTotalCount(iModelSV.getModelCountByExample(example));

            return ResultData.success().add("list",list).add("pagination",pagination);
        }catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }
    }



    /**
     * 查询展示工作模式明细
     * @param model
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryOneByAjax.json")
    @ResponseBody
    public ResultData detail(@ModelAttribute("model") Model model){
        logger.debug("request : {},{}", model.getId(), model);
        try{
            Model result = null;
            if(model.getId() != null) {
                result = iModelSV.getModelByPK(model.getId());
            }else {
                Model_Example example = ExampleUtils.parseExample(model, Model_Example.class);
                List<Model> list = iModelSV.getModelListByExample(example);
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
    * 搜索一个工作模式
    * @param  model
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/searchOneByAjax.json")
    @ResponseBody
    public ResultData search(@ModelAttribute(" model") Model  model){
        logger.debug("request : {}",  model);
        try{
            Model result = null;
            if(model.getId() != null) {
                result =  iModelSV.getModelByPK(model.getId());
            }else {
                Model_Example example = ExampleUtils.parseExample( model, Model_Example.class);

                example.setLimitStart(0);
                example.setLimitEnd(1);

                List<Model> list =  iModelSV.getModelListByExample(example);
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
    * 创建工作模式
    * @param model
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createByAjax.json")
    @ResponseBody
    public ResultData create(@ModelAttribute("model") Model model) {
        logger.debug("request : {}", model);
        try {
            ControllerHelper.setDefaultValue(model, "id");
            int result = iModelSV.create(model);
            if(result > 0) {
                return ResultData.success(model);
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
    * 批量维护工作模式
    * @param models
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createsByAjax.json")
    @ResponseBody
    public ResultData batchCreate(@RequestBody Model[] models) {
        logger.debug("request : {}", models);

        try {
            ControllerHelper.setDefaultValue(models, "id");
            ControllerHelper.reorderProperty(models);

            int result = iModelSV.batchOperate(models);
            if(result > 0) {
                return ResultData.success(models);
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
    * 更新工作模式
    * @param model
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/updateByAjax.json")
    @ResponseBody
    public ResultData update(@ModelAttribute("model") Model model) {
        logger.debug("request : {}", model);
        try {
            ControllerHelper.setDefaultValue(model, "id");
            int result = iModelSV.update(model);
            if(result > 0) {
                return ResultData.success(model);
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
    * 创建或更新工作模式
    * @param model
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/saveOrUpdateByAjax.json")
    @ResponseBody
    public ResultData saveOrUpdate(@ModelAttribute("model") Model model) {
        logger.debug("request : {}", model);
        try {
            ControllerHelper.setDefaultValue(model, "id");
            int result = iModelSV.batchOperate(new Model[]{model});
            if(result > 0) {
                return ResultData.success(model);
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
    * 删除工作模式
    * @param model
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/deleteByAjax.json")
    @ResponseBody
    public ResultData delete(@ModelAttribute("model") Model model) {
        logger.debug("request : {}", model);

        try {
            ControllerHelper.setDefaultValue(model, "id");
            int result = iModelSV.delete(model);
            if(result > 0) {
                return ResultData.success(model);
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
 	
	public IModelSV getIModelSV(){
		return iModelSV;
	}
	//setter
	public void setIModelSV(IModelSV iModelSV){
    	this.iModelSV = iModelSV;
    }
}
