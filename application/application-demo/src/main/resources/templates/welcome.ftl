<#assign base=request.contextPath/>
<#assign items=["demo1","demo2","demo3"]/>
<html>
<head>
  <title>欢迎页</title>
</head>
<body>
<h1>Welcome to welcome.html!</h1>
<h2>Welcome ${welcomeVO.userName}</h2>
<div>
  <a href="${base}/${welcomeVO.urlName}">${welcomeVO.urlDescription}</a>
</div>
<div>
  <#list items as item>
      <#switch item>
        <#case 'demo3'>
          test1 ---- xx1
            <#break />
        <#case 'demo2'>
          test2 ---- xx2
            <#break />
        <#case 'demo1'>
          test3 ---- xx3
            <#break />
          <#default >
          default
      </#switch>
  </#list>
</div>
<div>
  today ${today?string('yyyy年MM月dd日 HH:mm:ss')}
</div>
<div>
  <#include 'header.ftl'/>
</div>
<div>
testStr1: ${testStr1!"testStr默认值"}<br>
<#if testStr1??>
    testStr1 存在
  <#else >
    testStr1 不存在
</#if><br>
testStr2: ${testStr2}<br>
<#if testStr2??>
  testStr2 存在
<#else >
  testStr2 不存在
</#if>
</div>
</body>
</html>