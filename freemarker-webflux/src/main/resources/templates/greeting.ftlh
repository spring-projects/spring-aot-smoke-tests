<#-- @ftlvariable name="model" type="com.example.freemarker.webflux.TestController.IndexModel" -->
<!DOCTYPE html>
<html lang="en">
<head>
	<title>freemarker-webflux test page</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
</head>
<body>

<p>
    ${model.greeting()} ${model.name()}
</p>

</body>

</html>
