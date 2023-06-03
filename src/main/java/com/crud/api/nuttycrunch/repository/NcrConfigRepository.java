package com.crud.api.nuttycrunch.repository;

import com.crud.api.nuttycrunch.entity.NcrConfigEntity;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NcrConfigRepository extends JpaRepository<NcrConfigEntity, Integer> {

    public List<NcrConfigEntity> findByConfigNameIn(List<String> configName);

    public NcrConfigEntity findByConfigName(String configName);

}
