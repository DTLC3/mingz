package com.chen.mingz.common.basic.dao.impl;

import com.chen.mingz.common.basic.model.CmzSearch;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository("cmzsearch_repository")
public interface CmzSearchRepository extends CrudRepository<CmzSearch, String> {
}
