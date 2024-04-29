using UIConfigGenerator.Config;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Routing;

namespace UIConfigGenerator.Parser
{
    internal static class UIComponentFactory
    {
        public static List<UIComponent> CretateComponents(Type controller)
        {
            string[] baseUrls = GetControllerUrls(controller);
            var componentDisplayName = UIFieldFactory.GetDisplayName(controller, controller.Name);
            var components = new List<UIComponent>();
            foreach (var _ in baseUrls)
            {
                components.Add(new UIComponent() { Name = componentDisplayName, Endpoints = [] });
            }
            foreach (var method in controller.GetMethods())
            {
                var httpAttributes = Attribute.GetCustomAttributes(method, typeof(HttpMethodAttribute)).Cast<HttpMethodAttribute>().ToArray();
                var routeAttributes = Attribute.GetCustomAttributes(method, typeof(RouteAttribute)).Cast<RouteAttribute>().ToArray();
                if (httpAttributes.Length == 0 && routeAttributes.Length == 0)
                {
                    continue;
                }
                UIRequestType requestType = UIRequestType.GET;
                UIRequestBody? requestBody = null;
                List<UIField> pathParams = [];
                List<UIField> queryParams = [];
                List<UIField> responseBody = [];
                var endpointDisplayName = UIFieldFactory.GetDisplayName(method, method.Name);
                foreach (var param in method.GetParameters())
                {
                    Attribute? bodyAttr = Attribute.GetCustomAttribute(param, typeof(FromBodyAttribute));
                    if (bodyAttr != null)
                    {
                        List<UIField> bodyFields = GetBody(param.ParameterType);
                        string bodyName = UIFieldFactory.GetDisplayName(param, param.Name);
                        requestBody = new() { EntityName = bodyName, Fields = bodyFields };
                        continue;
                    }
                    Attribute? routeAttr = Attribute.GetCustomAttribute(param, typeof(FromRouteAttribute));
                    if (routeAttr != null)
                    {
                        FromRouteAttribute routeAttribute = routeAttr as FromRouteAttribute;
                        string displayValue = UIFieldFactory.GetDisplayName(param, param.Name);
                        string submitValue = routeAttribute.Name ?? UIFieldFactory.SanitizeCodeName(param.Name);
                        pathParams.Add(UIFieldFactory.CreateField(param.ParameterType, displayValue, submitValue));
                        continue;
                    }
                    Attribute? queryAttr = Attribute.GetCustomAttribute(param, typeof(FromQueryAttribute));
                    FromQueryAttribute? queryAttribute = queryAttr as FromQueryAttribute;
                    string displayName = UIFieldFactory.GetDisplayName(param, param.Name);
                    string submitName = queryAttribute?.Name ?? UIFieldFactory.SanitizeCodeName(param.Name);
                    queryParams.Add(UIFieldFactory.CreateField(param.ParameterType, displayName, submitName));
                    continue;
                    
                }
                if (method.ReturnType != typeof(void))
                {
                    responseBody = GetBody(method.ReturnType);
                }
                if (httpAttributes.Length == 0)
                {
                    for (int i = 0; i < baseUrls.Length; i++)
                    {
                        var newEndpoints = CreateEndpoints(endpointDisplayName, baseUrls[i], null, routeAttributes, requestType,
                                requestBody, pathParams, queryParams, responseBody);
                        components[i].Endpoints.AddRange(newEndpoints);
                    }
                    continue;
                }
                foreach (var httpAttr in httpAttributes)
                {
                    string? methodPath = httpAttr.Template;
                    requestType = GetRequestType(httpAttr);
                    for (int i = 0; i < baseUrls.Length; i++)
                    {
                        if (routeAttributes.Length == 0)
                        {
                            components[i].Endpoints.Add(new UIEndpoint()
                            {
                                Name = endpointDisplayName,
                                Mapping = baseUrls[i] + (methodPath != null ? "/" + methodPath : ""),
                                RequestType = requestType,
                                RequestBody = requestBody,
                                PathParams = pathParams.Count == 0 ? null : pathParams,
                                QueryParams = queryParams.Count == 0 ? null : queryParams,
                                ResponseBody = responseBody.Count == 0 ? null : responseBody,
                            });
                        }
                        else
                        {
                            var newEndpoints = CreateEndpoints(endpointDisplayName, baseUrls[i], methodPath, routeAttributes, requestType,
                                requestBody, pathParams, queryParams, responseBody);
                            components[i].Endpoints.AddRange(newEndpoints);
                        }
                        continue;
                    }
                }
            }
            return components;
        }

        private static List<UIEndpoint> CreateEndpoints(string displayName, string baseUrl, string? methodPath, RouteAttribute[] routes, UIRequestType requestType,
            UIRequestBody? requestBody, List<UIField> pathParams, List<UIField> queryParams, List<UIField> responseBody)
        {
            List<UIEndpoint> endpoints = [];
            foreach (var route in routes)
            {
                string fullUrl;
                if (route.Template.StartsWith('/'))
                {
                    fullUrl = route.Template;
                }
                else
                {
                    fullUrl = baseUrl + (methodPath != null ? "/" + methodPath : "") + "/" + route.Template;
                }
                endpoints.Add(new UIEndpoint()
                {
                    Name = displayName,
                    Mapping = fullUrl,
                    RequestType = requestType,
                    RequestBody = requestBody,
                    PathParams = pathParams.Count == 0 ? null : pathParams,
                    QueryParams = queryParams.Count == 0 ? null : queryParams,
                    ResponseBody = responseBody.Count == 0 ? null : responseBody,
                });
            }
            return endpoints;
        }

        private static UIRequestType GetRequestType(HttpMethodAttribute httpAttr)
        {
            switch (httpAttr)
            {
                case HttpPostAttribute:
                    return UIRequestType.POST;
                case HttpGetAttribute:
                    return UIRequestType.GET;
                case HttpPutAttribute:
                    return UIRequestType.PUT;
                case HttpDeleteAttribute:
                    return UIRequestType.DELETE;
                default:
                    return UIRequestType.OTHER;
            }
        }

        private static List<UIField> GetBody(Type type)
        {
            return type.GetProperties()
                .Select(property => {
                    string displayName = UIFieldFactory.GetDisplayName(property, property.Name);
                    string codeName = UIFieldFactory.SanitizeCodeName(property.Name); 
                    return UIFieldFactory.CreateField(property.PropertyType, displayName, codeName);
                })
                .ToList();
        }

        private static string[] GetControllerUrls(Type controller)
        {
            return Attribute.GetCustomAttributes(controller, typeof(RouteAttribute))
                 .Cast<RouteAttribute>()
                 .Select(route => route.Template)
                 .ToArray();
        }
    }
}
