# Neurons数据库连接池

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.nervousync/neurons-jdk17/badge.svg)](https://maven-badges.herokuapp.com/maven-central/org.nervousync/neurons-jdk17/)
[![License](https://img.shields.io/github/license/wmkm0113/neurons-jdk17.svg)](https://github.com/wmkm0113/neurons-jdk17/blob/master/LICENSE)
![Language](https://img.shields.io/badge/language-Java-green)
[![Twitter:wmkm0113](https://img.shields.io/twitter/follow/wmkm0113?label=Follow)](https://twitter.com/wmkm0113)

一个简单的JDBC数据库连接池开源项目，提供了一个高性能、高稳定的JDBC数据库连接池，帮助开发人员管理与监控数据库连接。

[English](README.md)
简体中文
[繁體中文](README_zh_TW.md)

## 目录
* [JDK版本](#JDK版本)
* [生命周期](#生命周期)
* [使用方法](#使用方法)
  + [在项目中添加支持](#1在项目中添加支持)
  + [初始化数据源和连接池](#2初始化数据源和连接池)
  + [JMX监控](#3jmx监控)
  + [数据源和连接池的关闭](#4数据源和连接池的关闭)
* [贡献与反馈](#贡献与反馈)
* [赞助与鸣谢](#赞助与鸣谢)

## JDK版本：
编译：OpenJDK 17   
运行：OpenJDK 17+ 或兼容版本

## 生命周期：
**功能冻结：** 2029年12月31日   
**安全更新：** 2032年12月31日

## 使用方法：
### 1、在项目中添加支持
**Maven:**   
```
<dependency>
    <groupId>org.nervousync</groupId>
	<artifactId>neurons-jdk17</artifactId>
    <version>${version}</version>
</dependency>
```
**Gradle:**   
```
Manual: compileOnly group: 'org.nervousync', name: 'neurons-jdk17', version: '${version}'
Short: compileOnly 'org.nervousync:neurons-jdk17:${version}'
```
**SBT:**   
```
libraryDependencies += "org.nervousync" % "neurons-jdk17" % "${version}" % "provided"
```
**Ivy:**   
```
<dependency org="org.nervousync" name="neurons-jdk17" rev="${version}"/>
```

### 2、初始化数据源和连接池
程序开发人员需要调用 org.nervousync.database.neurons.NeuronsDataSource 的构造方法来初始化数据源和连接池实例对象。

| 参数              | 数据类型       | 备注             |
|-----------------|------------|----------------|
| minConnections  | int        | 最小连接数          |
| maxConnections  | int        | 最大连接数          |
| validateTimeout | int        | 连接检查超时时间（单位：秒） |
| connectTimeout  | int        | 建立连接超时时间（单位：秒） |
| testOnBorrow    | boolean    | 在获取连接时检查连接是否有效 |
| testOnReturn    | boolean    | 在归还连接时检查连接是否有效 |
| retryLimit      | int        | 获取连接的重试次数      |
| jdbcUrl         | String     | JDBC连接字符串      |
| jdbcProperties  | Properties | JDBC配置信息       |
| username        | String     | 数据库用户名         |
| password        | String     | 数据库密码          |

### 3、JMX监控
在数据源初始化的过程中，会自动注册JMX监控服务。在JMX的监控列表中命名空间为 “NeuronsDataSource”，type值为 “DataSource”， name值为 “Neurons”。

### 4、数据源和连接池的关闭
程序开发人员需要显示调用数据源实例对象的 close 方法，完成数据源的关闭与连接池的销毁。在关闭连接池的过程中，数据源会自动回滚所有正在使用中的数据库连接。

## 贡献与反馈
欢迎各位朋友将此文档及项目中的提示信息、错误信息等翻译为更多语言，以帮助更多的使用者更好地了解与使用此工具包。   
如果在使用过程中发现问题或需要改进、添加相关功能，请提交issue到本项目或发送电子邮件到[wmkm0113\@gmail.com](mailto:wmkm0113@gmail.com?subject=bugs_and_features)   
为了更好地沟通，请在提交issue或发送电子邮件时，写明如下信息：   
1、目的是：发现Bug/功能改进/添加新功能   
2、请粘贴以下信息（如果存在）：传入数据，预期结果，错误堆栈信息   
3、您认为可能是哪里的代码出现问题（如提供可以帮助我们尽快地找到并解决问题）   
如果您提交的是添加新功能的相关信息，请确保需要添加的功能是一般性的通用需求，即添加的新功能可以帮助到大多数使用者。

如果您需要添加的是定制化的特殊需求，我将收取一定的定制开发费用，具体费用金额根据定制化的特殊需求的工作量进行评估。   
定制化特殊需求请直接发送电子邮件到[wmkm0113\@gmail.com](mailto:wmkm0113@gmail.com?subject=payment_features)，同时请尽量在邮件中写明您可以负担的开发费用预算金额。

## 赞助与鸣谢
<span id="JetBrains">
    <img src="https://resources.jetbrains.com/storage/products/company/brand/logos/jb_beam.png" width="100px" height="100px" alt="JetBrains Logo (Main) logo.">
    <span>非常感谢 <a href="https://www.jetbrains.com/">JetBrains</a> 通过许可证赞助我们的开源项目。</span>
</span>