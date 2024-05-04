using System.Text.Json.Serialization;

namespace UIConfigGenerator.Config
{
    [JsonDerivedType(typeof(UIClassField))]
    [JsonDerivedType(typeof(UIListField))]
    [JsonDerivedType(typeof(UIEnumField))]
    [JsonDerivedType(typeof(UITextField))]
    [JsonDerivedType(typeof(UINumberField))]
    internal class UIField
    {
        public string? DisplayName { get; set; }
        public string? CodeName { get; set; }
        public required UIFieldType FieldType { get; set; }
        public required bool Required { get; set; }
    }
}
