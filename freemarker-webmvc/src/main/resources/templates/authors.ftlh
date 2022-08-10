<#-- @ftlvariable name="authors" type="com.example.freemarker.webmvc.TestController.Author[]" -->
<!DOCTYPE html>
<html lang="en">
<head>
	<title>freemarker-webflux test page</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
</head>
<body>

<ul>
    <#list authors as author>
		<li>${author.name()}</li>
    </#list>
</ul>

</body>

</html>
