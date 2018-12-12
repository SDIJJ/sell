<html>
<head>
    <meta charset="utf-8">
    <title>卖家商品</title>
    <link href="https://cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css" rel="stylesheet">
    <link ref="stylesheet" href="/static/css/style.css">
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
                            <th>商品id</th>
                            <th>名称</th>
                            <th>图片</th>
                            <th>单价</th>
                            <th>库存</th>
                            <th>描述</th>
                            <th>类目</th>
                            <th>修改时间</th>
                            <th colspan="2">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                <#list productInfoPage.content as productInfo>
                <tr>
                    <td>${productInfo.getProductId()}</td>
                    <td>${productInfo.getProductName()}</td>
                    <td><img height="100" width="100" src=""></td>
                    <td>${productInfo.getProductPrice()}</td>
                    <td>${productInfo.getProductStock()}</td>
                    <td>${productInfo.getProductDescription()}</td>
                    <td>${productInfo.getCategoryType()}</td>
                    <td>${productInfo.getCreateTime()}</td>
                    <td>${productInfo.getUpdateTime()}</td>
                    <td>
                        <a href="/sell/seller/product/index?productId=${productInfo.getProductId()}">详情</a>
                    </td>
                    <td>
                        <#--TODO 处理上下架-->
                        <#if productInfo.getProductStatusEnum().getMessage()=="在架">
                            <a href="/sell/seller/product/offSale?productId=${productInfo.getProductId()}">下架</a>
                        <#else >
                            <a href="/sell/seller/product/onSale?productId=${productInfo.getProductId()}">在架</a>

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
                            <li><a href="/seller/product/list?page=${currentPage-1}&size=10">${index}</a></li>
                        </#if>
                        <#list 1..productInfoPage.getTotalPages() as index>
                            <#if currentPage==index>
                                    <li class="disabled"><a href="#">${index}</a></li>
                            <#else >
                                <li><a href="/seller/product/list?page=${index}&size=${size}">${index}</a></li>
                            </#if>
                        </#list>
                        <#if currentPage gte productInfoPage.getTotalPages()>
                        <li class="disabled"><a href="#">下一页</a></li>
                        <#else >
                            <li><a href="/seller/product/list?page=${currentPage+1}&size=${size}">${index}</a></li>
                        </#if>
                    </ul>
                </div>
            </div>

        </div>
    </div>
</div>





</body>
</head>
</html>