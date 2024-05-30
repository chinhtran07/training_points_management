package com.tps.pojo;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "missing_report_image")
public class MissingReportImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "url")
    private String url;

    @Column(name = "missing_report_id")
    private Integer missingReport;
}
