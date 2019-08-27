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
import com.hframework.myna.config.domain.model.QnaActions;
import com.hframework.myna.config.domain.model.QnaActions_Example;
import com.hframework.myna.config.service.interfaces.IQnaActionsSV;

@Controller
@RequestMapping(value = "/config/qnaActions")
public class QnaActionsController   {
    private static final Logger logger = LoggerFactory.getLogger(QnaActionsController.class);

	@Resource
	private IQnaActionsSV iQnaActionsSV;
  





    @InitBinder
    protected void initBinder(HttpServletRequest request,
        ServletRequestDataBinder binder) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CustomDateEditor editor = new CustomDateEditor(df, false);
        binder.registerCustomEditor(Date.class, editor);
    }

    /**
     * 查询展示问答行为列表
     * @param qnaActions
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryListByAjax.json")
    @ResponseBody
    public ResultData list(@ModelAttribute("qnaActions") QnaActions qnaActions,
                                      @ModelAttribute("example") QnaActions_Example example, Pagination pagination){
        logger.debug("request : {},{},{}", qnaActions, example, pagination);
        try{
            ExampleUtils.parseExample(qnaActions, example);
            //设置分页信息
            example.setLimitStart(pagination.getStartIndex());
            example.setLimitEnd(pagination.getEndIndex());

            final List< QnaActions> list = iQnaActionsSV.getQnaActionsListByExample(example);
            pagination.setTotalCount(iQnaActionsSV.getQnaActionsCountByExample(example));

            return ResultData.success().add("list",list).add("pagination",pagination);
        }catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }
    }



    /**
     * 查询展示问答行为明细
     * @param qnaActions
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryOneByAjax.json")
    @ResponseBody
    public ResultData detail(@ModelAttribute("qnaActions") QnaActions qnaActions){
        logger.debug("request : {},{}", qnaActions.getId(), qnaActions);
        try{
            QnaActions result = null;
            if(qnaActions.getId() != null) {
                result = iQnaActionsSV.getQnaActionsByPK(qnaActions.getId());
            }else {
                QnaActions_Example example = ExampleUtils.parseExample(qnaActions, QnaActions_Example.class);
                List<QnaActions> list = iQnaActionsSV.getQnaActionsListByExample(example);
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
    * 搜索一个问答行为
    * @param  qnaActions
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/searchOneByAjax.json")
    @ResponseBody
    public ResultData search(@ModelAttribute(" qnaActions") QnaActions  qnaActions){
        logger.debug("request : {}",  qnaActions);
        try{
            QnaActions result = null;
            if(qnaActions.getId() != null) {
                result =  iQnaActionsSV.getQnaActionsByPK(qnaActions.getId());
            }else {
                QnaActions_Example example = ExampleUtils.parseExample( qnaActions, QnaActions_Example.class);

                example.setLimitStart(0);
                example.setLimitEnd(1);

                List<QnaActions> list =  iQnaActionsSV.getQnaActionsListByExample(example);
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
    * 创建问答行为
    * @param qnaActions
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createByAjax.json")
    @ResponseBody
    public ResultData create(@ModelAttribute("qnaActions") QnaActions qnaActions) {
        logger.debug("request : {}", qnaActions);
        try {
            ControllerHelper.setDefaultValue(qnaActions, "id");
            int result = iQnaActionsSV.create(qnaActions);
            if(result > 0) {
                return ResultData.success(qnaActions);
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
    * 批量维护问答行为
    * @param qnaActionss
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createsByAjax.json")
    @ResponseBody
    public ResultData batchCreate(@RequestBody QnaActions[] qnaActionss) {
        logger.debug("request : {}", qnaActionss);

        try {
            ControllerHelper.setDefaultValue(qnaActionss, "id");
            ControllerHelper.reorderProperty(qnaActionss);

            int result = iQnaActionsSV.batchOperate(qnaActionss);
            if(result > 0) {
                return ResultData.success(qnaActionss);
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
    * 更新问答行为
    * @param qnaActions
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/updateByAjax.json")
    @ResponseBody
    public ResultData update(@ModelAttribute("qnaActions") QnaActions qnaActions) {
        logger.debug("request : {}", qnaActions);
        try {
            ControllerHelper.setDefaultValue(qnaActions, "id");
            int result = iQnaActionsSV.update(qnaActions);
            if(result > 0) {
                return ResultData.success(qnaActions);
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
    * 创建或更新问答行为
    * @param qnaActions
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/saveOrUpdateByAjax.json")
    @ResponseBody
    public ResultData saveOrUpdate(@ModelAttribute("qnaActions") QnaActions qnaActions) {
        logger.debug("request : {}", qnaActions);
        try {
            ControllerHelper.setDefaultValue(qnaActions, "id");
            int result = iQnaActionsSV.batchOperate(new QnaActions[]{qnaActions});
            if(result > 0) {
                return ResultData.success(qnaActions);
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
    * 删除问答行为
    * @param qnaActions
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/deleteByAjax.json")
    @ResponseBody
    public ResultData delete(@ModelAttribute("qnaActions") QnaActions qnaActions) {
        logger.debug("request : {}", qnaActions);

        try {
            ControllerHelper.setDefaultValue(qnaActions, "id");
            int result = iQnaActionsSV.delete(qnaActions);
            if(result > 0) {
                return ResultData.success(qnaActions);
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
 	
	public IQnaActionsSV getIQnaActionsSV(){
		return iQnaActionsSV;
	}
	//setter
	public void setIQnaActionsSV(IQnaActionsSV iQnaActionsSV){
    	this.iQnaActionsSV = iQnaActionsSV;
    }
}
