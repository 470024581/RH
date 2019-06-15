package com.pro.warehouse.pojo;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class RhBomRecord {
	
	@Id
	//设置主键并且设置主键为自增
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long id;
	// 物料编码
	private String code;
	// 出/入库
	private String type;
	// 数量
	private int num;
	// 操作时间
	private Date time;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}

}
