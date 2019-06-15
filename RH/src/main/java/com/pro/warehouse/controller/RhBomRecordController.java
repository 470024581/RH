package com.pro.warehouse.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pro.warehouse.dao.CommonRepository;
import com.pro.warehouse.dao.RhBomRecordRepository;
import com.pro.warehouse.pojo.EntrepotStatus;
import com.pro.warehouse.pojo.RhBomRecord;
import com.pro.warehouse.util.PageUtil;

/**
 * bom出入库管理API
 */
@Controller
public class RhBomRecordController {
	
	Logger logger = LoggerFactory.getLogger(RhBomRecordController.class.getName());
    @Autowired
    private RhBomRecordRepository RhBomRecordRepository;
    // 通过@Resource注解引入JdbcTemplate对象
    @Resource
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private CommonRepository<RhBomRecord> commonRepository;

    private Integer pagesize = 5;//每页显示的条数

    @RequestMapping(value = "/bom-record-list", method = {RequestMethod.GET, RequestMethod.POST})
    public String bomList(RhBomRecord rhBomRecord, int pagenum, ModelMap modelMap) {
        StringBuffer sql = null;
        try {
            sql = commonRepository.getFiledValues(rhBomRecord, pagenum);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        sql.append(" 1 = 1");
        int totalpage = jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper(RhBomRecord.class)).size();
        sql.append(" LIMIT " + (pagenum - 1) * pagesize + "," + pagesize );
        List<RhBomRecord> records = jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper(RhBomRecord.class));
        logger.debug("显示records列表：" + records);
        modelMap.addAttribute("records", records);
        modelMap.addAttribute("page", pagenum);
        modelMap.addAttribute("totalpage", PageUtil.getTotalPage(totalpage, pagesize));
        return "bom_record_list";
    }
    
    @RequestMapping(value = "/bom-record-add", method = {RequestMethod.GET, RequestMethod.POST})
    public String bomAdd(String code,String type,Integer num) throws Exception {
    	RhBomRecord record = new RhBomRecord();
    	record.setCode(code);
    	record.setNum(num);
    	record.setTime(new Date());
    	record.setType(type);
    	RhBomRecordRepository.save(record);
        return "redirect:/bom-record-list?pagenum=1";
    }
    
    @RequestMapping(value = "/bom-record-delete", method = {RequestMethod.GET, RequestMethod.POST})
    public String bomDelete(Long id) throws Exception {
    	RhBomRecordRepository.deleteById(id);
        return "redirect:/bom-record-list?pagenum=1";
    }
    
    @RequestMapping(value = "/bom-record-search", method = {RequestMethod.GET, RequestMethod.POST})
    public String doSearch(String searchItem,String searchValue,RhBomRecord RhBomRecord, int pagenum, ModelMap modelMap) {
        StringBuffer sql = null;
        try {
            sql = commonRepository.getFiledValues(RhBomRecord, pagenum);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        int totalpage = 0;
        if(searchValue!=null||!"".equals(searchValue)) {
        	sql.append(searchItem + " like '%" + searchValue + "%'");
        }else{
            sql.append(" 1 = 1");
        }
        totalpage = jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper(RhBomRecord.class)).size();
        sql.append(" LIMIT " + (pagenum - 1) * pagesize + "," + pagesize);
        List<RhBomRecord> records = jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper(RhBomRecord.class));
        logger.debug("查询结果：" + records+"查询语句"+sql);
        modelMap.addAttribute("records", records);
        modelMap.addAttribute("page", pagenum);
        modelMap.addAttribute("totalpage", PageUtil.getTotalPage(totalpage, pagesize));
        /*modelMap.addAttribute("searchItem", searchItem);
        modelMap.addAttribute("searchValue", searchValue);*/
        return "bom_record_list";
    }
    
}
