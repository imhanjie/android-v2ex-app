package com.imhanjie.widget.common

import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

/**
 * 从子类递归向上(父类)寻找 ViewBinding 泛型，直到寻找到。
 * ps: 如有多个泛型声明，ViewBinding 的泛型需要放在第一个。
 */
@Suppress("UNCHECKED_CAST")
fun <VB : ViewBinding> getVBClass(enterClazz: Class<*>): Class<VB> {
    val type = enterClazz.genericSuperclass as ParameterizedType
    val clazz: Class<VB> = type.actualTypeArguments[0] as Class<VB>
    return if (ViewBinding::class.java.isAssignableFrom(clazz)) {
        clazz
    } else {
        getVBClass(enterClazz.superclass as Class<*>)
    }
}