package com.zyx.admin.mapper;
import com.zyx.admin.domain.MerchantInfo;

import java.util.List;
import java.util.Map;

public interface MerchantInfoMapper {

    List<MerchantInfo> findAll(Map<String, Object> maps);

    void add(MerchantInfo merchantInfo);

    int update(MerchantInfo merchantInfo);

}
