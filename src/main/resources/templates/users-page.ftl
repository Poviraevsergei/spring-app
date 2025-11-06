<#import "macro/user-info-macros.ftl" as ui>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>All users</title>
</head>
<body>
All users:
<#list usersKey as user>
    <@ui.userMacros user/>
    <br>
</#list>
</body>
</html>