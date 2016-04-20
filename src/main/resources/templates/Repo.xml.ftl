<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<!-- 务必配置正确namespace 就是所映射的实体类 -->
<mapper namespace="mapper.${entryName}">

    <resultMap type="${entryName?cap_first}" id="${entryName}Map">
<#list columns as col>
    <#if col.autoIncrement>
        <id column="${col.columnName}" property="${col.propertyName}"/>
    <#else>
        <result column="${col.columnName}" property="${col.propertyName}"/>
    </#if>
</#list>
    </resultMap>
    
    <!-- 新增 -->
    <insert id="add" parameterType="${entryName?cap_first}" keyProperty="${key}"
        useGeneratedKeys="true">
        insert into ${tableName}(
        <#list columns as col>
        <#if !col.autoIncrement && (columns?size)==(col_index+1)>
            ${col.columnName}
        <#elseif !col.autoIncrement>
            ${col.columnName},
        </#if>
        </#list>
        )
        values
        (
        <#list columns as col>
        <#if !col.autoIncrement && (columns?size)==(col_index+1)>
            ${"#{"+col.propertyName+"}"}
        <#elseif !col.autoIncrement>
            ${"#{"+col.propertyName+"}"},
        </#if>
        </#list>
        )
    </insert>
    
    <!-- 更新 -->
    <update id="update" parameterType="${entryName?cap_first}">
        update ${tableName} set 
        <#list columns as col>
        <#if !col.autoIncrement && (columns?size)==(col_index+1)>
            ${col.columnName}=${"#{"+col.propertyName+"}"}
        <#elseif !col.autoIncrement>
            ${col.columnName}=${"#{"+col.propertyName+"}"},
        </#if>
        </#list>
        where 
        <#list columns as col>
        <#if col.autoIncrement>
            ${col.columnName}=${"#{"+col.propertyName+"}"}
        </#if>
        </#list>
    </update>

    <!-- 根据ID删除 -->
    <delete id="removeById" parameterType="int">
        delete from ${tableName} where ${key} = ${"#{"+"id}"}
    </delete>
    
    <!-- 根据条件删除 -->
    <delete id="remove" parameterType="${entryName?cap_first}">
        delete from ${tableName} where 1=1
        <#list columns as col>
        <if test="${col.propertyName} != null">
            and ${col.columnName}=${"#{"+col.propertyName+"}"}
        </if>
        </#list>
    </delete>

    <!-- 根据${key}查询 -->
    <select id="getById" resultMap="${entryName}Map" parameterType="int">
        select * from ${tableName} where ${key} =${"#{"+"id}"}
    </select>

    <!-- 条件查询拼接 -->
    <sql id="findResultsWhere">
        <where>
            <#list columns as col>
            <if test="params.${col.propertyName} != null">
                and ${col.columnName} = ${"#{"}params.${col.propertyName}${"}"}
            </if>
            </#list>
        </where>
    </sql>
    
    <!-- 按example条件查询拼接 -->
    <sql id="findByExampleWhere">
        <where>
            <#list columns as col>
            <if test="${col.propertyName} != null">
                and ${col.columnName} = ${"#{"}${col.propertyName}${"}"}
            </if>
            </#list>
        </where>
    </sql>
    
    <!-- 条件查询 -->
    <select id="findResults" resultMap="${entryName}Map" parameterType="map">
        select * from ${tableName}  
        <include refid="findResultsWhere"></include>
        <if test="params.page != null">
        limit ${"#{"}params.page.from${"}"}, ${"#{"}params.page.size${"}"}
        </if>
    </select>
    
    <!-- 按实体查询 -->
    <select id="findByExample" resultMap="${entryName}Map" parameterType="${entryName?cap_first}">
        select * from ${tableName}  
        <include refid="findByExampleWhere"></include>
    </select>
    
    <!-- 按实体查询,返回count -->
    <select id="findByExampleCount" resultType="int" parameterType="${entryName?cap_first}">
        select count(1) from ${tableName}  
        <include refid="findByExampleWhere"></include>
    </select>

    <!-- 条件查询结果数量 -->
    <select id="findResultCount" resultType="int" parameterType="map">
        select count(1) from ${tableName}  
         <include refid="findResultsWhere"></include>
    </select>
    
    <!-- 批量插入 -->
    <insert id="addBatch" parameterType="iterator" useGeneratedKeys="true">
        insert into ${tableName}(
        <#list columns as col>
        <#if !col.autoIncrement && (columns?size)==(col_index+1)>
            ${col.columnName}
        <#elseif !col.autoIncrement>
            ${col.columnName},
        </#if>
        </#list>
        )
        values
        <foreach collection="array" item="item" separator="," >
        (
        <#list columns as col>
        <#if !col.autoIncrement && (columns?size)==(col_index+1)>
            ${"#{"+"item."+col.propertyName+"}"}
        <#elseif !col.autoIncrement>
            ${"#{"+"item."+col.propertyName+"}"},
        </#if>
        </#list>
        )
        </foreach>
    </insert>
    
</mapper>