package com.crud.api.nuttycrunch.model;


import com.crud.api.nuttycrunch.entity.NcrConfigEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.crud.api.nuttycrunch.enums.DataType;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NcrConfigModel {

	@JsonProperty("id")
	private Integer id;

	@JsonProperty("config_name")
	private String configName;

	@JsonProperty("config_value")
	private String configValue;

	@JsonProperty("description")
	private String description;

	@JsonProperty("write_enabled")
	private Boolean writeEnabled;

	@JsonProperty("active")
	private Boolean active;

	@JsonProperty("created_by")
	private int createdBy;

	@JsonProperty("created_on")
	private Date createdOn;

	@JsonProperty("protected")
	private Boolean protectedProd;

	@JsonProperty("updated_by")
	private int updatedBy;

	@JsonProperty("updated_on")
	private Date updatedOn;

	@JsonProperty("data_type")
	private DataType dataType;

	@JsonProperty("message")
	private String message;

	public NcrConfigModel(NcrConfigEntity entity) {
		this.id = entity.getId();
		this.configName = entity.getConfigName();
		this.configValue = entity.getConfigValue();
		this.description = entity.getDescription();
		this.writeEnabled = entity.getWriteEnabled();
		this.active = entity.getActive();
		this.createdBy = entity.getCreatedBy();
		this.createdOn = entity.getCreatedOn();
		this.updatedBy = entity.getUpdatedBy();
		this.updatedOn = entity.getUpdatedOn();
		this.protectedProd = entity.getProtectedProd();
		this.dataType = entity.getDataType();
		this.message = entity.getMessage();
	}

}
