<#assign base=request.contextPath/>
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
</body>
</html>