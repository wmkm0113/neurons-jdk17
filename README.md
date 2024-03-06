# Neurons Database Connection Pool

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.nervousync/neurons-jdk17/badge.svg)](https://maven-badges.herokuapp.com/maven-central/org.nervousync/neurons-jdk17/)
[![License](https://img.shields.io/github/license/wmkm0113/neurons-jdk17.svg)](https://github.com/wmkm0113/neurons-jdk17/blob/master/LICENSE)
![Language](https://img.shields.io/badge/language-Java-green)
[![Twitter:wmkm0113](https://img.shields.io/twitter/follow/wmkm0113?label=Follow)](https://twitter.com/wmkm0113)

English
[简体中文](README_zh_CN.md)
[繁體中文](README_zh_TW.md)

A simple JDBC database connection pool open source project provides a high-performance, highly stable JDBC database connection pool to help developers manage and monitor database connections.

## Table of contents
* [JDK Version](#JDK-Version)
* [End of Life](#End-of-Life)
* [Usage](#Usage)
  + [Add support to the project](#1-add-support-to-the-project)
  + [Initialize data source](#2-initialize-data-source)
  + [JMX monitor](#3-jmx-monitor)
  + [Close data source](#4-close-data-source)
* [Contributions and feedback](#contributions-and-feedback)
* [Sponsorship and Thanks To](#sponsorship-and-thanks-to)

## JDK Version
Compile：OpenJDK 17   
Runtime: OpenJDK 17+ or compatible version

## End of Life

**Features Freeze:** 31, Dec, 2029   
**Secure Patch:** 31, Dec, 2032

## Usage
### 1. Add support to the project
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

### 2. Initialize data source
Developers need to invoke the constructor method for org.nervousync.database.neurons.NeuronsDataSource to initialize the data source instance.

| Parameter       | Type       | Notes                                                      |
|-----------------|------------|------------------------------------------------------------|
| minConnections  | int        | Minimum connection limit                                   |
| maxConnections  | int        | Maximum connection limit                                   |
| validateTimeout | int        | Timeout value of connection validate (Unit: seconds)       |
| connectTimeout  | int        | Timeout value of create connection (Unit: seconds)         |
| testOnBorrow    | boolean    | Check connection validate when obtains database connection |
| testOnReturn    | boolean    | Check connection validate when return database connection  |
| retryLimit      | int        | Retry count if obtains connection has error                |
| jdbcUrl         | String     | JDBC connection url string                                 |
| jdbcProperties  | Properties | JDBC properties information                                |
| username        | String     | Database username                                          |
| password        | String     | Database password                                          |

### 3. JMX monitor
Data source instance will register JMX manager bean automatically to initialize the data source.
The namespace of JMX is "NeuronsDataSource" type value is "DataSource" and name value is "Neurons"

### 4. Close data source
Developers need to explicitly call the close method of the data source instance object to complete the closing of the data source and the destruction of the connection pool. 
During the process of closing the connection pool, the data source automatically rolls back all database connections in use.

## Contributions and feedback
Friends are welcome to translate the prompt information, error messages, 
etc. in this document and project into more languages to help more users better understand and use this toolkit.   
If you find problems during use or need to improve or add related functions, please submit an issue to this project
or send email to [wmkm0113\@gmail.com](mailto:wmkm0113@gmail.com?subject=bugs_and_features)   
For better communication, please include the following information when submitting an issue or sending an email:
1. The purpose is: discover bugs/function improvements/add new features   
2. Please paste the following information (if it exists): incoming data, expected results, error stack information   
3. Where do you think there may be a problem with the code (if provided, it can help us find and solve the problem as soon as possible)

If you are submitting information about adding new features, please ensure that the features to be added are general needs, that is, the new features can help most users.

If you need to add customized special requirements, I will charge a certain custom development fee.
The specific fee amount will be assessed based on the workload of the customized special requirements.   
For customized special features, please send an email directly to [wmkm0113\@gmail.com](mailto:wmkm0113@gmail.com?subject=payment_features). At the same time, please try to indicate the budget amount of development cost you can afford in the email.

## Sponsorship and Thanks To
<span id="JetBrains">
    <img src="https://resources.jetbrains.com/storage/products/company/brand/logos/jb_beam.png" width="100px" height="100px" alt="JetBrains Logo (Main) logo.">
    <span>Many thanks to <a href="https://www.jetbrains.com/">JetBrains</a> for sponsoring our Open Source projects with a license.</span>
</span>