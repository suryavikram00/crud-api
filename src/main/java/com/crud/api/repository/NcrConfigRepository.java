package com.crud.api.repository;

import com.crud.api.ncr.entities.NcrConfigEntity;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NcrConfigRepository extends JpaRepository<NcrConfigEntity, Integer> {

    public List<NcrConfigEntity> findByConfigNameIn(List<String> configName);

    public NcrConfigEntity findByConfigName(String configName);

}
