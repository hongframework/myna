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
import com.hframework.myna.config.domain.model.Robot;
import com.hframework.myna.config.domain.model.Robot_Example;
import com.hframework.myna.config.service.interfaces.IRobotSV;

@Controller
@RequestMapping(value = "/config/robot")
public class RobotController   {
    private static final Logger logger = LoggerFactory.getLogger(RobotController.class);

	@Resource
	private IRobotSV iRobotSV;
  





    @InitBinder
    protected void initBinder(HttpServletRequest request,
        ServletRequestDataBinder binder) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CustomDateEditor editor = new CustomDateEditor(df, false);
        binder.registerCustomEditor(Date.class, editor);
    }

    /**
     * 查询展示机器人列表
     * @param robot
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryListByAjax.json")
    @ResponseBody
    public ResultData list(@ModelAttribute("robot") Robot robot,
                                      @ModelAttribute("example") Robot_Example example, Pagination pagination){
        logger.debug("request : {},{},{}", robot, example, pagination);
        try{
            ExampleUtils.parseExample(robot, example);
            //设置分页信息
            example.setLimitStart(pagination.getStartIndex());
            example.setLimitEnd(pagination.getEndIndex());

            final List< Robot> list = iRobotSV.getRobotListByExample(example);
            pagination.setTotalCount(iRobotSV.getRobotCountByExample(example));

            return ResultData.success().add("list",list).add("pagination",pagination);
        }catch (Exception e) {
            logger.error("error : ", e);
            return ResultData.error(ResultCode.ERROR);
        }
    }



    /**
     * 查询展示机器人明细
     * @param robot
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/queryOneByAjax.json")
    @ResponseBody
    public ResultData detail(@ModelAttribute("robot") Robot robot){
        logger.debug("request : {},{}", robot.getId(), robot);
        try{
            Robot result = null;
            if(robot.getId() != null) {
                result = iRobotSV.getRobotByPK(robot.getId());
            }else {
                Robot_Example example = ExampleUtils.parseExample(robot, Robot_Example.class);
                List<Robot> list = iRobotSV.getRobotListByExample(example);
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
    * 搜索一个机器人
    * @param  robot
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/searchOneByAjax.json")
    @ResponseBody
    public ResultData search(@ModelAttribute(" robot") Robot  robot){
        logger.debug("request : {}",  robot);
        try{
            Robot result = null;
            if(robot.getId() != null) {
                result =  iRobotSV.getRobotByPK(robot.getId());
            }else {
                Robot_Example example = ExampleUtils.parseExample( robot, Robot_Example.class);

                example.setLimitStart(0);
                example.setLimitEnd(1);

                List<Robot> list =  iRobotSV.getRobotListByExample(example);
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
    * 创建机器人
    * @param robot
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createByAjax.json")
    @ResponseBody
    public ResultData create(@ModelAttribute("robot") Robot robot) {
        logger.debug("request : {}", robot);
        try {
            ControllerHelper.setDefaultValue(robot, "id");
            int result = iRobotSV.create(robot);
            if(result > 0) {
                return ResultData.success(robot);
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
    * 批量维护机器人
    * @param robots
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/createsByAjax.json")
    @ResponseBody
    public ResultData batchCreate(@RequestBody Robot[] robots) {
        logger.debug("request : {}", robots);

        try {
            ControllerHelper.setDefaultValue(robots, "id");
            ControllerHelper.reorderProperty(robots);

            int result = iRobotSV.batchOperate(robots);
            if(result > 0) {
                return ResultData.success(robots);
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
    * 更新机器人
    * @param robot
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/updateByAjax.json")
    @ResponseBody
    public ResultData update(@ModelAttribute("robot") Robot robot) {
        logger.debug("request : {}", robot);
        try {
            ControllerHelper.setDefaultValue(robot, "id");
            int result = iRobotSV.update(robot);
            if(result > 0) {
                return ResultData.success(robot);
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
    * 创建或更新机器人
    * @param robot
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/saveOrUpdateByAjax.json")
    @ResponseBody
    public ResultData saveOrUpdate(@ModelAttribute("robot") Robot robot) {
        logger.debug("request : {}", robot);
        try {
            ControllerHelper.setDefaultValue(robot, "id");
            int result = iRobotSV.batchOperate(new Robot[]{robot});
            if(result > 0) {
                return ResultData.success(robot);
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
    * 删除机器人
    * @param robot
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/deleteByAjax.json")
    @ResponseBody
    public ResultData delete(@ModelAttribute("robot") Robot robot) {
        logger.debug("request : {}", robot);

        try {
            ControllerHelper.setDefaultValue(robot, "id");
            int result = iRobotSV.delete(robot);
            if(result > 0) {
                return ResultData.success(robot);
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
 	
	public IRobotSV getIRobotSV(){
		return iRobotSV;
	}
	//setter
	public void setIRobotSV(IRobotSV iRobotSV){
    	this.iRobotSV = iRobotSV;
    }
}
