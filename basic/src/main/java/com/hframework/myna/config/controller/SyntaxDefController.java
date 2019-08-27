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
import com.hframework.myna.config.domain.model.SyntaxDef;
import com.hframework.myna.config.domain.model.SyntaxDef_Example;
import com.hframework.myna.config.service.interfaces.ISyntaxDefSV;

@Controller
@RequestMapping(value = "/config/syntaxDef")
public class SyntaxDefController   {
    private static final Logger logger = LoggerFactory.getLogger(SyntaxDefController.class);

	@Resource
	private ISyntaxDefSV iSyntaxDefSV;
  





    @InitBinder
    protected void initBinder(HttpServletRequest request,
        ServletRequestDataBinder binder) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CustomDateEditor editor = new CustomDateEditor(df, false);
        binder.registerCustomEditor(Date.class, editor);
    }

    /**
     * 查询展示句法定义列表
     * @param syntaxDef
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryListByAjax.json")
    @ResponseBody
    public ResultData list(@ModelAttribute("syntaxDef") SyntaxDef syntaxDef,
                                      @ModelAttribute("example") SyntaxDef_Example example, Pagination pagination){
        logger.debug("request : {},{},{}", syntaxDef, example, pagination);
        try{
            ExampleUtils.parseExample(syntaxDef, example);
            //设置分页信息
            example.setLimitStart(pagination.getStartIndex());
            example.setLimitEnd(pagination.getEndIndex());

            final List< SyntaxDef> list = iSyntaxDefSV.getSyntaxDefListByExample(example);
            pagination.setTotalCount(iSyntaxDefSV.getSyntaxDefCountByExample(example));

            return ResultData.success().add("list",list).add("pagination",pagination);
        }catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }
    }



    /**
     * 查询展示句法定义明细
     * @param syntaxDef
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryOneByAjax.json")
    @ResponseBody
    public ResultData detail(@ModelAttribute("syntaxDef") SyntaxDef syntaxDef){
        logger.debug("request : {},{}", syntaxDef.getId(), syntaxDef);
        try{
            SyntaxDef result = null;
            if(syntaxDef.getId() != null) {
                result = iSyntaxDefSV.getSyntaxDefByPK(syntaxDef.getId());
            }else {
                SyntaxDef_Example example = ExampleUtils.parseExample(syntaxDef, SyntaxDef_Example.class);
                List<SyntaxDef> list = iSyntaxDefSV.getSyntaxDefListByExample(example);
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
    * 搜索一个句法定义
    * @param  syntaxDef
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/searchOneByAjax.json")
    @ResponseBody
    public ResultData search(@ModelAttribute(" syntaxDef") SyntaxDef  syntaxDef){
        logger.debug("request : {}",  syntaxDef);
        try{
            SyntaxDef result = null;
            if(syntaxDef.getId() != null) {
                result =  iSyntaxDefSV.getSyntaxDefByPK(syntaxDef.getId());
            }else {
                SyntaxDef_Example example = ExampleUtils.parseExample( syntaxDef, SyntaxDef_Example.class);

                example.setLimitStart(0);
                example.setLimitEnd(1);

                List<SyntaxDef> list =  iSyntaxDefSV.getSyntaxDefListByExample(example);
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
    * 创建句法定义
    * @param syntaxDef
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createByAjax.json")
    @ResponseBody
    public ResultData create(@ModelAttribute("syntaxDef") SyntaxDef syntaxDef) {
        logger.debug("request : {}", syntaxDef);
        try {
            ControllerHelper.setDefaultValue(syntaxDef, "id");
            int result = iSyntaxDefSV.create(syntaxDef);
            if(result > 0) {
                return ResultData.success(syntaxDef);
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
    * 批量维护句法定义
    * @param syntaxDefs
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createsByAjax.json")
    @ResponseBody
    public ResultData batchCreate(@RequestBody SyntaxDef[] syntaxDefs) {
        logger.debug("request : {}", syntaxDefs);

        try {
            ControllerHelper.setDefaultValue(syntaxDefs, "id");
            ControllerHelper.reorderProperty(syntaxDefs);

            int result = iSyntaxDefSV.batchOperate(syntaxDefs);
            if(result > 0) {
                return ResultData.success(syntaxDefs);
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
    * 更新句法定义
    * @param syntaxDef
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/updateByAjax.json")
    @ResponseBody
    public ResultData update(@ModelAttribute("syntaxDef") SyntaxDef syntaxDef) {
        logger.debug("request : {}", syntaxDef);
        try {
            ControllerHelper.setDefaultValue(syntaxDef, "id");
            int result = iSyntaxDefSV.update(syntaxDef);
            if(result > 0) {
                return ResultData.success(syntaxDef);
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
    * 创建或更新句法定义
    * @param syntaxDef
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/saveOrUpdateByAjax.json")
    @ResponseBody
    public ResultData saveOrUpdate(@ModelAttribute("syntaxDef") SyntaxDef syntaxDef) {
        logger.debug("request : {}", syntaxDef);
        try {
            ControllerHelper.setDefaultValue(syntaxDef, "id");
            int result = iSyntaxDefSV.batchOperate(new SyntaxDef[]{syntaxDef});
            if(result > 0) {
                return ResultData.success(syntaxDef);
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
    * 删除句法定义
    * @param syntaxDef
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/deleteByAjax.json")
    @ResponseBody
    public ResultData delete(@ModelAttribute("syntaxDef") SyntaxDef syntaxDef) {
        logger.debug("request : {}", syntaxDef);

        try {
            ControllerHelper.setDefaultValue(syntaxDef, "id");
            int result = iSyntaxDefSV.delete(syntaxDef);
            if(result > 0) {
                return ResultData.success(syntaxDef);
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
 	
	public ISyntaxDefSV getISyntaxDefSV(){
		return iSyntaxDefSV;
	}
	//setter
	public void setISyntaxDefSV(ISyntaxDefSV iSyntaxDefSV){
    	this.iSyntaxDefSV = iSyntaxDefSV;
    }
}
