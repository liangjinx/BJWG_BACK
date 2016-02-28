<%@ page language="java" contentType="text/html; charset=UTF-8"  
    pageEncoding="UTF-8"%>  
    <%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">  
<html>  
<head>  

<script type="text/javascript" src="<%=basePath%>resources/js/jquery-1.7.2.js"></script>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">  
<title>Insert title here</title>  
<script type="text/javascript">  
    j = 1;  
    $(document).ready(function(){  
          
        $("#btn_add2").click(function(){  
            document.getElementById("newUpload2").innerHTML+='<div id="div_'+j+'"><input  name="file_'+j+'" type="file"  /><input type="button" value="删除"  onclick="del_2('+j+')"/></div>';  
              j = j + 1;  
        });  
    });  
  
    function del_2(o){  
         document.getElementById("newUpload2").removeChild(document.getElementById("div_"+o));  
    }  
  
</script>  
</head>  
<body>  
    <form name="userForm2" action="<%=basePath%>file/upload" enctype="multipart/form-data" method="post"">  
        <div id="newUpload2">  
            <input type="file" name="file0">  
        </div>  
        <input type="button" id="btn_add2" value="增加一行" >  
        <input type="submit" value="上传" >  
        
    </form>   
</body>  
</html>  