using System.Reflection;
using System.Text.Json;
using System.Text.Json.Serialization;
using UIConfigGenerator.Parser;

namespace UIConfigGenerator
{
    public static class UIConfigGenerator
    {
        public const string DEFAULT_FILENAME = "ui-config.json";

        private static readonly JsonSerializerOptions JSON_OPTIONS = new()
        {
            PropertyNamingPolicy = JsonNamingPolicy.CamelCase,
            DefaultIgnoreCondition = JsonIgnoreCondition.WhenWritingNull,
            Converters = { new JsonStringEnumConverter(JsonNamingPolicy.SnakeCaseUpper) },
            WriteIndented = true,
        };

        public static void ParseAssembley(Assembly assembly, string baseUrl, string configName = DEFAULT_FILENAME)
        {
            var uiConfig = UIConfigFactory.CreateUI(assembly, baseUrl);

            var json = JsonSerializer.Serialize(uiConfig, JSON_OPTIONS);

            File.WriteAllText(Path.GetFullPath(configName), json);
        }
    }
}
