<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <title>黑名单管理</title>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" type="text/css" href="js/lib/bootstrap/bootstrap.min.css">  
</head>
<body>
    <div id=siteView class="container" style="display:none">
        <div class="row clearfix">
            <div class="col-md-12 column">
                <div style="vertical-align:bottom; font-family:宋体;color:black;font-size:24px"> 
                    <label style="font-family:宋体;color:black;font-size:24px">添加黑名单</lable>
                    <a href="javascript:;"><img id="addSiteIcon" src="image/addsite.png" width=24></a>
                </div> 
                <hr/>
                <table id="siteTab" class="table" style="font-family:å®ä½;color:black;font-size:20px;text-align:left">
                    <thead>
                        <tr>
                            <th>编号</th>
                            <th>网站地址</th>
                            <th>网站名称</th>
                            <th>网站类别</th>
                            <th>风险/影响</th>
                            <th>编辑/删除</th>
                        </tr>
                    </thead>
                    <tbody id="siteTabBody" align="left">
                        <script type='text/template' id='templateSiteRow'>
                            <tr id=site_tr_{%= trId %} style="background-color:{%=color%}">
                                <td id=siteSerial_{%= trId %}>
                                    {%= trId %}
                                    <div id=siteId_{%= trId %} style="display:none">{%= site.id %}
                                </td>
                                <td id=siteUrl_{%= trId %}>
                                    <div id=siteUrl_div_{%= trId %}>{%= site.pattern %}</div>
                                    <input id=inputUrl_{%= trId %} type="text" value="{%= site.pattern %}" style="display:none">
                                </td>
                                <td id=siteName_{%= trId %}>
                                    <div id=siteName_div_{%= trId %}>{%= site.name %}</div>
                                    <input id=inputName_{%= trId %} type="text" value="{%= site.name %}" style="display:none">
                                </td>
                                <td id=siteType_{%= trId %}>
                                    <div id=siteType_div_{%= trId %}>{%= site.type %}</div>
                                    <input id=inputType_{%= trId %} type="text" value="{%= site.type %}" style="display:none">
                                </td>
                                <td id=siteRisk_{%= trId %}>
                                    <div id=siteRisk_div_{%= trId %}>{%= site.risk %}</div>
                                    <input id=inputRisk_{%= trId %} type="text" value="{%= site.risk %}" style="display:none">
                                </td>
                                <td id=siteOper_{%= trId %}>
                                    <a href="javascript:;"><img id=siteEdit_{%= trId %} src="image/edit.png" width=24>
                                    <img id=siteEditDone_{%= trId %} src="image/editDone.png" width=24 style="display:none"></a>
                                    <a href="javascript:;"><img id=siteDelete_{%= trId %} src="image/delete.png" width=24 data-toggle="confirmation"></a>
                                </td>
                            </tr>
                        </script>
                        <script type='text/template' id='templateNewSiteRow'>
                            <tr id=site_tr_{%= trId %} style="background-color:{%=color%}">
                                <td id=siteSerial_{%= trId %}>{%= trId %}</td>
                                <td><input id=newSiteUrl_{%= trId %} type="text" placeholder="可带通配符"></td>
                                <td><input id=newSiteName_{%= trId %} type="text"></td>
                                <td>
                                    <input id=newSiteType_{%= trId %} type="text" value="{%= site.type %}">
                                </td>
                                <td><input id=newSiteRisk_{%= trId %} type="text"></td>
                                <td>
                                    <a href="javascript:;"><img id=newSiteEdit_{%= trId %} src="image/editdone.png" width=24></a>
                                    <a href="javascript:;"><img id=newSiteDelete_{%= trId %} src="image/delete.png" width=24></a>
                                </td>
                            </tr>
                        </script>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <!--
    <script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>
    <script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    -->
    <script src="js/lib/jquery-2.1.1.min.js"></script>
    <script src="js/lib/bootstrap/bootstrap-3.3.7.min.js"></script>
    <script src="js/lib/bootstrap-confirmation.js"></script>
    <script src="js/lib/underscore-1.8.3.min.js"></script>
    <script src="js/lib/backbone-1.3.3.min.js"></script>
    <script src="js/adminSite.js?ver="1.9"></script>
</body>
</html>