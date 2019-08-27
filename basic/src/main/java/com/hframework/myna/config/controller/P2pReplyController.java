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
import com.hframework.myna.config.domain.model.P2pReply;
import com.hframework.myna.config.domain.model.P2pReply_Example;
import com.hframework.myna.config.service.interfaces.IP2pReplySV;

@Controller
@RequestMapping(value = "/config/p2pReply")
public class P2pReplyController   {
    private static final Logger logger = LoggerFactory.getLogger(P2pReplyController.class);

	@Resource
	private IP2pReplySV iP2pReplySV;
  





    @InitBinder
    protected void initBinder(HttpServletRequest request,
        ServletRequestDataBinder binder) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CustomDateEditor editor = new CustomDateEditor(df, false);
        binder.registerCustomEditor(Date.class, editor);
    }

    /**
     * 查询展示点对点应答列表
     * @param p2pReply
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryListByAjax.json")
    @ResponseBody
    public ResultData list(@ModelAttribute("p2pReply") P2pReply p2pReply,
                                      @ModelAttribute("example") P2pReply_Example example, Pagination pagination){
        logger.debug("request : {},{},{}", p2pReply, example, pagination);
        try{
            ExampleUtils.parseExample(p2pReply, example);
            //设置分页信息
            example.setLimitStart(pagination.getStartIndex());
            example.setLimitEnd(pagination.getEndIndex());

            final List< P2pReply> list = iP2pReplySV.getP2pReplyListByExample(example);
            pagination.setTotalCount(iP2pReplySV.getP2pReplyCountByExample(example));

            return ResultData.success().add("list",list).add("pagination",pagination);
        }catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }
    }



    /**
     * 查询展示点对点应答明细
     * @param p2pReply
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryOneByAjax.json")
    @ResponseBody
    public ResultData detail(@ModelAttribute("p2pReply") P2pReply p2pReply){
        logger.debug("request : {},{}", p2pReply.getId(), p2pReply);
        try{
            P2pReply result = null;
            if(p2pReply.getId() != null) {
                result = iP2pReplySV.getP2pReplyByPK(p2pReply.getId());
            }else {
                P2pReply_Example example = ExampleUtils.parseExample(p2pReply, P2pReply_Example.class);
                List<P2pReply> list = iP2pReplySV.getP2pReplyListByExample(example);
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
    * 搜索一个点对点应答
    * @param  p2pReply
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/searchOneByAjax.json")
    @ResponseBody
    public ResultData search(@ModelAttribute(" p2pReply") P2pReply  p2pReply){
        logger.debug("request : {}",  p2pReply);
        try{
            P2pReply result = null;
            if(p2pReply.getId() != null) {
                result =  iP2pReplySV.getP2pReplyByPK(p2pReply.getId());
            }else {
                P2pReply_Example example = ExampleUtils.parseExample( p2pReply, P2pReply_Example.class);

                example.setLimitStart(0);
                example.setLimitEnd(1);

                List<P2pReply> list =  iP2pReplySV.getP2pReplyListByExample(example);
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
    * 创建点对点应答
    * @param p2pReply
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createByAjax.json")
    @ResponseBody
    public ResultData create(@ModelAttribute("p2pReply") P2pReply p2pReply) {
        logger.debug("request : {}", p2pReply);
        try {
            ControllerHelper.setDefaultValue(p2pReply, "id");
            int result = iP2pReplySV.create(p2pReply);
            if(result > 0) {
                return ResultData.success(p2pReply);
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
    * 批量维护点对点应答
    * @param p2pReplys
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createsByAjax.json")
    @ResponseBody
    public ResultData batchCreate(@RequestBody P2pReply[] p2pReplys) {
        logger.debug("request : {}", p2pReplys);

        try {
            ControllerHelper.setDefaultValue(p2pReplys, "id");
            ControllerHelper.reorderProperty(p2pReplys);

            int result = iP2pReplySV.batchOperate(p2pReplys);
            if(result > 0) {
                return ResultData.success(p2pReplys);
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
    * 更新点对点应答
    * @param p2pReply
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/updateByAjax.json")
    @ResponseBody
    public ResultData update(@ModelAttribute("p2pReply") P2pReply p2pReply) {
        logger.debug("request : {}", p2pReply);
        try {
            ControllerHelper.setDefaultValue(p2pReply, "id");
            int result = iP2pReplySV.update(p2pReply);
            if(result > 0) {
                return ResultData.success(p2pReply);
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
    * 创建或更新点对点应答
    * @param p2pReply
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/saveOrUpdateByAjax.json")
    @ResponseBody
    public ResultData saveOrUpdate(@ModelAttribute("p2pReply") P2pReply p2pReply) {
        logger.debug("request : {}", p2pReply);
        try {
            ControllerHelper.setDefaultValue(p2pReply, "id");
            int result = iP2pReplySV.batchOperate(new P2pReply[]{p2pReply});
            if(result > 0) {
                return ResultData.success(p2pReply);
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
    * 删除点对点应答
    * @param p2pReply
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/deleteByAjax.json")
    @ResponseBody
    public ResultData delete(@ModelAttribute("p2pReply") P2pReply p2pReply) {
        logger.debug("request : {}", p2pReply);

        try {
            ControllerHelper.setDefaultValue(p2pReply, "id");
            int result = iP2pReplySV.delete(p2pReply);
            if(result > 0) {
                return ResultData.success(p2pReply);
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
 	
	public IP2pReplySV getIP2pReplySV(){
		return iP2pReplySV;
	}
	//setter
	public void setIP2pReplySV(IP2pReplySV iP2pReplySV){
    	this.iP2pReplySV = iP2pReplySV;
    }
}
