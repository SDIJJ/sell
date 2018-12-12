<html>
<head>
    <meta charset="utf-8">
    <title>卖家商品</title>
    <link href="https://cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css" rel="stylesheet">
    <link ref="stylesheet" href="/static/css/style.css">
</head>
<body>
<div id="wrapper" class="toggled">
<#--边栏sidebar-->
<#--<#include "../common/nav.ftl">-->

<#--主要内容区域-->
    <div id="page-content-wrapper">
        <div class="container-fluid">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <table class="table table-striped table-condensed">
                        <thead>
                        <tr>
                            <th>订单id</th>
                            <th>姓名</th>
                            <th>手机号</th>
                            <th>地址</th>
                            <th>金额</th>
                            <th>订单状态</th>
                            <th>支付状态</th>
                            <th>创建时间</th>
                            <th colspan="2">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                <#list orderDtoPage.content as orderDto>
                <tr>
                    <td>${orderDto.orderId}</td>
                    <td>${orderDto.getBuyerName()}</td>
                    <td>${orderDto.getBuyerPhone()}</td>
                    <td>${orderDto.getBuyerAddress()}</td>
                    <td>${orderDto.getOrderAmount()}</td>
                    <td>${orderDto.getOrderStatusEnum().getMsg()}</td>
                    <td>${orderDto.getPayStatusEnum().getMsg()}</td>
                    <td>${orderDto.getCreateTime()}</td>
                    <td>
                        <a href="/sell/seller/order/detail?orderId=${orderDto.getOrderId()}">详情</a>
                    </td>
                    <td>
                        <#if orderDto.getOrderStatusEnum().getMsg()=="新订单">
                            <a href="/sell/seller/order/cancel?orderId=${orderDto.getOrderId()}">取消</a>
                        </#if>
                    </td>

                </tr>
                </#list>
                        </tbody>
                    </table>
                </div>
            <#--分页-->
                <div class="col-md-12 column">
                    <ul class="pagination pull-right">
        <#if currentPage lte 1>
        <li class="disabled"><a href="#">上一页</a></li>
        <#else >
            <li><a href="/seller/order/list?page=${currentPage-1}&size=10">${index}</a></li>
        </#if>
        <#list 1..orderDtoPage.getTotalPages() as index>
            <#if currentPage==index>
                    <li class="disabled"><a href="#">${index}</a></li>
            <#else >
                <li><a href="/seller/order/list?page=${index}&size=${size}">${index}</a></li>
            </#if>
        </#list>
        <#if currentPage gte orderDtoPage.getTotalPages()>
        <li class="disabled"><a href="#">下一页</a></li>
        <#else >
            <li><a href="/seller/order/list?page=${currentPage+1}&size=${size}">${index}</a></li>
        </#if>
                    </ul>
                </div>
            </div>

        </div>
    </div>
</div>

<#--弹窗-->

<a id="modal-321726" href="#modal-container-321726" role="button" class="btn" data-toggle="modal">触发遮罩窗体</a>

<div class="modal fade" id="myModel" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title" id="myModalLabel">
                    提醒
                </h4>
            </div>
            <div class="modal-body">
                你有新的订单
            </div>
            <div class="modal-footer">
                <button type="button" onclick="javascript:document.getElementById('notice').onpause"
                        class="btn btn-default" data-dismiss="modal">关闭
                </button>
                <button onclick="locaton.reload()" type="button" class="btn btn-primary">查看新订单</button>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
<script src="https://cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<#--播放音乐-->
<audio id="notice" loop="loop">
    <source src="/sell/map3/song.map3" type="audio/mpeg"/>
</audio>

<script>
    var webSocket = null;
    if ('WebSocket' in window) {
        webSocket = new WebSocket('ws://jhn.natapp1.cc/sell/webSocket');
    } else {
        alert('该浏览器不支持websocket!')
    }

    webSocket.onopen = function (event) {
        console.log('建立连接')
    }
    webSocket.onclose = function (even) {
        console.log('链接关闭')
    }
    webSocket.onmessage = function (event) {
        console.log('收到消息' + event.data)
        //弹窗提醒,播放音乐
        $("#myModel").showModal()
        document.getElementById('notice').onplay
    }
    webSocket.onerror = function (event) {
        alert('webSocket通信发生错误!')
    }
    window.onbeforeunload = function (event) {
        webSocket.onclose;
    }

</script>


</body>

</html>