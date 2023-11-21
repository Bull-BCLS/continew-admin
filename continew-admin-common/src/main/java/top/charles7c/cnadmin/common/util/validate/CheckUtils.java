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

package top.charles7c.cnadmin.common.util.validate;

import java.util.function.BooleanSupplier;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import cn.hutool.core.util.StrUtil;

import top.charles7c.cnadmin.common.exception.ServiceException;
import top.charles7c.continew.starter.core.constant.StringConsts;

/**
 * 业务参数校验工具类（抛出 500 ServiceException）
 *
 * @see ServiceException
 * @author Charles7c
 * @since 2023/1/2 22:12
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CheckUtils extends Validator {

    private static final Class<ServiceException> EXCEPTION_TYPE = ServiceException.class;

    /**
     * 如果不存在，抛出异常
     *
     * @param obj
     *            被检测的对象
     * @param entityName
     *            实体名
     * @param fieldName
     *            字段名
     * @param fieldValue
     *            字段值
     */
    public static void throwIfNotExists(Object obj, String entityName, String fieldName, Object fieldValue) {
        String message = String.format("%s 为 [%s] 的 %s 记录已不存在", fieldName, fieldValue,
            StrUtil.replace(entityName, "DO", StringConsts.EMPTY));
        throwIfNull(obj, message, EXCEPTION_TYPE);
    }

    /**
     * 如果为空，抛出异常
     *
     * @param obj
     *            被检测的对象
     * @param template
     *            异常信息模板，被替换的部分用 {} 表示，如果模板为 null，返回 "null"
     * @param params
     *            参数值
     */
    public static void throwIfNull(Object obj, String template, Object... params) {
        throwIfNull(obj, StrUtil.format(template, params), EXCEPTION_TYPE);
    }

    /**
     * 如果不为空，抛出异常
     *
     * @param obj
     *            被检测的对象
     * @param template
     *            异常信息模板，被替换的部分用 {} 表示，如果模板为 null，返回 "null"
     * @param params
     *            参数值
     */
    public static void throwIfNotNull(Object obj, String template, Object... params) {
        throwIfNotNull(obj, StrUtil.format(template, params), EXCEPTION_TYPE);
    }

    /**
     * 如果存在，抛出异常
     *
     * @param obj
     *            被检测的对象
     * @param entityName
     *            实体名
     * @param fieldName
     *            字段名
     * @param fieldValue
     *            字段值
     */
    public static void throwIfExists(Object obj, String entityName, String fieldName, Object fieldValue) {
        String message = String.format("%s 为 [%s] 的 %s 记录已存在", fieldName, fieldValue, entityName);
        throwIfNotNull(obj, message, EXCEPTION_TYPE);
    }

    /**
     * 如果为空，抛出异常
     *
     * @param obj
     *            被检测的对象
     * @param template
     *            异常信息模板，被替换的部分用 {} 表示，如果模板为 null，返回 "null"
     * @param params
     *            参数值
     */
    public static void throwIfEmpty(Object obj, String template, Object... params) {
        throwIfEmpty(obj, StrUtil.format(template, params), EXCEPTION_TYPE);
    }

    /**
     * 如果不为空，抛出异常
     *
     * @param obj
     *            被检测的对象
     * @param template
     *            异常信息模板，被替换的部分用 {} 表示，如果模板为 null，返回 "null"
     * @param params
     *            参数值
     */
    public static void throwIfNotEmpty(Object obj, String template, Object... params) {
        throwIfNotEmpty(obj, StrUtil.format(template, params), EXCEPTION_TYPE);
    }

    /**
     * 如果为空，抛出异常
     *
     * @param str
     *            被检测的字符串
     * @param template
     *            异常信息模板，被替换的部分用 {} 表示，如果模板为 null，返回 "null"
     * @param params
     *            参数值
     */
    public static void throwIfBlank(CharSequence str, String template, Object... params) {
        throwIfBlank(str, StrUtil.format(template, params), EXCEPTION_TYPE);
    }

    /**
     * 如果不为空，抛出异常
     *
     * @param str
     *            被检测的字符串
     * @param template
     *            异常信息模板，被替换的部分用 {} 表示，如果模板为 null，返回 "null"
     * @param params
     *            参数值
     */
    public static void throwIfNotBlank(CharSequence str, String template, Object... params) {
        throwIfNotBlank(str, StrUtil.format(template, params), EXCEPTION_TYPE);
    }

    /**
     * 如果相同，抛出异常
     *
     * @param obj1
     *            要比较的对象1
     * @param obj2
     *            要比较的对象2
     * @param template
     *            异常信息模板，被替换的部分用 {} 表示，如果模板为 null，返回 "null"
     * @param params
     *            参数值
     */
    public static void throwIfEqual(Object obj1, Object obj2, String template, Object... params) {
        throwIfEqual(obj1, obj2, StrUtil.format(template, params), EXCEPTION_TYPE);
    }

    /**
     * 如果不相同，抛出异常
     *
     * @param obj1
     *            要比较的对象1
     * @param obj2
     *            要比较的对象2
     * @param template
     *            异常信息模板，被替换的部分用 {} 表示，如果模板为 null，返回 "null"
     * @param params
     *            参数值
     */
    public static void throwIfNotEqual(Object obj1, Object obj2, String template, Object... params) {
        throwIfNotEqual(obj1, obj2, StrUtil.format(template, params), EXCEPTION_TYPE);
    }

    /**
     * 如果相同，抛出异常（不区分大小写）
     *
     * @param str1
     *            要比较的字符串1
     * @param str2
     *            要比较的字符串2
     * @param template
     *            异常信息模板，被替换的部分用 {} 表示，如果模板为 null，返回 "null"
     * @param params
     *            参数值
     */
    public static void throwIfEqualIgnoreCase(CharSequence str1, CharSequence str2, String template, Object... params) {
        throwIfEqualIgnoreCase(str1, str2, StrUtil.format(template, params), EXCEPTION_TYPE);
    }

    /**
     * 如果不相同，抛出异常（不区分大小写）
     *
     * @param str1
     *            要比较的字符串1
     * @param str2
     *            要比较的字符串2
     * @param template
     *            异常信息模板，被替换的部分用 {} 表示，如果模板为 null，返回 "null"
     * @param params
     *            参数值
     */
    public static void throwIfNotEqualIgnoreCase(CharSequence str1, CharSequence str2, String template,
        Object... params) {
        throwIfNotEqualIgnoreCase(str1, str2, StrUtil.format(template, params), EXCEPTION_TYPE);
    }

    /**
     * 如果条件成立，抛出异常
     *
     * @param condition
     *            条件
     * @param template
     *            异常信息模板，被替换的部分用 {} 表示，如果模板为 null，返回 "null"
     * @param params
     *            参数值
     */
    public static void throwIf(boolean condition, String template, Object... params) {
        throwIf(condition, StrUtil.format(template, params), EXCEPTION_TYPE);
    }

    /**
     * 如果条件成立，抛出异常
     *
     * @param conditionSupplier
     *            条件
     * @param template
     *            异常信息模板，被替换的部分用 {} 表示，如果模板为 null，返回 "null"
     * @param params
     *            参数值
     */
    public static void throwIf(BooleanSupplier conditionSupplier, String template, Object... params) {
        throwIf(conditionSupplier, StrUtil.format(template, params), EXCEPTION_TYPE);
    }
}
