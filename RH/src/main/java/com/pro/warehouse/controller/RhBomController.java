package com.pro.warehouse.controller;

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
import com.pro.warehouse.dao.RhBomRepository;
import com.pro.warehouse.pojo.EntrepotStatus;
import com.pro.warehouse.pojo.RhBom;
import com.pro.warehouse.util.PageUtil;

/**
 * bom管理API
 */
@Controller
public class RhBomController {
	
	Logger logger = LoggerFactory.getLogger(RhBomController.class.getName());
    @Autowired
    private RhBomRepository rhBomRepository;
    @Autowired
    private RhBomRecordRepository rhBomRecordRepository;
    // 通过@Resource注解引入JdbcTemplate对象
    @Resource
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private CommonRepository<RhBom> commonRepository;

    private Integer pagesize = 5;//每页显示的条数

    @RequestMapping(value = "/bom-list", method = {RequestMethod.GET, RequestMethod.POST})
    public String bomList(RhBom rhBom, int pagenum, ModelMap modelMap) {
        StringBuffer sql = null;
        try {
            sql = commonRepository.getFiledValues(rhBom, pagenum);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        sql.append(" 1 = 1");
        int totalpage = jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper(RhBom.class)).size();
        sql.append(" LIMIT " + (pagenum - 1) * pagesize + "," + pagesize );
        List<RhBom> boms = jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper(RhBom.class));
        for(RhBom bom : boms) {
        	String code = bom.getCode();
        	Integer input = rhBomRecordRepository.getSum(code, "入库");
        	Integer output = rhBomRecordRepository.getSum(code, "出库");
        	if(input == null) {
        		input = 0;
        	}
        	if(output == null) {
        		output = 0;
        	}
        	bom.setStock(input - output);
        }
        logger.debug("显示bom列表：" + boms);
        modelMap.addAttribute("boms", boms);
        modelMap.addAttribute("page", pagenum);
        modelMap.addAttribute("totalpage", PageUtil.getTotalPage(totalpage, pagesize));
        return "bom_list";
    }
    
    @RequestMapping(value = "/bom-add", method = {RequestMethod.GET, RequestMethod.POST})
    public String bomAdd(String code,String name,String spec,String unit, String remark) throws Exception {
    	RhBom bom = new RhBom();
    	bom.setCode(code);
    	bom.setName(name);
    	bom.setSpec(spec);
    	bom.setUnit(unit);
    	bom.setRemark(remark);
    	rhBomRepository.save(bom);
        return "redirect:/bom-list?pagenum=1";
    }
    
    @RequestMapping(value = "/bom-delete", method = {RequestMethod.GET, RequestMethod.POST})
    public String bomDelete(Long id) throws Exception {
    	rhBomRepository.deleteById(id);
        return "redirect:/bom-list?pagenum=1";
    }
    
    @RequestMapping(value = "/bom-search", method = {RequestMethod.GET, RequestMethod.POST})
    public String doSearch(String searchItem,String searchValue,RhBom rhBom, int pagenum, ModelMap modelMap) {
        StringBuffer sql = null;
        try {
            sql = commonRepository.getFiledValues(rhBom, pagenum);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        int totalpage = 0;
        if(searchValue!=null||!"".equals(searchValue)) {
        	sql.append(searchItem + " like '%" + searchValue + "%'");
        }else{
            sql.append(" 1 = 1");
        }
        totalpage = jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper(RhBom.class)).size();
        sql.append(" LIMIT " + (pagenum - 1) * pagesize + "," + pagesize);
        List<RhBom> boms = jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper(RhBom.class));
        logger.debug("查询结果：" + boms+"查询语句"+sql);
        modelMap.addAttribute("boms", boms);
        modelMap.addAttribute("page", pagenum);
        modelMap.addAttribute("totalpage", PageUtil.getTotalPage(totalpage, pagesize));
        /*modelMap.addAttribute("searchItem", searchItem);
        modelMap.addAttribute("searchValue", searchValue);*/
        return "bom_list";
    }
    
}
