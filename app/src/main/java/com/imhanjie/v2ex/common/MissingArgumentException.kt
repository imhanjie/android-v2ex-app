package com.imhanjie.v2ex.common

class MissingArgumentException(argName: String) : RuntimeException("缺少 $argName 参数")