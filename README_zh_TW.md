# Neurons資料庫連線池

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.nervousync/neurons-jdk17/badge.svg)](https://maven-badges.herokuapp.com/maven-central/org.nervousync/neurons-jdk17/)
[![License](https://img.shields.io/github/license/wmkm0113/neurons-jdk17.svg)](https://github.com/wmkm0113/neurons-jdk17/blob/master/LICENSE)
![Language](https://img.shields.io/badge/language-Java-green)
[![Twitter:wmkm0113](https://img.shields.io/twitter/follow/wmkm0113?label=Follow)](https://twitter.com/wmkm0113)

[English](README.md)
[简体中文](README_zh_CN.md)
繁體中文

## 目錄
* [JDK版本](#JDK版本)
* [生命週期](#生命週期)
* [使用方法](#使用方法)
  + [在專案中添加支持](#1在專案中添加支持)
  + [初始化資料庫和連接池](#2初始化資料庫和連接池)
  + [JMX監控](#3jmx監控)
  + [資料庫和連接池的關閉](#4資料庫和連接池的關閉)
* [貢獻與回饋](#貢獻與回饋)
* [贊助與鳴謝](#贊助與鳴謝)

## JDK版本：
編譯：OpenJDK 17   
運行：OpenJDK 17+ 或相容版本

## 生命週期：
**功能凍結：** 2029年12月31日   
**安全更新：** 2032年12月31日

## 使用方法：
### 1、在專案中添加支持
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

### 2、初始化資料庫和連接池
程式開發人員需要調用 org.nervousync.database.neurons.NeuronsDataSource 的構造方法來初始化資料庫和連接池實例物件。

| 參數              | 資料類型       | 備註             |
|-----------------|------------|----------------|
| minConnections  | int        | 最小連接數          |
| maxConnections  | int        | 最大連接數          |
| validateTimeout | int        | 連接檢查超時時間（單位：秒） |
| connectTimeout  | int        | 建立連接逾時時間（單位：秒） |
| testOnBorrow    | boolean    | 在獲取連接時檢查連接是否有效 |
| testOnReturn    | boolean    | 在歸還連接時檢查連接是否有效 |
| retryLimit      | int        | 獲取連接的重試次數      |
| jdbcUrl         | String     | JDBC連接字串       |
| jdbcProperties  | Properties | JDBC配置資訊       |
| username        | String     | 資料庫用戶名         |
| password        | String     | 資料庫密碼          |

### 3、JMX監控
在資料庫初始化的過程中，會自動註冊JMX監控服務。在JMX的監控列表中命名空間為 “NeuronsDataSource”，type值為 “DataSource”， name值為 “Neurons”。

### 4、資料庫和連接池的關閉
程式開發人員需要顯示調用資料庫實例物件的 close 方法，完成資料庫的關閉與連接池的銷毀。在關閉連接池的過程中，資料庫會自動回滾所有正在使用中的資料庫連接。

## 貢獻與回饋
歡迎各位朋友將此文檔及專案中的提示資訊、錯誤資訊等翻譯為更多語言，以説明更多的使用者更好地瞭解與使用此工具包。   
如果在使用過程中發現問題或需要改進、添加相關功能，請提交issue到本專案或發送電子郵件到[wmkm0113\@gmail.com](mailto:wmkm0113@gmail.com?subject=bugs_and_features)   
為了更好地溝通，請在提交issue或發送電子郵件時，寫明如下資訊：   
1、目的是：發現Bug/功能改進/添加新功能   
2、請粘貼以下資訊（如果存在）：傳入資料，預期結果，錯誤堆疊資訊   
3、您認為可能是哪裡的代碼出現問題（如提供可以幫助我們儘快地找到並解決問題）   
如果您提交的是添加新功能的相關資訊，請確保需要添加的功能是一般性的通用需求，即添加的新功能可以幫助到大多數使用者。

如果您需要添加的是定制化的特殊需求，我將收取一定的定制開發費用，具體費用金額根據定制化的特殊需求的工作量進行評估。   
定制化特殊需求請直接發送電子郵件到[wmkm0113\@gmail.com](mailto:wmkm0113@gmail.com?subject=payment_features)，同時請儘量在郵件中寫明您可以負擔的開發費用預算金額。

## 贊助與鳴謝
<span id="JetBrains">
    <img src="https://resources.jetbrains.com/storage/products/company/brand/logos/jb_beam.png" width="100px" height="100px" alt="JetBrains Logo (Main) logo.">
    <span>非常感謝 <a href="https://www.jetbrains.com/">JetBrains</a> 通過許可證贊助我們的開源項目。</span>
</span>