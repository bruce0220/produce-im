/*
 * Copyright (c) 2016. All rights reserved by XuanWu Wireless Technology Co.Ltd.
 */
package ${packagePath}.db.entity.${entryName};
 
<#list importlist as import>
import ${import};
</#list>
import com.xuanwu.im.db.base.Entity;
import com.xuanwu.im.db.base.GenericEntity;

import org.apache.commons.lang3.builder.ToStringBuilder;
 
 /**
* @author ${author}
* @date ${currDate}
 * @version 1.0.0
 */
public class ${entryName?cap_first} /*extends GenericEntity*/ implements Entity {

    private static final long serialVersionUID = 1L;
    
    <#list columns as col>
    /**  ${col.comment}  */
    private ${col.columnTypeName} ${col.propertyName};
    
    </#list>
    
    <#list columns as col>
    public ${col.columnTypeName} ${"get"+col.propertyName?cap_first}() {
        return ${col.propertyName};
    }

    public void ${"set"+col.propertyName?cap_first}(${col.columnTypeName} ${col.propertyName}) {
        ${"this."+col.propertyName} = ${col.propertyName};
    }
    
    </#list>
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}