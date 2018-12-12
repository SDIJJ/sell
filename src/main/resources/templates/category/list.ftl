<html>
<html>
<head>
    <meta charset="utf-8">
    <title>卖家商品</title>
    <link href="https://cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css" rel="stylesheet">
    <link ref="stylesheet" href="/static/css/style.css">
<body>
<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <table class="table table-bordered">
                <thead>
                <tr>
                   <th>类目id</th>
                   <th>名字</th>
                   <th>type</th>
                   <th>创建时间</th>
                   <th>修改时间</th>
                   <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <#list productCategoryList as category>
                <tr class="info">
                    <td>${category.getCategoryId()}</td>
                    <td>${category.getCategoryName()}</td>
                    <td>${category.getCategoryType()}</td>
                    <td>${category.getCreateTime()}</td>
                    <td>${category.getUpdateTime()}</td>
                    <td>
                        <a href="/sell/seller/category/index?categoryId=${category.getCategoryId()}">修改</a>
                    </td>
                </tr>
                </#list>
                </tbody>
            </table>
        </div>
    </div>
</div>

</body>
</html>