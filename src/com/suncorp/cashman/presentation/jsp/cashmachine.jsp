<%@ taglib prefix="s" uri="/WEB-INF/lib/tld/struts-tags.tld" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<link href="<%=request.getContextPath()%>/css/cashmachine.css" rel="stylesheet" type="text/css">

<s:form action="cashmachine" name="cashmachine" theme="simple">

    <h1 class="title">Cash Machine</h1>
    <table width="250" border="0" cellpadding="0" cellspacing="2">
        <tr>
            <td width="200">Note Type</td>
            <td>Quantity</td>
        </tr>
        <s:iterator value="stock">
            <tr class="bigtext">
                <td><s:property value="noteType"/></td>
                <td><s:textfield name="stockMap['%{noteType}'].quantity" value="%{quantity}"/></td>
            </tr>
        </s:iterator>
    </table>
    <br>
    <table width="250" border="0" cellpadding="0" cellspacing="2">
        <tr>
            <td align="right">
                <s:submit cssClass="button" key="Save" method="save"/>
            </td>
        </tr>
    </table>
    <hr>
    <table width="300" border="0" cellpadding="0" cellspacing="2">
        <tr class="bigtext">
            <td>Amount: <s:textfield name="withdrawalAmount" value="%{withdrawalAmount}"/></td>
            <td><s:submit cssClass="button" key="Withdraw" method="withdraw"/></td>
        </tr>
    </table>
    <br>
    <table id="cashmachine_Withdrawal" width="250" border="0" cellpadding="0" cellspacing="2">
        <tr class="bigtext">
            <td colspan="2">Take Your Notes:<br></td>
        </tr>
        <s:iterator value="withdrawal">
            <tr class="bigtext">
                <td><s:property value="noteType"/></td>
                <td><s:property value="quantity"/></td>
            </tr>
        </s:iterator>
    </table>

    <s:property value="withdrawalErrorMessage"/></td>

</s:form>