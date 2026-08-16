package com.hyunbindev.common.auth

@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class VisitorId(val required:Boolean=false)
