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
import com.hframework.myna.config.domain.model.Words;
import com.hframework.myna.config.domain.model.Words_Example;
import com.hframework.myna.config.service.interfaces.IWordsSV;

@Controller
@RequestMapping(value = "/config/words")
public class WordsController   {
    private static final Logger logger = LoggerFactory.getLogger(WordsController.class);

	@Resource
	private IWordsSV iWordsSV;
  





    @InitBinder
    protected void initBinder(HttpServletRequest request,
        ServletRequestDataBinder binder) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CustomDateEditor editor = new CustomDateEditor(df, false);
        binder.registerCustomEditor(Date.class, editor);
    }

    /**
     * 查询展示词汇列表
     * @param words
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryListByAjax.json")
    @ResponseBody
    public ResultData list(@ModelAttribute("words") Words words,
                                      @ModelAttribute("example") Words_Example example, Pagination pagination){
        logger.debug("request : {},{},{}", words, example, pagination);
        try{
            ExampleUtils.parseExample(words, example);
            //设置分页信息
            example.setLimitStart(pagination.getStartIndex());
            example.setLimitEnd(pagination.getEndIndex());

            final List< Words> list = iWordsSV.getWordsListByExample(example);
            pagination.setTotalCount(iWordsSV.getWordsCountByExample(example));

            return ResultData.success().add("list",list).add("pagination",pagination);
        }catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }
    }



    /**
     * 查询展示词汇明细
     * @param words
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryOneByAjax.json")
    @ResponseBody
    public ResultData detail(@ModelAttribute("words") Words words){
        logger.debug("request : {},{}", words.getId(), words);
        try{
            Words result = null;
            if(words.getId() != null) {
                result = iWordsSV.getWordsByPK(words.getId());
            }else {
                Words_Example example = ExampleUtils.parseExample(words, Words_Example.class);
                List<Words> list = iWordsSV.getWordsListByExample(example);
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
    * 搜索一个词汇
    * @param  words
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/searchOneByAjax.json")
    @ResponseBody
    public ResultData search(@ModelAttribute(" words") Words  words){
        logger.debug("request : {}",  words);
        try{
            Words result = null;
            if(words.getId() != null) {
                result =  iWordsSV.getWordsByPK(words.getId());
            }else {
                Words_Example example = ExampleUtils.parseExample( words, Words_Example.class);

                example.setLimitStart(0);
                example.setLimitEnd(1);

                List<Words> list =  iWordsSV.getWordsListByExample(example);
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
    * 创建词汇
    * @param words
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createByAjax.json")
    @ResponseBody
    public ResultData create(@ModelAttribute("words") Words words) {
        logger.debug("request : {}", words);
        try {
            ControllerHelper.setDefaultValue(words, "id");
            int result = iWordsSV.create(words);
            if(result > 0) {
                return ResultData.success(words);
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
    * 批量维护词汇
    * @param wordss
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createsByAjax.json")
    @ResponseBody
    public ResultData batchCreate(@RequestBody Words[] wordss) {
        logger.debug("request : {}", wordss);

        try {
            ControllerHelper.setDefaultValue(wordss, "id");
            ControllerHelper.reorderProperty(wordss);

            int result = iWordsSV.batchOperate(wordss);
            if(result > 0) {
                return ResultData.success(wordss);
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
    * 更新词汇
    * @param words
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/updateByAjax.json")
    @ResponseBody
    public ResultData update(@ModelAttribute("words") Words words) {
        logger.debug("request : {}", words);
        try {
            ControllerHelper.setDefaultValue(words, "id");
            int result = iWordsSV.update(words);
            if(result > 0) {
                return ResultData.success(words);
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
    * 创建或更新词汇
    * @param words
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/saveOrUpdateByAjax.json")
    @ResponseBody
    public ResultData saveOrUpdate(@ModelAttribute("words") Words words) {
        logger.debug("request : {}", words);
        try {
            ControllerHelper.setDefaultValue(words, "id");
            int result = iWordsSV.batchOperate(new Words[]{words});
            if(result > 0) {
                return ResultData.success(words);
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
    * 删除词汇
    * @param words
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/deleteByAjax.json")
    @ResponseBody
    public ResultData delete(@ModelAttribute("words") Words words) {
        logger.debug("request : {}", words);

        try {
            ControllerHelper.setDefaultValue(words, "id");
            int result = iWordsSV.delete(words);
            if(result > 0) {
                return ResultData.success(words);
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
 	
	public IWordsSV getIWordsSV(){
		return iWordsSV;
	}
	//setter
	public void setIWordsSV(IWordsSV iWordsSV){
    	this.iWordsSV = iWordsSV;
    }
}
