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
import com.hframework.myna.config.domain.model.Options;
import com.hframework.myna.config.domain.model.Options_Example;
import com.hframework.myna.config.service.interfaces.IOptionsSV;

@Controller
@RequestMapping(value = "/config/options")
public class OptionsController   {
    private static final Logger logger = LoggerFactory.getLogger(OptionsController.class);

	@Resource
	private IOptionsSV iOptionsSV;
  





    @InitBinder
    protected void initBinder(HttpServletRequest request,
        ServletRequestDataBinder binder) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CustomDateEditor editor = new CustomDateEditor(df, false);
        binder.registerCustomEditor(Date.class, editor);
    }

    /**
     * 查询展示选项集列表
     * @param options
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryListByAjax.json")
    @ResponseBody
    public ResultData list(@ModelAttribute("options") Options options,
                                      @ModelAttribute("example") Options_Example example, Pagination pagination){
        logger.debug("request : {},{},{}", options, example, pagination);
        try{
            ExampleUtils.parseExample(options, example);
            //设置分页信息
            example.setLimitStart(pagination.getStartIndex());
            example.setLimitEnd(pagination.getEndIndex());

            final List< Options> list = iOptionsSV.getOptionsListByExample(example);
            pagination.setTotalCount(iOptionsSV.getOptionsCountByExample(example));

            return ResultData.success().add("list",list).add("pagination",pagination);
        }catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }
    }



    /**
     * 查询展示选项集明细
     * @param options
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryOneByAjax.json")
    @ResponseBody
    public ResultData detail(@ModelAttribute("options") Options options){
        logger.debug("request : {},{}", options.getId(), options);
        try{
            Options result = null;
            if(options.getId() != null) {
                result = iOptionsSV.getOptionsByPK(options.getId());
            }else {
                Options_Example example = ExampleUtils.parseExample(options, Options_Example.class);
                List<Options> list = iOptionsSV.getOptionsListByExample(example);
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
    * 搜索一个选项集
    * @param  options
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/searchOneByAjax.json")
    @ResponseBody
    public ResultData search(@ModelAttribute(" options") Options  options){
        logger.debug("request : {}",  options);
        try{
            Options result = null;
            if(options.getId() != null) {
                result =  iOptionsSV.getOptionsByPK(options.getId());
            }else {
                Options_Example example = ExampleUtils.parseExample( options, Options_Example.class);

                example.setLimitStart(0);
                example.setLimitEnd(1);

                List<Options> list =  iOptionsSV.getOptionsListByExample(example);
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
    * 创建选项集
    * @param options
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createByAjax.json")
    @ResponseBody
    public ResultData create(@ModelAttribute("options") Options options) {
        logger.debug("request : {}", options);
        try {
            ControllerHelper.setDefaultValue(options, "id");
            int result = iOptionsSV.create(options);
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
    * 批量维护选项集
    * @param optionss
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createsByAjax.json")
    @ResponseBody
    public ResultData batchCreate(@RequestBody Options[] optionss) {
        logger.debug("request : {}", optionss);

        try {
            ControllerHelper.setDefaultValue(optionss, "id");
            ControllerHelper.reorderProperty(optionss);

            int result = iOptionsSV.batchOperate(optionss);
            if(result > 0) {
                return ResultData.success(optionss);
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
    * 更新选项集
    * @param options
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/updateByAjax.json")
    @ResponseBody
    public ResultData update(@ModelAttribute("options") Options options) {
        logger.debug("request : {}", options);
        try {
            ControllerHelper.setDefaultValue(options, "id");
            int result = iOptionsSV.update(options);
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
    * 创建或更新选项集
    * @param options
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/saveOrUpdateByAjax.json")
    @ResponseBody
    public ResultData saveOrUpdate(@ModelAttribute("options") Options options) {
        logger.debug("request : {}", options);
        try {
            ControllerHelper.setDefaultValue(options, "id");
            int result = iOptionsSV.batchOperate(new Options[]{options});
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
    * 删除选项集
    * @param options
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/deleteByAjax.json")
    @ResponseBody
    public ResultData delete(@ModelAttribute("options") Options options) {
        logger.debug("request : {}", options);

        try {
            ControllerHelper.setDefaultValue(options, "id");
            int result = iOptionsSV.delete(options);
            if(result > 0) {
                return ResultData.success(options);
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
 	
	public IOptionsSV getIOptionsSV(){
		return iOptionsSV;
	}
	//setter
	public void setIOptionsSV(IOptionsSV iOptionsSV){
    	this.iOptionsSV = iOptionsSV;
    }
}
