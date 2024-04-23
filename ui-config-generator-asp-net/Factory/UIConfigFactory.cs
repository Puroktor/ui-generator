using Microsoft.AspNetCore.Mvc;
using System.Reflection;
using UIConfigGenerator.Attributes;
using UIConfigGenerator.Config;

namespace UIConfigGenerator.Parser
{
    internal static class UIConfigFactory
    {
        public static UIConfig CreateUI(Assembly assembly, string baseUrl)
        {
            List<UIComponent> components = assembly.GetTypes()
                .Where(type => type.IsSubclassOf(typeof(ControllerBase)))
                .Where(type => Attribute.IsDefined(type, typeof(AutoGenerateUIAttribute)))
                .Select(UIComponentFactory.CretateComponents)
                .SelectMany(components => components)
                .ToList();
            return new UIConfig { Components = components, BaseUrl = baseUrl };
        }
    }
}
