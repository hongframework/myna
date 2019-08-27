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
import com.hframework.myna.config.domain.model.Qna;
import com.hframework.myna.config.domain.model.Qna_Example;
import com.hframework.myna.config.service.interfaces.IQnaSV;

@Controller
@RequestMapping(value = "/config/qna")
public class QnaController   {
    private static final Logger logger = LoggerFactory.getLogger(QnaController.class);

	@Resource
	private IQnaSV iQnaSV;
  





    @InitBinder
    protected void initBinder(HttpServletRequest request,
        ServletRequestDataBinder binder) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CustomDateEditor editor = new CustomDateEditor(df, false);
        binder.registerCustomEditor(Date.class, editor);
    }

    /**
     * 查询展示问答列表
     * @param qna
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryListByAjax.json")
    @ResponseBody
    public ResultData list(@ModelAttribute("qna") Qna qna,
                                      @ModelAttribute("example") Qna_Example example, Pagination pagination){
        logger.debug("request : {},{},{}", qna, example, pagination);
        try{
            ExampleUtils.parseExample(qna, example);
            //设置分页信息
            example.setLimitStart(pagination.getStartIndex());
            example.setLimitEnd(pagination.getEndIndex());

            final List< Qna> list = iQnaSV.getQnaListByExample(example);
            pagination.setTotalCount(iQnaSV.getQnaCountByExample(example));

            return ResultData.success().add("list",list).add("pagination",pagination);
        }catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }
    }



    /**
     * 查询展示问答明细
     * @param qna
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryOneByAjax.json")
    @ResponseBody
    public ResultData detail(@ModelAttribute("qna") Qna qna){
        logger.debug("request : {},{}", qna.getId(), qna);
        try{
            Qna result = null;
            if(qna.getId() != null) {
                result = iQnaSV.getQnaByPK(qna.getId());
            }else {
                Qna_Example example = ExampleUtils.parseExample(qna, Qna_Example.class);
                List<Qna> list = iQnaSV.getQnaListByExample(example);
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
    * 搜索一个问答
    * @param  qna
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/searchOneByAjax.json")
    @ResponseBody
    public ResultData search(@ModelAttribute(" qna") Qna  qna){
        logger.debug("request : {}",  qna);
        try{
            Qna result = null;
            if(qna.getId() != null) {
                result =  iQnaSV.getQnaByPK(qna.getId());
            }else {
                Qna_Example example = ExampleUtils.parseExample( qna, Qna_Example.class);

                example.setLimitStart(0);
                example.setLimitEnd(1);

                List<Qna> list =  iQnaSV.getQnaListByExample(example);
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
    * 创建问答
    * @param qna
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createByAjax.json")
    @ResponseBody
    public ResultData create(@ModelAttribute("qna") Qna qna) {
        logger.debug("request : {}", qna);
        try {
            ControllerHelper.setDefaultValue(qna, "id");
            int result = iQnaSV.create(qna);
            if(result > 0) {
                return ResultData.success(qna);
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
    * 批量维护问答
    * @param qnas
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createsByAjax.json")
    @ResponseBody
    public ResultData batchCreate(@RequestBody Qna[] qnas) {
        logger.debug("request : {}", qnas);

        try {
            ControllerHelper.setDefaultValue(qnas, "id");
            ControllerHelper.reorderProperty(qnas);

            int result = iQnaSV.batchOperate(qnas);
            if(result > 0) {
                return ResultData.success(qnas);
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
    * 更新问答
    * @param qna
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/updateByAjax.json")
    @ResponseBody
    public ResultData update(@ModelAttribute("qna") Qna qna) {
        logger.debug("request : {}", qna);
        try {
            ControllerHelper.setDefaultValue(qna, "id");
            int result = iQnaSV.update(qna);
            if(result > 0) {
                return ResultData.success(qna);
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
    * 创建或更新问答
    * @param qna
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/saveOrUpdateByAjax.json")
    @ResponseBody
    public ResultData saveOrUpdate(@ModelAttribute("qna") Qna qna) {
        logger.debug("request : {}", qna);
        try {
            ControllerHelper.setDefaultValue(qna, "id");
            int result = iQnaSV.batchOperate(new Qna[]{qna});
            if(result > 0) {
                return ResultData.success(qna);
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
    * 删除问答
    * @param qna
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/deleteByAjax.json")
    @ResponseBody
    public ResultData delete(@ModelAttribute("qna") Qna qna) {
        logger.debug("request : {}", qna);

        try {
            ControllerHelper.setDefaultValue(qna, "id");
            int result = iQnaSV.delete(qna);
            if(result > 0) {
                return ResultData.success(qna);
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
 	
	public IQnaSV getIQnaSV(){
		return iQnaSV;
	}
	//setter
	public void setIQnaSV(IQnaSV iQnaSV){
    	this.iQnaSV = iQnaSV;
    }
}
