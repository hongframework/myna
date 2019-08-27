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
import com.hframework.myna.config.domain.model.P2pCorpus;
import com.hframework.myna.config.domain.model.P2pCorpus_Example;
import com.hframework.myna.config.service.interfaces.IP2pCorpusSV;

@Controller
@RequestMapping(value = "/config/p2pCorpus")
public class P2pCorpusController   {
    private static final Logger logger = LoggerFactory.getLogger(P2pCorpusController.class);

	@Resource
	private IP2pCorpusSV iP2pCorpusSV;
  





    @InitBinder
    protected void initBinder(HttpServletRequest request,
        ServletRequestDataBinder binder) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CustomDateEditor editor = new CustomDateEditor(df, false);
        binder.registerCustomEditor(Date.class, editor);
    }

    /**
     * 查询展示点对点语料列表
     * @param p2pCorpus
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryListByAjax.json")
    @ResponseBody
    public ResultData list(@ModelAttribute("p2pCorpus") P2pCorpus p2pCorpus,
                                      @ModelAttribute("example") P2pCorpus_Example example, Pagination pagination){
        logger.debug("request : {},{},{}", p2pCorpus, example, pagination);
        try{
            ExampleUtils.parseExample(p2pCorpus, example);
            //设置分页信息
            example.setLimitStart(pagination.getStartIndex());
            example.setLimitEnd(pagination.getEndIndex());

            final List< P2pCorpus> list = iP2pCorpusSV.getP2pCorpusListByExample(example);
            pagination.setTotalCount(iP2pCorpusSV.getP2pCorpusCountByExample(example));

            return ResultData.success().add("list",list).add("pagination",pagination);
        }catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }
    }



    /**
     * 查询展示点对点语料明细
     * @param p2pCorpus
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryOneByAjax.json")
    @ResponseBody
    public ResultData detail(@ModelAttribute("p2pCorpus") P2pCorpus p2pCorpus){
        logger.debug("request : {},{}", p2pCorpus.getId(), p2pCorpus);
        try{
            P2pCorpus result = null;
            if(p2pCorpus.getId() != null) {
                result = iP2pCorpusSV.getP2pCorpusByPK(p2pCorpus.getId());
            }else {
                P2pCorpus_Example example = ExampleUtils.parseExample(p2pCorpus, P2pCorpus_Example.class);
                List<P2pCorpus> list = iP2pCorpusSV.getP2pCorpusListByExample(example);
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
    * 搜索一个点对点语料
    * @param  p2pCorpus
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/searchOneByAjax.json")
    @ResponseBody
    public ResultData search(@ModelAttribute(" p2pCorpus") P2pCorpus  p2pCorpus){
        logger.debug("request : {}",  p2pCorpus);
        try{
            P2pCorpus result = null;
            if(p2pCorpus.getId() != null) {
                result =  iP2pCorpusSV.getP2pCorpusByPK(p2pCorpus.getId());
            }else {
                P2pCorpus_Example example = ExampleUtils.parseExample( p2pCorpus, P2pCorpus_Example.class);

                example.setLimitStart(0);
                example.setLimitEnd(1);

                List<P2pCorpus> list =  iP2pCorpusSV.getP2pCorpusListByExample(example);
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
    * 创建点对点语料
    * @param p2pCorpus
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createByAjax.json")
    @ResponseBody
    public ResultData create(@ModelAttribute("p2pCorpus") P2pCorpus p2pCorpus) {
        logger.debug("request : {}", p2pCorpus);
        try {
            ControllerHelper.setDefaultValue(p2pCorpus, "id");
            int result = iP2pCorpusSV.create(p2pCorpus);
            if(result > 0) {
                return ResultData.success(p2pCorpus);
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
    * 批量维护点对点语料
    * @param p2pCorpuss
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createsByAjax.json")
    @ResponseBody
    public ResultData batchCreate(@RequestBody P2pCorpus[] p2pCorpuss) {
        logger.debug("request : {}", p2pCorpuss);

        try {
            ControllerHelper.setDefaultValue(p2pCorpuss, "id");
            ControllerHelper.reorderProperty(p2pCorpuss);

            int result = iP2pCorpusSV.batchOperate(p2pCorpuss);
            if(result > 0) {
                return ResultData.success(p2pCorpuss);
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
    * 更新点对点语料
    * @param p2pCorpus
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/updateByAjax.json")
    @ResponseBody
    public ResultData update(@ModelAttribute("p2pCorpus") P2pCorpus p2pCorpus) {
        logger.debug("request : {}", p2pCorpus);
        try {
            ControllerHelper.setDefaultValue(p2pCorpus, "id");
            int result = iP2pCorpusSV.update(p2pCorpus);
            if(result > 0) {
                return ResultData.success(p2pCorpus);
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
    * 创建或更新点对点语料
    * @param p2pCorpus
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/saveOrUpdateByAjax.json")
    @ResponseBody
    public ResultData saveOrUpdate(@ModelAttribute("p2pCorpus") P2pCorpus p2pCorpus) {
        logger.debug("request : {}", p2pCorpus);
        try {
            ControllerHelper.setDefaultValue(p2pCorpus, "id");
            int result = iP2pCorpusSV.batchOperate(new P2pCorpus[]{p2pCorpus});
            if(result > 0) {
                return ResultData.success(p2pCorpus);
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
    * 删除点对点语料
    * @param p2pCorpus
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/deleteByAjax.json")
    @ResponseBody
    public ResultData delete(@ModelAttribute("p2pCorpus") P2pCorpus p2pCorpus) {
        logger.debug("request : {}", p2pCorpus);

        try {
            ControllerHelper.setDefaultValue(p2pCorpus, "id");
            int result = iP2pCorpusSV.delete(p2pCorpus);
            if(result > 0) {
                return ResultData.success(p2pCorpus);
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
 	
	public IP2pCorpusSV getIP2pCorpusSV(){
		return iP2pCorpusSV;
	}
	//setter
	public void setIP2pCorpusSV(IP2pCorpusSV iP2pCorpusSV){
    	this.iP2pCorpusSV = iP2pCorpusSV;
    }
}
