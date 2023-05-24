package com.crud.api.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crud.api.ncr.entities.NcrConfigEntity;
import com.crud.api.repository.NcrConfigRepository;
import com.crud.api.services.interfaces.INcrConfigService;
import com.crud.api.utility.JwtTokenUtils;
import com.ncr.config.exception.NcrConfigException;
import java.util.LinkedList;

import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class NcrConfigService implements INcrConfigService {

    @Autowired
    private NcrConfigRepository ncrConfigRepo;

    public List<NcrConfigEntity> getAllConfigs() {
        return ncrConfigRepo.findAll();
    }

    public List<NcrConfigEntity> getConfigsByConfigNames(List<String> configNames) {
        return ncrConfigRepo.findByConfigNameIn(configNames);
    }

    public List<NcrConfigEntity> updateConfig(List<NcrConfigEntity> configList) {
        try {
//			List of configEntity from DB
            List<NcrConfigEntity> configEntityListFromDB = new ArrayList<>();
            List<String> configName = new ArrayList<>();
            for (NcrConfigEntity ncrConfigEntity : configList) {
                configName.add(ncrConfigEntity.getConfigName());
            }
//			get list of configEntity using configNames
            configEntityListFromDB = getConfigsByConfigNames(configName);
//          Map to store configName and corresponding entity record			
            Map<String, Object> ConfigNametoConfigEntity = new HashMap<String, Object>();
            for (NcrConfigEntity ncrConfigEntity : configEntityListFromDB) {
                ConfigNametoConfigEntity.put(ncrConfigEntity.getConfigName(), ncrConfigEntity);
            }

            for (NcrConfigEntity ncrConfigEntity : configList) {
//				get ncrConfigEntity using configName from map and update the values
                NcrConfigEntity ncrConfigEntityFromDB = (NcrConfigEntity) ConfigNametoConfigEntity
                        .get(ncrConfigEntity.getConfigName());
                ncrConfigEntityFromDB.setConfigValue(ncrConfigEntity.getConfigValue());
                ncrConfigEntityFromDB.setUpdatedOn(new Date());
                ncrConfigEntityFromDB.setUpdatedBy(
                        Objects.nonNull(JwtTokenUtils.getLoggedInUserId()) ? JwtTokenUtils.getLoggedInUserId() : null);
                ncrConfigEntityFromDB.setDescription(
                        Objects.nonNull(ncrConfigEntity.getDescription()) ? ncrConfigEntity.getDescription()
                        : ncrConfigEntityFromDB.getDescription());
            }
            return ncrConfigRepo.saveAll(configEntityListFromDB);
        } catch (NcrConfigException e) {
            throw new NcrConfigException(e.getMessage());
        } catch (Exception e) {
            log.info("exception caught: {}", e.getMessage());
            return Collections.EMPTY_LIST;
        }
    }

    public Set<NcrConfigEntity> invalidConfig(List<NcrConfigEntity> configList) {
//		List of configEntity from DB
        Set<NcrConfigEntity> invalidConfigEntitiesInFile = new HashSet<>();
        invalidConfigEntitiesInFile.addAll(getMissingConfigName(configList));
        List<NcrConfigEntity> missingConfigList = invalidConfigEntitiesInFile.stream().collect(Collectors.toList());
        configList.removeAll(missingConfigList);
        invalidConfigEntitiesInFile.addAll(getConfigWithInvalidValue(configList));
        return invalidConfigEntitiesInFile;
    }

    public Set<NcrConfigEntity> getConfigWithInvalidValue(List<NcrConfigEntity> configList) {
        Set<NcrConfigEntity> invalidConfigValueList = new HashSet<>();
        for (NcrConfigEntity ncrConfigEntity : configList) {
            if (!isNumeric(ncrConfigEntity.getConfigValue())) {
                ncrConfigEntity.setMessage("The value is not a number : " + ncrConfigEntity.getConfigValue());
                invalidConfigValueList.add(ncrConfigEntity);
                continue;
            }
            if (!(Integer.parseInt((ncrConfigEntity.getConfigValue())) >= 0
                    && Integer.parseInt((ncrConfigEntity.getConfigValue())) <= 99)) {
                ncrConfigEntity
                        .setMessage("The value should be between 0 and 99 : " + ncrConfigEntity.getConfigValue());
                invalidConfigValueList.add(ncrConfigEntity);
            }
        }
        return invalidConfigValueList;

    }

    public Set<NcrConfigEntity> getMissingConfigName(List<NcrConfigEntity> configList) {
//		List of configEntity from DB
        List<NcrConfigEntity> configEntityListFromDB = new ArrayList<>();

        List<String> configName = new ArrayList<>();
        for (NcrConfigEntity ncrConfigEntity : configList) {
            configName.add(ncrConfigEntity.getConfigName());
        }
//		get list of configEntity using configNames
        configEntityListFromDB = getConfigsByConfigNames(configName);
//      Map to store configName and corresponding entity record			
        Map<String, Object> ConfigNametoConfigEntity = new HashMap<String, Object>();
        for (NcrConfigEntity ncrConfigEntity : configEntityListFromDB) {
            ConfigNametoConfigEntity.put(ncrConfigEntity.getConfigName(), ncrConfigEntity);
        }
//	list of all missing config_entity which are present in file but not in db;
        Set<NcrConfigEntity> missingConfigEntitiesInFile = new HashSet<>();
        for (NcrConfigEntity ncrConfigEntity : configList) {
            if (!ConfigNametoConfigEntity.containsKey(ncrConfigEntity.getConfigName())) {
                ncrConfigEntity.setMessage("Config name is not present in the database");
                missingConfigEntitiesInFile.add(ncrConfigEntity);
            }

        }
        return missingConfigEntitiesInFile;
    }

    public List<NcrConfigEntity> validConfig(List<NcrConfigEntity> configList,
            List<NcrConfigEntity> invalidConfigList) {
        configList.removeAll(invalidConfigList);
        return configList;

    }

    public boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    @Transactional(transactionManager = "ncrTransactionManager")
    public List<NcrConfigEntity> saveOrUpdateConfig(List<NcrConfigEntity> ncrConfigEntityList) {
        List<NcrConfigEntity> updatedConfigEntityList = new LinkedList<>();
        try {            
            for (NcrConfigEntity eachNcrConfigEntity : ncrConfigEntityList) {
                NcrConfigEntity ncrConfigEntityFromDB = ncrConfigRepo.findByConfigName(eachNcrConfigEntity.getConfigName());
                if (ncrConfigEntityFromDB != null) {
                    if (ncrConfigEntityFromDB.getConfigValue() != null
                            && ncrConfigEntityFromDB.getConfigValue().equals(eachNcrConfigEntity.getConfigValue())) {
                        updatedConfigEntityList.add(ncrConfigEntityFromDB);
                        continue;
                    }
                    ncrConfigEntityFromDB.setConfigValue(eachNcrConfigEntity.getConfigValue());
                    ncrConfigEntityFromDB.setUpdatedOn(new Date());
                    ncrConfigEntityFromDB.setUpdatedBy(
                            Objects.nonNull(JwtTokenUtils.getLoggedInUserId()) ? JwtTokenUtils.getLoggedInUserId() : null);
                    ncrConfigRepo.save(ncrConfigEntityFromDB);
                    updatedConfigEntityList.add(ncrConfigEntityFromDB);
                    continue;
                } else {
                    eachNcrConfigEntity.setDefaultValues();
                    ncrConfigRepo.save(eachNcrConfigEntity);
                    updatedConfigEntityList.add(eachNcrConfigEntity);
                    continue;
                }
            }
        } catch (Exception e) {
            log.info("Exception in saveOrUpdateConfig :: ", e);
            return Collections.EMPTY_LIST;
        }
        return updatedConfigEntityList;
    }

}
