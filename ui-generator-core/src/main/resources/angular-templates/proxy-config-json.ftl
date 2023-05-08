<#-- @ftlvariable name="baseUrl" type="java.lang.String" -->
{
    "/api/*": {
        "target": "${baseUrl}",
        "secure": false,
        "headers": {
            "Connection": "keep-alive"
        }
    }
}