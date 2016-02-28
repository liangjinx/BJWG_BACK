<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="common" uri="/WEB-INF/ct-common.tld"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<common:WebRoot/>">

		<title>查看用户</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
	</head>

	<body>
		<!-- head -->
		<form action="<common:WebRoot/>/v/user/submit" method="post"
			enctype="multipart/form-data">

			<input type="hidden" name="typeAU" id="typeAU" value="${type}" />
			<input type="hidden" name="userId" id="userId" value="${user.userId}" />
			<div class="form-group">
				<div class="label">
					<label for="logo">
						用户头像
					</label>
				</div>
				<div class="field">
					<c:if test="${user.headImg != ''}">
						<img alt="" src="${domain}${user.headImg}${photoSize}"
							<c:if test="${photoSize==''}">
                    				width="100px;" height="80px;"
                    			</c:if> />
					</c:if>
				</div>
			</div>
			<div class="form-group">
				<div class="label">
					<label for="readme">
						用户ID
					</label>
				</div>
				<div class="field">
					<label>
						${user.userId}
					</label>
				</div>
			</div>
			<div class="form-group">
				<div class="label">
					<label for="readme">
						用户名称
					</label>
				</div>
				<div class="field">
					<label>
						${user.username}
					</label>
				</div>
			</div>
			<div class="form-group">
				<div class="label">
					<label for="readme">
						手机号码
					</label>
				</div>
				<div class="field">
					<label>
						${user.phone}
					</label>
				</div>
			</div>
			<div class="form-group">
				<div class="label">
					<label for="readme">
						注册时间
					</label>
				</div>
				<div class="field">
					<label>
						<fmt:formatDate value='${user.registerTime }'
							pattern='yyyy-MM-dd HH:mm' />
					</label>
				</div>
			</div>
			<div class="form-group">
				<div class="label">
					<label for="readme">
						最后登入时间
					</label>
				</div>
				<div class="field">
					<label>
						<fmt:formatDate value='${user.lastLoginTime }'
							pattern='yyyy-MM-dd HH:mm:ss' />
					</label>
				</div>
			</div>
			<div class="form-group">
				<div class="label">
					<label for="readme">
						关联店铺
					</label>
				</div>
				<div class="field">
					<label>
						${user.shopName}
					</label>
				</div>
			</div>
			<div class="form-group">
				<div class="label">
					<label for="readme">
						收藏数量
					</label>
				</div>
				<div class="field">
					<label>
						${user.favorites}
					</label>
				</div>
			</div>
			<div class="form-group">
				<div class="label">
					<label for="readme">
						拨打数量
					</label>
				</div>
				<div class="field">
					<label>
						${user.calls}
					</label>
				</div>
			</div>
		</form>
	</body>
</html>
