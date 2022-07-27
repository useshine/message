package com.example.message.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.message.entity.MessageTemplate;
import com.example.message.vo.EditMessgeVo;
import com.example.message.vo.SearchVo;
import org.apache.ibatis.annotations.Param;

public interface MessageTemplateMapper extends IMapper<MessageTemplate> {
    void Save(MessageTemplate messageTemplate);
    IPage<MessageTemplate> Search(@Param("page")Page<MessageTemplate> page, @Param("searchCondition")SearchVo searchVo);
    void UpdateMsg(EditMessgeVo editMessgeVo);
}
