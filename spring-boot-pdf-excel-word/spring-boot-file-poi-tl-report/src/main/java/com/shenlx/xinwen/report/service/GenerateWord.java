package com.shenlx.xinwen.report.service;

import com.shenlx.xinwen.report.model.LabelData;

/**
 *  封装统一数据生成接口
 */
public interface GenerateWord {
    Object generateWord(LabelData data);
}
