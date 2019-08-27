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
import com.hframework.myna.config.domain.model.Option;
import com.hframework.myna.config.domain.model.Option_Example;
import com.hframework.myna.config.service.interfaces.IOptionSV;

@Controller
@RequestMapping(value = "/config/option")
public class OptionController   {
    private static final Logger logger = LoggerFactory.getLogger(OptionController.class);

	@Resource
	private IOptionSV iOptionSV;
  





    @InitBinder
    protected void initBinder(HttpServletRequest request,
        ServletRequestDataBinder binder) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CustomDateEditor editor = new CustomDateEditor(df, false);
        binder.registerCustomEditor(Date.class, editor);
    }

    /**
     * 查询展示选项列表
     * @param option
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryListByAjax.json")
    @ResponseBody
    public ResultData list(@ModelAttribute("option") Option option,
                                      @ModelAttribute("example") Option_Example example, Pagination pagination){
        logger.debug("request : {},{},{}", option, example, pagination);
        try{
            ExampleUtils.parseExample(option, example);
            //设置分页信息
            example.setLimitStart(pagination.getStartIndex());
            example.setLimitEnd(pagination.getEndIndex());

            final List< Option> list = iOptionSV.getOptionListByExample(example);
            pagination.setTotalCount(iOptionSV.getOptionCountByExample(example));

            return ResultData.success().add("list",list).add("pagination",pagination);
        }catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }
    }



    /**
     * 查询展示选项明细
     * @param option
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryOneByAjax.json")
    @ResponseBody
    public ResultData detail(@ModelAttribute("option") Option option){
        logger.debug("request : {},{}", option.getId(), option);
        try{
            Option result = null;
            if(option.getId() != null) {
                result = iOptionSV.getOptionByPK(option.getId());
            }else {
                Option_Example example = ExampleUtils.parseExample(option, Option_Example.class);
                List<Option> list = iOptionSV.getOptionListByExample(example);
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
    * 搜索一个选项
    * @param  option
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/searchOneByAjax.json")
    @ResponseBody
    public ResultData search(@ModelAttribute(" option") Option  option){
        logger.debug("request : {}",  option);
        try{
            Option result = null;
            if(option.getId() != null) {
                result =  iOptionSV.getOptionByPK(option.getId());
            }else {
                Option_Example example = ExampleUtils.parseExample( option, Option_Example.class);

                example.setLimitStart(0);
                example.setLimitEnd(1);

                List<Option> list =  iOptionSV.getOptionListByExample(example);
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
    * 创建选项
    * @param option
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createByAjax.json")
    @ResponseBody
    public ResultData create(@ModelAttribute("option") Option option) {
        logger.debug("request : {}", option);
        try {
            ControllerHelper.setDefaultValue(option, "id");
            int result = iOptionSV.create(option);
            if(result > 0) {
                return ResultData.success(option);
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
    * 批量维护选项
    * @param options
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createsByAjax.json")
    @ResponseBody
    public ResultData batchCreate(@RequestBody Option[] options) {
        logger.debug("request : {}", options);

        try {
            ControllerHelper.setDefaultValue(options, "id");
            ControllerHelper.reorderProperty(options);

            int result = iOptionSV.batchOperate(options);
            if(result > 0) {
                return ResultData.success(options);
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
    * 更新选项
    * @param option
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/updateByAjax.json")
    @ResponseBody
    public ResultData update(@ModelAttribute("option") Option option) {
        logger.debug("request : {}", option);
        try {
            ControllerHelper.setDefaultValue(option, "id");
            int result = iOptionSV.update(option);
            if(result > 0) {
                return ResultData.success(option);
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
    * 创建或更新选项
    * @param option
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/saveOrUpdateByAjax.json")
    @ResponseBody
    public ResultData saveOrUpdate(@ModelAttribute("option") Option option) {
        logger.debug("request : {}", option);
        try {
            ControllerHelper.setDefaultValue(option, "id");
            int result = iOptionSV.batchOperate(new Option[]{option});
            if(result > 0) {
                return ResultData.success(option);
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
    * 删除选项
    * @param option
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/deleteByAjax.json")
    @ResponseBody
    public ResultData delete(@ModelAttribute("option") Option option) {
        logger.debug("request : {}", option);

        try {
            ControllerHelper.setDefaultValue(option, "id");
            int result = iOptionSV.delete(option);
            if(result > 0) {
                return ResultData.success(option);
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
 	
	public IOptionSV getIOptionSV(){
		return iOptionSV;
	}
	//setter
	public void setIOptionSV(IOptionSV iOptionSV){
    	this.iOptionSV = iOptionSV;
    }
}
