package com.tps.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TableData {
    private String title;
    private List<String> headers;
    private List<List<String>> rows;
}
