<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@  taglib uri="/struts-tags"  prefix="s"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>load</title>
    <link href="${pageContext.request.contextPath }/css/Style.css" type="text/css" rel="stylesheet" />
   </head>
  
  <body>
    <table width="100%" border="0" id="table8">
               <s:if test="#request.pagelist!=null">
                     <s:iterator value="%{#request.pagelist}" var="slist">
                         <tr>
                            <td align="left" valign="middle"  style="word-break: break-all">
                                <span class="style1">
                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${slist.staRun }
                                </span>
                            </td>
                        </tr>
                        </s:iterator>
                </s:if>
	</table>
  </body>
</html>