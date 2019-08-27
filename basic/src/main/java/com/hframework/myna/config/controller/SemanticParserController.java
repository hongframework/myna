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
import com.hframework.myna.config.domain.model.SemanticParser;
import com.hframework.myna.config.domain.model.SemanticParser_Example;
import com.hframework.myna.config.service.interfaces.ISemanticParserSV;

@Controller
@RequestMapping(value = "/config/semanticParser")
public class SemanticParserController   {
    private static final Logger logger = LoggerFactory.getLogger(SemanticParserController.class);

	@Resource
	private ISemanticParserSV iSemanticParserSV;
  





    @InitBinder
    protected void initBinder(HttpServletRequest request,
        ServletRequestDataBinder binder) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CustomDateEditor editor = new CustomDateEditor(df, false);
        binder.registerCustomEditor(Date.class, editor);
    }

    /**
     * 查询展示语义解析器列表
     * @param semanticParser
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryListByAjax.json")
    @ResponseBody
    public ResultData list(@ModelAttribute("semanticParser") SemanticParser semanticParser,
                                      @ModelAttribute("example") SemanticParser_Example example, Pagination pagination){
        logger.debug("request : {},{},{}", semanticParser, example, pagination);
        try{
            ExampleUtils.parseExample(semanticParser, example);
            //设置分页信息
            example.setLimitStart(pagination.getStartIndex());
            example.setLimitEnd(pagination.getEndIndex());

            final List< SemanticParser> list = iSemanticParserSV.getSemanticParserListByExample(example);
            pagination.setTotalCount(iSemanticParserSV.getSemanticParserCountByExample(example));

            return ResultData.success().add("list",list).add("pagination",pagination);
        }catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }
    }



    /**
     * 查询展示语义解析器明细
     * @param semanticParser
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryOneByAjax.json")
    @ResponseBody
    public ResultData detail(@ModelAttribute("semanticParser") SemanticParser semanticParser){
        logger.debug("request : {},{}", semanticParser.getId(), semanticParser);
        try{
            SemanticParser result = null;
            if(semanticParser.getId() != null) {
                result = iSemanticParserSV.getSemanticParserByPK(semanticParser.getId());
            }else {
                SemanticParser_Example example = ExampleUtils.parseExample(semanticParser, SemanticParser_Example.class);
                List<SemanticParser> list = iSemanticParserSV.getSemanticParserListByExample(example);
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
    * 搜索一个语义解析器
    * @param  semanticParser
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/searchOneByAjax.json")
    @ResponseBody
    public ResultData search(@ModelAttribute(" semanticParser") SemanticParser  semanticParser){
        logger.debug("request : {}",  semanticParser);
        try{
            SemanticParser result = null;
            if(semanticParser.getId() != null) {
                result =  iSemanticParserSV.getSemanticParserByPK(semanticParser.getId());
            }else {
                SemanticParser_Example example = ExampleUtils.parseExample( semanticParser, SemanticParser_Example.class);

                example.setLimitStart(0);
                example.setLimitEnd(1);

                List<SemanticParser> list =  iSemanticParserSV.getSemanticParserListByExample(example);
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
    * 创建语义解析器
    * @param semanticParser
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createByAjax.json")
    @ResponseBody
    public ResultData create(@ModelAttribute("semanticParser") SemanticParser semanticParser) {
        logger.debug("request : {}", semanticParser);
        try {
            ControllerHelper.setDefaultValue(semanticParser, "id");
            int result = iSemanticParserSV.create(semanticParser);
            if(result > 0) {
                return ResultData.success(semanticParser);
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
    * 批量维护语义解析器
    * @param semanticParsers
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createsByAjax.json")
    @ResponseBody
    public ResultData batchCreate(@RequestBody SemanticParser[] semanticParsers) {
        logger.debug("request : {}", semanticParsers);

        try {
            ControllerHelper.setDefaultValue(semanticParsers, "id");
            ControllerHelper.reorderProperty(semanticParsers);

            int result = iSemanticParserSV.batchOperate(semanticParsers);
            if(result > 0) {
                return ResultData.success(semanticParsers);
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
    * 更新语义解析器
    * @param semanticParser
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/updateByAjax.json")
    @ResponseBody
    public ResultData update(@ModelAttribute("semanticParser") SemanticParser semanticParser) {
        logger.debug("request : {}", semanticParser);
        try {
            ControllerHelper.setDefaultValue(semanticParser, "id");
            int result = iSemanticParserSV.update(semanticParser);
            if(result > 0) {
                return ResultData.success(semanticParser);
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
    * 创建或更新语义解析器
    * @param semanticParser
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/saveOrUpdateByAjax.json")
    @ResponseBody
    public ResultData saveOrUpdate(@ModelAttribute("semanticParser") SemanticParser semanticParser) {
        logger.debug("request : {}", semanticParser);
        try {
            ControllerHelper.setDefaultValue(semanticParser, "id");
            int result = iSemanticParserSV.batchOperate(new SemanticParser[]{semanticParser});
            if(result > 0) {
                return ResultData.success(semanticParser);
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
    * 删除语义解析器
    * @param semanticParser
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/deleteByAjax.json")
    @ResponseBody
    public ResultData delete(@ModelAttribute("semanticParser") SemanticParser semanticParser) {
        logger.debug("request : {}", semanticParser);

        try {
            ControllerHelper.setDefaultValue(semanticParser, "id");
            int result = iSemanticParserSV.delete(semanticParser);
            if(result > 0) {
                return ResultData.success(semanticParser);
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
 	
	public ISemanticParserSV getISemanticParserSV(){
		return iSemanticParserSV;
	}
	//setter
	public void setISemanticParserSV(ISemanticParserSV iSemanticParserSV){
    	this.iSemanticParserSV = iSemanticParserSV;
    }
}
