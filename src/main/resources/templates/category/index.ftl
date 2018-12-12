<html>
<html>
<head>
    <meta charset="utf-8">
    <title>卖家类目</title>
    <link href="https://cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css" rel="stylesheet">
    <link ref="stylesheet" href="/static/css/style.css">
<body>
<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <form role="form" method="post" action="/sell/seller/category/save">
                <div class="form-group">
                    <label>名称</label>
                    <input name="categoryName" type="text" class="form-control"
                           value="${(productCategory.getCategoryName())!""}"/>
                </div>
                <div class="form-group">
                    <label>Type</label>
                    <input name="categoryType" type="text" class="form-control"
                           value="${(productCategory.getCategoryType())!""}"/>
                </div>

    <input type="hidden" value="${(productCategory.getCategoryId())!""}" name="categoryId">


</div>
<button type="submit" class="btn btn-default">提交</button>
</form>
</div>
</div>
</div>

</body>

</html>