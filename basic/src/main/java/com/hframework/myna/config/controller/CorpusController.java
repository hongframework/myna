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
import com.hframework.myna.config.domain.model.Corpus;
import com.hframework.myna.config.domain.model.Corpus_Example;
import com.hframework.myna.config.service.interfaces.ICorpusSV;

@Controller
@RequestMapping(value = "/config/corpus")
public class CorpusController   {
    private static final Logger logger = LoggerFactory.getLogger(CorpusController.class);

	@Resource
	private ICorpusSV iCorpusSV;
  





    @InitBinder
    protected void initBinder(HttpServletRequest request,
        ServletRequestDataBinder binder) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CustomDateEditor editor = new CustomDateEditor(df, false);
        binder.registerCustomEditor(Date.class, editor);
    }

    /**
     * 查询展示语料库列表
     * @param corpus
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryListByAjax.json")
    @ResponseBody
    public ResultData list(@ModelAttribute("corpus") Corpus corpus,
                                      @ModelAttribute("example") Corpus_Example example, Pagination pagination){
        logger.debug("request : {},{},{}", corpus, example, pagination);
        try{
            ExampleUtils.parseExample(corpus, example);
            //设置分页信息
            example.setLimitStart(pagination.getStartIndex());
            example.setLimitEnd(pagination.getEndIndex());

            final List< Corpus> list = iCorpusSV.getCorpusListByExample(example);
            pagination.setTotalCount(iCorpusSV.getCorpusCountByExample(example));

            return ResultData.success().add("list",list).add("pagination",pagination);
        }catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }
    }



    /**
     * 查询展示语料库明细
     * @param corpus
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryOneByAjax.json")
    @ResponseBody
    public ResultData detail(@ModelAttribute("corpus") Corpus corpus){
        logger.debug("request : {},{}", corpus.getId(), corpus);
        try{
            Corpus result = null;
            if(corpus.getId() != null) {
                result = iCorpusSV.getCorpusByPK(corpus.getId());
            }else {
                Corpus_Example example = ExampleUtils.parseExample(corpus, Corpus_Example.class);
                List<Corpus> list = iCorpusSV.getCorpusListByExample(example);
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
    * 搜索一个语料库
    * @param  corpus
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/searchOneByAjax.json")
    @ResponseBody
    public ResultData search(@ModelAttribute(" corpus") Corpus  corpus){
        logger.debug("request : {}",  corpus);
        try{
            Corpus result = null;
            if(corpus.getId() != null) {
                result =  iCorpusSV.getCorpusByPK(corpus.getId());
            }else {
                Corpus_Example example = ExampleUtils.parseExample( corpus, Corpus_Example.class);

                example.setLimitStart(0);
                example.setLimitEnd(1);

                List<Corpus> list =  iCorpusSV.getCorpusListByExample(example);
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
    * 创建语料库
    * @param corpus
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createByAjax.json")
    @ResponseBody
    public ResultData create(@ModelAttribute("corpus") Corpus corpus) {
        logger.debug("request : {}", corpus);
        try {
            ControllerHelper.setDefaultValue(corpus, "id");
            int result = iCorpusSV.create(corpus);
            if(result > 0) {
                return ResultData.success(corpus);
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
    * 批量维护语料库
    * @param corpuss
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createsByAjax.json")
    @ResponseBody
    public ResultData batchCreate(@RequestBody Corpus[] corpuss) {
        logger.debug("request : {}", corpuss);

        try {
            ControllerHelper.setDefaultValue(corpuss, "id");
            ControllerHelper.reorderProperty(corpuss);

            int result = iCorpusSV.batchOperate(corpuss);
            if(result > 0) {
                return ResultData.success(corpuss);
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
    * 更新语料库
    * @param corpus
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/updateByAjax.json")
    @ResponseBody
    public ResultData update(@ModelAttribute("corpus") Corpus corpus) {
        logger.debug("request : {}", corpus);
        try {
            ControllerHelper.setDefaultValue(corpus, "id");
            int result = iCorpusSV.update(corpus);
            if(result > 0) {
                return ResultData.success(corpus);
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
    * 创建或更新语料库
    * @param corpus
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/saveOrUpdateByAjax.json")
    @ResponseBody
    public ResultData saveOrUpdate(@ModelAttribute("corpus") Corpus corpus) {
        logger.debug("request : {}", corpus);
        try {
            ControllerHelper.setDefaultValue(corpus, "id");
            int result = iCorpusSV.batchOperate(new Corpus[]{corpus});
            if(result > 0) {
                return ResultData.success(corpus);
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
    * 删除语料库
    * @param corpus
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/deleteByAjax.json")
    @ResponseBody
    public ResultData delete(@ModelAttribute("corpus") Corpus corpus) {
        logger.debug("request : {}", corpus);

        try {
            ControllerHelper.setDefaultValue(corpus, "id");
            int result = iCorpusSV.delete(corpus);
            if(result > 0) {
                return ResultData.success(corpus);
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
 	
	public ICorpusSV getICorpusSV(){
		return iCorpusSV;
	}
	//setter
	public void setICorpusSV(ICorpusSV iCorpusSV){
    	this.iCorpusSV = iCorpusSV;
    }
}
