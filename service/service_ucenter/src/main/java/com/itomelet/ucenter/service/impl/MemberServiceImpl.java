package com.itomelet.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itomelet.commonutils.JwtUtils;
import com.itomelet.commonutils.MD5;
import com.itomelet.servicebase.exception.GuliException;
import com.itomelet.ucenter.entity.Member;
import com.itomelet.ucenter.mapper.MemberMapper;
import com.itomelet.ucenter.service.MemberService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author ekko
 * @since 2020-08-15
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

    //登录的方法
    @Override
    public String login(Member member) {
        //获取登录号手机和密码
        String mobile = member.getMobile();
        String password = member.getPassword();
        //手机号和密码非空判断
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            throw new GuliException(20001, "登录失败");
        }
        //判断手机号是否正确
        QueryWrapper<Member> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Member::getMobile, mobile).eq(Member::getPassword, MD5.encrypt(password));
        Member mobileMember = baseMapper.selectOne(wrapper);
        if (mobileMember == null) {
            throw new GuliException(20001, "登录失败");
        }
        if (mobileMember.getIsDisabled()) {
            throw new GuliException(20001, "用户被禁用");
        }
        //登录成功
        return JwtUtils.getJwtToken(mobileMember.getId(), mobileMember.getNickname());
    }
}
