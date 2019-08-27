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
import com.hframework.myna.config.domain.model.Utterance;
import com.hframework.myna.config.domain.model.Utterance_Example;
import com.hframework.myna.config.service.interfaces.IUtteranceSV;

@Controller
@RequestMapping(value = "/config/utterance")
public class UtteranceController   {
    private static final Logger logger = LoggerFactory.getLogger(UtteranceController.class);

	@Resource
	private IUtteranceSV iUtteranceSV;
  





    @InitBinder
    protected void initBinder(HttpServletRequest request,
        ServletRequestDataBinder binder) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CustomDateEditor editor = new CustomDateEditor(df, false);
        binder.registerCustomEditor(Date.class, editor);
    }

    /**
     * 查询展示话术列表
     * @param utterance
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryListByAjax.json")
    @ResponseBody
    public ResultData list(@ModelAttribute("utterance") Utterance utterance,
                                      @ModelAttribute("example") Utterance_Example example, Pagination pagination){
        logger.debug("request : {},{},{}", utterance, example, pagination);
        try{
            ExampleUtils.parseExample(utterance, example);
            //设置分页信息
            example.setLimitStart(pagination.getStartIndex());
            example.setLimitEnd(pagination.getEndIndex());

            final List< Utterance> list = iUtteranceSV.getUtteranceListByExample(example);
            pagination.setTotalCount(iUtteranceSV.getUtteranceCountByExample(example));

            return ResultData.success().add("list",list).add("pagination",pagination);
        }catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }
    }



    /**
     * 查询展示话术明细
     * @param utterance
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryOneByAjax.json")
    @ResponseBody
    public ResultData detail(@ModelAttribute("utterance") Utterance utterance){
        logger.debug("request : {},{}", utterance.getId(), utterance);
        try{
            Utterance result = null;
            if(utterance.getId() != null) {
                result = iUtteranceSV.getUtteranceByPK(utterance.getId());
            }else {
                Utterance_Example example = ExampleUtils.parseExample(utterance, Utterance_Example.class);
                List<Utterance> list = iUtteranceSV.getUtteranceListByExample(example);
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
    * 搜索一个话术
    * @param  utterance
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/searchOneByAjax.json")
    @ResponseBody
    public ResultData search(@ModelAttribute(" utterance") Utterance  utterance){
        logger.debug("request : {}",  utterance);
        try{
            Utterance result = null;
            if(utterance.getId() != null) {
                result =  iUtteranceSV.getUtteranceByPK(utterance.getId());
            }else {
                Utterance_Example example = ExampleUtils.parseExample( utterance, Utterance_Example.class);

                example.setLimitStart(0);
                example.setLimitEnd(1);

                List<Utterance> list =  iUtteranceSV.getUtteranceListByExample(example);
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
    * 创建话术
    * @param utterance
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createByAjax.json")
    @ResponseBody
    public ResultData create(@ModelAttribute("utterance") Utterance utterance) {
        logger.debug("request : {}", utterance);
        try {
            ControllerHelper.setDefaultValue(utterance, "id");
            int result = iUtteranceSV.create(utterance);
            if(result > 0) {
                return ResultData.success(utterance);
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
    * 批量维护话术
    * @param utterances
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createsByAjax.json")
    @ResponseBody
    public ResultData batchCreate(@RequestBody Utterance[] utterances) {
        logger.debug("request : {}", utterances);

        try {
            ControllerHelper.setDefaultValue(utterances, "id");
            ControllerHelper.reorderProperty(utterances);

            int result = iUtteranceSV.batchOperate(utterances);
            if(result > 0) {
                return ResultData.success(utterances);
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
    * 更新话术
    * @param utterance
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/updateByAjax.json")
    @ResponseBody
    public ResultData update(@ModelAttribute("utterance") Utterance utterance) {
        logger.debug("request : {}", utterance);
        try {
            ControllerHelper.setDefaultValue(utterance, "id");
            int result = iUtteranceSV.update(utterance);
            if(result > 0) {
                return ResultData.success(utterance);
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
    * 创建或更新话术
    * @param utterance
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/saveOrUpdateByAjax.json")
    @ResponseBody
    public ResultData saveOrUpdate(@ModelAttribute("utterance") Utterance utterance) {
        logger.debug("request : {}", utterance);
        try {
            ControllerHelper.setDefaultValue(utterance, "id");
            int result = iUtteranceSV.batchOperate(new Utterance[]{utterance});
            if(result > 0) {
                return ResultData.success(utterance);
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
    * 删除话术
    * @param utterance
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/deleteByAjax.json")
    @ResponseBody
    public ResultData delete(@ModelAttribute("utterance") Utterance utterance) {
        logger.debug("request : {}", utterance);

        try {
            ControllerHelper.setDefaultValue(utterance, "id");
            int result = iUtteranceSV.delete(utterance);
            if(result > 0) {
                return ResultData.success(utterance);
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
 	
	public IUtteranceSV getIUtteranceSV(){
		return iUtteranceSV;
	}
	//setter
	public void setIUtteranceSV(IUtteranceSV iUtteranceSV){
    	this.iUtteranceSV = iUtteranceSV;
    }
}
