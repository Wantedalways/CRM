<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getScheme() + "://" +
            request.getServerName() + ":" + request.getServerPort() +
            request.getContextPath() + "/";
%>
<html>
<head>
    <base href="<%=basePath%>"/>
    <link href="static/jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet"/>
    <link href="static/jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css"
          rel="stylesheet"/>
    <link rel="stylesheet" type="text/css" href="static/jquery/bs_pagination/jquery.bs_pagination.min.css">

    <script type="text/javascript" src="static/jquery/jquery-1.11.1-min.js"></script>
    <script type="text/javascript" src="static/jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
    <script type="text/javascript"
            src="static/jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
    <script type="text/javascript"
            src="static/jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>
    <script type="text/javascript" src="static/jquery/bs_pagination/jquery.bs_pagination.min.js"></script>
    <script type="text/javascript" src="static/jquery/bs_pagination/en.js"></script>

    <script type="text/javascript">

        $(function () {

            // 时间模块
            $.fn.datetimepicker.dates['zh-CN'] = {
                days: ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"],
                daysShort: ["周日", "周一", "周二", "周三", "周四", "周五", "周六", "周日"],
                daysMin:  ["日", "一", "二", "三", "四", "五", "六", "日"],
                months: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
                monthsShort: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
                today: "今天",
                suffix: [],
                meridiem: ["上午", "下午"]
            };

            $(".time").datetimepicker({
                minView: "month",
                language: 'zh-CN',
                format: 'yyyy-mm-dd',
                autoclose: true,
                todayBtn: true,
                pickerPosition: "bottom-left"
            });

            // 展现添加市场活动的模态窗口
            $("#addCreateModal").click(function () {

                // 所有者下拉列表
                $.ajax({
                    url: "activity/getUserList.do",
                    type: "get",
                    dataType: "json",
                    success: function (data) {

                        let html = "<option></option>";

                        $.each(data, function (i, n) {

                            html += "<option value='" + n.id + "'>" + n.name + "</option>";
                        })

                        $("#create-marketActivityOwner").html(html);

                        $("#create-marketActivityOwner").val("${user.id}");
                    }
                });

                // 打开模态窗口
                $("#createActivityModal").modal("show");

            });

            // 添加市场活动
            $("#saveCreateModal").click(function () {

                $.ajax({
                    url : "activity/save.do",
                    data : {
                        "owner" : $.trim($("#create-marketActivityOwner").val()),
                        "name" : $.trim($("#create-marketActivityName").val()),
                        "startDate" : $.trim($("#create-startTime").val()),
                        "endDate" : $.trim($("#create-endTime").val()),
                        "cost" : $.trim($("#create-cost").val()),
                        "description" : $.trim($("#create-describe").val())
                    },
                    type : "post",
                    dataType : "json",
                    success : function (data) {

                        if (data.success) {
                            // 刷新市场活动列表
                            activityList(1,5);
                            // 关闭模态窗口
                            $("#createActivityModal").modal("hide");
                            // 清空填写项
                            $("#addActivityForm")[0].reset();
                        } else {
                            alert("添加失败！");
                        }
                    }
                })
            })

            // 展示市场活动列表
            activityList(1,5);

            // 市场活动查询按钮
            $("#search-Btn").click(function () {

                // 将查询条件保存到隐藏域
                $("#hide-name").val($.trim($("#search-activityName").val()));
                $("#hide-owner").val($.trim($("#search-activityOwner").val()));
                $("#hide-startDate").val($.trim($("#search-activityStartDate").val()));
                $("#hide-endDate").val($.trim($("#search-activityEndDate").val()));

                activityList(1,10);

            });

            // 复选框的全选
            $("#select-all").click(function () {

                $(".select-single").prop("checked",this.checked);

            })

            $("#activityListTbody").on("click",$(".select-single"),function () {

                $("#select-all").prop("checked",$(".select-single:checked").length === $(".select-single").length);

            })

            // 删除市场活动
            $("#del-btn").click(function () {

                // 找到列表中选中的市场活动
                var $checked = $(".select-single:checked");

                if ($checked === 0) {

                    alert("请选择需要删除的记录！");

                } else {

                    if (confirm("确认删除选中的内容？")) {

                        // 拼接参数
                        var param = "";

                        for (var i = 0;i < $checked.length; i ++) {

                            param += "id=" + $checked[i].value;

                            if (i < $checked.length - 1) {

                                param += "&";
                            }
                        }

                        $.ajax({

                            url : "activity/delete.do",
                            data : param,
                            type : "post",
                            dataType : "json",
                            success : function (data) {

                                if (data.success) {

                                    // 删除成功后，回到首页，并维持
                                    activityList(1,$("#activityPage").bs_pagination('getOption', 'rowsPerPage'));

                                } else {

                                    alert(data.msg + "删除市场活动失败！");

                                }
                            }
                        })
                    }

                }
            })
            $("#addEditModal").click(function () {

                var $checked = $(".select-single:checked");

                if ($checked.length === 0) {

                    alert("请选择一项市场活动！");

                } else if ($checked.length > 1) {

                    alert("仅支持单项修改！");

                } else {

                    $.ajax({

                        url : "activity/getUserList.do",
                        type : "get",
                        dataType : "json",
                        success : function (data) {

                            let html = "<option></option>";

                            $.each(data,function (i,n) {

                                html += "<option value='" + n.id + "'>" + n.name + "</option>";

                            })

                            $("#edit-marketActivityOwner").html(html);

                            $.ajax({

                                url : "activity/getActivityById.do",
                                data : {

                                    "id" : $checked.val()

                                },
                                dataType : "json",
                                type : "post",
                                success : function (data) {

                                    $("#hide-id").val(data.id);
                                    // 选中value值为data.owner的option标签
                                    $("#edit-marketActivityOwner").val(data.owner);
                                    $("#edit-marketActivityName").val(data.name);
                                    $("#edit-startTime").val(data.startDate);
                                    $("#edit-endTime").val(data.endDate);
                                    $("#edit-cost").val(data.cost);
                                    $("#edit-describe").val(data.description);

                                }
                            })

                        }
                    })

                    $("#editActivityModal").modal("show");
                }
            })
            // 修改市场活动
            $("#edit-btn").click(function () {

                $.ajax({

                    url : "activity/update.do",
                    data : {

                        "id" : $("#hide-id").val(),
                        "name" : $("#edit-marketActivityName").val(),
                        "owner" : $("#edit-marketActivityOwner").val(),
                        "startDate" : $("#edit-startTime").val(),
                        "endDate" : $("#edit-endTime").val(),
                        "cost" : $("#edit-cost").val(),
                        "description" : $("#edit-describe").val()
                    },
                    dataType : "json",
                    type : "post",
                    success : function (data) {

                        if (data.success) {

                            $("#editActivityModal").modal("hide");

                            activityList(1,5)

                        } else {

                            alert(data.msg);

                        }
                    }
                })
                // 修改之后停留在当前页，并维持用户设置每页条数
                activityList($("#activityPage").bs_pagination('getOption', 'currentPage'),
                    $("#activityPage").bs_pagination('getOption', 'rowsPerPage'))
            })
        });

        // 刷新市场活动的方法
        activityList = function (pageNo,pageSize) {

            /*
            $("#activityPage").bs_pagination('getOption', 'currentPage') 表示停留在当前页
			$("#activityPage").bs_pagination('getOption', 'rowsPerPage') 表示维持修改后的每页条数
             */

            // 全选按钮恢复
            $(".select-all").prop("checked",false);

            // 查询前将隐藏域中的条件信息提取，赋予搜索框
            $("#search-activityName").val($.trim($("#hide-name").val()));
            $("#search-activityOwner").val($.trim($("#hide-owner").val()));
            $("#search-activityStartDate").val($.trim($("#hide-startDate").val()));
            $("#search-activityEndDate").val($.trim($("#hide-endDate").val()));


            $.ajax({

                url : "activity/pageList.do",
                data : {

                    "pageNo" : pageNo,
                    "pageSize" : pageSize,
                    "name" : $.trim($("#search-activityName").val()),
                    "createBy" : $.trim($("#search-activityOwner").val()),
                    "startDate" : $.trim($("#search-activityStartDate").val()),
                    "endDate" : $.trim($("#search-activityEndDate").val())
                },
                type : "get",
                dataType : "json",
                success : function (data) {

                    var html = "";

                    $.each(data.dataList,function (i,n) {

                        html += '<tr class="active">';
                        html += '<td><input type="checkbox" value="'+n.id+'" class="select-single"/></td>';
                        html += '<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href=\'activity/detail.do?id='+ n.id +'\';">'+n.name+'</a></td>';
                        html += '<td>'+n.owner+'</td>';
                        html += '<td>'+n.startDate+'</td>';
                        html += '<td>'+n.endDate+'</td>';
                        html += '</tr>';

                    })

                    $("#activityListTbody").html(html);
                    $("#totalCount").html(data.total);

                    // 计算总页数
                    var totalPages = (data.total % pageSize === 0) ? (data.total / pageSize) : Math.ceil(data.total / pageSize);

                    // 使用分页插件
                    $("#activityPage").bs_pagination({
                        currentPage: pageNo, // 页码
                        rowsPerPage: pageSize, // 每页显示的记录条数
                        maxRowsPerPage: 20, // 每页最多显示的记录条数
                        totalPages: totalPages, // 总页数
                        totalRows: data.total, // 总记录条数

                        visiblePageLinks: 3, // 显示几个卡片

                        showGoToPage: true,
                        showRowsPerPage: true,
                        showRowsInfo: true,
                        showRowsDefaultInfo: true,

                        // 点击分页组件时触发
                        onChangePage : function(event, data){
                            activityList(data.currentPage , data.rowsPerPage);
                        }
                    });
                }
            })
        }

    </script>
    <title></title>
</head>
<body>

<!--保存查询条件的隐藏域-->
<input type="hidden" id="hide-name">
<input type="hidden" id="hide-owner">
<input type="hidden" id="hide-startDate">
<input type="hidden" id="hide-endDate">

<!-- 创建市场活动的模态窗口 -->
<div class="modal fade" id="createActivityModal" role="dialog">
    <div class="modal-dialog" role="document" style="width: 85%;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">×</span>
                </button>
                <h4 class="modal-title" id="myModalLabel1">创建市场活动</h4>
            </div>
            <div class="modal-body">

                <form class="form-horizontal" role="form" id="addActivityForm">

                    <div class="form-group">
                        <label for="create-marketActivityOwner" class="col-sm-2 control-label">所有者<span
                                style="font-size: 15px; color: red;">*</span></label>
                        <div class="col-sm-10" style="width: 300px;">
                            <select class="form-control" id="create-marketActivityOwner"></select>
                        </div>
                        <label for="create-marketActivityName" class="col-sm-2 control-label">名称<span
                                style="font-size: 15px; color: red;">*</span></label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" id="create-marketActivityName">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="create-startTime" class="col-sm-2 control-label">开始日期</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control time" id="create-startTime">
                        </div>
                        <label for="create-endTime" class="col-sm-2 control-label">结束日期</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control time" id="create-endTime">
                        </div>
                    </div>
                    <div class="form-group">

                        <label for="create-cost" class="col-sm-2 control-label">成本</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" id="create-cost">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="create-describe" class="col-sm-2 control-label">描述</label>
                        <div class="col-sm-10" style="width: 81%;">
                            <textarea class="form-control" rows="3" id="create-describe"></textarea>
                        </div>
                    </div>

                </form>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="saveCreateModal">保存</button>
            </div>
        </div>
    </div>
</div>

<!-- 修改市场活动的模态窗口 -->
<div class="modal fade" id="editActivityModal" role="dialog">
    <div class="modal-dialog" role="document" style="width: 85%;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">×</span>
                </button>
                <h4 class="modal-title" id="myModalLabel2">修改市场活动</h4>
            </div>
            <div class="modal-body">

                <form class="form-horizontal" role="form">

                    <input type="hidden" id="hide-id" />

                    <div class="form-group">
                        <label for="edit-marketActivityOwner" class="col-sm-2 control-label">所有者<span
                                style="font-size: 15px; color: red;">*</span></label>
                        <div class="col-sm-10" style="width: 300px;">
                            <select class="form-control" id="edit-marketActivityOwner"></select>
                        </div>
                        <label for="edit-marketActivityName" class="col-sm-2 control-label">名称<span
                                style="font-size: 15px; color: red;">*</span></label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" id="edit-marketActivityName">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="edit-startTime" class="col-sm-2 control-label">开始日期</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control time" id="edit-startTime">
                        </div>
                        <label for="edit-endTime" class="col-sm-2 control-label">结束日期</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control time" id="edit-endTime">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="edit-cost" class="col-sm-2 control-label">成本</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" id="edit-cost">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="edit-describe" class="col-sm-2 control-label">描述</label>
                        <div class="col-sm-10" style="width: 81%;">
                            <textarea class="form-control" rows="3" id="edit-describe"></textarea>
                        </div>
                    </div>

                </form>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="edit-btn">更新</button>
            </div>
        </div>
    </div>
</div>


<div>
    <div style="position: relative; left: 10px; top: -10px;">
        <div class="page-header">
            <h3>市场活动列表</h3>
        </div>
    </div>
</div>
<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
    <div style="width: 100%; position: absolute;top: 5px; left: 10px;">
        <!--条件查询市场活动-->
        <div class="btn-toolbar" role="toolbar" style="height: 80px;">
            <form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">

                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">名称</div>
                        <input class="form-control" type="text" id="search-activityName">
                    </div>
                </div>

                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">所有者</div>
                        <input class="form-control" type="text" id="search-activityOwner">
                    </div>
                </div>


                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">开始日期</div>
                        <input class="form-control time" type="text" id="search-activityStartDate"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">结束日期</div>
                        <input class="form-control time" type="text" id="search-activityEndDate">
                    </div>
                </div>

                <button type="button" class="btn btn-default" id="search-Btn">查询</button>

            </form>
        </div>
        <div class="btn-toolbar" role="toolbar"
             style="background-color: #F7F7F7; height: 50px; position: relative;top: 5px;">
            <div class="btn-group" style="position: relative; top: 18%;">
                <button type="button" class="btn btn-primary" id="addCreateModal"><span
                        class="glyphicon glyphicon-plus"></span> 创建
                </button>
                <button type="button" class="btn btn-default" id="addEditModal"><span
                        class="glyphicon glyphicon-pencil"></span> 修改
                </button>
                <button type="button" class="btn btn-danger" id="del-btn"><span class="glyphicon glyphicon-minus"></span> 删除</button>
            </div>

        </div>
        <div style="position: relative;top: 10px;">
            <table class="table table-hover">
                <thead>
                <tr style="color: #B3B3B3;">
                    <td><input type="checkbox" id="select-all"/></td>
                    <td>名称</td>
                    <td>所有者</td>
                    <td>开始日期</td>
                    <td>结束日期</td>
                </tr>
                </thead>
                <tbody id="activityListTbody"></tbody>
            </table>
        </div>

        <div style="height: 50px; position: relative;top: 30px;">
            <div id="activityPage"></div>
        </div>

    </div>

</div>
</body>
</html>