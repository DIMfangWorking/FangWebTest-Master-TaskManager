package com.xinwei.taskmanager.deploymodel;

import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Field;

import com.xinwei.taskmanager.model.annotation.CheckField;

public class EIDetailed {

	@Indexed(background = true, useGeneratedName = true, unique = true, dropDups = true, direction = IndexDirection.ASCENDING)
	@Field("MsgId")
	@CheckField(unique = true, required = true)
	private int MsgId;

	@Indexed(background = true, useGeneratedName = true, unique = true, dropDups = true, direction = IndexDirection.ASCENDING)
	@Field("name")
	@CheckField(unique = true, required = true, trim = true)
	private String name;

	@Field("DspId")
	@CheckField(required = true)
	private int DspId;

	@Field("CoreId")
	@CheckField(required = true)
	private int CoreId;

	@Field("Tlv")
	@CheckField(required = true)
	private Object Tlv;

	@Field("Struct")
	@CheckField(required = true)
	private Object Struct;

	public int getMsgId() {
		return MsgId;
	}

	public void setMsgId(int msgId) {
		MsgId = msgId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDspId() {
		return DspId;
	}

	public void setDspId(int dspId) {
		DspId = dspId;
	}

	public int getCoreId() {
		return CoreId;
	}

	public void setCoreId(int coreId) {
		CoreId = coreId;
	}

	public Object getTlv() {
		return Tlv;
	}

	public void setTlv(Object tlv) {
		Tlv = tlv;
	}

	public Object getStruct() {
		return Struct;
	}

	public void setStruct(Object struct) {
		Struct = struct;
	}

}
