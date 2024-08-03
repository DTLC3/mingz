package com.chen.mingz.common.basic.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;


@Entity(name = "cmz_search")
@Data
public class CmzSearch extends BaseParameter {

    private static final long serialVersionUID = 365103917149598L;

    @Id
    @Column(name = "service")
    private String service;

    private String inquire;

    private String include;

    private Date create_time;

    private Integer enable;

    private String mark;

}
