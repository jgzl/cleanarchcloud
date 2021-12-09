<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
    <title>MicroservicePlatform微服务统一认证</title>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js" integrity="sha512-894YE6QWD5I59HgZOGReFYm4dnWc1Qt5NtvYSaNcOP+u1T9qYdvdihz0PPSiiqn/+/3e7Jo4EaG7TubfWGUrMQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
    <link href="/css/bootstrap.min.css" rel="stylesheet">
    <link href="/css/signin.css" rel="stylesheet">
</head>
<body class="sign_body">
<div class="container form-margin-top">
    <form class="form-signin" action="/token/form" method="post">
        <h2 class="form-signin-heading" align="center">统一认证系统</h2>
        <#if tenantList??>
            <select class="form-control form-margin-top" placeholder="所属租户" name="TENANT-CODE">
                <#list tenantList as tenant>
                    <option value="${tenant.code}">${tenant.name}</option>
                </#list>
            </select>
        </#if>
        <input type="text" name="username" class="form-control form-margin-top" placeholder="账号" required autofocus>
        <input type="password" name="password" class="form-control" placeholder="密码" required>
        <button class="btn btn-lg btn-primary btn-block" type="submit" onclick="setTenant">登录</button>
        <#if error??>
            <span style="color: red; ">${error}</span>
        </#if>
    </form>
</div>
<footer>
    <p>support by: li7hai26</p>
    <p>email: <a href="mailto:li7hai26@qq.com">li7hai26@qq.com</a>.</p>
</footer>
</body>
<script type="text/javascript">
    function init() {
        var result = $.ajax({ url: "/third/oauth", dataType: "json", success: function(){
            console.log("成功发送请求/third/oauth")
        }});
        var resultJson = JSON.parse(result)
        if (resultJson[data] != null) {
            for (var source of resultJson[data]) {
                $("#thirdLogin").innerHTML=$("#thirdLogin").innerHTML+"|&nbsp<a href='/third/oauth/login/"+type+"'>"+type+"登录</a>&nbsp"
            }
        }
        console.log("result:"+JSON.parse(result))
    }
    init()
</script>
</html>
