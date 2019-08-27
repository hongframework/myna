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
import com.hframework.myna.config.domain.model.Thesaurus;
import com.hframework.myna.config.domain.model.Thesaurus_Example;
import com.hframework.myna.config.service.interfaces.IThesaurusSV;

@Controller
@RequestMapping(value = "/config/thesaurus")
public class ThesaurusController   {
    private static final Logger logger = LoggerFactory.getLogger(ThesaurusController.class);

	@Resource
	private IThesaurusSV iThesaurusSV;
  





    @InitBinder
    protected void initBinder(HttpServletRequest request,
        ServletRequestDataBinder binder) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CustomDateEditor editor = new CustomDateEditor(df, false);
        binder.registerCustomEditor(Date.class, editor);
    }

    /**
     * 查询展示词库列表
     * @param thesaurus
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryListByAjax.json")
    @ResponseBody
    public ResultData list(@ModelAttribute("thesaurus") Thesaurus thesaurus,
                                      @ModelAttribute("example") Thesaurus_Example example, Pagination pagination){
        logger.debug("request : {},{},{}", thesaurus, example, pagination);
        try{
            ExampleUtils.parseExample(thesaurus, example);
            //设置分页信息
            example.setLimitStart(pagination.getStartIndex());
            example.setLimitEnd(pagination.getEndIndex());

            final List< Thesaurus> list = iThesaurusSV.getThesaurusListByExample(example);
            pagination.setTotalCount(iThesaurusSV.getThesaurusCountByExample(example));

            return ResultData.success().add("list",list).add("pagination",pagination);
        }catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }
    }



    /**
     * 查询展示词库明细
     * @param thesaurus
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryOneByAjax.json")
    @ResponseBody
    public ResultData detail(@ModelAttribute("thesaurus") Thesaurus thesaurus){
        logger.debug("request : {},{}", thesaurus.getId(), thesaurus);
        try{
            Thesaurus result = null;
            if(thesaurus.getId() != null) {
                result = iThesaurusSV.getThesaurusByPK(thesaurus.getId());
            }else {
                Thesaurus_Example example = ExampleUtils.parseExample(thesaurus, Thesaurus_Example.class);
                List<Thesaurus> list = iThesaurusSV.getThesaurusListByExample(example);
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
    * 搜索一个词库
    * @param  thesaurus
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/searchOneByAjax.json")
    @ResponseBody
    public ResultData search(@ModelAttribute(" thesaurus") Thesaurus  thesaurus){
        logger.debug("request : {}",  thesaurus);
        try{
            Thesaurus result = null;
            if(thesaurus.getId() != null) {
                result =  iThesaurusSV.getThesaurusByPK(thesaurus.getId());
            }else {
                Thesaurus_Example example = ExampleUtils.parseExample( thesaurus, Thesaurus_Example.class);

                example.setLimitStart(0);
                example.setLimitEnd(1);

                List<Thesaurus> list =  iThesaurusSV.getThesaurusListByExample(example);
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
    * 创建词库
    * @param thesaurus
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createByAjax.json")
    @ResponseBody
    public ResultData create(@ModelAttribute("thesaurus") Thesaurus thesaurus) {
        logger.debug("request : {}", thesaurus);
        try {
            ControllerHelper.setDefaultValue(thesaurus, "id");
            int result = iThesaurusSV.create(thesaurus);
            if(result > 0) {
                return ResultData.success(thesaurus);
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
    * 批量维护词库
    * @param thesauruss
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createsByAjax.json")
    @ResponseBody
    public ResultData batchCreate(@RequestBody Thesaurus[] thesauruss) {
        logger.debug("request : {}", thesauruss);

        try {
            ControllerHelper.setDefaultValue(thesauruss, "id");
            ControllerHelper.reorderProperty(thesauruss);

            int result = iThesaurusSV.batchOperate(thesauruss);
            if(result > 0) {
                return ResultData.success(thesauruss);
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
    * 更新词库
    * @param thesaurus
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/updateByAjax.json")
    @ResponseBody
    public ResultData update(@ModelAttribute("thesaurus") Thesaurus thesaurus) {
        logger.debug("request : {}", thesaurus);
        try {
            ControllerHelper.setDefaultValue(thesaurus, "id");
            int result = iThesaurusSV.update(thesaurus);
            if(result > 0) {
                return ResultData.success(thesaurus);
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
    * 创建或更新词库
    * @param thesaurus
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/saveOrUpdateByAjax.json")
    @ResponseBody
    public ResultData saveOrUpdate(@ModelAttribute("thesaurus") Thesaurus thesaurus) {
        logger.debug("request : {}", thesaurus);
        try {
            ControllerHelper.setDefaultValue(thesaurus, "id");
            int result = iThesaurusSV.batchOperate(new Thesaurus[]{thesaurus});
            if(result > 0) {
                return ResultData.success(thesaurus);
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
    * 删除词库
    * @param thesaurus
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/deleteByAjax.json")
    @ResponseBody
    public ResultData delete(@ModelAttribute("thesaurus") Thesaurus thesaurus) {
        logger.debug("request : {}", thesaurus);

        try {
            ControllerHelper.setDefaultValue(thesaurus, "id");
            int result = iThesaurusSV.delete(thesaurus);
            if(result > 0) {
                return ResultData.success(thesaurus);
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
 	
	public IThesaurusSV getIThesaurusSV(){
		return iThesaurusSV;
	}
	//setter
	public void setIThesaurusSV(IThesaurusSV iThesaurusSV){
    	this.iThesaurusSV = iThesaurusSV;
    }
}
