<html>
<head>
    <meta charset="utf-8">
    <title></title>
    <link href="https://cdn.bootcss.com/bootstrap/3.0.1/css/bootstrap.min.css" rel="stylesheet">

</head>
<body>

<div class="container">
    <div class="row clearfix">
        <div class="col-md-4 column">
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th>
                        订单id
                    </th>
                    <th>
                        订单总金额
                    </th>

                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>${orderDto.getOrderId()}</td>
                    <td>${orderDto.getOrderAmount()}</td>
                </tr>

                </tbody>
            </table>
        </div>
        <#--订单详情表-->
        <div class="col-md-12 column">
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th>商品id</th>
                    <th>商品名称</th>
                    <th>价格</th>
                    <th>数量</th>
                    <th>总额</th>
                </tr>
                </thead>
                <tbody>
                <#list orderDto.getOrderDetailList() as orderDetail>
                <tr>
                    <td>${orderDetail.getOrderId()}</td>
                    <td>${orderDetail.getProductName()}</td>
                    <td>${orderDetail.getProductPrice()}</td>
                    <td>${orderDetail.getProductQuantity()}</td>
                    <td>${orderDetail.getProductQuantity()*orderDetail.getProductPrice()}</td>
                    <td></td>

                </tr>
                </#list>
                </tbody>
            </table>
        </div>
        <#--按钮-->

            <div class="col-md-12 column">
                <#if orderDto.getOrderStatusEnum().getMsg()=="新订单">
                <a href="/sell/seller/order/finish?orderId=${orderDto.getOrderId()}" type="button" class="btn btn-default btn-primary">完成订单</a>
                <a href="/sell/seller/order/cancel?orderId=${orderDto.getOrderId()}" type="button" class="btn btn-default btn-danger">取消订单</a>
                </#if>
            </div>

    </div>
</div>
</body>
</html>