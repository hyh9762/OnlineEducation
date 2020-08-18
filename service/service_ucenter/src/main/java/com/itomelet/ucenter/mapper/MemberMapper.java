package com.itomelet.ucenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itomelet.ucenter.entity.Member;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author ekko
 * @since 2020-08-15
 */
public interface MemberMapper extends BaseMapper<Member> {

    Integer countRegisterDay(String day);
}
