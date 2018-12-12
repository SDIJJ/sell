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
            <form role="form" method="post" action="/sell/seller/product/save">
                <div class="form-group">
                    <label>名称</label>
                    <input name="productName" type="text" class="form-control"
                           value="${(productInfo.getProductName())!""}"/>
                </div>
                <div class="form-group">
                    <label>价格</label>
                    <input name="productPrice" type="text" class="form-control"
                           value="${(productInfo.getProductPrice())!""}"/>
                </div>
                <div class="form-group">
                    <label>库存</label>
                    <input name="productStock" type="number" class="form-control"
                           value="${(productInfo.getProductStock())!""}"/>
                </div>
                <div class="form-group">
                    <label>描述</label>
                    <input name="productDescription" type="text" class="form-control"
                           value="${(productInfo.getProductDescription())!""}"/>
                </div>
                <label>图片</label>
                <img width="100" height="100" src="${productInfo.getProductIcon()!""}" alt="">
                <input name="productDescription" type="text" class="form-control" value=""/>
        </div>
    </div>
    <div class="form-group">
        <label>类目</label>
        <select name="CategoryType" class="form-control">
                    <#list productCategoryList as productCategory>
                        <option value="${productCategory.getCategoryType()} "
                           <#if (productCategory.getCategoryType())?? && productInfo.getCategoryType()== productCategory.getCategoryType() >
                                selected
                           </#if>
                        >${productCategory.getCategoryName()}
                        </option>
                    </#list>
        </select>
    </div>
     <input type="hidden" value="${(productInfo.getProductId())!""}" name="productId">


</div>
<button type="submit" class="btn btn-default">提交</button>
</form>
</div>
</div>
</div>

</body>

</html>