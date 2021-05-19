<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getScheme() + "://" +
            request.getServerName() + ":" + request.getServerPort() +
            request.getContextPath() + "/";
%>
<html>
<head>
    <base href="<%=basePath%>" />
    <link href="static/jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
    <script type="text/javascript" src="static/jquery/jquery-1.11.1-min.js"></script>
    <script type="text/javascript" src="static/jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
    <script type="text/javascript">
        $(function () {

            // 页面打开用户名清空并自动获得焦点
            $("#userName").val("");
            $("#userName").focus();
            // 登录按钮验证登录
            $("#loginBtn").click(function () {
                login();
            })
            // 捕捉回车键验证登录
            $(window).keydown(function (event) {
                if (event.key === "Enter") {
                    login();
                }
            })
        })
        // 验证登录方法
        function login() {
            // 验证账号密码不能为空(去除前后空白)
            const userName = $.trim($("#userName").val());
            const password = $.trim($("#password").val());
            if (userName === "" || password === "") {
                $("#msg").html("用户名和密码不能为空！");
                return false;
            }
            // ajax验证登录信息的合法性
            $.ajax({
                url :"user/login.do",
                data : {
                    "loginAct" : userName,
                    "loginPwd" : password
                },
                dataType :"json",
                type : "post",
                success : function (data) {
                    if (data.success) {
                        // 验证成功则跳转到初始工作界面
                        window.location.href = "workbench/index.jsp";
                    } else {
                        // 验证失败则显示错误信息
                        $("#msg").html(data.msg);
                    }
                }
            })
        }
    </script>
    <title></title>
</head>
<body>
<div style="position: absolute; top: 0px; left: 0px; width: 60%;">
    <img src="static/image/IMG_7114.JPG" style="width: 100%; height: 90%; position: relative; top: 50px;">
</div>
<div id="top" style="height: 50px; background-color: #3C3C3C; width: 100%;">
    <div style="position: absolute; top: 5px; left: 0px; font-size: 30px; font-weight: 400; color: white; font-family: 'times new roman'">CRM &nbsp;<span style="font-size: 12px;">&copy;2021&nbsp;Wantedalways</span></div>
</div>

<div style="position: absolute; top: 120px; right: 100px;width:450px;height:400px;border:1px solid #D5D5D5">
    <div style="position: absolute; top: 0px; right: 60px;">
        <div class="page-header">
            <h1>登录</h1>
        </div>
        <form action="workbench/index.html" class="form-horizontal" role="form">
            <div class="form-group form-group-lg">
                <div style="width: 350px;">
                    <input class="form-control" type="text" placeholder="用户名" id="userName">
                </div>
                <div style="width: 350px; position: relative;top: 20px;">
                    <input class="form-control" type="password" placeholder="密码" id="password">
                </div>
                <div class="checkbox"  style="position: relative;top: 30px; left: 10px;">

                    <span id="msg" style="color: #d43f3a"></span>

                </div>
                <button type="button" id="loginBtn" class="btn btn-primary btn-lg btn-block"  style="width: 350px; position: relative;top: 45px;">登录</button>
            </div>
        </form>
    </div>
</div>
</body>
</html>
