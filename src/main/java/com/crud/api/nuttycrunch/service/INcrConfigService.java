package com.crud.api.nuttycrunch.service;

import java.util.List;

import com.crud.api.nuttycrunch.entity.NcrConfigEntity;

public interface INcrConfigService {

	public List<NcrConfigEntity> getAllConfigs();

	public List<NcrConfigEntity> getConfigsByConfigNames(List<String> configNames);

	public List<NcrConfigEntity> updateConfig(List<NcrConfigEntity> configList);

}
