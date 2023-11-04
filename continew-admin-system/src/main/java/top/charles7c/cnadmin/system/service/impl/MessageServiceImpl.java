/*
 * Copyright (c) 2022-present Charles7c Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package top.charles7c.cnadmin.system.service.impl;

import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.extra.spring.SpringUtil;

import top.charles7c.cnadmin.common.model.query.PageQuery;
import top.charles7c.cnadmin.common.model.resp.PageDataResp;
import top.charles7c.cnadmin.common.service.CommonUserService;
import top.charles7c.cnadmin.common.util.ExceptionUtils;
import top.charles7c.cnadmin.common.util.helper.QueryHelper;
import top.charles7c.cnadmin.common.util.validate.CheckUtils;
import top.charles7c.cnadmin.system.mapper.MessageMapper;
import top.charles7c.cnadmin.system.model.entity.MessageDO;
import top.charles7c.cnadmin.system.model.query.MessageQuery;
import top.charles7c.cnadmin.system.model.req.MessageReq;
import top.charles7c.cnadmin.system.model.resp.MessageResp;
import top.charles7c.cnadmin.system.service.MessageService;
import top.charles7c.cnadmin.system.service.MessageUserService;

/**
 * 消息业务实现
 *
 * @author BULL_BCLS
 * @since 2023/10/15 19:05
 */
@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageMapper baseMapper;
    private final MessageUserService messageUserService;

    @Override
    public PageDataResp<MessageResp> page(MessageQuery query, PageQuery pageQuery) {
        QueryWrapper<MessageDO> queryWrapper = QueryHelper.build(query);
        queryWrapper.apply(null != query.getUserId(), "t2.user_id={0}", query.getUserId())
            .apply(null != query.getIsRead(), "t2.is_read={0}", query.getIsRead());
        IPage<MessageResp> page = baseMapper.selectPageByUserId(pageQuery.toPage(), queryWrapper);
        page.getRecords().forEach(this::fill);
        return PageDataResp.build(page);
    }

    @Override
    public void add(MessageReq req, List<Long> userIdList) {
        CheckUtils.throwIf(() -> CollUtil.isEmpty(userIdList), "消息接收人不能为空");
        MessageDO message = BeanUtil.copyProperties(req, MessageDO.class);
        baseMapper.insert(message);
        messageUserService.add(message.getId(), userIdList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> ids) {
        baseMapper.deleteBatchIds(ids);
        messageUserService.deleteByMessageIds(ids);
    }

    /**
     * 填充数据
     *
     * @param message
     *            待填充信息
     */
    private void fill(MessageResp message) {
        Long createUser = message.getCreateUser();
        if (null == createUser) {
            return;
        }
        CommonUserService userService = SpringUtil.getBean(CommonUserService.class);
        message.setCreateUserString(ExceptionUtils.exToNull(() -> userService.getNicknameById(createUser)));
    }
}