package com.itomelet.ucenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itomelet.ucenter.entity.Member;
import com.itomelet.ucenter.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author ekko
 * @since 2020-08-15
 */
public interface MemberService extends IService<Member> {

    String login(Member member);

    void register(RegisterVo registerVo);

    Member getOpenIdMember(String openid);
}
