# thyeme

Thymeleaf 是一种模板语言。那模板语言或模板引擎是什么？常见的模板语言都包含以下几个概念：数据（Data）、模板（Template）、模板引擎（Template Engine）和结果文档（Result Documents）。

- 数据：数据是信息的表现形式和载体，可以是符号、文字、数字、语音、图像、视频等。数据和信息是不可分离的，数据是信息的表达，信息是数据的内涵。数据本身没有意义，数据只有对实体行为产生影响时才成为信息。
- 模板：模板，是一个蓝图，即一个与类型无关的类。编译器在使用模板时，会根据模板实参对模板进行实例化，得到一个与类型相关的类。
- 模板引擎：模板引擎（这里特指用于Web开发的模板引擎）是为了使用户界面与业务数据（内容）分离而产生的，它可以生成特定格式的文档，用于网站的模板引擎就会生成一个标准的HTML文档。
- 结果文档：一种特定格式的文档，比如用于网站的模板引擎就会生成一个标准的HTML文档。

模板语言用途广泛，常见的用途如下
- 页面渲染
- 文档生成
- 代码生成
- 所有 “数据+模板=文本” 的应用场景

# 集成

## 添加依赖

```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
```

## 创建目录
* resources/static：存放css、js等资源文件
* resources/templates：存放视图模板文件

## 配置添加

```
spring:
    thymeleaf:
        cache: true  # Enable template caching.
        check-template: true # Check that the template exists before rendering it.
        check-template-location: true # Check that the templates location exists.
        enabled: true # Enable Thymeleaf view resolution for Web frameworks.
        encoding: UTF-8 # Template files encoding.
        excluded-view-names:  # Comma-separated list of view names that should be excluded from resolution.
        mode: HTML5 # Template mode to be applied to templates. See also StandardTemplateModeHandlers.
        prefix: classpath:/templates/ # Prefix that gets prepended to view names when building a URL.
        suffix: .html # Suffix that gets appended to view names when building a URL.
        template-resolver-order:  # Order of the template resolver in the chain.
        view-names:  # Comma-separated list of view names that can be resolved.
        reactive:
            max-chunk-size:  # Maximum size of data buffers used for writing to the response, in bytes.
            media-types:  # Media types supported by the view technology.
        servlet:
            content-type: text/html # Content-Type value written to HTTP responses.

```
```
spring.thymeleaf.cache=true # Enable template caching.
spring.thymeleaf.check-template=true # Check that the template exists before rendering it.
spring.thymeleaf.check-template-location=true # Check that the templates location exists.
spring.thymeleaf.enabled=true # Enable Thymeleaf view resolution for Web frameworks.
spring.thymeleaf.encoding=UTF-8 # Template files encoding.
spring.thymeleaf.excluded-view-names= # Comma-separated list of view names that should be excluded from resolution.
spring.thymeleaf.mode=HTML5 # Template mode to be applied to templates. See also StandardTemplateModeHandlers.
spring.thymeleaf.prefix=classpath:/templates/ # Prefix that gets prepended to view names when building a URL.
spring.thymeleaf.reactive.max-chunk-size= # Maximum size of data buffers used for writing to the response, in bytes.
spring.thymeleaf.reactive.media-types= # Media types supported by the view technology.
spring.thymeleaf.servlet.content-type=text/html # Content-Type value written to HTTP responses.
spring.thymeleaf.suffix=.html # Suffix that gets appended to view names when building a URL.
spring.thymeleaf.template-resolver-order= # Order of the template resolver in the chain.
spring.thymeleaf.view-names= # Comma-separated list of view names that can be resolved.
```