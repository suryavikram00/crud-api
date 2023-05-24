package com.crud.api.services.interfaces;

import java.util.List;

import com.crud.api.ncr.entities.NcrConfigEntity;

public interface INcrConfigService {

	public List<NcrConfigEntity> getAllConfigs();

	public List<NcrConfigEntity> getConfigsByConfigNames(List<String> configNames);

	public List<NcrConfigEntity> updateConfig(List<NcrConfigEntity> configList);

}
