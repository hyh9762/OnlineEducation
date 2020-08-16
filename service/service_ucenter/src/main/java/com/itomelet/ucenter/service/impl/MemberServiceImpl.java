package com.itomelet.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itomelet.commonutils.JwtUtils;
import com.itomelet.commonutils.MD5;
import com.itomelet.servicebase.exception.GuliException;
import com.itomelet.ucenter.entity.Member;
import com.itomelet.ucenter.entity.vo.RegisterVo;
import com.itomelet.ucenter.mapper.MemberMapper;
import com.itomelet.ucenter.service.MemberService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

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

    @Resource
    private RedisTemplate<String, String> redisTemplate;

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

    //注册的方法
    @Override
    public void register(RegisterVo registerVo) {
        //获取注册的数据
        String code = registerVo.getCode(); //验证码
        String mobile = registerVo.getMobile(); //手机号
        String nickname = registerVo.getNickname(); //昵称
        String password = registerVo.getPassword(); //密码

        //非空判断
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)
            || StringUtils.isEmpty(code) || StringUtils.isEmpty(nickname)) {
            throw new GuliException(20001, "注册失败");
        }
        //判断验证码
        //获取redis验证码
        String redisCode = redisTemplate.opsForValue().get(mobile);
        if (!code.equals(redisCode)) {
            throw new GuliException(20001, "注册失败,验证码错误");
        }

        //判断手机号是否重复，表里面存在相同手机号不进行添加
        QueryWrapper<Member> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        Integer count = baseMapper.selectCount(wrapper);
        if (count > 0) {
            throw new GuliException(20001, "注册失败,手机号已存在");
        }

        //数据添加数据库中
        Member member = new Member();
        member.setMobile(mobile);
        member.setNickname(nickname);
        member.setPassword(MD5.encrypt(password));//密码需要加密的
        member.setIsDisabled(false);//用户不禁用
        member.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");
        baseMapper.insert(member);
    }
}
